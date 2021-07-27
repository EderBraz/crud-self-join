package com.eder.crud;

import com.eder.crud.dto.PessoaFisicaDTO;
import com.eder.crud.model.EstadoCivil;
import com.eder.crud.model.PessoaFisica;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PessoaFisicaTest {

    @Autowired
    private ModelMapper modelMapper;

    public PessoaFisica convertToEntity(PessoaFisicaDTO dto) {
        return modelMapper.map(dto, PessoaFisica.class);
    }

    private PessoaFisicaDTO convertToDto(PessoaFisica p) {
        return modelMapper.map(p, PessoaFisicaDTO.class);
    }

    @Test
    void checkAddressMapping() {
        PessoaFisicaDTO dto = new PessoaFisicaDTO();
        dto.setNome("Eder");
        dto.setCpf("12345678901");
        //dto.setDataNascimento(03/03/2000);
        dto.setEstadoCivil(EstadoCivil.SOLTEIRO);

        PessoaFisica pessoaFisica = this.convertToEntity(dto);
        assertEquals(dto.getNome(), pessoaFisica.getNome());
        assertEquals(dto.getCpf(), pessoaFisica.getCpf());
        //assertEquals(dto.getDataNascimento(), pessoa.getDataNascimento());
        assertEquals(dto.getEstadoCivil(), pessoaFisica.getEstadoCivil());

        PessoaFisica pessoaFisica2 = new PessoaFisica();
        pessoaFisica2.setNome("Braz");
        pessoaFisica2.setCpf("12345678901");
        //pessoa2.setDataNascimento("Taubate");
        pessoaFisica2.setEstadoCivil(EstadoCivil.CASADO);

        PessoaFisicaDTO dto2 = this.convertToDto(pessoaFisica2);
        assertEquals(pessoaFisica2.getNome(), dto2.getNome());
        assertEquals(pessoaFisica2.getCpf(), dto2.getCpf());
        //assertEquals(pessoa2.getDataNascimento(), dto2.getDataNascimento());
        assertEquals(pessoaFisica2.getEstadoCivil(), dto2.getEstadoCivil());

    }
}