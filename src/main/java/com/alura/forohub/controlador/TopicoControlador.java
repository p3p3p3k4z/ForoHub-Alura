package com.alura.forohub.controlador;

import com.alura.forohub.dto.TopicoRequest;
import com.alura.forohub.modelo.Topico;
import com.alura.forohub.servicio.TopicoServicio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Year;

@RestController
@RequestMapping("/topicos")
public class TopicoControlador {

    private final TopicoServicio topicoServicio;

    public TopicoControlador(TopicoServicio topicoServicio) {
        this.topicoServicio = topicoServicio;
    }

    // Crear un nuevo tópico
    @PostMapping
    public ResponseEntity<Topico> registrarTopico(@RequestBody @Valid TopicoRequest request) {
        Topico topico = topicoServicio.registrarTopico(request);
        return ResponseEntity.ok(topico);
    }

    // Listar todos los tópicos
    @GetMapping
    public ResponseEntity<Page<Topico>> listarTopicos(@PageableDefault(size = 10, sort = "fechaCreacion") Pageable pageable) {
        Page<Topico> topicos = topicoServicio.listarTopicos(pageable);
        return ResponseEntity.ok(topicos);
    }

    // Buscar tópicos por curso y año
    @GetMapping("/buscar")
    public ResponseEntity<Page<Topico>> buscarTopicosPorCursoYAno(
            @RequestParam String curso,
            @RequestParam int ano,
            @PageableDefault(size = 10, sort = "fechaCreacion") Pageable pageable) {
        Page<Topico> topicos = topicoServicio.buscarTopicosPorCursoYAno(curso, Year.of(ano), pageable);
        return ResponseEntity.ok(topicos);
    }

    // Obtener detalles de un tópico
    @GetMapping("/{id}")
    public ResponseEntity<Topico> obtenerTopico(@PathVariable Long id) {
        Topico topico = topicoServicio.obtenerTopicoPorId(id);
        return ResponseEntity.ok(topico);
    }

    // Actualizar un tópico
    @PutMapping("/{id}")
    public ResponseEntity<Topico> actualizarTopico(@PathVariable Long id, @RequestBody @Valid TopicoRequest request) {
        Topico topico = topicoServicio.actualizarTopico(id, request);
        return ResponseEntity.ok(topico);
    }

    // Eliminar un tópico
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        topicoServicio.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }
}
