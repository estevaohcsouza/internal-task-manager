package br.com.estevaohcs.internaltaskmanager.integrations;

import br.com.estevaohcs.internaltaskmanager.dtos.UserDTO;
import br.com.estevaohcs.internaltaskmanager.entities.User;
import br.com.estevaohcs.internaltaskmanager.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("GET - se o id existir deve retornar 200")
    void findByIdShouldReturn200WhenUserExists() throws Exception {
        User user = new User();
        user.setNome("João da Silva");
        user.setEmail("joao@email.com");
        user = userRepository.save(user);

        mockMvc.perform(get("/usuarios/{id}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId().toString()))
                .andExpect(jsonPath("$.nome").value("João da Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"));
    }

    @Test
    @DisplayName("GET - se o id não existir deve retornar 404")
    void findByIdShouldReturn404WhenUserNotFound() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(get("/usuarios/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST - se forem passados valores válidos a requisição deve retornar 201")
    void createUserShouldReturn201WhenValid() throws Exception {
        UserDTO requestDTO = new UserDTO();
        requestDTO.setNome("João da Silva");
        requestDTO.setEmail("joao@email.com");

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON.toString())
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nome").value("João da Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"));
    }

    @Test
    @DisplayName("POST - se o email estiver cadastrado para algum usuário deve retornar 400")
    void createUserShouldReturn400WhenEmailDuplicated() throws Exception {
        User user = new User();
        user.setNome("João da Silva");
        user.setEmail("joao@email.com");
        userRepository.save(user);

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON.toString())
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST - se o nome for inválido retornar 400")
    void createUserShouldReturn400WhenInvalidName() throws Exception {
        UserDTO requestDTO = new UserDTO();
        requestDTO.setNome("João");
        requestDTO.setEmail("joao@gmail.com");

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON.toString())
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("POST - se o email for inválido retornar 400")
    void createUserShouldReturn400WhenInvalidEmail() throws Exception {
        UserDTO requestDTO = new UserDTO();
        requestDTO.setNome("João da Silva");
        requestDTO.setEmail("joaogmail.com");

        mockMvc.perform(post("/usuarios")
                        .contentType(MediaType.APPLICATION_JSON.toString())
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isBadRequest());
    }

}
