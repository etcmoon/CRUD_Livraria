package com.biblioteca.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "Multas")
public class Multa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long multaID;

    @OneToOne
    @JoinColumn(name = "emprestimoID", nullable = false)
    private Emprestimo emprestimo;

    private Double valor;
    private LocalDate dataAplicacao;
    private String statusPagamento;
    public Long getMultaID() {
        return multaID;
    }
    public void setMultaID(Long multaID) {
        this.multaID = multaID;
    }
    public Emprestimo getEmprestimo() {
        return emprestimo;
    }
    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }
    public Double getValor() {
        return valor;
    }
    public void setValor(Double valor) {
        this.valor = valor;
    }
    public LocalDate getDataAplicacao() {
        return dataAplicacao;
    }
    public void setDataAplicacao(LocalDate dataAplicacao) {
        this.dataAplicacao = dataAplicacao;
    }
    public String getStatusPagamento() {
        return statusPagamento;
    }
    public void setStatusPagamento(String statusPagamento) {
        this.statusPagamento = statusPagamento;
    }
    public Multa(Long multaID, Emprestimo emprestimo, Double valor, LocalDate dataAplicacao, String statusPagamento) {
        this.multaID = multaID;
        this.emprestimo = emprestimo;
        this.valor = valor;
        this.dataAplicacao = dataAplicacao;
        this.statusPagamento = statusPagamento;
    }

    public Multa(){
        
    }
}