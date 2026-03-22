package br.com.estevaohcs.internaltaskmanager.dtos;

import br.com.estevaohcs.internaltaskmanager.entities.Activity;
import br.com.estevaohcs.internaltaskmanager.entities.enums.Status;
import br.com.estevaohcs.internaltaskmanager.services.validations.ValidationConstants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public abstract class ActivityDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Setter(AccessLevel.NONE)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

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

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant dataCriacao;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Instant dataConclusao;

    public ActivityDTO(Activity activity) {
        this.id = activity.getId();
        this.titulo = activity.getTitulo();
        this.descricao = activity.getDescricao();
        this.status = activity.getStatus();
        this.dataCriacao = activity.getDataCriacao();
        this.dataConclusao = activity.getDataConclusao();
    }

    public void setTitulo(String titulo) {
        this.titulo = (titulo != null) ? titulo.trim() : null;
    }

    public void setDescricao(String descricao) {
        this.descricao = (descricao != null) ? descricao.trim() : null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ActivityDTO that = (ActivityDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
