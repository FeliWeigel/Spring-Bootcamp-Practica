package com.example.practicacompleta.controllers;

import com.example.practicacompleta.entities.Car;
import com.example.practicacompleta.repository.CarRepository;
import com.example.practicacompleta.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;

import java.util.ArrayList;
import java.util.Collections;
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
