package br.com.estevaohcs.internaltaskmanager.services;

import br.com.estevaohcs.internaltaskmanager.dtos.SubtaskRequestDTO;
import br.com.estevaohcs.internaltaskmanager.dtos.SubtaskResponseDTO;
import br.com.estevaohcs.internaltaskmanager.entities.Subtask;
import br.com.estevaohcs.internaltaskmanager.entities.Task;
import br.com.estevaohcs.internaltaskmanager.entities.enums.Status;
import br.com.estevaohcs.internaltaskmanager.repositories.SubtaskRepository;
import br.com.estevaohcs.internaltaskmanager.repositories.TaskRepository;
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
public class SubtaskService {

    @Autowired
    private SubtaskRepository subtaskRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Transactional(readOnly = true)
    public Page<SubtaskResponseDTO> findByTaskId(Pageable pageable, UUID taskId) {
        taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada!"));

        Page<Subtask> subtasks = subtaskRepository.findByTarefaId(pageable, taskId);
        return subtasks.map(SubtaskResponseDTO::new);
    }

    @Transactional
    public SubtaskResponseDTO insert(UUID taskId, SubtaskRequestDTO subtaskRequestDTO) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada!"));

        if (task.getStatus().equals(Status.CONCLUIDA)) {
            throw new StatusDependencyException("Não é possível adicionar uma nova subtarefa para uma tarefa concluída!");
        }

        Subtask subtask = new Subtask(subtaskRequestDTO);
        subtask.setTarefaId(taskId);
        subtask = subtaskRepository.save(subtask);
        return new SubtaskResponseDTO(subtask);
    }

    @Transactional
    public SubtaskResponseDTO updateStatus(UUID id, Status status) {
        Subtask subtask = subtaskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Subtarefa não encontrada!"));

        if (subtask.getStatus().equals(Status.CONCLUIDA)) {
            throw new StatusDependencyException("Não é possível alterar o status de uma subtarefa concluida!");
        }

        if (status.equals(Status.CONCLUIDA)) {
            subtask.setDataConclusao(Instant.now());
        }

        subtask.setStatus(status);
        subtask = subtaskRepository.save(subtask);
        return new SubtaskResponseDTO(subtask);
    }

}
