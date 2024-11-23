package com.biblioteca.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "Livros")
public class Livro {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long livroID;

    private String autor;
    private String titulo;
    private String editora;
    private String categoria;
    private Integer anoPublicacao;
    private Integer quantidadeDisponivel;
    private String isbn;
    private Boolean disponivel;

    public Livro() {
    }
    
    public Long getLivroID() {
        return livroID;
    }
    public void setLivroID(Long livroID) {
        this.livroID = livroID;
    }
    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getEditora() {
        return editora;
    }
    public void setEditora(String editora) {
        this.editora = editora;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public Integer getAnoPublicacao() {
        return anoPublicacao;
    }
    public void setAnoPublicacao(Integer anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }
    public Integer getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }
    public void setQuantidadeDisponivel(Integer quantidadeDisponivel) {
        this.quantidadeDisponivel = quantidadeDisponivel;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public Boolean isDisponivel() {
        return disponivel;
    }
    public void setDisponivel(Boolean disponivel) {
        this.disponivel = disponivel;
    }
    public Livro(Long livroID, String autor, String titulo, String editora, String categoria, Integer anoPublicacao,
            Integer quantidadeDisponivel, String isbn, Boolean disponivel) {
        this.livroID = livroID;
        this.autor = autor;
        this.titulo = titulo;
        this.editora = editora;
        this.categoria = categoria;
        this.anoPublicacao = anoPublicacao;
        this.quantidadeDisponivel = quantidadeDisponivel;
        this.isbn = isbn;
        this.disponivel = disponivel;
    }

    
}