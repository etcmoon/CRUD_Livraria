package com.biblioteca.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.model.Usuario;
import com.biblioteca.service.UsuarioService;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<Usuario> registerUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario novoUsuario = usuarioService.registrarUsuario(usuario);
            return ResponseEntity.ok(novoUsuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestParam String email, @RequestParam String senha) {
        Optional<Usuario> usuarioOpt = usuarioService.login(email, senha);
        if (usuarioOpt.isPresent()) {
            return ResponseEntity.ok(usuarioOpt.get());
        }
        return ResponseEntity.status(401).build();
    }

    @PostMapping("/{id}/logout")
    public ResponseEntity<String> logout(@PathVariable Long id) {
        try {
            String mensagem = usuarioService.logout(id);
            return ResponseEntity.ok(mensagem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PutMapping("/{id}/alterarSenha")
    public ResponseEntity<Void> alterarSenha(@PathVariable Long id, @RequestParam String novaSenha) {
        usuarioService.alterarSenha(id, novaSenha);
        return ResponseEntity.ok().build();
    }

    // Outros endpoints conforme necessário
}