package com.biblioteca.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/administradores")
public class AdministradorController extends UsuarioController {

    @Autowired
    private AdministradorService administradorService;

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarAdministradorPorId(@PathVariable Long id) {
        try {
            Administrador administrador = administradorService.buscarAdministradorPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Administrador não encontrado."));
            return ResponseEntity.ok(administrador);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Administrador>> listarAdministradores() {
        List<Administrador> admins = administradorService.listarAdministradores();
        return ResponseEntity.ok(admins);
    }

    /**
     * Endpoint para cadastrar um novo Usuário.
     *
     * @param usuario Os dados do Usuário a ser criado.
     * @return Resposta HTTP com o Usuário criado ou mensagem de erro.
     */
    @PostMapping("/usuarios")
    public ResponseEntity<?> criarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario novoUsuario = administradorService.registrarUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Endpoint para listar todos os Usuários.
     *
     * @return Resposta HTTP com a lista de Usuários.
     */
    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = administradorService.listarUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    /**
     * Endpoint para cadastrar um novo Livro.
     *
     * @param livro Os dados do Livro a ser criado.
     * @return Resposta HTTP com o Livro criado ou mensagem de erro.
     */
    @PostMapping("/livros")
    public ResponseEntity<?> cadastrarLivro(@RequestBody Livro livro) {
        try {
            Livro novoLivro = administradorService.registrarLivro(livro);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoLivro);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Endpoint para atualizar informações de um Livro existente.
     *
     * @param livroId ID do Livro a ser atualizado.
     * @param livro   Dados atualizados do Livro.
     * @return Resposta HTTP com o Livro atualizado ou mensagem de erro.
     */
    @PutMapping("/livros/{livroId}")
    public ResponseEntity<?> atualizarLivro(@PathVariable Long livroId, @RequestBody Livro livro) {
        try {
            Livro livroAtualizado = administradorService.atualizarLivro(livroId, livro);
            return ResponseEntity.ok(livroAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Endpoint para remover um Livro.
     *
     * @param livroId ID do Livro a ser removido.
     * @return Resposta HTTP indicando o sucesso ou falha da operação.
     */
    @DeleteMapping("/livros/{livroId}")
    public ResponseEntity<Void> removerLivro(@PathVariable Long livroId) {
        try {
            administradorService.removerLivro(livroId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Endpoint para atualizar um Usuário existente.
     *
     * @param usuarioId ID do Usuário a ser atualizado.
     * @param novosDados Dados atualizados do Usuário.
     * @return Resposta HTTP com o Usuário atualizado ou mensagem de erro.
     */
    @PutMapping("/usuarios/{usuarioId}")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Long usuarioId, @RequestBody Usuario novosDados) {
        try {
            Usuario usuarioAtualizado = administradorService.atualizarUsuario(usuarioId, novosDados);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Endpoint para atualizar uma Multa existente.
     *
     * @param multaId  ID da Multa a ser atualizada.
     * @param novosDados Dados atualizados da Multa.
     * @return Resposta HTTP com a Multa atualizada ou mensagem de erro.
     */
    @PutMapping("/multas/{multaId}")
    public ResponseEntity<Multa> atualizarMulta(@PathVariable Long multaId, @RequestBody Multa novosDados) {
        try {
            Multa multaAtualizada = administradorService.atualizarMulta(multaId, novosDados);
            return ResponseEntity.ok(multaAtualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    /**
     * Endpoint para notificar um Cliente.
     *
     * @param clienteId ID do Cliente a ser notificado.
     * @param mensagem  Mensagem de notificação.
     * @return Resposta HTTP indicando o sucesso ou falha da operação.
     */
    @PostMapping("/clientes/{clienteId}/notificacoes")
    public ResponseEntity<Void> notificarCliente(@PathVariable Long clienteId, @RequestBody String mensagem) {
        try {
            administradorService.notificarCliente(clienteId, mensagem);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}