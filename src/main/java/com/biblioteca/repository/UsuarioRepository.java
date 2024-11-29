package com.biblioteca.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.biblioteca.model.Usuario;



@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByUserID(Long userID);
    Optional<Usuario> findByCPF(String CPF);
    Optional<Usuario> findByAdministrador(boolean administrador);
}