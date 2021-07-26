package com.eder.crud.repository;

import com.eder.crud.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    Optional<Pessoa> findByNomeEquals(String nome);
    Optional<Pessoa> findByCpfEquals(String cpf);
}
