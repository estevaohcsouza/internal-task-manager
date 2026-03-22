package br.com.estevaohcs.internaltaskmanager.services;

import br.com.estevaohcs.internaltaskmanager.dtos.UserDTO;
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
    public UserDTO findById(UUID id) {
        Optional<User> user = repository.findById(id);
        return new UserDTO(user.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!")));
    }

    @Transactional
    public UserDTO insert(UserDTO dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new DataBaseException("E-mail já cadastrado na base de dados!");
        }

        User user = new User(dto);
        user = repository.save(user);
        return new UserDTO(user);
    }

}
