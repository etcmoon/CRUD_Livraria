package com.biblioteca.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.model.Livro;
import com.biblioteca.repository.LivroRepository;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public Livro adicionarLivro(Livro livro) {
        return livroRepository.save(livro);
    }

    public Optional<Livro> buscarLivroPorId(Long id) {
        return livroRepository.findById(id);
    }

    public List<Livro> listarLivros() {
        return livroRepository.findAll();
    }

    public Livro atualizarLivro(Livro livro) {
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
        livro.setDisponivel(novosDados.isDisponivel());
        // Atualize outros campos conforme necessário
        return livroRepository.save(livro);
    }

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
        return livroRepository.save(livro);
    }

    // Outros métodos conforme necessário
}