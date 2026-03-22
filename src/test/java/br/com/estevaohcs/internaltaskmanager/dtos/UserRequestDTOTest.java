package br.com.estevaohcs.internaltaskmanager.dtos;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserRequestDTOTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("Se passar valores válidos para nome e email não deve falhar")
    void shouldPassValidationWhenValidData() {
        UserDTO dto = new UserDTO();
        dto.setNome("João da Silva");
        dto.setEmail("joao@email.com");

        var violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Se o nome for vazio deve falhar")
    void shouldFailWhenNameIsEmpty() {
        UserDTO dto = new UserDTO();
        dto.setNome("");
        dto.setEmail("joao@email.com");

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Se o nome for null deve falhar")
    void shouldFailWhenNameIsNull() {
        UserDTO dto = new UserDTO();
        dto.setEmail("joao@email.com");

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Se o nome não receber pelo menos duas strings deve falhar")
    void shouldFailWhenOneName() {
        UserDTO dto = new UserDTO();
        dto.setNome("João");
        dto.setEmail("joao@email.com");

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Se o nome não receber pelo menos duas strings com no mínimo 2 caracteres cada deve falhar")
    void shouldFailWhenOneNameIsShort() {
        UserDTO dto = new UserDTO();
        dto.setNome("João da S");
        dto.setEmail("joao@email.com");

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Se o nome receber símbolos deve falhar")
    void shouldFailWhenNameWithInvalidCharacters() {
        UserDTO dto = new UserDTO();
        dto.setNome("João da Silva @");
        dto.setEmail("joao@email.com");

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Se o nome receber números deve falhar")
    void shouldFailWhenNameWithNumbers() {
        UserDTO dto = new UserDTO();
        dto.setNome("João da Silva 25");
        dto.setEmail("joao@email.com");

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Se o nome completo for maior de que 50 caracteres deve falhar")
    void shouldFailWhenNameIsLong() {
        UserDTO dto = new UserDTO();
        dto.setNome("João".repeat(6) + " " + "Silva".repeat(6));
        dto.setEmail("joao@email.com");

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Se o email for vazio deve falhar")
    void shouldFailWhenEmailIsEmpty() {
        UserDTO dto = new UserDTO();
        dto.setNome("João da Silva");
        dto.setEmail("");

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Se o email for null deve falhar")
    void shouldFailWhenEmailIsNull() {
        UserDTO dto = new UserDTO();
        dto.setNome("João da Silva");

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Se o email não tiver @ deve falhar")
    void shouldFailWhenEmailIsInvalid() {
        UserDTO dto = new UserDTO();
        dto.setNome("João da Silva");
        dto.setEmail("joao.com");

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

}
