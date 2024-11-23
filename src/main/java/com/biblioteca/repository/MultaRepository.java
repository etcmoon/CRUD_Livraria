package com.biblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.model.Multa;

@Repository
public interface MultaRepository extends JpaRepository<Multa, Long> {
    List<Multa> findByEmprestimoClienteUserID(Long clienteID);
}