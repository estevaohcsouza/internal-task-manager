package br.com.estevaohcs.internaltaskmanager.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SubtaskRequestDTO extends ActivityRequestDTO {

    private UUID tarefaId;

}
