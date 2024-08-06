package com.daroca.controllers;


import com.daroca.models.Customer;
import com.daroca.models.ProductCategory;
import com.daroca.repositories.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productCategory")
public class ProductCategoryController {
    @Autowired
    private ProductCategoryRepository repository;

    @GetMapping
    public List<ProductCategory> all(){return repository.findAll();}

    @GetMapping("/productCategory/{id}")
    public Optional<ProductCategory> one(@PathVariable Integer id){return repository.findById(id);}

    @DeleteMapping("/productCategory/{id}")
    public void delete(@PathVariable Integer id){
        repository.deleteById(id);
    }

    @PostMapping
    public ProductCategory save(@RequestBody ProductCategory newPC){
        return repository.save(newPC);
    }

    @PutMapping
    public ProductCategory update(@RequestBody ProductCategory newPc, @PathVariable Integer id ){
        return repository.findById(id)
                .map(productCategory ->{
                    productCategory.setName(newPc.getName());

                    return  repository.save(productCategory);
                })
                .orElseGet(()->{
                    return  repository.save(newPc);
                });
    }


}
