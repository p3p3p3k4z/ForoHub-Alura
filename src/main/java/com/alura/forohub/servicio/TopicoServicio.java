package com.alura.forohub.servicio;

import com.alura.forohub.dto.TopicoRequest;
import com.alura.forohub.excepciones.RecursoNoEncontradoException;
import com.alura.forohub.modelo.Curso;
import com.alura.forohub.modelo.Topico;
import com.alura.forohub.modelo.Usuario;
import com.alura.forohub.repositorio.CursoRepositorio;
import com.alura.forohub.repositorio.TopicoRepositorio;
import com.alura.forohub.repositorio.UsuarioRepositorio;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Year;
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

    // Registrar un nuevo tópico
    public Topico registrarTopico(TopicoRequest request) {
        Optional<Topico> duplicado = topicoRepositorio.findByTituloAndMensaje(request.getTitulo(), request.getMensaje());
        if (duplicado.isPresent()) {
            throw new IllegalArgumentException("El tópico ya existe.");
        }

        Usuario autor = usuarioRepositorio.findById(request.getAutorId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Autor no encontrado."));
        Curso curso = cursoRepositorio.findById(request.getCursoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado."));

        Topico topico = new Topico();
        topico.setTitulo(request.getTitulo());
        topico.setMensaje(request.getMensaje());
        topico.setAutor(autor);
        topico.setCurso(curso);

        return topicoRepositorio.save(topico);
    }

    // Listar todos los tópicos con paginación
    public Page<Topico> listarTopicos(Pageable pageable) {
        return topicoRepositorio.findAll(pageable);
    }

    // Buscar tópicos por curso y año
    public Page<Topico> buscarTopicosPorCursoYAno(String curso, Year ano, Pageable pageable) {
        return topicoRepositorio.findByCursoNombreAndFechaCreacionYear(curso, ano, pageable);
    }

    // Obtener un tópico por ID
    public Topico obtenerTopicoPorId(Long id) {
        return topicoRepositorio.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró el tópico con ID: " + id));
    }

    // Actualizar un tópico por ID
    public Topico actualizarTopico(Long id, TopicoRequest request) {
        Topico topico = obtenerTopicoPorId(id);

        Usuario autor = usuarioRepositorio.findById(request.getAutorId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Autor no encontrado."));
        Curso curso = cursoRepositorio.findById(request.getCursoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Curso no encontrado."));

        topico.setTitulo(request.getTitulo());
        topico.setMensaje(request.getMensaje());
        topico.setAutor(autor);
        topico.setCurso(curso);

        return topicoRepositorio.save(topico);
    }

    // Eliminar un tópico por ID
    public void eliminarTopico(Long id) {
        if (!topicoRepositorio.existsById(id)) {
            throw new RecursoNoEncontradoException("No se encontró el tópico con ID: " + id);
        }
        topicoRepositorio.deleteById(id);
    }
}
