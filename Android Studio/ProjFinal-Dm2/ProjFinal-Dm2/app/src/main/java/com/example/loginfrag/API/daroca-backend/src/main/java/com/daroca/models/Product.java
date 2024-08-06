package com.daroca.models;

import jakarta.persistence.*;

@Entity
@Table
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column(length = 50,nullable = false)
    private String name;

    @Column(nullable = false)
    private double unitPrice;

    @OneToMany
    @JoinColumn( name = "product_category_productId", foreignKey = @ForeignKey(name = "FK_Product_ProductCategory"))
    private ProductCategory productCategory;

    public Product(int id,String name,double unitPrice){
        this.productId = id;
        this.name = name;
        this.unitPrice = unitPrice;
    }

    public Integer getId(){return this.productId;}
    public void setId(int id){this.productId = id;}

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }

    public Double getUnitPrice(){
        return this.unitPrice;
    }
    public void setUnitPrice(double uPrice){
        this.unitPrice = uPrice;
    }

    public boolean equals(Customer other){
        return this.productId.equals(other.getId()) && this.name.equals(other.getName());
    }

    public String toString(){
        return "Product[id = " + getId() + " " + "name = " + getName() + "unitPrice = " + getUnitPrice() + "]";
    }
}
