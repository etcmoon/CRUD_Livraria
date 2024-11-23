package com.biblioteca.service;

import com.biblioteca.model.Administrador;
import com.biblioteca.repository.AdministradorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    public Administrador criarAdministrador(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    public Optional<Administrador> buscarAdministradorPorId(Long id) {
        return administradorRepository.findById(id);
    }

    public List<Administrador> listarAdministradores() {
        return administradorRepository.findAll();
    }

    public Administrador atualizarAdministrador(Administrador administrador) {
        return administradorRepository.save(administrador);
    }

    public void deletarAdministrador(Long id) {
        administradorRepository.deleteById(id);
    }

    // Outros métodos conforme necessário
}