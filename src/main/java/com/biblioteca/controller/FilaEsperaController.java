package com.biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.model.FilaEspera;
import com.biblioteca.service.FilaEsperaService;

@RestController
@RequestMapping("/api/filaEspera")
public class FilaEsperaController {

    @Autowired
    private FilaEsperaService filaEsperaService;

    @PostMapping
    public ResponseEntity<FilaEspera> adicionarFila(@RequestBody FilaEspera filaEspera) {
        FilaEspera novaFila = filaEsperaService.adicionarFila(filaEspera);
        return ResponseEntity.ok(novaFila);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerFila(@PathVariable Long id) {
        filaEsperaService.removerFila(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<FilaEspera>> listarFila() {
        List<FilaEspera> fila = filaEsperaService.listarFila();
        return ResponseEntity.ok(fila);
    }

    // Outros endpoints conforme necessário
}