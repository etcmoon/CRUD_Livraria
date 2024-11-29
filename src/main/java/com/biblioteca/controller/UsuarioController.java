package com.biblioteca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.model.Usuario;
import com.biblioteca.service.UsuarioService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Endpoint para registrar um novo Usuário.
     *
     * @param usuario Os dados do Usuário a ser registrado.
     * @return Resposta HTTP com o Usuário registrado ou mensagem de erro.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario novoUsuario = usuarioService.registrarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Endpoint para login de Usuário.
     *
     * @param email Email do Usuário.
     * @param senha Senha do Usuário.
     * @return Resposta HTTP com o resultado do login ou mensagem de erro.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario loginRequest) {
        try {
            Usuario usuario = usuarioService.login(loginRequest.getEmail(), loginRequest.getSenha());
            return ResponseEntity.ok(usuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    /**
     * Endpoint para logout de Usuário.
     *
     * @param id ID do Usuário que está realizando o logout.
     * @return Resposta HTTP indicando o sucesso da operação.
     */
    @PostMapping("/{id}/logout")
    public ResponseEntity<String> logout(@PathVariable Long id) {
        try {
            usuarioService.logout(id);
            return ResponseEntity.ok("Logout realizado com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Endpoint para alterar a senha de um Usuário.
     *
     * @param id        ID do Usuário.
     * @param novaSenha Nova senha a ser definida.
     * @return Resposta HTTP indicando o sucesso ou falha da operação.
     */
    @PutMapping("/{id}/alterarSenha")
    public ResponseEntity<?> alterarSenha(@PathVariable Long id, @RequestParam(required = false) String novaSenha) {
        try {
            usuarioService.alterarSenha(id, novaSenha);
            return ResponseEntity.ok("Senha alterada com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}