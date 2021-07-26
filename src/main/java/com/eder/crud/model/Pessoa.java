package com.eder.crud.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cpf;

    @JsonFormat(pattern = "dd/MM/yyyy")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date dataNascimento;
    private EstadoCivil estadoCivil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parente", referencedColumnName = "id")
    private Pessoa parente;

    @OneToMany(
            cascade = {CascadeType.ALL},
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "parente")
    private List<Pessoa> dependentes;
}
