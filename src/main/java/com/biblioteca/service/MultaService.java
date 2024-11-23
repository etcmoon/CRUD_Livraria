package com.biblioteca.service;

import com.biblioteca.model.Multa;
import com.biblioteca.repository.MultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MultaService {

    @Autowired
    private MultaRepository multaRepository;

    public Multa aplicarMulta(Multa multa) {
        return multaRepository.save(multa);
    }

    public Optional<Multa> buscarMultaPorId(Long id) {
        return multaRepository.findById(id);
    }

    public List<Multa> buscarMultasPorCliente(Long clienteId) {
        return multaRepository.findByEmprestimoClienteUserID(clienteId);
    }

    public Multa pagarMulta(Long id) {
        Optional<Multa> multaOpt = multaRepository.findById(id);
        if (multaOpt.isPresent()) {
            Multa multa = multaOpt.get();
            multa.setStatusPagamento("Pago");
            return multaRepository.save(multa);
        }
        return null;
    }

    public void isentarMulta(Long id) {
        Optional<Multa> multaOpt = multaRepository.findById(id);
        if (multaOpt.isPresent()) {
            Multa multa = multaOpt.get();
            multa.setValor(0.0);
            multa.setStatusPagamento("Isento");
            multaRepository.save(multa);
        }
    }

    // Outros métodos conforme necessário
}