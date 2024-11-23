package com.biblioteca.service;

import com.biblioteca.model.Livro;
import com.biblioteca.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public void removerLivro(Long id) {
        livroRepository.deleteById(id);
    }

    public List<Livro> buscarLivroPorCriterio(String criterio) {
        // Implementar lógica de busca
        return livroRepository.findAll(); // Placeholder
    }

    // Outros métodos conforme necessário
}