package br.com.estevaohcs.internaltaskmanager.dtos;

import br.com.estevaohcs.internaltaskmanager.entities.User;
import br.com.estevaohcs.internaltaskmanager.services.validations.ValidationConstants;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Setter(AccessLevel.NONE)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private UUID id;

    @NotBlank(message = "O campo nome é obrigatório!")
    @Pattern(regexp = ValidationConstants.FULL_NAME_REGEX, message = "Nome e sobrenome são obrigatórios e o nome completo " +
            "deve conter até 50 caracteres válidos (sem números e/ou símbolos)!")
    @Schema(example = "João da Silva")
    private String nome;

    @NotBlank(message = "O campo email é obrigatório!")
    @Email(message = "O email informado é inválido!")
    @Schema(example = "joao@gmail.com")
    private String email;

    public UserDTO(User user) {
        this.id = user.getId();
        this.nome = user.getNome();
        this.email = user.getEmail();
    }

    public void setNome(String nome) {
        this.nome = (nome != null) ? nome.trim() : null;
    }

}
