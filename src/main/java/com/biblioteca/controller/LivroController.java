package com.biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.model.Livro;
import com.biblioteca.service.LivroService;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    /**
     * Endpoint para buscar um Livro por ID.
     *
     * @param id ID do Livro a ser buscado.
     * @return Resposta HTTP com o Livro encontrado ou mensagem de erro.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarLivroPorId(@PathVariable Long id) {
        try {
            Livro livro = livroService.buscarLivroPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado."));
            return ResponseEntity.ok(livro);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Endpoint para listar todos os Livros.
     *
     * @return Resposta HTTP com a lista de Livros.
     */
    @GetMapping
    public ResponseEntity<List<Livro>> listarLivros() {
        List<Livro> livros = livroService.listarLivros();
        return ResponseEntity.ok(livros);
    }

    /**
     * Endpoint para buscar Livros por título.
     *
     * @param titulo Título do Livro a ser buscado.
     * @return Resposta HTTP com a lista de Livros encontrados ou mensagem de erro.
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Livro>> buscarLivros(@RequestParam String isbn, String titulo, String autor, String categoria) {
        List<Livro> livros = livroService.buscarLivros(isbn, titulo, autor, categoria);
        if (livros.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(livros);
        }
        return ResponseEntity.ok(livros);
    }

}