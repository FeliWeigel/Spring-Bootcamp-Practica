package com.example.practicacompleta.repository;

import com.example.practicacompleta.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findByBrand(String brand);
    Car findByModel(String model);
}
