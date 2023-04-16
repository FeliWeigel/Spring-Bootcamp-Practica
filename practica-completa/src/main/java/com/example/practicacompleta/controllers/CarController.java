package com.example.practicacompleta.controllers;

import com.example.practicacompleta.entities.Car;
import com.example.practicacompleta.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cars")
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping
    public ResponseEntity<List<Car>> allCars(){
        return ResponseEntity.ok(carService.allCars());
    }

    @PostMapping("/add")
    public ResponseEntity<Car> addCar(@RequestBody Car car){
        return ResponseEntity.ok(carService.addCar(car));
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<Car>> getCarsByBrand(@PathVariable String brand){
        return ResponseEntity.ok(carService.carsByBrand(brand));
    }

}
