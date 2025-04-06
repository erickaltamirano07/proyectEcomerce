package com.shopland.usuarios.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.shopland.usuarios.model.db.Usuario;
import com.shopland.usuarios.model.response.UsuarioAdminResponse;
import com.shopland.usuarios.model.response.UsuarioClientResponse;
import com.shopland.usuarios.service.UsuarioCrudService;

import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/usuario")
public class UsuarioController {

    private final UsuarioCrudService service;

    @GetMapping("/info/{}")
    public ResponseEntity<UsuarioClientResponse> getUsuario(@PathVariable String username) {
        Usuario usuario = service.getUsuario(username);
        UsuarioClientResponse clientResponse = UsuarioClientResponse.builder()
                .id(usuario.getId()) // Mapeo del ID del usuario
                .nombre(usuario.getNombre()) // Mapeo del nombre
                .email(usuario.getEmail()) // Mapeo del email
                .build();
        return ResponseEntity.ok(clientResponse);
    }

    @GetMapping("/usuariosList")
    @PreAuthorize("Admin")
    public ResponseEntity<List<UsuarioAdminResponse>> getUsuarios() {
        List<Usuario> usuarios = service.getUsuarios();
        List<UsuarioAdminResponse> adminResponses = usuarios.stream()
                .map(usuario -> UsuarioAdminResponse.builder()
                        .id(usuario.getId()) // Mapeo del ID del usuario
                        .nombre(usuario.getNombre())
                        .username(usuario.getUsername()) // Mapeo del nombre
                        .email(usuario.getEmail()) // Mapeo del email
                        .rol(usuario.getRol()) // Mapeo del rol (si es necesario)
                        .build()) // Construcción del objeto
                .collect(Collectors.toList());
        return ResponseEntity.ok(adminResponses);
    }

}
