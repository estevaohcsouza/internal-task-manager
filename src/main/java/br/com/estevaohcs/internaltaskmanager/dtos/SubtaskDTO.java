package br.com.estevaohcs.internaltaskmanager.dtos;

import br.com.estevaohcs.internaltaskmanager.entities.Subtask;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class SubtaskDTO extends ActivityDTO {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID tarefaId;

    public SubtaskDTO(Subtask subtask) {
        super(subtask);
        this.tarefaId = subtask.getTarefaId();
    }

}
