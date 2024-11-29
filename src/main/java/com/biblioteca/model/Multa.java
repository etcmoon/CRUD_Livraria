package com.biblioteca.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Multas")
public class Multa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long multaID;

    @OneToOne
    @JoinColumn(name = "emprestimoID", nullable = false)
    private Emprestimo emprestimo;

    @ManyToOne
    @JoinColumn(name = "clienteID", nullable = false)
    private Usuario usuario;

    private Double valor;
    private LocalDate dataAplicacao;
    private String statusPagamento;

}