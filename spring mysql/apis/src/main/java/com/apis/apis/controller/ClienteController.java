package com.apis.apis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.apis.apis.models.Cliente;
import com.apis.apis.repository.ClienteRepository;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping("/{email}/{senha}")
    public Cliente acharLogin(@PathVariable String email, @PathVariable String senha) {
        Cliente verify = clienteRepository.findByEmail(email);
        if (verify.getSenha().equals(senha)) {
            return verify;
        } else {
            return null;
        }
    }

    @GetMapping
    public List<Cliente> retornaTodos() {
        return clienteRepository.findAll();
    }

    @PostMapping
    public Cliente registroCliente(@RequestBody Cliente c) {
        return clienteRepository.save(c);
    }

    @DeleteMapping("/{codigo}")
    public void remover(@PathVariable int codigo) {
        Cliente obj = clienteRepository.findByIdCliente(codigo);
        clienteRepository.delete(obj);
    }
}
