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
        SubtaskDTO dto = new SubtaskDTO();
        dto.setTitulo("Título da tarefa #123");
        dto.setDescricao("Descrição da tarefa #123");
        dto.setStatus(Status.PENDENTE);

        var violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Se o titulo for vazio deve falhar")
    void shouldFailWhenTitleIsEmpty() {
        SubtaskDTO dto = new SubtaskDTO();
        dto.setTitulo("");
        dto.setDescricao("Descrição da tarefa #123");
        dto.setStatus(Status.PENDENTE);

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Se o titulo for null deve falhar")
    void shouldFailWhenTitleIsNull() {
        SubtaskDTO dto = new SubtaskDTO();
        dto.setDescricao("Descrição da tarefa #123");
        dto.setStatus(Status.PENDENTE);

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Se o titulo receber mais de 100 caracteres deve falhar")
    void shouldFailWhenTitleIsBiggerThen100Characters() {
        SubtaskDTO dto = new SubtaskDTO();
        dto.setTitulo("T".repeat(50) + " " + "T".repeat(50));
        dto.setDescricao("Descrição da tarefa #123");
        dto.setStatus(Status.PENDENTE);

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Se o titulo receber menos de 3 caracteres deve falhar")
    void shouldFailWhenTitleIsShorterThen3Characters() {
        SubtaskDTO dto = new SubtaskDTO();
        dto.setTitulo("T");
        dto.setDescricao("Descrição da tarefa #123");
        dto.setStatus(Status.PENDENTE);

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Se a descrição receber mais de 500 caracteres deve falhar")
    void shouldFailWhenDescriptionIsBiggerThen500Characters() {
        SubtaskDTO dto = new SubtaskDTO();
        dto.setTitulo("Título da tarefa #123");
        dto.setDescricao("T".repeat(300) + " " + "T".repeat(200));
        dto.setStatus(Status.PENDENTE);

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Se o status for null deve falhar")
    void shouldFailWhenStatusIsNull() {
        SubtaskDTO dto = new SubtaskDTO();
        dto.setTitulo("Título da tarefa #123");
        dto.setDescricao("Descrição da tarefa #123");

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

}
