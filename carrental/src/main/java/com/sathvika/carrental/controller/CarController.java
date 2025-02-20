package com.sathvika.carrental.controller;

import com.sathvika.carrental.model.Car;
import com.sathvika.carrental.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping("/all")
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }
    @PostMapping("/add")
    public String addCar(Car car)
    {
        carService.saveCar(car);
        return "car added successfully";
    }
}