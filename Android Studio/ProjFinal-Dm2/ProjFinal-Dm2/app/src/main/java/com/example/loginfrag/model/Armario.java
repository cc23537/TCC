package com.example.loginfrag.model;

public class Armario {
    String nomeDoAlimento;
    Integer calorias;

    public Armario(String alim, int cal){
        this.nomeDoAlimento = alim;
        this.calorias = cal;
    }

    public String toString(){
        return this.nomeDoAlimento + " " + this.calorias;
    }
}
