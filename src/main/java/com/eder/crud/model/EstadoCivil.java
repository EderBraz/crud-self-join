package com.eder.crud.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum EstadoCivil {

    SOLTEIRO( "Solteiro(a)"),
    UNIAOESTAVEL("União Estável"),
    CASADO("Casado(a)"),
    DIVORCIADO("Divorciado(a)"),
    VIUVO("Viúvo(a)");

    private String valor;

    public EstadoCivil getEtapaBy(String valor) {
        for (EstadoCivil etapa : EstadoCivil.values()) {
            if (etapa.getValor().equalsIgnoreCase(valor)) {
                return etapa;
            }
        }
        return null;
    }
}