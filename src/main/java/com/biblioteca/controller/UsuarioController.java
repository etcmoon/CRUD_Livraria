package com.biblioteca.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biblioteca.model.Emprestimo;
import com.biblioteca.model.Livro;
import com.biblioteca.model.Multa;
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
     * @param loginRequest Objeto contendo email e senha do Usuário.
     * @return Resposta HTTP com o Usuário autenticado ou mensagem de erro.
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
     * Endpoint para listar todas as Multas de um Usuário.
     *
     * @param id ID do Usuário.
     * @return Resposta HTTP com a lista de Multas ou mensagem de erro.
     */
    @GetMapping("/{id}/multas")
    public ResponseEntity<?> listarMultas(@PathVariable Long id) {
        try {
            List<Multa> multas = usuarioService.listarMultas(id);
            return ResponseEntity.ok(multas);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    /**
     * Endpoint para listar todos os Usuários (administradores).
     *
     * @param id ID do Usuário solicitante.
     * @return Resposta HTTP com a lista de Usuários ou mensagem de erro.
     */
    @GetMapping("/{id}/usuarios")
    public ResponseEntity<?> listarUsuarios(@PathVariable Long id) {
        try {
            List<Usuario> usuarios = usuarioService.listarUsuarios(id);
            return ResponseEntity.ok(usuarios);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    /**
     * Endpoint para listar todos os Empréstimos de um Usuário.
     *
     * @param id ID do Usuário.
     * @return Resposta HTTP com a lista de Empréstimos ou mensagem de erro.
     */
    @GetMapping("/{id}/emprestimos")
    public ResponseEntity<?> listarEmprestimos(@PathVariable Long id) {
        try {
            List<Emprestimo> emprestimos = usuarioService.listarEmprestimos(id);
            return ResponseEntity.ok(emprestimos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    /**
     * Endpoint para listar todos os Livros acessíveis por um Usuário.
     *
     * @param id ID do Usuário.
     * @return Resposta HTTP com a lista de Livros ou mensagem de erro.
     */
    @GetMapping("/{id}/livros")
    public ResponseEntity<?> listarLivros(@PathVariable Long id) {
        try {
            List<Livro> livros = usuarioService.listarLivros(id);
            return ResponseEntity.ok(livros);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
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
    public ResponseEntity<?> alterarSenha(@PathVariable Long id, @RequestParam String novaSenha) {
        try {
            usuarioService.alterarSenha(id, novaSenha);
            return ResponseEntity.ok("Senha alterada com sucesso.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Endpoint para realizar Logout de um Usuário.
     *
     * @param id ID do Usuário.
     * @return Resposta HTTP indicando o sucesso ou falha da operação.
     */
    @PostMapping("/{id}/logout")
    public ResponseEntity<?> logout(@PathVariable Long id) {
        try {
            String message = usuarioService.logout(id);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Endpoint para consultar Empréstimos de um Cliente.
     *
     * @param clienteId ID do Cliente.
     * @return Resposta HTTP com a lista de Empréstimos ou mensagem de erro.
     */
    @GetMapping("/consultarEmprestimos/{clienteId}")
    public ResponseEntity<?> consultarEmprestimos(@PathVariable Long clienteId) {
        try {
            List<Emprestimo> emprestimos = usuarioService.consultarEmprestimos(clienteId);
            return ResponseEntity.ok(emprestimos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Endpoint para consultar um Livro por ID.
     *
     * @param livroId ID do Livro.
     * @return Resposta HTTP com o Livro ou mensagem de erro.
     */
    @GetMapping("/consultarLivro/{livroId}")
    public ResponseEntity<?> consultarLivro(@PathVariable Long livroId) {
        try {
            Livro livro = usuarioService.consultarLivro(livroId);
            return ResponseEntity.ok(livro);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * Endpoint para consultar Multas de um Usuário.
     *
     * @param usuarioId ID do Usuário.
     * @return Resposta HTTP com a lista de Multas ou mensagem de erro.
     */
    @GetMapping("/consultarMultas/{usuarioId}")
    public ResponseEntity<?> consultarMultas(@PathVariable Long usuarioId) {
        try {
            List<Multa> multas = usuarioService.consultarMultas(usuarioId);
            return ResponseEntity.ok(multas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Endpoint para buscar um Usuário por ID.
     *
     * @param id ID do Usuário.
     * @return Resposta HTTP com o Usuário ou mensagem de erro.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarUsuarioPorId(id);
        if (usuarioOpt.isPresent()) {
            return ResponseEntity.ok(usuarioOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
    }

    /**
     * Endpoint para atualizar os dados de cadastro de um Usuário.
     *
     * @param usuarioId ID do Usuário.
     * @param novosDados Novos dados do Usuário.
     * @return Resposta HTTP com o Usuário atualizado ou mensagem de erro.
     */
    @PutMapping("/{usuarioId}/atualizarCadastro")
    public ResponseEntity<?> atualizarCadastro(@PathVariable Long usuarioId, @RequestBody Usuario novosDados) {
        try {
            Usuario usuarioAtualizado = usuarioService.atualizarCadastro(usuarioId, novosDados);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Endpoint para atualizar uma Multa.
     *
     * @param multaId    ID da Multa.
     * @param novosDados Novos dados da Multa.
     * @return Resposta HTTP com a Multa atualizada ou mensagem de erro.
     */
    @PutMapping("/multas/{multaId}")
    public ResponseEntity<?> atualizarMulta(@PathVariable Long multaId, @RequestBody Multa novosDados) {
        try {
            Multa multaAtualizada = usuarioService.atualizarMulta(multaId, novosDados);
            return ResponseEntity.ok(multaAtualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Endpoint para atualizar um Livro.
     *
     * @param livroId    ID do Livro.
     * @param novosDados Novos dados do Livro.
     * @return Resposta HTTP com o Livro atualizado ou mensagem de erro.
     */
    @PutMapping("/livros/{livroId}")
    public ResponseEntity<?> atualizarLivro(@PathVariable Long livroId, @RequestBody Livro novosDados) {
        try {
            Livro livroAtualizado = usuarioService.atualizarLivro(livroId, novosDados);
            return ResponseEntity.ok(livroAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Endpoint para atualizar um Usuário.
     *
     * @param usuarioId  ID do Usuário.
     * @param novosDados Novos dados do Usuário.
     * @return Resposta HTTP com o Usuário atualizado ou mensagem de erro.
     */
    @PutMapping("/{usuarioId}/atualizarUsuario")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Long usuarioId, @RequestBody Usuario novosDados) {
        try {
            Usuario usuarioAtualizado = usuarioService.atualizarUsuario(usuarioId, novosDados);
            return ResponseEntity.ok(usuarioAtualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Endpoint para remover um Livro.
     *
     * @param livroId ID do Livro.
     * @return Resposta HTTP indicando o sucesso ou falha da operação.
     */
    @DeleteMapping("/livros/{livroId}")
    public ResponseEntity<?> removerLivro(@PathVariable Long livroId) {
        try {
            String message = usuarioService.removerLivro(livroId);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Endpoint para remover um Usuário.
     *
     * @param id ID do Usuário.
     * @return Resposta HTTP indicando o sucesso ou falha da operação.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removerUsuario(@PathVariable Long id) {
        try {
            String message = usuarioService.removerUsuario(id);
            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Endpoint para registrar um novo Livro.
     *
     * @param livro Os dados do Livro a ser registrado.
     * @return Resposta HTTP com o Livro registrado ou mensagem de erro.
     */
    @PostMapping("/livros")
    public ResponseEntity<?> registrarLivro(@RequestBody Livro livro) {
        try {
            Livro novoLivro = usuarioService.registrarLivro(livro);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoLivro);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Endpoint para realizar um Empréstimo.
     *
     * @param userID  ID do Usuário que está realizando o Empréstimo.
     * @param livroId ID do Livro a ser emprestado.
     * @return Resposta HTTP com o Empréstimo realizado ou mensagem de erro.
     */
    @PostMapping("/{userID}/emprestimos")
    public ResponseEntity<?> realizarEmprestimo(@PathVariable Long userID, @RequestBody Long livroId) {
        try {
            Emprestimo emprestimo = usuarioService.realizarEmprestimo(userID, livroId);
            return ResponseEntity.status(HttpStatus.CREATED).body(emprestimo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}