package br.com.estevaohcs.internaltaskmanager.repositories;

import br.com.estevaohcs.internaltaskmanager.entities.Subtask;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SubtaskRepository extends JpaRepository<Subtask, UUID> {

    Page<Subtask> findByTarefaId(Pageable pageable, UUID taskId);

    List<Subtask> findByTarefaId(UUID taskId);

}
