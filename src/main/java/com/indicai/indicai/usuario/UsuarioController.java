package com.indicai.indicai.usuario;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

  private final UsuarioService usuarioService;

  @PutMapping("/{id}")
  public ResponseEntity<Usuario> updateUsuario(@RequestBody Usuario usuario,
      @PathVariable Long id) {
    Usuario user = usuarioService.salvar(usuario);
    return ResponseEntity.ok(user);
  }

  @PostMapping
  public ResponseEntity<UsuarioResponseDto> createUsuario(@RequestBody UsuarioCreateDto createDto) {
    Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(createDto));
    return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
  }

  @GetMapping("/{id}")
  // @PreAuthorize("hasRole('ADMIN') OR (hasRole('EMPLOYEE') AND #id ==
  // authentication.principal.id)")
  public ResponseEntity<UsuarioResponseDto> getUsuario(@PathVariable Long id) {
    Usuario user = usuarioService.buscarPorId(id);
    return ResponseEntity.ok(UsuarioMapper.toDto(user));
  }

  @GetMapping("username/{username}")
  // @PreAuthorize("hasRole('ADMIN') OR (hasRole('EMPLOYEE') AND #id ==
  // authentication.principal.id)")
  public ResponseEntity<UsuarioResponseDto> getUsuarioByUsername(@PathVariable String username) {
    Usuario user = usuarioService.buscarPorUsername(username);
    return ResponseEntity.ok(UsuarioMapper.toDto(user));
  }

  @GetMapping
  //@PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<List<Usuario>> getUsuarios() {
    List<Usuario> users = usuarioService.buscarTodos();
    return ResponseEntity.ok(users);
  }

  // Funções para fazer amigos
  @PutMapping("/{idRemetente}/solicitar-amizade/{idDestinatario}")
  public ResponseEntity<Void> enviarSolicitaçãoDeAmizade(@PathVariable Long idRemetente,
      @PathVariable Long idDestinatario) {
    usuarioService.enviarSolicitaçãoDeAmizade(idRemetente, idDestinatario);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{idDestinatario}/aceitar-amizade/{idRemetente}")
  public ResponseEntity<Void> aceitarSolicitaçãoDeAmizade(@PathVariable Long idDestinatario,
      @PathVariable Long idRemetente) {
    usuarioService.aceitarSolicitaçãoDeAmizade(idDestinatario, idRemetente);
    return ResponseEntity.ok().build();
  }

}