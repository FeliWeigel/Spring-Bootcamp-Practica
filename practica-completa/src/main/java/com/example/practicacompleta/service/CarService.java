package com.example.practicacompleta.service;

import com.example.practicacompleta.entities.Car;
import com.example.practicacompleta.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<Car> carsByBrand(String brand){
        List<Car> allCars = new ArrayList<>();
        List<Car> brandCars = new ArrayList<>();

        allCars = carRepository.findAll();
        brandCars = carRepository.findByBrand(brand);

        System.out.println(allCars);
        System.out.println(brandCars);

        return brandCars;
    }

    public List<Car> allCars(){
        return carRepository.findAll();
    }

    public Car addCar(Car car){
        List<Car> carsList = carRepository.findAll();
        if(carRepository.findByModel(car.getModel()) == null){
            return carRepository.save(car);
        }
        return null;
    }
}
