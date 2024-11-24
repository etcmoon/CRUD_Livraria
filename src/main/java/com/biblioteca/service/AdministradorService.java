package com.biblioteca.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.model.Administrador;
import com.biblioteca.model.Cliente;
import com.biblioteca.model.Emprestimo;
import com.biblioteca.model.Livro;
import com.biblioteca.model.Multa;
import com.biblioteca.model.Notificacao;
import com.biblioteca.model.Usuario;
import com.biblioteca.repository.AdministradorRepository;
import com.biblioteca.repository.ClienteRepository;
import com.biblioteca.repository.EmprestimoRepository;
import com.biblioteca.repository.LivroRepository;
import com.biblioteca.repository.MultaRepository;
import com.biblioteca.repository.NotificacaoRepository;
import com.biblioteca.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class AdministradorService extends UsuarioService {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private MultaRepository multaRepository;

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    // Buscar administrador por ID
    public Optional<Administrador> buscarAdministradorPorId(Long id) {
        try {
            return administradorRepository.findById(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Administrador não encontrado.");
        }
    }

    

    // Listar todos os administradores
    public List<Administrador> listarAdministradores() {
        return administradorRepository.findAll();
    }

    // Listar todos os usuários
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Listar todos os empréstimos
    public List<Emprestimo> listarEmprestimos() {
        return emprestimoRepository.findAll();
    }

    // Listar todos os livros
    public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }

    // Listar todas as multas
    public List<Multa> listarMultas() {
        return multaRepository.findAll();
    }

    // Listar todos os clientes
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }

    // Listar todas filas de espera
    public List<Emprestimo> listarFilaEspera() {
        return emprestimoRepository.findAll();
    }

    // Listar todas as notificações
    public List<Notificacao> listarNotificacoes() {
        return notificacaoRepository.findAll();
    }

    // Atualizar administrador
    public Administrador atualizarAdministrador(Administrador administrador) {
        return administradorRepository.save(administrador);
    }



    // Cadastrar um novo cliente
    @Transactional
    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        Optional<Usuario> usuarioCPF = usuarioRepository.findByCPF(usuario.getCPF());
        if (usuario.getNome() == null || usuario.getNome().trim().isEmpty() ||
        usuario.getEmail() == null || usuario.getEmail().trim().isEmpty() ||
        usuario.getCPF() == null || usuario.getCPF().trim().isEmpty() ||
        usuario.getSenha() == null || usuario.getSenha().trim().isEmpty() ||
        usuario.getFuncao() == null || usuario.getFuncao().trim().isEmpty()) {
        throw new IllegalArgumentException("Erro ao registrar usuário. Verifique os dados fornecidos.");
    }
        // Verificar se o email já está em uso
        Optional<Usuario> usuarioEmail = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioEmail.isPresent()) {
            throw new IllegalArgumentException("O email já está em uso.");
        }

        // Verificar se o CPF já está em uso
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

        // Salvar o usuário no banco de dados
        return usuarioRepository.save(usuario);
    }

    // Atualizar Usuário
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
        if (novosDados.getFuncao() != null){
        usuario.setFuncao(novosDados.getFuncao());
        }
        if (novosDados.getEndereco() != null){
        usuario.setEndereco(novosDados.getEndereco());
        }
        if (novosDados.getStatusConta() != null){
        usuario.setStatusConta(novosDados.getStatusConta());
        }
        return usuarioRepository.save(usuario);
    }

    // Remover usuário por ID
    public String removerUsuario(Long id) {
        try {
        usuarioRepository.deleteById(id);
        return "Usuário removido com suceso";
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }
    }

    // Cadastrar um novo livro
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

    // Definir disponibilidade como true se não estiver definida
    if (livro.isDisponivel() == null) {
        livro.setDisponivel(true);
    }
    // Definir quantidade como 0 se não estiver definida
    if (livro.getQuantidadeDisponivel() == null) {
        livro.setQuantidadeDisponivel(0);
    }
        return livroRepository.save(livro);
    }

    // Atualizar informações de um livro existente
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

    // Remover um livro
    public String removerLivro(Long livroId) {
        if (!livroRepository.existsById(livroId)) {
            throw new IllegalArgumentException("Livro não encontrado.");
        }
        livroRepository.deleteById(livroId);
        return "Livro removido com sucesso.";
    }

    // Atualizar multas de um cliente
    @Transactional
    public Multa atualizarMulta(Long multaId, Multa novosDados) {
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

    // Notificar um cliente
    public void notificarCliente(Long clienteId, String mensagem) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
        if (!clienteOpt.isPresent()) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
        Cliente cliente = clienteOpt.get();
        // Implementar lógica de notificação, como enviar email
        // Exemplo simplificado:
        System.out.println("Notificação para " + cliente.getEmail() + ": " + mensagem);
    }

    // Atualizar Multa

}