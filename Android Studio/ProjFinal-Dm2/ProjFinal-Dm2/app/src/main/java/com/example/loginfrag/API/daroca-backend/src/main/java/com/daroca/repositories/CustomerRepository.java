package com.daroca.repositories;

import com.daroca.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Integer> { //Primeiro atributo é a classe base, e o
                                                                                // segundo é o tipo primitivo(não pode tipos como int)

}
