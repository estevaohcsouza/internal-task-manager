package br.com.estevaohcs.internaltaskmanager.controllers;

import br.com.estevaohcs.internaltaskmanager.dtos.UserRequestDTO;
import br.com.estevaohcs.internaltaskmanager.dtos.UserResponseDTO;
import br.com.estevaohcs.internaltaskmanager.entities.User;
import br.com.estevaohcs.internaltaskmanager.services.UserService;
import br.com.estevaohcs.internaltaskmanager.services.exceptions.DataBaseException;
import br.com.estevaohcs.internaltaskmanager.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET - se o id existir deve retornar 200")
    void findByIdShouldReturn200WhenUserExists() throws Exception {
        UUID id = UUID.randomUUID();

        User user = mock(User.class);
        when(user.getId()).thenReturn(id);
        when(user.getNome()).thenReturn("João da Silva");
        when(user.getEmail()).thenReturn("joao@email.com");

        UserResponseDTO userResponseDTO = new UserResponseDTO(user);

        when(service.findById(id)).thenReturn(userResponseDTO);

        mockMvc.perform(get("/usuarios/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.nome").value("João da Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"));
    }

    @Test
    @DisplayName("GET - se o id não existir deve retornar 404")
    void findByIdShouldReturn404WhenUserNotFound() throws Exception {
        UUID id = UUID.randomUUID();

        when(service.findById(id)).thenThrow(new ResourceNotFoundException("Usuário não encontrado!"));

        mockMvc.perform(get("/usuarios/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST - se forem passados valores válidos na requisição deve retornar 201")
    void createUserShouldReturn201WhenValid() throws Exception {
        UUID id = UUID.randomUUID();

        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setNome("João da Silva");
        userRequestDTO.setEmail("joao@email.com");

        User user = mock(User.class);
        when(user.getId()).thenReturn(id);
        when(user.getNome()).thenReturn(userRequestDTO.getNome());
        when(user.getEmail()).thenReturn(userRequestDTO.getEmail());

        UserResponseDTO userResponseDTO = new UserResponseDTO(user);

        when(service.insert(any(UserRequestDTO.class))).thenReturn(userResponseDTO);

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON.toString())
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.nome").value("João da Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"));
    }

    @Test
    @DisplayName("POST - se o email estiver cadastrado para algum usuário deve retornar 400")
    void createUserShouldReturn400WhenEmailDuplicated() throws Exception {
        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setNome("João da Silva");
        userRequestDTO.setEmail("joao@email.com");

        when(service.insert(any(UserRequestDTO.class)))
                .thenThrow(new DataBaseException("E-mail já cadastrado!"));

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON.toString())
                        .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isBadRequest());
    }

}
