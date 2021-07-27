package com.eder.crud.controller;

import com.eder.crud.dto.PessoaFisicaDTO;
import com.eder.crud.dto.Response;
import com.eder.crud.model.PessoaFisica;
import com.eder.crud.service.PessoaFisicaService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pessoa")
public class PessoaFisicaController {

    @Autowired
    private PessoaFisicaService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<PessoaFisica>> getPessoas() {
        List<PessoaFisica> listPessoa = service.findAll();
        return new ResponseEntity<>(listPessoa, HttpStatus.OK);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<PessoaFisica> findPessoaByCpf(@PathVariable("cpf") String cpf) {
        Optional<PessoaFisica> pessoa = service.findbyCpf(cpf);
        return pessoa.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<PessoaFisica> findPessoaByNome(@PathVariable("nome") String nome){
        Optional<PessoaFisica> pessoa = service.findByNome(nome);
        return  pessoa.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Response<PessoaFisicaDTO>> create(@Valid @RequestBody PessoaFisicaDTO dto, BindingResult result) {

        Response<PessoaFisicaDTO> response = new Response<>();

        if(result.hasErrors()) {
            result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));

            return ResponseEntity.badRequest().body(response);
        }

        PessoaFisica pessoaFisica = service.save(this.convertToEntity(dto));
        response.setData(this.convertToDto(pessoaFisica));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{pessoaId}/parente/{parenteId}")
    public ResponseEntity<Void> setParente(@PathVariable Long pessoaId, @PathVariable Long parenteId) {
        service.pessoaSetParente(pessoaId, parenteId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<PessoaFisicaDTO>> updatePessoa(@PathVariable("id") Long id, @RequestBody @Valid PessoaFisicaDTO dto,
                                                                  BindingResult result) {
        Response<PessoaFisicaDTO> response = new Response<>();

        if (result.hasErrors() || (dto == null)) {
            result.getAllErrors().forEach(r -> response.getErrors().add(r.getDefaultMessage()));

            return ResponseEntity.badRequest().body(response);
        }

        PessoaFisica pessoaFisicaFind = this.convertToEntity(dto);

        service.findByIdOrFail(pessoaFisicaFind.getId());
        service.save(pessoaFisicaFind);
        response.setData(convertToDto(pessoaFisicaFind));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PessoaFisica> deleteById(@PathVariable Long id) {
        try {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private PessoaFisica convertToEntity(PessoaFisicaDTO dto) {
        return modelMapper.map(dto, PessoaFisica.class);
    }

    private PessoaFisicaDTO convertToDto(PessoaFisica p) {
        return modelMapper.map(p, PessoaFisicaDTO.class);
    }
}
