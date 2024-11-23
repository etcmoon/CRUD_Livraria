package com.biblioteca.service;

import com.biblioteca.model.Notificacao;
import com.biblioteca.model.Usuario;
import com.biblioteca.repository.NotificacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificacaoService {

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    public Notificacao enviarNotificacao(Notificacao notificacao) {
        return notificacaoRepository.save(notificacao);
    }

    public List<Notificacao> listarNotificacoesPorUsuario(Usuario usuario) {
        return notificacaoRepository.findByDestinatario(usuario);
    }

    public void marcarComoLida(Long notificacaoId) {
        notificacaoRepository.findById(notificacaoId).ifPresent(notificacao -> {
            notificacao.setStatus("Lida");
            notificacaoRepository.save(notificacao);
        });
    }

    // Outros métodos conforme necessário
}