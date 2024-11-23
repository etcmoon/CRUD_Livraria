package com.biblioteca.controller;

import com.biblioteca.model.Multa;
import com.biblioteca.service.MultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/multas")
public class MultaController {

    @Autowired
    private MultaService multaService;

    @PostMapping
    public ResponseEntity<Multa> aplicarMulta(@RequestBody Multa multa) {
        Multa novaMulta = multaService.aplicarMulta(multa);
        return ResponseEntity.ok(novaMulta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Multa> buscarMultaPorId(@PathVariable Long id) {
        Optional<Multa> multaOpt = multaService.buscarMultaPorId(id);
        return multaOpt.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Multa>> buscarMultasPorCliente(@PathVariable Long clienteId) {
        List<Multa> multas = multaService.buscarMultasPorCliente(clienteId);
        return ResponseEntity.ok(multas);
    }

    @PutMapping("/{id}/pagar")
    public ResponseEntity<Multa> pagarMulta(@PathVariable Long id) {
        Multa multa = multaService.pagarMulta(id);
        if(multa != null){
            return ResponseEntity.ok(multa);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/isentar")
    public ResponseEntity<Void> isentarMulta(@PathVariable Long id) {
        multaService.isentarMulta(id);
        return ResponseEntity.ok().build();
    }

    // Outros endpoints conforme necessário
}