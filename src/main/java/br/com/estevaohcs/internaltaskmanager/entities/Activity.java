package br.com.estevaohcs.internaltaskmanager.entities;

import br.com.estevaohcs.internaltaskmanager.dtos.ActivityRequestDTO;
import br.com.estevaohcs.internaltaskmanager.entities.enums.Status;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class Activity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false)
    private String titulo;

    private String descricao;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant dataCriacao;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private Instant dataConclusao;

    public Activity(ActivityRequestDTO activityRequestDTO) {
        this.titulo = activityRequestDTO.getTitulo();
        this.descricao = activityRequestDTO.getDescricao();
        this.status = activityRequestDTO.getStatus();
        this.dataCriacao = Instant.now();
        if (activityRequestDTO.getStatus().equals(Status.CONCLUIDA)) {
            this.dataConclusao = Instant.now();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Activity activity = (Activity) o;
        return Objects.equals(id, activity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

}
