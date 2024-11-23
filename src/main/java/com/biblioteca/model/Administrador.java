package com.biblioteca.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;


@Entity
@Table(name = "Administradores")
public class Administrador extends Usuario {

    public Administrador() {
    }

    public Administrador(Long userID, String cPF, String nome, String email, String telefone, String endereco, LocalDate dataCadastro, String statusConta, String funcao, String senha) {
        super(userID, cPF, nome, email, telefone, endereco, dataCadastro, statusConta, funcao, senha);
    }

}