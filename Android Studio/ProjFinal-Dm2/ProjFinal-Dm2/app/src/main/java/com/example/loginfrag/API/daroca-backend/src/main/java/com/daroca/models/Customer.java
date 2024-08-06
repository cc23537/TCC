package com.daroca.models;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
@Entity
@Table
public class Customer {
    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Auto-Increment
    private Integer CustomerId;
    @Column(length = 50, nullable = false) //Varchar(50) not null
    private String name;
    @Column(length = 100) //Varchar(100)
    private String city;
    @Column(length = 100) //Varchar(100)
    private String state;
    @Column
    private Double latitude;
    @Column
    private Double longitude;

    public Customer(int id, String name, String city, Double latitude, Double longitude,String state ){
        this.CustomerId = id;
        this.name = name;
        this.city = city;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public Integer getId(){
        return this.CustomerId;
    }
    public void setId(int id){
        this.CustomerId = id;
    }
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getState(){return this.state;}
    public void setState(String state){
        this.state = state;
    }
    public String getCity(){
        return this.city;
    }
    public void setCity(String city){
        this.city = city;
    }
    public Double getLatitude(){
        return this.latitude;
    }
    public void setLatitude(Double latitude){
        this.latitude = latitude;
    }
    public Double getLongitude(){
        return  this.longitude;
    }
    public void setLongitude(Double longitude){
        this.longitude = longitude;
    }
    public boolean equals(Customer other){
        return this.CustomerId.equals(other.getId()) && this.name.equals(other.getName());
    }
    public String toString(){
        return "Customer[id = " + getId() + " " + "name = " + getName() + "]";
    }
}
