package br.com.estevaohcs.internaltaskmanager.dtos;

import br.com.estevaohcs.internaltaskmanager.entities.enums.Status;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskRequestDTOTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("Se os atributos receberem valores válidos não deve falhar")
    void shouldPassValidationWhenValidData() {
        TaskDTO dto = new TaskDTO();
        dto.setTitulo("Título da tarefa #123");
        dto.setDescricao("Descrição da tarefa #123");
        dto.setStatus(Status.PENDENTE);
        dto.setUsuarioId(UUID.randomUUID());

        var violations = validator.validate(dto);

        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Se o titulo for vazio deve falhar")
    void shouldFailWhenTitleIsEmpty() {
        TaskDTO dto = new TaskDTO();
        dto.setTitulo("");
        dto.setDescricao("Descrição da tarefa #123");
        dto.setStatus(Status.PENDENTE);
        dto.setUsuarioId(UUID.randomUUID());

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Se o titulo for null deve falhar")
    void shouldFailWhenTitleIsNull() {
        TaskDTO dto = new TaskDTO();
        dto.setDescricao("Descrição da tarefa #123");
        dto.setStatus(Status.PENDENTE);
        dto.setUsuarioId(UUID.randomUUID());

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Se o titulo receber mais de 100 caracteres deve falhar")
    void shouldFailWhenTitleIsBiggerThen100Characters() {
        TaskDTO dto = new TaskDTO();
        dto.setTitulo("T".repeat(50) + " " + "T".repeat(50));
        dto.setDescricao("Descrição da tarefa #123");
        dto.setStatus(Status.PENDENTE);
        dto.setUsuarioId(UUID.randomUUID());

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Se o titulo receber menos de 3 caracteres deve falhar")
    void shouldFailWhenTitleIsShorterThen3Characters() {
        TaskDTO dto = new TaskDTO();
        dto.setTitulo("T");
        dto.setDescricao("Descrição da tarefa #123");
        dto.setStatus(Status.PENDENTE);
        dto.setUsuarioId(UUID.randomUUID());

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Se a descrição receber mais de 500 caracteres deve falhar")
    void shouldFailWhenDescriptionIsBiggerThen500Characters() {
        TaskDTO dto = new TaskDTO();
        dto.setTitulo("Título da tarefa #123");
        dto.setDescricao("T".repeat(300) + " " + "T".repeat(200));
        dto.setStatus(Status.PENDENTE);
        dto.setUsuarioId(UUID.randomUUID());

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Se o status for null deve falhar")
    void shouldFailWhenStatusIsNull() {
        TaskDTO dto = new TaskDTO();
        dto.setTitulo("Título da tarefa #123");
        dto.setDescricao("Descrição da tarefa #123");
        dto.setUsuarioId(UUID.randomUUID());

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

    @Test
    @DisplayName("Se o usuarioId for null deve falhar")
    void shouldFailWhenUserIdIsNull() {
        TaskDTO dto = new TaskDTO();
        dto.setTitulo("Título da tarefa #123");
        dto.setDescricao("Descrição da tarefa #123");
        dto.setStatus(Status.PENDENTE);

        var violations = validator.validate(dto);

        assertFalse(violations.isEmpty());
    }

}
