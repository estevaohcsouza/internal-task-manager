package br.com.estevaohcs.internaltaskmanager.dtos;

import br.com.estevaohcs.internaltaskmanager.entities.Activity;
import br.com.estevaohcs.internaltaskmanager.entities.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public abstract class ActivityResponseDTO {

    @Setter(AccessLevel.NONE)
    private UUID id;
    private String titulo;
    private String descricao;
    private Status status;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Instant dataCriacao;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    private Instant dataConclusao;

    public ActivityResponseDTO(Activity activity) {
        this.id = activity.getId();
        this.titulo = activity.getTitulo();
        this.descricao = activity.getDescricao();
        this.status = activity.getStatus();
        this.dataCriacao = activity.getDataCriacao();
        this.dataConclusao = activity.getDataConclusao();
    }

}
