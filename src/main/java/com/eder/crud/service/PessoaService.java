package com.eder.crud.service;

import com.eder.crud.model.Pessoa;
import com.eder.crud.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository repository;

    public List<Pessoa> findAll() {
        return repository.findAll();
    }

    public Optional<Pessoa> findPessoaByNome(String nome) {
        return repository.findByNomeEquals(nome);
    }

    public Optional<Pessoa> findbyCpf(String cpf) {
        return repository.findByCpfEquals(cpf);
    }

    public Pessoa save(Pessoa pessoa){
        return repository.save(pessoa);
    }

    public Optional<Pessoa> findById(Long id) {
        return repository.findById(id);
    }

    public Pessoa findByIdOrFail(Long id) {
        return repository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public void pessoaSetParente(Long userId, Long parenteId) {
        Pessoa pessoa = findByIdOrFail(userId);
        Pessoa parente = findByIdOrFail(parenteId);

        pessoa.setParente(parente);
        this.save(pessoa);
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
