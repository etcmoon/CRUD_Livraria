package com.biblioteca.controller;

import com.biblioteca.model.Administrador;
import com.biblioteca.service.AdministradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/administradores")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    @PostMapping
    public ResponseEntity<Administrador> criarAdministrador(@RequestBody Administrador administrador) {
        Administrador novoAdmin = administradorService.criarAdministrador(administrador);
        return ResponseEntity.ok(novoAdmin);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Administrador> buscarAdministradorPorId(@PathVariable Long id) {
        Optional<Administrador> adminOpt = administradorService.buscarAdministradorPorId(id);
        return adminOpt.map(ResponseEntity::ok)
                      .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Administrador>> listarAdministradores() {
        List<Administrador> admins = administradorService.listarAdministradores();
        return ResponseEntity.ok(admins);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Administrador> atualizarAdministrador(@PathVariable Long id, @RequestBody Administrador administrador) {
        administrador.setUserID(id);
        Administrador adminAtualizado = administradorService.atualizarAdministrador(administrador);
        return ResponseEntity.ok(adminAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarAdministrador(@PathVariable Long id) {
        administradorService.deletarAdministrador(id);
        return ResponseEntity.ok().build();
    }

    // Outros endpoints conforme necessário
}