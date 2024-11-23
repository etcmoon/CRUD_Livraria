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
@Table(name = "FilaEspera")
public class FilaEspera {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long filaID;

    @ManyToOne
    @JoinColumn(name = "livroID", nullable = false)
    private Livro livro;

    @ManyToOne
    @JoinColumn(name = "clienteID", nullable = false)
    private Cliente cliente;

    private LocalDate dataSolicitacao;
    private String status;

    public FilaEspera() {
    }

    public Long getFilaID() {
        return filaID;
    }

    public void setFilaID(Long filaID) {
        this.filaID = filaID;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    public void setDataSolicitacao(LocalDate dataSolicitacao) {
        this.dataSolicitacao = dataSolicitacao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FilaEspera(Long filaID, Livro livro, Cliente cliente, LocalDate dataSolicitacao, String status) {
        this.filaID = filaID;
        this.livro = livro;
        this.cliente = cliente;
        this.dataSolicitacao = dataSolicitacao;
        this.status = status;
    }

    
}