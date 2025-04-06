package com.shopland.usuarios.service;

import com.shopland.usuarios.model.db.Usuario;
import com.shopland.usuarios.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registerUser(Usuario usuario) throws Exception {
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new Exception("El nombre de usuario ya existe");
        }
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new Exception("El email ya se encuentra en uso");
        }
        // Cifrar la contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario resetPassword(String username, String newPassword) throws Exception {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);
        if (usuarioOpt.isEmpty()) {
            throw new Exception("Usuario no encontrado");
        }
        Usuario usuario = usuarioOpt.get();
        usuario.setPassword(passwordEncoder.encode(newPassword));
        return usuarioRepository.save(usuario);
    }

}
