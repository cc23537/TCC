package com.apis.apis.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.*;
import com.apis.apis.models.Pessoa;

public interface Repositorio extends CrudRepository<Pessoa, Integer>{

} 