package com.eder.crud.dto;

import com.eder.crud.model.EstadoCivil;
import com.eder.crud.model.Pessoa;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;


@Getter
@Setter
public class PessoaDTO {

    @Id
    @NotNull
    private Long id;
    @NotNull(message = "Por favor insira um nome.")
    private String nome;
    @Column(unique = true)
    @Length(min = 11, max = 11, message = "O cpf deve possuir 11 digitos")
    private String cpf;
    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;
    @NotNull(message = "Insira um estado civil.")
    private EstadoCivil estadoCivil;
    @ManyToOne
    private Pessoa pai;
    @OneToMany
    private Set<Pessoa> dependentes;

}
