package com.biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
     * Endpoint para adicionar um novo Livro.
     *
     * @param livro Os dados do Livro a ser adicionado.
     * @return Resposta HTTP com o Livro adicionado ou mensagem de erro.
     */
    @PostMapping
    public ResponseEntity<?> adicionarLivro(@RequestBody Livro livro) {
        try {
            Livro novoLivro = livroService.adicionarLivro(livro);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoLivro);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

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
     * Endpoint para remover um Livro.
     *
     * @param id ID do Livro a ser removido.
     * @return Resposta HTTP indicando o sucesso ou falha da operação.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerLivro(@PathVariable Long id) {
        try {
            livroService.removerLivro(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Endpoint para buscar Livros por título.
     *
     * @param titulo Título do Livro a ser buscado.
     * @return Resposta HTTP com a lista de Livros encontrados ou mensagem de erro.
     */
    @GetMapping("/buscar")
    public ResponseEntity<List<Livro>> buscarLivroTitulo(@RequestParam String titulo) {
        List<Livro> livros = livroService.buscarLivro(titulo);
        if (livros.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(livros);
        }
        return ResponseEntity.ok(livros);
    }

    /**
     * Endpoint para atualizar a quantidade disponível de um Livro.
     *
     * @param id         ID do Livro a ser atualizado.
     * @param quantidade Nova quantidade disponível.
     * @return Resposta HTTP com o Livro atualizado ou mensagem de erro.
     */
    @PutMapping("/{id}/quantidade")
    public ResponseEntity<?> atualizarQuantidade(@PathVariable Long id, @RequestParam int quantidade) {
        try {
            Livro livroAtualizado = livroService.atualizarQuantidade(id, quantidade);
            return ResponseEntity.ok(livroAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}