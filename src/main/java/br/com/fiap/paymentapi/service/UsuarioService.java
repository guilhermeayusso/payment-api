package br.com.fiap.paymentapi.service;

import br.com.fiap.paymentapi.entity.Usuario;
import br.com.fiap.paymentapi.exception.UsernameUniqueViolationException;
import br.com.fiap.paymentapi.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario salvar(Usuario usuario){
        try {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            return usuarioRepository.save(usuario);
        }catch (DataIntegrityViolationException ex){
            throw new UsernameUniqueViolationException(
                    String.format("Username {%s} já cadastrado",usuario.getUsername()));
        }
    }

    @Transactional
    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username).orElseThrow(
                () -> new EntityNotFoundException(String.format("Usuario com '%s' não encontrado", username))
        );
    }

    @Transactional
    public Usuario.Role buscarRolePorUsername(String username) {
        return usuarioRepository.findRoleByUsername(username);
    }

}
