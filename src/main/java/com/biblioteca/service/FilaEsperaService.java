package com.biblioteca.service;

import com.biblioteca.model.FilaEspera;
import com.biblioteca.repository.FilaEsperaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilaEsperaService {

    @Autowired
    private FilaEsperaRepository filaEsperaRepository;

    public FilaEspera adicionarFila(FilaEspera filaEspera) {
        return filaEsperaRepository.save(filaEspera);
    }

    public void removerFila(Long filaID) {
        filaEsperaRepository.deleteById(filaID);
    }

    public List<FilaEspera> listarFila() {
        return filaEsperaRepository.findAll();
    }

    // Outros métodos conforme necessário
}