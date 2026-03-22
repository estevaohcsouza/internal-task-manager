package br.com.estevaohcs.internaltaskmanager.entities;

import br.com.estevaohcs.internaltaskmanager.dtos.SubtaskRequestDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tb_subtask")
@Getter
@Setter
@NoArgsConstructor
public class Subtask extends Activity {

    @Column(nullable = false)
    private UUID tarefaId;

    public Subtask(SubtaskRequestDTO subtaskRequestDTO) {
        super(subtaskRequestDTO);
    }

}
