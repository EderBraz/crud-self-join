package com.eder.crud.controller;

import com.eder.crud.dto.PessoaDTO;
import com.eder.crud.dto.Response;
import com.eder.crud.model.Pessoa;
import com.eder.crud.service.PessoaService;
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
public class PessoaController {

    @Autowired
    private PessoaService service;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<Pessoa>> getPessoas() {
        List<Pessoa> listpessoa = service.findAll();
        return new ResponseEntity<>(listpessoa, HttpStatus.OK);
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Pessoa> findPatientById(@PathVariable("cpf") String cpf) {
        Optional<Pessoa> patient = service.findbyCpf(cpf);
        return patient.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Pessoa> findPessoaById(@PathVariable("nome") String nome){
        Optional<Pessoa> pessoa = service.findPessoaByNome(nome);
        return  pessoa.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Response<PessoaDTO>> create(@Valid @RequestBody PessoaDTO dto, BindingResult result) {

        Response<PessoaDTO> response = new Response<>();

        if(result.hasErrors()) {
            result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));

            return ResponseEntity.badRequest().body(response);
        }

        Pessoa pessoa = service.save(this.convertToEntity(dto));
        response.setData(this.convertToDto(pessoa));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{pessoaId}/parente/{parenteId}")
    public ResponseEntity<Void> setParente(@PathVariable Long pessoaId, @PathVariable Long parenteId) {
        service.pessoaSetParente(pessoaId, parenteId);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<PessoaDTO>> updatePessoa(@PathVariable("id") Long id, @RequestBody @Valid PessoaDTO dto,
                                           BindingResult result) {
        Response<PessoaDTO> response = new Response<>();

        if (result.hasErrors() || (dto == null)) {
            result.getAllErrors().forEach(r -> response.getErrors().add(r.getDefaultMessage()));

            return ResponseEntity.badRequest().body(response);
        }

        Pessoa pessoaFind = this.convertToEntity(dto);

        service.findByIdOrFail(pessoaFind.getId());
        service.save(pessoaFind);
        response.setData(convertToDto(pessoaFind));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pessoa> deleteById(@PathVariable Long id) {
        try {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private Pessoa convertToEntity(PessoaDTO dto) {
        return modelMapper.map(dto, Pessoa.class);
    }

    private PessoaDTO convertToDto(Pessoa p) {
        return modelMapper.map(p, PessoaDTO.class);
    }
}
