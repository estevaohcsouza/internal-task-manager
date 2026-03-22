package br.com.estevaohcs.internaltaskmanager.entities;

import br.com.estevaohcs.internaltaskmanager.dtos.TaskRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tb_task")
@Getter
@Setter
@NoArgsConstructor
public class Task extends Activity {

    @Column(nullable = false)
    private UUID usuarioId;

    public Task(TaskRequestDTO taskRequestDTO) {
        super(taskRequestDTO);
        this.usuarioId = taskRequestDTO.getUsuarioId();
    }

}
