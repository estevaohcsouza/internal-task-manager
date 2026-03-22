package br.com.estevaohcs.internaltaskmanager.services;

import br.com.estevaohcs.internaltaskmanager.dtos.UserRequestDTO;
import br.com.estevaohcs.internaltaskmanager.dtos.UserResponseDTO;
import br.com.estevaohcs.internaltaskmanager.entities.User;
import br.com.estevaohcs.internaltaskmanager.repositories.UserRepository;
import br.com.estevaohcs.internaltaskmanager.services.exceptions.DataBaseException;
import br.com.estevaohcs.internaltaskmanager.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

    @Test
    @DisplayName("GET - se o id existir deve retornar UserResponseDTO")
    void findByIdShouldReturnUserResponseDTOWhenUserExists() {
        UUID id = UUID.randomUUID();

        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setNome("João da Silva");
        userRequestDTO.setEmail("joao@email.com");

        User user = new User(userRequestDTO);

        when(repository.findById(id)).thenReturn(Optional.of(user));

        UserResponseDTO userResponseDTO = service.findById(id);

        assertNotNull(userResponseDTO);
        assertEquals(userRequestDTO.getNome(), userResponseDTO.getNome());
        assertEquals(userRequestDTO.getEmail(), userResponseDTO.getEmail());
    }

    @Test
    @DisplayName("GET - se o id não existir deve retornar ResourceNotFoundException")
    void findByIdShouldThrowResourceNotFoundExceptionWhenUserDoesNotExist() {
        UUID id = UUID.randomUUID();

        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(id);
        });
    }

    @Test
    @DisplayName("POST - se forem passados valores válidos a requisição deve retornar UserResponseDTO")
    void createShouldReturnUserResponseDTOWhenValidData() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setNome("João da Silva");
        userRequestDTO.setEmail("joao@email.com");

        when(repository.existsByEmail(userRequestDTO.getEmail())).thenReturn(false);

        User user = new User(userRequestDTO);
        when(repository.save(any(User.class))).thenReturn(user);

        UserResponseDTO userResponseDTO = service.insert(userRequestDTO);

        assertNotNull(userResponseDTO);
        assertEquals(userRequestDTO.getNome(), userResponseDTO.getNome());
        assertEquals(userRequestDTO.getEmail(), userResponseDTO.getEmail());
    }

    @Test
    @DisplayName("POST - se o email estiver cadastrado para algum usuário deve retornar DataBaseException")
    void createShouldThrowDataBaseExceptionWhenEmailAlreadyExists() {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setNome("João da Silva");
        userRequestDTO.setEmail("joao@email.com");

        when(repository.existsByEmail(userRequestDTO.getEmail())).thenReturn(true);

        assertThrows(DataBaseException.class, () -> {
            service.insert(userRequestDTO);
        });
    }

}
