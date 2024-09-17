    package com.apis.apis.models;

    import jakarta.persistence.*;

    @Entity
    @Table(name = "TCC_Compras")
    public class Compras /*implements Cloneable*/{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int idCompra;
        
        @Column(nullable = false)
        private String alimentoASerComprado;

        @Column(nullable = false)
        private int quantidade;

        public Compras(){}

        public Compras(int idCompra, String alimentoASerComprado, int quantidade) {
            this.idCompra = idCompra;
            this.alimentoASerComprado = alimentoASerComprado;
            this.quantidade = quantidade;
        }

        public int getIdCompra() {
            return idCompra;
        }

        public void setIdCompra(int idCompra) {
            this.idCompra = idCompra;
        }

        public String getAlimentoASerComprado() {
            return alimentoASerComprado;
        }

        public void setAlimentoASerComprado(String alimentoASerComprado) {
            this.alimentoASerComprado = alimentoASerComprado;
        }

        public int getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }
    }