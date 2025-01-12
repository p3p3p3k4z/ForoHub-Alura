package com.alura.forohub.servicio;

import com.alura.forohub.dto.TopicoRequest;
import com.alura.forohub.modelo.Curso;
import com.alura.forohub.modelo.Topico;
import com.alura.forohub.modelo.Usuario;
import com.alura.forohub.repositorio.CursoRepositorio;
import com.alura.forohub.repositorio.TopicoRepositorio;
import com.alura.forohub.repositorio.UsuarioRepositorio;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TopicoServicio {

    private final TopicoRepositorio topicoRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;
    private final CursoRepositorio cursoRepositorio;

    public TopicoServicio(TopicoRepositorio topicoRepositorio, UsuarioRepositorio usuarioRepositorio, CursoRepositorio cursoRepositorio) {
        this.topicoRepositorio = topicoRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
        this.cursoRepositorio = cursoRepositorio;
    }

    public Topico registrarTopico(TopicoRequest request) {
        // Verificar duplicados
        Optional<Topico> duplicado = topicoRepositorio.findByTituloAndMensaje(request.getTitulo(), request.getMensaje());
        if (duplicado.isPresent()) {
            throw new IllegalArgumentException("El tópico ya existe.");
        }

        // Validar autor y curso
        Usuario autor = usuarioRepositorio.findById(request.getAutorId())
                .orElseThrow(() -> new IllegalArgumentException("Autor no encontrado."));
        Curso curso = cursoRepositorio.findById(request.getCursoId())
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado."));

        // Crear y guardar el tópico
        Topico topico = new Topico();
        topico.setTitulo(request.getTitulo());
        topico.setMensaje(request.getMensaje());
        topico.setAutor(autor);
        topico.setCurso(curso);

        return topicoRepositorio.save(topico);
    }
}
