package com.eder.crud.controller;

import com.eder.crud.dto.Response;
import com.eder.crud.model.Pessoa;
import com.eder.crud.service.PessoaService;
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
    public ResponseEntity<Response<Pessoa>> create(@Valid @RequestBody Pessoa dto, BindingResult result) {

        Response<Pessoa> response = new Response<>();

        if(result.hasErrors()) {
            result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        Pessoa pessoa = service.save(dto);
        response.setData(pessoa);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<Pessoa>> updatePessoa(@PathVariable("id") Long id, @RequestBody @Valid Pessoa pessoa,
                                           BindingResult result) {
        Response<Pessoa> response = new Response<>();

        if (result.hasErrors() || (pessoa == null)) {
            result.getAllErrors().forEach(r -> response.getErrors().add(r.getDefaultMessage()));

            return ResponseEntity.badRequest().body(response);
        }

        Optional<Pessoa> pessoaEncontrada = service.findById(id);

        if (!pessoaEncontrada.isPresent())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        service.update(pessoa);
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

}
