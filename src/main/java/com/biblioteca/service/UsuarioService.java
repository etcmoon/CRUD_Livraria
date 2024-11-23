package com.biblioteca.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblioteca.model.Usuario;
import com.biblioteca.repository.UsuarioRepository;


@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario registrarUsuario(Usuario usuario) {
        // Verificar se o email já está em uso
        Optional<Usuario> usuarioEmail = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioEmail.isPresent()) {
            throw new IllegalArgumentException("O email já está em uso.");
        }

        // Verificar se o CPF já está em uso
        Optional<Usuario> usuarioCPF = usuarioRepository.findByCPF(usuario.getCPF());
        if (usuarioCPF.isPresent()) {
            throw new IllegalArgumentException("O CPF já está em uso.");
        }

        // Definir data de cadastro se não estiver definida
        if (usuario.getDataCadastro() == null) {
            usuario.setDataCadastro(LocalDate.now());
        }

        // Definir status da conta como "Ativa" se não estiver definido
        if (usuario.getStatusConta() == null || usuario.getStatusConta().isEmpty()) {
            usuario.setStatusConta("Ativa");
        }

        // Definir função como "Cliente" por padrão se não estiver definido
        if (usuario.getFuncao() == null || usuario.getFuncao().isEmpty()) {
            usuario.setFuncao("Cliente");
        }

        // Salvar o usuário no banco de dados
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> login(String email, String senha) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent() && usuario.get().getSenha().equals(senha)) {
            return usuario;
        }
        return Optional.empty();
    }

    @Transactional
    public String logout(Long userID) {
        // Verificar se o usuário existe
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(userID);
        if (!usuarioOpt.isPresent()) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }

        Usuario usuario = usuarioOpt.get();

        // Aqui, você pode implementar lógica adicional de logout, como invalidar tokens
        // ou remover informações de sessão. Para simplificação, retornaremos uma mensagem de sucesso.

        return "Logout realizado com sucesso para o usuário: " + usuario.getNome();
    }

    public void alterarSenha(Long usuarioId, String novaSenha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            usuario.setSenha(novaSenha);
            usuarioRepository.save(usuario);
        }
    }

    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Outros métodos conforme necessário
}