package br.com.estevaohcs.internaltaskmanager.controllers;

import br.com.estevaohcs.internaltaskmanager.dtos.UserRequestDTO;
import br.com.estevaohcs.internaltaskmanager.dtos.UserResponseDTO;
import br.com.estevaohcs.internaltaskmanager.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuários", description = "Endpoints para o gerenciamento de usuários")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/{id}")
    @Operation(summary = "Busca um único usuário pelo seu id",
            description = "Parâmetro obrigatório: id do usuário (usuarioId)<br>" +
                    "O 'id' (usuarioId) deve ser referente ao id de um usuário existente")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable UUID id) {
        UserResponseDTO userResponseDTO = service.findById(id);
        return ResponseEntity.ok().body(userResponseDTO);
    }

    @PostMapping
    @Operation(summary = "Cria um novo usuário",
            description = "Campos obrigatórios: 'nome' e 'email'<br>" +
                    "O 'nome' deve ser composto por no mínimo duas strings com no mínimo dois caracteres cada e conter apenas letras<br>" +
                    "O 'email' deve ser um e-mail válido e não pode se repetir")
    public ResponseEntity<UserResponseDTO> insert(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO userResponseDTO = service.insert(userRequestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userResponseDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(userResponseDTO);
    }

}
