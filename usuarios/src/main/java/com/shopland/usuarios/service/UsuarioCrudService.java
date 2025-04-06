package com.shopland.usuarios.service;

import java.util.List;

import com.shopland.usuarios.model.db.Usuario;

public interface UsuarioCrudService {

    Usuario getUsuario(String username);

    List<Usuario> getUsuarios();

}
