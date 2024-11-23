package com.biblioteca.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Clientes")
public class Cliente extends Usuario {

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Emprestimo> emprestimosAtivos;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FilaEspera> filaEspera;

    public Cliente() {
    }

    public List<Emprestimo> getEmprestimosAtivos() {
        return emprestimosAtivos;
    }

    public void setEmprestimosAtivos(List<Emprestimo> emprestimosAtivos) {
        this.emprestimosAtivos = emprestimosAtivos;
    }

    public List<FilaEspera> getFilaEspera() {
        return filaEspera;
    }

    public void setFilaEspera(List<FilaEspera> filaEspera) {
        this.filaEspera = filaEspera;
    }

    public Cliente(Long userID, String cPF, String nome, String email, String telefone, String endereco,
            LocalDate dataCadastro, String statusConta, String funcao, String senha, List<Emprestimo> emprestimosAtivos,
            List<FilaEspera> filaEspera) {
        super(userID, cPF, nome, email, telefone, endereco, dataCadastro, statusConta, funcao, senha);
        this.emprestimosAtivos = emprestimosAtivos;
        this.filaEspera = filaEspera;
    }

    
}