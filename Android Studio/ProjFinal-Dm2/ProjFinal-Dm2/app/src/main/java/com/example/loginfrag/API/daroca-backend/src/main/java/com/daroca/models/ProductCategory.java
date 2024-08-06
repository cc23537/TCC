package com.daroca.models;

import jakarta.persistence.*;


@Entity
@Table
public class ProductCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column(length = 50, nullable = false)
    private String name;

    public ProductCategory(String name,int productId){
        this.name = name;
        this.productId = productId;
    }

    public Integer getId(){return this.productId;}

    public void setId(int id){this.productId = id;}
    public void setName(String name){
        this.name = name;
    }
    public String  getName(){return this.name;}

    public boolean equals(Customer other){
        return this.productId.equals(other.getId()) && this.name.equals(other.getName());
    }

    public String toString(){
        return "Product Category[id = " + getId() + " " + "name = " + getName() + "]";
    }

}
