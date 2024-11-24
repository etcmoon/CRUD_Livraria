package com.biblioteca.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biblioteca.model.Emprestimo;
import com.biblioteca.model.Livro;
import com.biblioteca.model.Multa;
import com.biblioteca.model.Usuario;
import com.biblioteca.repository.EmprestimoRepository;
import com.biblioteca.repository.LivroRepository;
import com.biblioteca.repository.MultaRepository;
import com.biblioteca.repository.UsuarioRepository;


@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private MultaRepository multaRepository;

    @Transactional
    public Usuario registrarUsuario(Usuario usuario) {
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty() ||
        usuario.getEmail() == null || usuario.getEmail().trim().isEmpty() ||
        usuario.getCPF() == null || usuario.getCPF().trim().isEmpty() ||
        usuario.getSenha() == null || usuario.getSenha().trim().isEmpty()) {
        throw new IllegalArgumentException("Erro ao registrar usuário. Verifique os dados fornecidos.");
    }
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

    public Usuario login(String email, String senha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent() && usuarioOpt.get().getSenha().equals(senha)) {
            return usuarioOpt.get();
        }
        throw new IllegalArgumentException("Usuário ou senha incorretos");
    }

    @Transactional
    public String logout(Long userID) {
        // Verificar se o usuário existe
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(userID);
        if (!usuarioOpt.isPresent()) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        return "Logout realizado com sucesso";
    }

    public void alterarSenha(Long id, String novaSenha) {
        if (novaSenha == null || novaSenha.trim().isEmpty()) {
            throw new IllegalArgumentException("A nova senha é obrigatória.");
        }

        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (!usuarioOpt.isPresent()) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }

        Usuario usuario = usuarioOpt.get();
        usuario.setSenha(novaSenha);
        usuarioRepository.save(usuario);
    }

    public Livro consultarLivro(Long livroId) {
        Optional<Livro> livroOpt = livroRepository.findById(livroId);
        if (!livroOpt.isPresent()) {
            throw new IllegalArgumentException("Livro não encontrado.");
        }
        return livroOpt.get();
    }

    public Usuario atualizarCadastro(Long usuarioId, Usuario novosDados) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (!usuarioOpt.isPresent()) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        Usuario usuario = usuarioOpt.get();
        if (novosDados.getNome() != null){
        usuario.setNome(novosDados.getNome());
        }
        if (novosDados.getEmail() != null){
        usuario.setEmail(novosDados.getEmail());
        }
        if (novosDados.getEndereco() != null){
        usuario.setEndereco(novosDados.getEndereco());
        }
        if (novosDados.getTelefone() != null){
        usuario.setTelefone(novosDados.getTelefone());
        }
        return usuarioRepository.save(usuario);
    }

    public List<Emprestimo> consultarEmprestimos(Long clienteId) {
        return emprestimoRepository.findByClienteUserID(clienteId);
    }

    public List<Multa> consultarMultas(Long usuarioId) {
        return multaRepository.findByEmprestimoClienteUserID(usuarioId);
    }

    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // Outros métodos conforme necessário
}