package br.com.estevaohcs.internaltaskmanager.services;

import br.com.estevaohcs.internaltaskmanager.dtos.TaskDTO;
import br.com.estevaohcs.internaltaskmanager.entities.Task;
import br.com.estevaohcs.internaltaskmanager.entities.enums.Status;
import br.com.estevaohcs.internaltaskmanager.repositories.SubtaskRepository;
import br.com.estevaohcs.internaltaskmanager.repositories.TaskRepository;
import br.com.estevaohcs.internaltaskmanager.repositories.UserRepository;
import br.com.estevaohcs.internaltaskmanager.services.exceptions.ResourceNotFoundException;
import br.com.estevaohcs.internaltaskmanager.services.exceptions.StatusDependencyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private SubtaskRepository subtaskRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional(readOnly = true)
    public Page<TaskDTO> findAll(Pageable pageable, Status status) {
        Page<Task> tasks;

        if (status != null) {
            tasks = taskRepository.findByStatus(pageable, status);
        } else {
            tasks = taskRepository.findAll(pageable);
        }

        return tasks.map(TaskDTO::new);
    }

    @Transactional
    public TaskDTO insert(TaskDTO dto) {
        userRepository.findById(dto.getUsuarioId()).orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));

        Task task = new Task(dto);
        task = taskRepository.save(task);
        return new TaskDTO(task);
    }

    @Transactional
    public TaskDTO updateStatus(UUID taskId, Status status) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada"));

        if (task.getStatus().equals(Status.CONCLUIDA)) {
            throw new StatusDependencyException("Não é possível alterar o status de uma tarefa concluida!");
        }

        if (status.equals(Status.CONCLUIDA)) {
            verifySubtasksStatus(taskId, task);
        } else {
            task.setStatus(status);
        }

        task = taskRepository.save(task);
        return new TaskDTO(task);
    }

    private void verifySubtasksStatus(UUID taskId, Task task) {
        if (subtaskRepository.findByTarefaId(taskId)
                .stream()
                .allMatch(s -> s.getStatus().equals(Status.CONCLUIDA))) {
            task.setStatus(Status.CONCLUIDA);
            task.setDataConclusao(Instant.now());
        } else {
            throw new StatusDependencyException("Não é possível concluir a tarefa: existem subtarefas pendentes!");
        }
    }

}
