package br.com.estevaohcs.internaltaskmanager.dtos;

import br.com.estevaohcs.internaltaskmanager.entities.Task;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class TaskDTO extends ActivityDTO {

    @NotNull(message = "O campo usuarioId é obrigatório!")
    private UUID usuarioId;

    public TaskDTO(Task task) {
        super(task);
        this.usuarioId = task.getUsuarioId();
    }

}
