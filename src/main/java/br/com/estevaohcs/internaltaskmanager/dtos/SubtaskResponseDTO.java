package br.com.estevaohcs.internaltaskmanager.dtos;

import br.com.estevaohcs.internaltaskmanager.entities.Subtask;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class SubtaskResponseDTO extends ActivityResponseDTO {

    private UUID tarefaId;

    public SubtaskResponseDTO(Subtask subtask) {
        super(subtask);
        this.tarefaId = subtask.getTarefaId();
    }

}
