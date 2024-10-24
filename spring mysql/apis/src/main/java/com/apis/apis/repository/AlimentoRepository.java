package com.apis.apis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

import com.apis.apis.models.Alimento;


@Repository
public interface AlimentoRepository extends CrudRepository<Alimento, Integer>{

    List<Alimento> findAll();

    Alimento findByIdAlimento(int idAlimento);
    Alimento findByNomeAlimento(String nomeAlimento);
    Alimento findByNomeAlimentoAndValidade(String nomeAlimento,String validade);

    List<Alimento> findByValidade(String validade);

} 
