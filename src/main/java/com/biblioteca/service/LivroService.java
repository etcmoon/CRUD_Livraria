package com.biblioteca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.model.Livro;
import com.biblioteca.repository.LivroRepository;

import jakarta.transaction.Transactional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public Livro adicionarLivro(Livro livro) {
        atualizarDisponibilidade(livro);
        return livroRepository.save(livro);
    }

    public Optional<Livro> buscarLivroPorId(Long id) {
        try{
            return livroRepository.findById(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Livro não encontrado.");
        }
    }

    public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }

    public Livro atualizarLivro(Livro livro) {
        atualizarDisponibilidade(livro);
        return livroRepository.save(livro);
    }

    public boolean verificarDisponibilidade(Long id) {
        Optional<Livro> livroOpt = livroRepository.findById(id);
        if (!livroOpt.isPresent()) {
            throw new IllegalArgumentException("Livro não encontrado.");
        }
        return livroOpt.get().isDisponivel();
    }

    public Livro editarLivro(Long id, Livro novosDados) {
        Optional<Livro> livroOpt = livroRepository.findById(id);
        if (!livroOpt.isPresent()) {
            throw new IllegalArgumentException("Livro não encontrado.");
        }
        Livro livro = livroOpt.get();
        livro.setTitulo(novosDados.getTitulo());
        livro.setAutor(novosDados.getAutor());
        livro.setCategoria(novosDados.getCategoria());
        livro.setEditora(novosDados.getEditora());
        livro.setAnoPublicacao(novosDados.getAnoPublicacao());
        livro.setIsbn(novosDados.getIsbn());
        livro.setQuantidadeDisponivel(novosDados.getQuantidadeDisponivel());
        atualizarDisponibilidade(livro);
        return livroRepository.save(livro);
    }

    @Transactional
    public void removerLivro(Long id) {
        if (!livroRepository.existsById(id)) {
            throw new IllegalArgumentException("Livro não encontrado.");
        }
        livroRepository.deleteById(id);
    }

    public List<Livro> buscarLivro(String titulo) {
        return livroRepository.findByTitulo(titulo);
    }

    public Livro atualizarQuantidade(Long id, int quantidade) {
        Optional<Livro> livroOpt = livroRepository.findById(id);
        if (!livroOpt.isPresent()) {
            throw new IllegalArgumentException("Livro não encontrado.");
        }
        Livro livro = livroOpt.get();
        livro.setQuantidadeDisponivel(quantidade);
        atualizarDisponibilidade(livro);
        return livroRepository.save(livro);
    }

    // Método auxiliar para atualizar a disponibilidade do livro
    private void atualizarDisponibilidade(Livro livro) {
        if (livro.getQuantidadeDisponivel() != null && livro.getQuantidadeDisponivel() == 0) {
            livro.setDisponivel(false);
        } else {
            livro.setDisponivel(true);
        }
    }

    // Outros métodos conforme necessário
}