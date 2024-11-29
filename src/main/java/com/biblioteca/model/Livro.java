package com.biblioteca.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "Livros")
public class Livro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long livroID;
    
    @Column(nullable = false)
    private String autor;
    @Column(nullable = false)
    private String titulo;
    @Column(nullable = false)
    private String editora;
    @Column(nullable = false)
    private String categoria;
    @Column(nullable = false)
    private Integer anoPublicacao;
    private Integer quantidadeDisponivel;
    @Column(unique = true, nullable = false)
    private String isbn;
    private Boolean disponivel;

    public boolean isDisponivel() {
        return this.quantidadeDisponivel > 0;
    }

}