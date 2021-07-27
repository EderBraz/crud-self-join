package com.eder.crud.repository;

import com.eder.crud.model.PessoaFisica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, Long> {

    Optional<PessoaFisica> findByNomeEquals(String nome);
    Optional<PessoaFisica> findByCpfEquals(String cpf);
}
