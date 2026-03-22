package br.com.estevaohcs.internaltaskmanager.dtos;

import br.com.estevaohcs.internaltaskmanager.entities.enums.Status;
import br.com.estevaohcs.internaltaskmanager.services.validations.ValidationConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public abstract class ActivityRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "O campo titulo é obrigatório!")
    @Size(min = 3, max = 100, message = "O título deve ter de 3 a 100 caracteres!")
    @Pattern(regexp = ValidationConstants.TITLE_REGEX, message = "O título deve conter caracteres válidos!")
    @Schema(example = "Título da Tarefa #1")
    private String titulo;

    @Size(max = 500, message = "A descrição deve ter até 500 caracteres!")
    @Pattern(regexp = ValidationConstants.OPTIONAL_TEXT_REGEX, message = "A descrição deve conter caracteres válidos!")
    @Schema(example = "Descrição da Tarefa #1")
    private String descricao;

    @NotNull(message = "O campo status é obrigatório! E deve ser 'PENDENTE', 'EM_ANDAMENTO' ou 'CONCLUIDA'!")
    private Status status;

    public void setTitulo(String titulo) {
        this.titulo = (titulo != null) ? titulo.trim() : null;
    }

    public void setDescricao(String descricao) {
        this.descricao = (descricao != null) ? descricao.trim() : null;
    }

}
