package com.indicai.indicai.usuario;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class UsuarioCreateDto {

    private String username;
    private String password;
    private int anoNascimento;
    private String cidade;
    private String estado;
    private List<Usuario> amigos;
}
