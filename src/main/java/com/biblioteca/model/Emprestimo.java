package com.biblioteca.model;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@Entity
@Table(name = "Emprestimos")
public class Emprestimo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long emprestID;

    @ManyToOne
    @JoinColumn(name = "clienteID", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "livroID", nullable = false)
    private Livro livro;

    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoEfetiva;
    private String status;
    
    @OneToOne(mappedBy = "emprestimo", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Multa multa;



    public Emprestimo() {
    }

    public Emprestimo(Long emprestID, Cliente cliente, Livro livro, LocalDate dataEmprestimo,
            LocalDate dataDevolucaoPrevista, LocalDate dataDevolucaoEfetiva, String status, Multa multa) {
        this.emprestID = emprestID;
        this.cliente = cliente;
        this.livro = livro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        this.dataDevolucaoEfetiva = dataDevolucaoEfetiva;
        this.status = status;
        this.multa = multa;
    }

    
}