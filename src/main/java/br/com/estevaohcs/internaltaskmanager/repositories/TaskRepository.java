package br.com.estevaohcs.internaltaskmanager.repositories;

import br.com.estevaohcs.internaltaskmanager.entities.Task;
import br.com.estevaohcs.internaltaskmanager.entities.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    Page<Task> findByStatus(Pageable pageable, Status status);

}
