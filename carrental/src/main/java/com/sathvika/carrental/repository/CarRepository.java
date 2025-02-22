package com.sathvika.carrental.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<com.carrental.services.car.Car, Long> {
}


