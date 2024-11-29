package com.biblioteca.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.model.Emprestimo;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    // Métodos específicos se necessário
    List<Emprestimo> findByUsuarioUserID(Long usuarioId);// For finding by client's userID
    
    // Find empréstimos with multa
    List<Emprestimo> findByMultaIsNotNull();
    
    // Find by cliente's userID and has multa
    List<Emprestimo> findByUsuarioUserIDAndMultaIsNotNull(Long usuarioId);
}