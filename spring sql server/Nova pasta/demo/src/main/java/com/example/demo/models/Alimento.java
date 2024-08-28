package com.example.demo.controller;

import jakarta.persistence.*;

@Entity
@Table(name = "TCC_Alimentos")
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAlimento;
    
    @Column(nullable = false)
    private String nomeAlimento;

    @Column(nullable = true)
    private Double calorias;

    @Column(nullable = true)
    private String especificacoes;

    public Alimento(String nomeAlimento, Double calorias, String especificacoes) {
        this.nomeAlimento = nomeAlimento;
        this.calorias = calorias;
        this.especificacoes = especificacoes;
    }

    public int getIdAlimento() {
        return idAlimento;
    }

    public void setIdAlimento(int idAlimento) {
        this.idAlimento = idAlimento;
    }

    public String getNomeAlimento() {
        return nomeAlimento;
    }

    public void setNomeAlimento(String nomeAlimento) {
        this.nomeAlimento = nomeAlimento;
    }

    public Double getCalorias() {
        return calorias;
    }

    public void setCalorias(Double calorias) {
        this.calorias = calorias;
    }

    public String getEspecificacoes() {
        return especificacoes;
    }

    public void setEspecificacoes(String especificacoes) {
        this.especificacoes = especificacoes;
    }
}
