package br.com.estevaohcs.internaltaskmanager.dtos;

import br.com.estevaohcs.internaltaskmanager.entities.enums.Status;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SubtaskRequestDTOTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("Se os atributos receberem valores válidos não deve falhar")
    void shouldPassValidationWhenValidData() {
        SubtaskRequestDTO dto = new SubtaskRequestDTO();
        dto.setTitulo("Título da tarefa #123");
        dto.setDescricao("Descrição da tarefa #123");
        dto.setStatus(Status.PENDENTE);

        var violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Se o titulo for vazio deve falhar")
    void shouldFailWhenTitleIsEmpty() {
        SubtaskRequestDTO dto = new SubtaskRequestDTO();
        dto.setTitulo("");
        dto.setDescricao("Descrição da tarefa #123");
        dto.setStatus(Status.PENDENTE);

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Se o titulo for null deve falhar")
    void shouldFailWhenTitleIsNull() {
        SubtaskRequestDTO dto = new SubtaskRequestDTO();
        dto.setDescricao("Descrição da tarefa #123");
        dto.setStatus(Status.PENDENTE);

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Se o titulo receber mais de 100 caracteres deve falhar")
    void shouldFailWhenTitleIsBiggerThen100Characters() {
        SubtaskRequestDTO dto = new SubtaskRequestDTO();
        dto.setTitulo("T".repeat(50) + " " + "T".repeat(50));
        dto.setDescricao("Descrição da tarefa #123");
        dto.setStatus(Status.PENDENTE);

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Se o titulo receber menos de 3 caracteres deve falhar")
    void shouldFailWhenTitleIsShorterThen3Characters() {
        SubtaskRequestDTO dto = new SubtaskRequestDTO();
        dto.setTitulo("T");
        dto.setDescricao("Descrição da tarefa #123");
        dto.setStatus(Status.PENDENTE);

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Se a descrição receber mais de 500 caracteres deve falhar")
    void shouldFailWhenDescriptionIsBiggerThen500Characters() {
        SubtaskRequestDTO dto = new SubtaskRequestDTO();
        dto.setTitulo("Título da tarefa #123");
        dto.setDescricao("T".repeat(300) + " " + "T".repeat(200));
        dto.setStatus(Status.PENDENTE);

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Se o status for null deve falhar")
    void shouldFailWhenStatusIsNull() {
        SubtaskRequestDTO dto = new SubtaskRequestDTO();
        dto.setTitulo("Título da tarefa #123");
        dto.setDescricao("Descrição da tarefa #123");

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

}
