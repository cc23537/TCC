package com.daroca.controllers;

import com.daroca.models.Customer;
import com.daroca.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerRepository repository;
    @GetMapping // cliente com o verbo get (/custumers/get)
    public List<Customer> all(){
        return repository.findAll();
    }

    @GetMapping("/customers/{id}")
    public Optional<Customer> one(@PathVariable Integer id){
        return repository.findById(id);
    }

    @DeleteMapping("/custumers/{id}")
    public void delete(@PathVariable Integer id){
        repository.deleteById(id);
    }

    @PostMapping
    public Customer save(@RequestBody Customer newCustomer){
        return repository.save(newCustomer);
    }

    @PutMapping
    public Customer update(@RequestBody Customer newCustomer, @PathVariable Integer id ){
        return repository.findById(id)
                .map(customer ->{
                    customer.setName(newCustomer.getName());
                    customer.setCity(newCustomer.getCity());
                    customer.setState(newCustomer.getState());
                    customer.setLatitude(newCustomer.getLatitude());
                    customer.setLongitude(newCustomer.getLongitude());
                    return  repository.save(customer);
                })
                .orElseGet(()->{
                    return  repository.save(newCustomer);
                });
    }
}
