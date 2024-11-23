package com.biblioteca.controller;

import com.biblioteca.model.Notificacao;
import com.biblioteca.model.Usuario;
import com.biblioteca.service.NotificacaoService;
import com.biblioteca.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notificacoes")
public class NotificacaoController {

    @Autowired
    private NotificacaoService notificacaoService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Notificacao> enviarNotificacao(@RequestBody Notificacao notificacao) {
        Notificacao novaNotificacao = notificacaoService.enviarNotificacao(notificacao);
        return ResponseEntity.ok(novaNotificacao);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Notificacao>> listarNotificacoesPorUsuario(@PathVariable Long usuarioId) {
        Usuario destinatario = usuarioService.buscarUsuarioPorId(usuarioId).orElse(null);
        if(destinatario != null){
            List<Notificacao> notificacoes = notificacaoService.listarNotificacoesPorUsuario(destinatario);
            return ResponseEntity.ok(notificacoes);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/marcarComoLida")
    public ResponseEntity<Void> marcarComoLida(@PathVariable Long id) {
        notificacaoService.marcarComoLida(id);
        return ResponseEntity.ok().build();
    }

    // Outros endpoints conforme necessário
}