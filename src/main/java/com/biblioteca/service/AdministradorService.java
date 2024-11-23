package com.biblioteca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.model.Administrador;
import com.biblioteca.model.Cliente;
import com.biblioteca.model.Livro;
import com.biblioteca.model.Multa;
import com.biblioteca.model.Usuario;
import com.biblioteca.repository.AdministradorRepository;
import com.biblioteca.repository.ClienteRepository;
import com.biblioteca.repository.LivroRepository;
import com.biblioteca.repository.MultaRepository;
import com.biblioteca.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
public class AdministradorService {

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

    public Administrador criarAdministrador(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    public Optional<Administrador> buscarAdministradorPorId(Long id) {
        return administradorRepository.findById(id);
    }

    public List<Administrador> listarAdministradores() {
        return administradorRepository.findAll();
    }

    public Administrador atualizarAdministrador(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    public void deletarAdministrador(Long id) {
        administradorRepository.deleteById(id);
    }

    // Cadastrar um novo cliente
    public Cliente cadastrarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    // Atualizar informações de um cliente existente
    public Cliente atualizarCliente(Long clienteId, Cliente novosDados) {
        Optional<Cliente> clienteOpt = clienteRepository.findById(clienteId);
        if (!clienteOpt.isPresent()) {
            throw new IllegalArgumentException("Cliente não encontrado.");
        }
        Cliente cliente = clienteOpt.get();
        cliente.setNome(novosDados.getNome());
        cliente.setEmail(novosDados.getEmail());
        // Atualize outros campos conforme necessário
        return clienteRepository.save(cliente);
    }

    // Listar todos os usuários
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    // Atualizar multas de um cliente
    @Transactional
    public Multa atualizarMultas(Long multaId, Multa novosDados) {
        Optional<Multa> multaOpt = multaRepository.findById(multaId);
        if (!multaOpt.isPresent()) {
            throw new IllegalArgumentException("Multa não encontrada.");
        }
        Multa multa = multaOpt.get();
        multa.setValor(novosDados.getValor());
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

    // Cadastrar um novo livro
    public Livro cadastrarLivro(Livro livro) {
        return livroRepository.save(livro);
    }

    // Atualizar informações de um livro existente
    public Livro atualizarLivro(Long livroId, Livro novosDados) {
        Optional<Livro> livroOpt = livroRepository.findById(livroId);
        if (!livroOpt.isPresent()) {
            throw new IllegalArgumentException("Livro não encontrado.");
        }
        Livro livro = livroOpt.get();
        livro.setTitulo(novosDados.getTitulo());
        livro.setAutor(novosDados.getAutor());
        livro.setDisponivel(novosDados.isDisponivel());
        return livroRepository.save(livro);
    }

    // Remover um livro
    public void removerLivro(Long livroId) {
        if (!livroRepository.existsById(livroId)) {
            throw new IllegalArgumentException("Livro não encontrado.");
        }
        livroRepository.deleteById(livroId);
    }

    // Outros métodos conforme necessário
}