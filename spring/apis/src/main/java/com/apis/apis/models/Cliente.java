package com.apis.apis.models;

import jakarta.persistence.*;

@Entity
@Table(name = "TCC_Clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idCliente;

    private String nomeCliente;
    private String email;
    private String senha;
    private Double peso;
    private Double altura;

    public Cliente(String nomeCliente, String email, String senha, Double peso, Double altura) {
        this.nomeCliente = nomeCliente;
        this.email = email;
        this.senha = senha;
        this.peso = peso;
        this.altura = altura;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }
}
