package com.biblioteca.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biblioteca.model.Emprestimo;
import com.biblioteca.model.Livro;
import com.biblioteca.model.Multa;
import com.biblioteca.repository.EmprestimoRepository;
import com.biblioteca.repository.LivroRepository;

@Service
public class EmprestimoService {

    @Autowired
    private EmprestimoRepository emprestimoRepository;

    @Autowired
    private LivroRepository livroRepository;


    public Optional<Emprestimo> buscarEmprestimoPorId(Long id) {
        return emprestimoRepository.findById(id);
    }

    public List<Emprestimo> listarEmprestimos() {
        return emprestimoRepository.findAll();
    }

    public Emprestimo atualizarEmprestimo(Emprestimo emprestimo) {
        return emprestimoRepository.save(emprestimo);
    }

    public void deletarEmprestimo(Long id) {
        emprestimoRepository.deleteById(id);
    }

    // Registrar a devolução de um empréstimo
    public Emprestimo registrarDevolucao(Long emprestimoId, LocalDate dataDevolucao) {
        Optional<Emprestimo> emprestimoOpt = emprestimoRepository.findById(emprestimoId);
        if (!emprestimoOpt.isPresent()) {
            throw new IllegalArgumentException("Empréstimo não encontrado.");
        }

        Emprestimo emprestimo = emprestimoOpt.get();
        emprestimo.setDataDevolucaoEfetiva(dataDevolucao);
        emprestimo.setStatus("Devolvido");

        // Calcular multa se houver atraso
        if (dataDevolucao.isAfter(emprestimo.getDataDevolucaoPrevista().plusDays(1))) {
            Multa multa = calcularMulta(emprestimo);
            emprestimo.setMulta(multa);
        }

        // Atualizar a quantidade disponível do livro após devolução
        Livro livro = emprestimo.getLivro();
        livro.setQuantidadeDisponivel(livro.getQuantidadeDisponivel() + 1);
        livroRepository.save(livro);

        return emprestimoRepository.save(emprestimo);
    }

    // Calcular multa com base na data de devolução
    public Multa calcularMulta(Emprestimo emprestimo) {
        LocalDate dataDevolucao = emprestimo.getDataDevolucaoEfetiva();
        LocalDate dataPrevista = emprestimo.getDataDevolucaoPrevista();

        if (dataDevolucao.isAfter(dataPrevista.plusDays(1))) {
            long diasAtraso = java.time.temporal.ChronoUnit.DAYS.between(dataPrevista.plusDays(1), dataDevolucao);
            double valorMultaDiaria = 5.0; // Valor da multa por dia
            Multa multa = new Multa();
            multa.setValor(diasAtraso * valorMultaDiaria);
            return multa;
        }
        Multa multa = new Multa();
        multa.setValor(0.0);
        return multa;
    }

    // Consultar o status de um empréstimo
    public String consultarStatus(Long emprestimoId) {
        Optional<Emprestimo> emprestimoOpt = emprestimoRepository.findById(emprestimoId);
        if (!emprestimoOpt.isPresent()) {
            throw new IllegalArgumentException("Empréstimo não encontrado.");
        }

        Emprestimo emprestimo = emprestimoOpt.get();
        return emprestimo.getStatus();
    }

    // Outros métodos conforme necessário
}