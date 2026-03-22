package br.com.estevaohcs.internaltaskmanager.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TaskRequestDTO extends ActivityRequestDTO {

    @NotNull(message = "O campo usuarioId é obrigatório!")
    private UUID usuarioId;

}
