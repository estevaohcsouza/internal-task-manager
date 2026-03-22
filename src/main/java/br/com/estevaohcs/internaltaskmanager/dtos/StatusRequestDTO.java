package br.com.estevaohcs.internaltaskmanager.dtos;

import br.com.estevaohcs.internaltaskmanager.entities.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusRequestDTO {

    @NotNull(message = "O campo status é obrigatório! E deve ser 'PENDENTE', 'EM_ANDAMENTO' ou 'CONCLUIDA'!")
    private Status status;

}
