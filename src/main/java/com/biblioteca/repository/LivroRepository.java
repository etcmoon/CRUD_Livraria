package com.biblioteca.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.biblioteca.model.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>, JpaSpecificationExecutor<Livro> {
    Optional<Livro> findByIsbn(String isbn);
    List<Livro> findByTitulo(String titulo);
    List<Livro> findByAutor(String autor);
    List<Livro> findByCategoria(String categoria);
}