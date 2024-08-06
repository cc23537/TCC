package com.daroca.controllers;

import com.daroca.models.Product;
import com.daroca.models.ProductCategory;
import com.daroca.repositories.ProductCategoryRepository;
import com.daroca.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepository repository;

    @GetMapping
    public List<Product> all(){return repository.findAll();}

    @GetMapping("/productCategory/{id}")
    public Optional<Product> one(@PathVariable Integer id){return repository.findById(id);}

    @DeleteMapping("/productCategory/{id}")
    public void delete(@PathVariable Integer id){
        repository.deleteById(id);
    }

    @PostMapping
    public Product save(@RequestBody Product newProduct){
        return repository.save(newProduct);
    }

    @PutMapping
    public Product update(@RequestBody Product newProduct, @PathVariable Integer id ){
        return repository.findById(id)
                .map(product ->{
                    product.setName(newProduct.getName());
                    product.setUnitPrice(newProduct.getUnitPrice());

                    return  repository.save(product);
                })
                .orElseGet(()->{
                    return  repository.save(newProduct);
                });
    }
}
