package br.com.estevaohcs.internaltaskmanager.dtos;

import br.com.estevaohcs.internaltaskmanager.entities.Task;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class TaskResponseDTO extends ActivityResponseDTO {

    private UUID usuarioId;

    public TaskResponseDTO(Task task) {
        super(task);
        this.usuarioId = task.getUsuarioId();
    }

}
