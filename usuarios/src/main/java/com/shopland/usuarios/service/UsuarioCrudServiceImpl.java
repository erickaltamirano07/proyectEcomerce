package com.shopland.usuarios.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopland.usuarios.model.db.Usuario;
import com.shopland.usuarios.repository.UsuarioRepository;

@Service
public class UsuarioCrudServiceImpl implements UsuarioCrudService {

    @Autowired
    private UsuarioRepository usuarioJpaRepository;

    @Override
    public Usuario getUsuario(String username) {
        return usuarioJpaRepository.findByUsername(username).orElse(null);
    }

    @Override
    public List<Usuario> getUsuarios() {
        List<Usuario> usuarios = usuarioJpaRepository.findAll();
        return usuarios.isEmpty() ? null : usuarios;
    }

}
