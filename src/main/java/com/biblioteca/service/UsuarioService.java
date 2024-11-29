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

    public List<Multa> listarMultas(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        if (!usuario.isAdministrador()) {
            throw new IllegalArgumentException("Acesso negado. Você não tem permissão para acessar essa funcionalidade.");
        }
        return multaRepository.findAll();
    }

    public List<Usuario> listarUsuarios(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        if (!usuario.isAdministrador()) {
            throw new IllegalArgumentException("Acesso negado. Você não tem permissão para acessar essa funcionalidade.");
        }
        return usuarioRepository.findAll();
    }

    public List<Emprestimo> listarEmprestimos(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        if (!usuario.isAdministrador()) {
            throw new IllegalArgumentException("Acesso negado. Você não tem permissão para acessar essa funcionalidade.");
        }
        return emprestimoRepository.findAll();
    }

    public List<Livro> listarLivros(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        if (!usuario.isAdministrador()) {
            throw new IllegalArgumentException("Acesso negado. Você não tem permissão para acessar essa funcionalidade.");
        }
        return livroRepository.findAll();
    }




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
        if (usuario.getDataCadastro() == null || usuario.getDataCadastro().toString().isEmpty()) {
            usuario.setDataCadastro(LocalDate.now());
        }

        // Definir status da conta como "Ativa" se não estiver definido
        if (usuario.getStatusConta() == null || usuario.getStatusConta().isEmpty()) {
            usuario.setStatusConta("Ativa");
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
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (!usuarioOpt.isPresent()) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
        
        if (novaSenha == null || novaSenha.trim().isEmpty()) {
            throw new IllegalArgumentException("A nova senha é obrigatória.");
        }

        Usuario usuario = usuarioOpt.get();
        usuario.setSenha(novaSenha);
        usuarioRepository.save(usuario);
    }




    public List<Emprestimo> consultarEmprestimos(Long clienteId) {
        return emprestimoRepository.findByUsuarioUserIDAndMultaIsNotNull(clienteId);
    }

    public Livro consultarLivro(Long livroId) {
        Optional<Livro> livroOpt = livroRepository.findById(livroId);
        if (!livroOpt.isPresent()) {
            throw new IllegalArgumentException("Livro não encontrado.");
        }
        return livroOpt.get();
    }

    public List<Multa> consultarMultas(Long usuarioId) {
        return multaRepository.findByEmprestimoUsuarioUserID(usuarioId);
    }

    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }


    @Transactional
    public Livro registrarLivro(Livro livro) {
        if (livro.getAutor() == null || livro.getAutor().trim().isEmpty() ||
        livro.getEditora() == null || livro.getEditora().trim().isEmpty() ||
        livro.getCategoria() ==  null || livro.getCategoria().trim().isEmpty() ||
        livro.getAnoPublicacao() == null ||
        livro.getIsbn() == null || livro.getIsbn().trim().isEmpty()){
        throw new IllegalArgumentException("Erro ao registrar Livro. Verifique os dados fornecidos.");
    }
    // Verificar se ISBN já está em uso
    Optional<Livro> livroIsbn = livroRepository.findByIsbn(livro.getIsbn());
    if (livroIsbn.isPresent()) {
        throw new IllegalArgumentException("O ISBN já está em uso.");
    }
    // Definir quantidade como 0 se não estiver definida
    if (livro.getQuantidadeDisponivel() == null) {
        livro.setQuantidadeDisponivel(0);
    }
        return livroRepository.save(livro);
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

    @Transactional
    public Multa atualizarMulta(Long multaId, Multa novosDados) {
        Usuario usuario = usuarioRepository.findById(multaId)
        .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado."));
        if (!usuario.isAdministrador()) {
            throw new IllegalArgumentException("Acesso negado. Você não tem permissão para acessar essa funcionalidade.");
        }
        Optional<Multa> multaOpt = multaRepository.findById(multaId);
        if (!multaOpt.isPresent()) {
            throw new IllegalArgumentException("Multa não encontrada.");
        }
        Multa multa = multaOpt.get();
        if (novosDados.getValor() != null){
            multa.setValor(novosDados.getValor());
        }
        if (novosDados.getStatusPagamento() != null){
            multa.setStatusPagamento(novosDados.getStatusPagamento());
        }
        return multaRepository.save(multa);
    }
    
    @Transactional
    public Livro atualizarLivro(Long livroId, Livro novosDados) {
        Optional<Livro> livroOpt = livroRepository.findById(livroId);
        if (!livroOpt.isPresent()) {
            throw new IllegalArgumentException("Livro não encontrado.");
        }
        Livro livro = livroOpt.get();
        if (novosDados.getTitulo() != null){
            livro.setTitulo(novosDados.getTitulo());
        }
        if (novosDados.getAutor() != null){
            livro.setAutor(novosDados.getAutor());
        }
        if (novosDados.getQuantidadeDisponivel() != null){
            livro.setQuantidadeDisponivel(novosDados.getQuantidadeDisponivel());
        }
        if (novosDados.getCategoria() != null){
            livro.setCategoria(novosDados.getCategoria());
        }
        if (novosDados.getAnoPublicacao() != null){
            livro.setAnoPublicacao(novosDados.getAnoPublicacao());
        }
        if (novosDados.getEditora() != null){
        livro.setEditora(novosDados.getEditora());
        }
        if (novosDados.getIsbn() != null){
        livro.setIsbn(novosDados.getIsbn());
        }
        return livroRepository.save(livro);
    }

    public Usuario atualizarUsuario(Long usuarioId, Usuario novosDados) {
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
        if (novosDados.getTelefone() != null){
        usuario.setTelefone(novosDados.getTelefone());
        }
        if (novosDados.getCPF() != null){
        usuario.setCPF(novosDados.getCPF());
        }
        if (novosDados.getEndereco() != null){
        usuario.setEndereco(novosDados.getEndereco());
        }
        if (novosDados.getStatusConta() != null){
        usuario.setStatusConta(novosDados.getStatusConta());
        }
        return usuarioRepository.save(usuario);
    }


    @Transactional
    public Emprestimo realizarEmprestimo(Long userID, Long livroId) {
        // Verificar se o cliente existe
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(userID);
        if (!usuarioOpt.isPresent()) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
        Usuario usuario = usuarioOpt.get();

        // Buscar o livro e verificar disponibilidade
        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new IllegalArgumentException("Livro com ID " + livroId + " não encontrado."));

        if (livro.getQuantidadeDisponivel() <= 0) {
            throw new IllegalArgumentException("Livro '" + livro.getTitulo() + "' não está disponível para empréstimo.");
        }

        // Criar o empréstimo
        Emprestimo emprestimo = Emprestimo
        .builder()
        .usuario(usuario)
        .livro(livro)
        .dataEmprestimo(LocalDate.now())
        .dataDevolucaoPrevista(LocalDate.now().plusWeeks(2))
        .status("Em andamento")
        .build();
        

        // Atualizar disponibilidade do livro
        livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() - 1);
        livroRepository.save(livro);

        // Salvar o empréstimo
        return emprestimoRepository.save(emprestimo);
    }

    public String removerLivro(Long livroId) {
        if (!livroRepository.existsById(livroId)) {
            throw new IllegalArgumentException("Livro não encontrado.");
        }
        livroRepository.deleteById(livroId);
        return "Livro removido com sucesso.";
    }

    public String removerUsuario(Long id) {
        try {
        usuarioRepository.deleteById(id);
        return "Usuário removido com suceso";
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
    }

}