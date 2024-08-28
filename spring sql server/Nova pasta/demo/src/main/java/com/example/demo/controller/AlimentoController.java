package com.apis.apis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.apis.apis.models.Alimento;
import com.apis.apis.repository.AlimentoRepository;

@RestController
@RequestMapping("/alimentos")
public class AlimentoController {

    @Autowired
    private AlimentoRepository alimentoRepository;

    @GetMapping
    public List<Alimento> retornaTodosAlimentos() {
        return alimentoRepository.findAll();
    }

    @PostMapping
    public Alimento cadastrar(@RequestBody Alimento a) {
        return alimentoRepository.save(a);
    }

    @DeleteMapping("/{codigo}")
    public void removerAlimento(@PathVariable int codigo) {
        Alimento obj = alimentoRepository.findByIdAlimento(codigo);
        alimentoRepository.delete(obj);
    }
}
