package com.apis.apis.models;

import jakarta.persistence.*;

@Entity
@Table(name = "TCC_Alimentos")
public class Alimento /*implements Cloneable*/ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAlimento;
    
    @Column(nullable = false)
    private String nomeAlimento;

    @Column(nullable = true)
    private Double calorias;

    @Column(nullable = true)
    private String especificacoes;

    @Column(nullable = true)
    private String validade;

    public Alimento(String nomeAlimento, Double calorias, String especificacoes, String validade) {
        this.nomeAlimento = nomeAlimento;
        this.calorias = calorias;
        this.especificacoes = especificacoes;
        this.validade = validade;
    }
    
    public String getValidade(){
        return validade;
    }
    public void setValidade(String validade){
        this.validade = validade;
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
