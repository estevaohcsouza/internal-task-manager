package br.com.estevaohcs.internaltaskmanager.services;

import br.com.estevaohcs.internaltaskmanager.dtos.UserDTO;
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

        UserDTO requestDTO = new UserDTO();
        requestDTO.setNome("João da Silva");
        requestDTO.setEmail("joao@email.com");

        User user = new User(requestDTO);

        when(repository.findById(id)).thenReturn(Optional.of(user));

        UserDTO responseDTO = service.findById(id);

        assertNotNull(responseDTO);
        assertEquals(requestDTO.getNome(), responseDTO.getNome());
        assertEquals(requestDTO.getEmail(), responseDTO.getEmail());
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
        UserDTO requestDTO = new UserDTO();
        requestDTO.setNome("João da Silva");
        requestDTO.setEmail("joao@email.com");

        when(repository.existsByEmail(requestDTO.getEmail())).thenReturn(false);

        User user = new User(requestDTO);
        when(repository.save(any(User.class))).thenReturn(user);

        UserDTO responseDTO = service.insert(requestDTO);

        assertNotNull(responseDTO);
        assertEquals(requestDTO.getNome(), responseDTO.getNome());
        assertEquals(requestDTO.getEmail(), responseDTO.getEmail());
    }

    @Test
    @DisplayName("POST - se o email estiver cadastrado para algum usuário deve retornar DataBaseException")
    void createShouldThrowDataBaseExceptionWhenEmailAlreadyExists() {
        UserDTO requestDTO = new UserDTO();
        requestDTO.setNome("João da Silva");
        requestDTO.setEmail("joao@email.com");

        when(repository.existsByEmail(requestDTO.getEmail())).thenReturn(true);

        assertThrows(DataBaseException.class, () -> {
            service.insert(requestDTO);
        });
    }

}
