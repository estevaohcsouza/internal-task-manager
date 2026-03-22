package br.com.estevaohcs.internaltaskmanager.dtos;

import br.com.estevaohcs.internaltaskmanager.services.validations.ValidationConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class UserRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "O campo nome é obrigatório!")
    @Pattern(regexp = ValidationConstants.FULL_NAME_REGEX, message = "Nome e sobrenome são obrigatórios e o nome completo " +
            "deve conter até 50 caracteres válidos (sem números e/ou símbolos)!")
    @Schema(example = "João da Silva")
    private String nome;

    @NotBlank(message = "O campo email é obrigatório!")
    @Email(message = "O email informado é inválido!")
    @Schema(example = "joao@gmail.com")
    private String email;

    public void setNome(String nome) {
        this.nome = (nome != null) ? nome.trim() : null;
    }

}
