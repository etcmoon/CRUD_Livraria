package com.biblioteca.repository;

import com.biblioteca.model.FilaEspera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilaEsperaRepository extends JpaRepository<FilaEspera, Long> {
    // Métodos específicos se necessário
}