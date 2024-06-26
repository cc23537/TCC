package com.apis.apis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.integration.IntegrationProperties.RSocket.Client;
import org.springframework.web.bind.annotation.*;

import com.apis.apis.models.Cliente;
import com.apis.apis.models.Pessoa;
import com.apis.apis.repository.AlimentoRepository;
import com.apis.apis.repository.ClienteRepository;
import com.apis.apis.repository.PessoaRepository;

@RestController
public class Controller {
    
    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private AlimentoRepository alimentoRepository;

    @Autowired
    private ClienteRepository clienteRepository;


    //Rotas De cliente
    @GetMapping("/cliente/{email}/{senha}")
    public Cliente acharLogin(@PathVariable String email,  @PathVariable String senha){
        Cliente verify =  clienteRepository.findByEmail(email);
        if(verify.getSenha()== senha){
            return verify;
        }
        else{
            return null;
        }
    }

    @GetMapping("/cliente")
    public List<Cliente> retornaTodos(){
        return clienteRepository.findAll();
    }

    @PostMapping("/cliente")
    public Cliente registroCliente(@RequestBody Cliente c){
        return clienteRepository.save(c);
    }

    @DeleteMapping("/cliente/{codigo}")
    public void remover(@PathVariable int codigo){
        Cliente obj = clienteRepository.findByIdCliente(codigo);
        clienteRepository.delete(obj);
    }





    //Rotas testes
    @PostMapping("/pessoas")
    public Pessoa cadastrar(@RequestBody Pessoa p){
        return pessoaRepository.save(p);
    }

    @GetMapping("/helloworld")
    public String mensagem(){
        return "Hello World!";
    }



}
