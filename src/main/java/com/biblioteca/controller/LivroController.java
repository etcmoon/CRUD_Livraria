package com.biblioteca.controller;

import com.biblioteca.model.Livro;
import com.biblioteca.service.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @PostMapping
    public ResponseEntity<Livro> adicionarLivro(@RequestBody Livro livro) {
        Livro novoLivro = livroService.adicionarLivro(livro);
        return ResponseEntity.ok(novoLivro);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarLivroPorId(@PathVariable Long id) {
        Optional<Livro> livroOpt = livroService.buscarLivroPorId(id);
        return livroOpt.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Livro>> listarLivros() {
        List<Livro> livros = livroService.listarLivros();
        return ResponseEntity.ok(livros);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Livro> atualizarLivro(@PathVariable Long id, @RequestBody Livro livro) {
        livro.setLivroID(id);
        Livro livroAtualizado = livroService.atualizarLivro(livro);
        return ResponseEntity.ok(livroAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerLivro(@PathVariable Long id) {
        livroService.removerLivro(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<Livro>> buscarLivroPorCriterio(@RequestParam String criterio) {
        List<Livro> livros = livroService.buscarLivroPorCriterio(criterio);
        return ResponseEntity.ok(livros);
    }

    // Outros endpoints conforme necessário
}