package com.eder.crud.service;

import com.eder.crud.model.PessoaFisica;
import com.eder.crud.repository.PessoaFisicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaFisicaService {

    @Autowired
    private PessoaFisicaRepository repository;

    public List<PessoaFisica> findAll() {
        return repository.findAll();
    }

    public Optional<PessoaFisica> findByNome(String nome) {
        return repository.findByNomeEquals(nome);
    }

    public Optional<PessoaFisica> findbyCpf(String cpf) {
        return repository.findByCpfEquals(cpf);
    }

    public Optional<PessoaFisica> findById(Long id) {
        return repository.findById(id);
    }

    public PessoaFisica findByIdOrFail(Long id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public PessoaFisica save(PessoaFisica pessoaFisica){
        return repository.save(pessoaFisica);
    }

    public void pessoaSetParente(Long userId, Long parenteId) {
        PessoaFisica pessoaFisica = findByIdOrFail(userId);
        PessoaFisica parente = findByIdOrFail(parenteId);

        pessoaFisica.setParente(parente);
        this.save(pessoaFisica);
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            repository.deleteById(id);
            repository.flush();

        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Entidade não encontrada");
        }
    }
}
