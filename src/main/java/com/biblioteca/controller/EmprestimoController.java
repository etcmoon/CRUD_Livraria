package com.biblioteca.controller;

import com.biblioteca.model.Emprestimo;
import com.biblioteca.service.EmprestimoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/emprestimos")
public class EmprestimoController {

    @Autowired
    private EmprestimoService emprestimoService;

    @PostMapping
    public ResponseEntity<Emprestimo> registrarEmprestimo(@RequestBody Emprestimo emprestimo) {
        Emprestimo novoEmprestimo = emprestimoService.registrarEmprestimo(emprestimo);
        return ResponseEntity.ok(novoEmprestimo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Emprestimo> buscarEmprestimoPorId(@PathVariable Long id) {
        Optional<Emprestimo> emprestimoOpt = emprestimoService.buscarEmprestimoPorId(id);
        return emprestimoOpt.map(ResponseEntity::ok)
                            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Emprestimo>> listarEmprestimos() {
        List<Emprestimo> emprestimos = emprestimoService.listarEmprestimos();
        return ResponseEntity.ok(emprestimos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Emprestimo> atualizarEmprestimo(@PathVariable Long id, @RequestBody Emprestimo emprestimo) {
        emprestimo.setEmprestID(id);
        Emprestimo emprestimoAtualizado = emprestimoService.atualizarEmprestimo(emprestimo);
        return ResponseEntity.ok(emprestimoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarEmprestimo(@PathVariable Long id) {
        emprestimoService.deletarEmprestimo(id);
        return ResponseEntity.ok().build();
    }

    // Outros endpoints conforme necessário
}