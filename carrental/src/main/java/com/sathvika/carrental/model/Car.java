package com.sathvika.carrental.model;

import jakarta.persistence.*;

import java.util.Locale;
@Entity
@Table(name="CARS")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int carId;
    private String name;
    private boolean available;
    private double price;
    public Car()
    {}

    public Car(int carId, String name, boolean available, double price) {
        this.carId=carId;
        this.name = name;
        this.available = available;
        this.price = price;
    }



    public int getCarId()
    {
        return carId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}