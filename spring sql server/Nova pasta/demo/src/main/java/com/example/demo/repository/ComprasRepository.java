package com.example.demo.controller;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.apis.apis.models.Compras;

@Repository
public interface ComprasRepository extends CrudRepository<Compras, Integer>{

    List<Compras> findAll();

    Compras findByIdCompra(int findByIdCompra);

}
