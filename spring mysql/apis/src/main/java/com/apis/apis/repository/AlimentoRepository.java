package com.apis.apis.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.apis.apis.models.Alimento;


@Repository
public interface AlimentoRepository extends CrudRepository<Alimento, Integer>{

    List<Alimento> findAll();

    Alimento findByIdAlimento(int idAlimento);

    Alimento findByValidade(String validade);

} 
