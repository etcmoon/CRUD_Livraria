package com.biblioteca.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "Usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userID;

    @Column(unique = true)
    private String CPF;

    @Column(nullable = false)
    private String nome;

    @Column(unique = true)
    private String email;

    private String telefone;
    private String endereco;
    private LocalDate dataCadastro;
    private String statusConta;
    private String funcao;
    private String senha;
    
    public Long getUserID() {
        return userID;
    }
    public String getCPF() {
        return CPF;
    }
    public String getNome() {
        return nome;
    }
    public String getEmail() {
        return email;
    }
    public String getTelefone() {
        return telefone;
    }
    public String getEndereco() {
        return endereco;
    }
    public LocalDate getDataCadastro() {
        return dataCadastro;
    }
    public String getStatusConta() {
        return statusConta;
    }
    public String getFuncao() {
        return funcao;
    }
    public String getSenha() {
        return senha;
    }
    public void setUserID(Long userID) {
        this.userID = userID;
    }
    public void setCPF(String CPF) {
        this.CPF = CPF;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }
    public void setStatusConta(String statusConta) {
        this.statusConta = statusConta;
    }
    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }
    public void setSenha(String senhaHash) {
        this.senha = senhaHash;
    }
    public Usuario(Long userID, String CPF, String nome, String email, String telefone, String endereco,
            LocalDate dataCadastro, String statusConta, String funcao, String senha) {
        this.userID = userID;
        this.CPF = CPF;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
        this.dataCadastro = dataCadastro;
        this.statusConta = statusConta;
        this.funcao = funcao;
        this.senha = senha;
    }

    public Usuario(){
        
    }
}