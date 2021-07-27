package com.eder.crud.dto;

import com.eder.crud.model.EstadoCivil;
import com.eder.crud.model.PessoaFisica;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;


@Getter
@Setter
public class PessoaFisicaDTO {

    @Id
    private Long id;

    @NotNull(message = "Por favor, insira um nome.")
    private String nome;

    @Column(unique = true)
    @Length(min = 11, max = 11, message = "O cpf deve possuir 11 digitos")
    private String cpf;

    @NotNull(message = "Por favor, insira uma data de nascimento.")
    @JsonFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dataNascimento;

    @NotNull(message = "Insira um estado civil.")
    private EstadoCivil estadoCivil;

    @ManyToOne
    private PessoaFisica parente;
}
