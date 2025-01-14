package com.alura.forohub.controlador;

import com.alura.forohub.dto.UsernamePasswordAuthenticationRequest;
import com.alura.forohub.servicio.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthControlador {

    private final TokenService tokenService;

    public AuthControlador(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> autenticar(@RequestBody @Valid UsernamePasswordAuthenticationRequest request) {
        // Simulación de autenticación básica. Reemplazar con lógica real.
        if ("usuario".equals(request.getUsername()) && "contraseña".equals(request.getPassword())) {
            String token = tokenService.generarToken(request.getUsername());
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas.");
        }
    }
}
