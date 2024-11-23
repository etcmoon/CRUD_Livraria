package com.biblioteca.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "Notificacoes")
public class Notificacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificacaoID;

    @ManyToOne
    @JoinColumn(name = "destinatarioID", nullable = false)
    private Usuario destinatario;

    private String mensagem;
    private LocalDate dataEnvio;
    private String status;
    public Long getNotificacaoID() {
        return notificacaoID;
    }
    public void setNotificacaoID(Long notificacaoID) {
        this.notificacaoID = notificacaoID;
    }
    public Usuario getDestinatario() {
        return destinatario;
    }
    public void setDestinatario(Usuario destinatario) {
        this.destinatario = destinatario;
    }
    public String getMensagem() {
        return mensagem;
    }
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
    public LocalDate getDataEnvio() {
        return dataEnvio;
    }
    public void setDataEnvio(LocalDate dataEnvio) {
        this.dataEnvio = dataEnvio;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Notificacao(Long notificacaoID, Usuario destinatario, String mensagem, LocalDate dataEnvio, String status) {
        this.notificacaoID = notificacaoID;
        this.destinatario = destinatario;
        this.mensagem = mensagem;
        this.dataEnvio = dataEnvio;
        this.status = status;
    }

    public Notificacao(){
        
    }
}