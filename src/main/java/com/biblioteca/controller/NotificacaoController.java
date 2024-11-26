package com.biblioteca.controller;

import java.util.List;
import java.util.Optional;

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

import com.biblioteca.model.Notificacao;
import com.biblioteca.model.Usuario;
import com.biblioteca.service.NotificacaoService;
import com.biblioteca.service.UsuarioService;

@RestController
@RequestMapping("/api/notificacoes")
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @Autowired
    private UsuarioService usuarioService;

    /**
     * Endpoint para enviar uma nova Notificação.
     *
     * @param notificacao Os dados da Notificação a ser enviada.
     * @return Resposta HTTP com a Notificação enviada ou mensagem de erro.
     */
    @PostMapping
    public ResponseEntity<?> enviarNotificacao(@RequestBody Notificacao notificacao) {
        try {
            Notificacao novaNotificacao = notificacaoService.enviarNotificacao(notificacao);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaNotificacao);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Endpoint para listar todas as Notificações de um Usuário.
     *
     * @param usuarioId ID do Usuário cujas Notificações serão listadas.
     * @return Resposta HTTP com a lista de Notificações ou mensagem de erro.
     */
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> listarNotificacoesPorUsuario(@PathVariable Long usuarioId) {
        try {
            Optional<Usuario> usuarioOpt = usuarioService.buscarUsuarioPorId(usuarioId);
            if (!usuarioOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
            }
            Usuario usuario = usuarioOpt.get();
            List<Notificacao> notificacoes = notificacaoService.listarNotificacoesPorUsuario(usuario);
            if (notificacoes.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Nenhuma notificação encontrada para este usuário.");
            }
            return ResponseEntity.ok(notificacoes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * Endpoint para marcar uma Notificação como lida.
     *
     * @param id ID da Notificação a ser marcada como lida.
     * @return Resposta HTTP indicando o sucesso ou falha da operação.
     */
    @PutMapping("/{id}/marcarComoLida")
    public ResponseEntity<?> marcarComoLida(@PathVariable Long id) {
        try {
            notificacaoService.marcarComoLida(id);
            return ResponseEntity.ok("Notificação marcada como lida.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Outros endpoints conforme necessário
}