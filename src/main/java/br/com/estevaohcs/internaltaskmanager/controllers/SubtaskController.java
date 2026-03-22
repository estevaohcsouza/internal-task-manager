package br.com.estevaohcs.internaltaskmanager.controllers;

import br.com.estevaohcs.internaltaskmanager.dtos.StatusRequestDTO;
import br.com.estevaohcs.internaltaskmanager.dtos.SubtaskDTO;
import br.com.estevaohcs.internaltaskmanager.services.SubtaskService;
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
@Tag(name = "Subtarefas", description = "Endpoints para o gerenciamento de subtarefas de tarefas")
public class SubtaskController {

    @Autowired
    private SubtaskService service;

    @GetMapping("/tarefas/{tarefaId}/subtarefas")
    @Operation(summary = "Busca as subtarefas de uma tarefa",
            description = "Parâmetro obrigatório: id da tarefa (tarefaId)<br>" +
                    "O 'tarefaId' deve ser referente ao id de uma tarefa existente<br>" +
                    "Como se trata de uma paginação, pode se alterar o número da página, a quantidade de subtarefas por página e a ordenação<br>" +
                    "Os valores padrões são: primeira página, 5 subtarefas por página e ordenado pelo título das subtarefas")
    public ResponseEntity<Page<SubtaskDTO>> findByTaskId(@PageableDefault(page = 0, size = 5, sort = "titulo") Pageable pageable,
                                                         @PathVariable UUID tarefaId) {
        Page<SubtaskDTO> dtos = service.findByTaskId(pageable, tarefaId);
        return ResponseEntity.ok().body(dtos);
    }

    @PostMapping("/tarefas/{tarefaId}/subtarefas")
    @Operation(summary = "Cria uma nova subtarefa para uma tarefa",
            description = "Parâmetro obrigatório: id da tarefa (tarefaId)<br>" +
                    "Campos obrigatórios: 'titulo' e 'status'<br>" +
                    "O 'tarefaId' deve ser referente ao id de uma tarefa existente<br>" +
                    "O 'titulo' deve ter de 3 a 100 caracteres<br>" +
                    "Se houver 'descricao', deve ter no máximo 500 caracteres<br>" +
                    "Valores aceitos como 'status': 'EM_ANDAMENTO', 'PENDENTE', ou 'CONCLUIDA'")
    public ResponseEntity<SubtaskDTO> insert(@PathVariable UUID tarefaId, @Valid @RequestBody SubtaskDTO dto) {
        dto = service.insert(tarefaId, dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PatchMapping("/subtarefas/{id}/status")
    @Operation(summary = "Altera o status de uma subtarefa",
            description = "Parâmetro obrigatório: id da subtarefa<br>" +
                    "Campo obrigatório: 'status'<br>" +
                    "O 'id' (subtarefaId) deve ser referente ao id de uma subtarefa existente<br>" +
                    "Valores aceitos como 'status': 'EM_ANDAMENTO', 'PENDENTE', ou 'CONCLUIDA'")
    public ResponseEntity<SubtaskDTO> updateStatus(@PathVariable UUID id, @Valid @RequestBody StatusRequestDTO statusRequestDTO) {
        SubtaskDTO dto = service.updateStatus(id, statusRequestDTO.getStatus());
        return ResponseEntity.ok().body(dto);
    }

}
