package com.apis.apis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.apis.apis.models.Pessoa;
import com.apis.apis.repository.PessoaRepository;

@RestController
public class Controller {
    
    @Autowired

    private PessoaRepository pessoaRepository;

    @PostMapping("/api")
    public Pessoa cadastrar(@RequestBody Pessoa p){
        return pessoaRepository.save(p);
    }

    @GetMapping("/hello")
    public String mensagem(){
        return "Hello World!";
    }


    @GetMapping("/boasvindas/{nome}")
    public String boasVindas(@PathVariable String nome){
        return "Seja bem vindo" + nome;
    }

    @PostMapping("/pessoa")
    public Pessoa pessoa(@RequestBody Pessoa p){
        return p;
    }


}
