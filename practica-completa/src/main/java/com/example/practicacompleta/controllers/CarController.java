package com.example.practicacompleta.controllers;

import com.example.practicacompleta.entities.Car;
import com.example.practicacompleta.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteAllCars(){
        carService.deleteAllCars();
        return ResponseEntity.ok("All cars deleted! Current list of Cars size: " + carService.allCars().size());
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<String> deleteCarById(@PathVariable Long id){
        return ResponseEntity.ok(carService.deleteCarById(id));
    }

    @DeleteMapping("/delete/model/{model}")
    public ResponseEntity<String> deleteCarByModel(@PathVariable String model){
        return ResponseEntity.ok(carService.deleteCarByModel(model));
    }

}
