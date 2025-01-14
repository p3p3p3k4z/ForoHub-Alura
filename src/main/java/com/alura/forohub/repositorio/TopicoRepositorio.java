package com.alura.forohub.repositorio;

import com.alura.forohub.modelo.Topico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Year;
import java.util.Optional;

public interface TopicoRepositorio extends JpaRepository<Topico, Long> {

    Optional<Topico> findByTituloAndMensaje(String titulo, String mensaje);

    Page<Topico> findByCursoNombreAndFechaCreacionYear(String curso, Year ano, Pageable pageable);
}
