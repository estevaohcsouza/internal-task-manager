package br.com.estevaohcs.internaltaskmanager.services;

import br.com.estevaohcs.internaltaskmanager.dtos.UserRequestDTO;
import br.com.estevaohcs.internaltaskmanager.dtos.UserResponseDTO;
import br.com.estevaohcs.internaltaskmanager.entities.User;
import br.com.estevaohcs.internaltaskmanager.repositories.UserRepository;
import br.com.estevaohcs.internaltaskmanager.services.exceptions.DataBaseException;
import br.com.estevaohcs.internaltaskmanager.services.exceptions.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Transactional
    public UserResponseDTO findById(UUID id) {
        Optional<User> user = repository.findById(id);
        return new UserResponseDTO(user.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!")));
    }

    @Transactional
    public UserResponseDTO insert(UserRequestDTO userRequestDTO) {
        if (repository.existsByEmail(userRequestDTO.getEmail())) {
            throw new DataBaseException("E-mail já cadastrado na base de dados!");
        }

        User user = new User(userRequestDTO);
        user = repository.save(user);
        return new UserResponseDTO(user);
    }

}
