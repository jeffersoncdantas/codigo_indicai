package com.indicai.indicai.usuario;

import java.util.List;
import java.util.Optional;

//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // @Transactional
    // public Usuario salvar(Usuario usuario) {
    // try {
    // usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
    // usuario.setAnoNascimento(usuario.getAnoNascimento());
    // usuario.setCidade(usuario.getCidade());
    // usuario.setEstado(usuario.getEstado());
    // return usuarioRepository.save(usuario);
    // } catch (org.springframework.dao.DataIntegrityViolationException ex) {
    // throw new UsernameNotFoundException(String.format("Username '%s' já
    // cadastrado", usuario.getUsername()));
    // }
    // }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        if (usuario.getId() != null) {
            // Verificar se o usuário já está presente no banco de dados
            Optional<Usuario> existingUserOptional = usuarioRepository.findById(usuario.getId());

            if (existingUserOptional.isPresent()) {
                // Se o usuário já estiver presente, é uma atualização
                Usuario existingUser = existingUserOptional.get();
                // Atualizar os campos relevantes do usuário
                existingUser.setUsername(usuario.getUsername());
                existingUser.setAnoNascimento(usuario.getAnoNascimento());
                existingUser.setCidade(usuario.getCidade());
                existingUser.setEstado(usuario.getEstado());

                // Verificar se uma nova senha foi fornecida
                if (usuario.getPassword() != null) {
                    // Codificar a nova senha antes de salvar
                    existingUser.setPassword(passwordEncoder.encode(usuario.getPassword()));
                }
                return usuarioRepository.save(existingUser);
            } else {
                throw new IllegalArgumentException("Usuário não encontrado com o ID fornecido: " + usuario.getId());
            }
        } else {
            // Se o ID for nulo, é uma criação de usuário
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            return usuarioRepository.save(usuario);
        }
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Usuário não encontrado"));
    }

    @Transactional(readOnly = true)
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException("Usuário com username não encontrado"));
    }

    @Transactional(readOnly = true)
    public Usuario.Role buscarRolePorUsername(String username) {
        return usuarioRepository.findRoleByUsername(username);
    }

    //Funções para fazer amizade:
    @Transactional
    public void enviarSolicitaçãoDeAmizade(Long idRemetente, Long idDestinatário) {
        Usuario remetente = buscarPorId(idRemetente);
        Usuario destinatário = buscarPorId(idDestinatário);

        if (!remetente.getSolicitaçõesDeAmizadePendentes().contains(destinatário)) {
            remetente.getSolicitaçõesDeAmizadePendentes().add(destinatário);
            usuarioRepository.save(remetente);
        }
    }

    @Transactional
    public void aceitarSolicitaçãoDeAmizade(Long idUsuario, Long idRemetente) {
        Usuario usuário = buscarPorId(idUsuario);
        Usuario remetente = buscarPorId(idRemetente);

        if (usuário.getSolicitaçõesDeAmizadePendentes().contains(remetente)) {
            usuário.getSolicitaçõesDeAmizadePendentes().remove(remetente);
            usuário.getAmigos().add(remetente);
            remetente.getAmigos().add(usuário);

            usuarioRepository.save(usuário);
            usuarioRepository.save(remetente);
        }
    }

}
