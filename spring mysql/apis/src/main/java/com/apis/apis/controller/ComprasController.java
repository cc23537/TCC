package com.apis.apis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.apis.apis.models.Compras;
import com.apis.apis.repository.ComprasRepository;

@RestController
@RequestMapping("/compras")
public class ComprasController {

    @Autowired
    private ComprasRepository comprasRepository;

    @GetMapping
    public List<Compras> CarrinhoDeCompras() {
        return comprasRepository.findAll();
    }

    @PostMapping
    public Compras alimentoASerComprado(@RequestBody Compras a) {
        return comprasRepository.save(a);
    }
}
