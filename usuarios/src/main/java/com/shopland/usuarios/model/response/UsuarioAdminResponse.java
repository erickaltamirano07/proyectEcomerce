package com.shopland.usuarios.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioAdminResponse {

    private Long id;
    private String nombre;
    private String username;
    private String rol;
    private String email;

}
