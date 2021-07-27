package com.eder.crud.model;

import lombok.Getter;

@Getter
public enum EstadoCivil {


    SOLTEIRO( "Solteiro(a)"),
    UNIAOESTAVEL("União Estável"),
    CASADO("Casado(a)"),
    DIVORCIADO("Divorciado(a)"),
    VIUVO("Viúvo(a)");

    private String s;

    EstadoCivil(String s) {
        this.s = s;
    }

}