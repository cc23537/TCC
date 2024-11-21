package com.apis.apis.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.apis.apis.models.Cliente;

@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Integer>{

    List<Cliente> findAll();

    Cliente findByIdCliente(int idCliente);

    Cliente findByEmail(String email);

    

    

} 