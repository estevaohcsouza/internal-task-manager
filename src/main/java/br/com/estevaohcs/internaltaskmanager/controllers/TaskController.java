package br.com.estevaohcs.internaltaskmanager.controllers;

import br.com.estevaohcs.internaltaskmanager.dtos.StatusRequestDTO;
import br.com.estevaohcs.internaltaskmanager.dtos.TaskRequestDTO;
import br.com.estevaohcs.internaltaskmanager.dtos.TaskResponseDTO;
import br.com.estevaohcs.internaltaskmanager.entities.enums.Status;
import br.com.estevaohcs.internaltaskmanager.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/tarefas")
@Tag(name = "Tarefas", description = "Endpoints para o gerenciamento de tarefas")
public class TaskController {

    @Autowired
    private TaskService service;

    @GetMapping
    @Operation(summary = "Busca tarefas",
            description = "Como se trata de uma paginação, pode se alterar o número da página, a quantidade de tarefas por página e a ordenação<br>" +
                    "Os valores padrões são: primeira página, 5 tarefas por página e ordenado pelo título das tarefas<br>" +
                    "Também é possível filtrar pelo status da tarefa e caso não seja escolhida uma opção, irá buscar todas as tarefas registradas")
    public ResponseEntity<Page<TaskResponseDTO>> findAll(@PageableDefault(page = 0, size = 5, sort = "titulo") Pageable pageable,
                                                         @RequestParam(required = false) Status status) {
        Page<TaskResponseDTO> taskResponseDTOs = service.findAll(pageable, status);
        return ResponseEntity.ok().body(taskResponseDTOs);
    }

    @PostMapping
    @Operation(summary = "Cria uma nova tarefa",
            description = "Campos obrigatórios: 'titulo', 'status' e 'usuarioId'<br>" +
                    "O 'usuarioId' deve ser referente ao id de um usuário existente<br>" +
                    "O 'titulo' deve ter de 3 a 100 caracteres<br>" +
                    "Se houver 'descricao', deve ter no máximo 500 caracteres<br>" +
                    "Valores aceitos como 'status': 'EM_ANDAMENTO', 'PENDENTE', ou 'CONCLUIDA'")
    public ResponseEntity<TaskResponseDTO> insert(@Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO taskResponseDTO = service.insert(taskRequestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(taskResponseDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(taskResponseDTO);
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "Altera o status de uma tarefa",
            description = "Parâmetro obrigatório: id da tarefa (tarefaId)<br>" +
                    "Campo obrigatório: 'status'<br>" +
                    "O 'id' (tarefaId) deve ser referente ao id de uma tarefa existente<br>" +
                    "Valores aceitos como 'status': 'EM_ANDAMENTO', 'PENDENTE', ou 'CONCLUIDA'<br>" +
                    "Só será possível alterar o 'status' para 'CONCLUIDA' caso todas as subtarefas desta tarefa também estejam com 'status' de 'CONCLUIDA'<br>" +
                    "Após alterar o 'status' para 'CONCLUIDA' não será mais possível alterar o 'status' desta tarefa novamente")
    public ResponseEntity<TaskResponseDTO> updateStatus(@PathVariable UUID id, @Valid @RequestBody StatusRequestDTO statusRequestDTO) {
        TaskResponseDTO taskResponseDTO = service.updateStatus(id, statusRequestDTO.getStatus());
        return ResponseEntity.ok().body(taskResponseDTO);
    }

}
