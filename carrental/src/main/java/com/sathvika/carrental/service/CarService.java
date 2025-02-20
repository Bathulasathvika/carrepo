package com.sathvika.carrental.service;

import com.sathvika.carrental.model.Car;
import com.sathvika.carrental.model.User;
import com.sathvika.carrental.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    @Autowired
    private CarRepository carRepository;

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public boolean setCarPrice(int carId, double newPrice) {
        Car car = carRepository.findById(carId).orElse(null);
        if (car != null) {
            car.setPrice(newPrice);
            carRepository.save(car);
            return true;
        }
        return false;
    }

    public boolean rentCar(User user, int carId) {
        Car car = carRepository.findById(carId).orElse(null);
        if (car != null && car.isAvailable()) {
            car.setAvailable(false);
            carRepository.save(car);
            user.setRentedCar(car);
            return true;
        }
        return false;
    }

    public void returnCar(User user) {
        Car car = user.getRentedCar();
        if (car != null) {
            car.setAvailable(true);
            carRepository.save(car);
            user.setRentedCar(null);
        }
    }

    public Car saveCar(Car car) {
        return carRepository.save(car);
    }


    public boolean returnCar(User user, int returnCarId) {
        return false;
    }
}
