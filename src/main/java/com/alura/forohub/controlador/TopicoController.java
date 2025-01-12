package com.alura.forohub.controlador;

import com.alura.forohub.dto.TopicoRequest;
import com.alura.forohub.modelo.Topico;
import com.alura.forohub.servicio.TopicoServicio;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/topicos")
public class TopicoControlador {

    private final TopicoServicio topicoServicio;

    public TopicoControlador(TopicoServicio topicoServicio) {
        this.topicoServicio = topicoServicio;
    }

    @PostMapping
    public ResponseEntity<Topico> registrarTopico(@RequestBody @Valid TopicoRequest request) {
        Topico topico = topicoServicio.registrarTopico(request);
        return ResponseEntity.ok(topico);
    }
}
