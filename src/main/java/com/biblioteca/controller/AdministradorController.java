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
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.model.Administrador;
import com.biblioteca.model.Livro;
import com.biblioteca.model.Multa;
import com.biblioteca.model.Usuario;
import com.biblioteca.service.AdministradorService;

@RestController
@RequestMapping("/api/administradores")
public class AdministradorController extends UsuarioController {

    @Autowired
    private AdministradorService administradorService;

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarAdministradorPorId(@PathVariable Long id) {
        try{
            Administrador administrador = administradorService.buscarAdministradorPorId(id).
                orElseThrow(() -> new IllegalArgumentException("Administrador não encontrado."));
            return ResponseEntity.ok(administrador);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
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
    public ResponseEntity<?> deletarAdministrador(@PathVariable Long id) {
        try {
            administradorService.deletarAdministrador(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Administrador não encontrado.");
        }
    }

    // Cadastrar um novo cliente
    @PostMapping("/usuarios")
    @Override
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario novoUsuario = administradorService.registrarUsuario(usuario);
            return ResponseEntity.ok(novoUsuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Listar todos os usuários
    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = administradorService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @PostMapping("/livros")
    public ResponseEntity<?> cadastrarLivro(@RequestBody Livro livro) {
        try {
            Livro novoLivro = administradorService.cadastrarLivro(livro);
            return ResponseEntity.ok(novoLivro);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // Atualizar informações de um livro existente
    @PutMapping("/livros/{livroId}")
    public ResponseEntity<?> atualizarLivro(@PathVariable Long livroId, @RequestBody Livro livro) {
        Livro livroAtualizado = administradorService.atualizarLivro(livroId, livro);
        return ResponseEntity.ok(livroAtualizado);
    }

    // Remover um livro
    @DeleteMapping("/livros/{livroId}")
    public ResponseEntity<Void> removerLivro(@PathVariable Long livroId) {
        administradorService.removerLivro(livroId);
        return ResponseEntity.ok().build();
    }

    // Atualizar Usuário
    @PutMapping("/usuarios/{usuarioId}")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Long usuarioId, @RequestBody Usuario novosDados) {
        Usuario usuarioAtualizado = administradorService.atualizarUsuario(usuarioId, novosDados);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    // Atualizar Multa
    @PutMapping("/multas/{multaId}")
    public ResponseEntity<Multa> atualizarMulta(@PathVariable Long multaId, @RequestBody Multa novosDados) {
        Multa multaAtualizada = administradorService.atualizarMulta(multaId, novosDados);
        return ResponseEntity.ok(multaAtualizada);
    }

     // Notificar um cliente
    @PostMapping("/clientes/{clienteId}/notificacoes")
    public ResponseEntity<Void> notificarCliente(@PathVariable Long clienteId, @RequestBody String mensagem) {
        administradorService.notificarCliente(clienteId, mensagem);
        return ResponseEntity.ok().build();
    }

    // Outros endpoints conforme necessário
}