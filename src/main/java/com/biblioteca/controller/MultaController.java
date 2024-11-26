package com.biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.model.Multa;
import com.biblioteca.service.MultaService;

@RestController
@RequestMapping("/api/multas")
public class MultaController {

    @Autowired
    private MultaService multaService;

    /**
     * Endpoint para aplicar uma nova Multa.
     *
     * @param multa Os dados da Multa a ser aplicada.
     * @return Resposta HTTP com a Multa aplicada ou mensagem de erro.
     */
    @PostMapping
    public ResponseEntity<?> aplicarMulta(@RequestBody Multa multa) {
        try {
            Multa novaMulta = multaService.aplicarMulta(multa);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaMulta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Endpoint para buscar uma Multa por ID.
     *
     * @param id ID da Multa a ser buscada.
     * @return Resposta HTTP com a Multa encontrada ou mensagem de erro.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarMultaPorId(@PathVariable Long id) {
        try {
            Multa multa = multaService.buscarMultaPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Multa não encontrada."));
            return ResponseEntity.ok(multa);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Endpoint para buscar Multas por ID do Cliente.
     *
     * @param clienteId ID do Cliente cujas Multas serão buscadas.
     * @return Resposta HTTP com a lista de Multas ou mensagem de erro.
     */
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<?> buscarMultasPorCliente(@PathVariable Long clienteId) {
        try {
            List<Multa> multas = multaService.buscarMultasPorCliente(clienteId);
            if (multas.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma multa encontrada para este cliente.");
            }
            return ResponseEntity.ok(multas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Endpoint para pagar uma Multa.
     *
     * @param id ID da Multa a ser paga.
     * @return Resposta HTTP com a Multa atualizada ou mensagem de erro.
     */
    @PutMapping("/{id}/pagar")
    public ResponseEntity<?> pagarMulta(@PathVariable Long id) {
        try {
            Multa multa = multaService.pagarMulta(id);
            return ResponseEntity.ok(multa);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Endpoint para isentar uma Multa.
     *
     * @param id ID da Multa a ser isentada.
     * @return Resposta HTTP indicando o sucesso ou falha da operação.
     */
    @PutMapping("/{id}/isentar")
    public ResponseEntity<?> isentarMulta(@PathVariable Long id) {
        try {
            multaService.isentarMulta(id);
            return ResponseEntity.ok("Multa isentada com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Outros endpoints conforme necessário
}