package com.example.practicacompleta.service;

import com.example.practicacompleta.entities.Car;
import com.example.practicacompleta.repository.CarRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;

    public List<Car> allCars(){
        return carRepository.findAll();
    }

    public List<Car> carsByBrand(String brand){
        List<Car> allCars = new ArrayList<>();
        List<Car> brandCars = new ArrayList<>();

        allCars = carRepository.findAll();
        brandCars = carRepository.findByBrand(brand);

        System.out.println(allCars);
        System.out.println(brandCars);

        return brandCars;
    }

    public Car addCar(Car car){
        if(carRepository.findByModel(car.getModel()).isEmpty()){
            return carRepository.save(car);
        }
        return null;
    }

    public void deleteAllCars(){
        carRepository.deleteAll();
    }

    public String deleteCarById(Long id){
        if(carRepository.findById(id).isPresent()){
            carRepository.deleteById(id);
            return "Car successfully delete!";
        }

        return "Error! Car not found";
    }

    public String deleteCarByModel(String model){
        Optional<Car> car = carRepository.findByModel(model);
        if(car.isPresent()){
            carRepository.deleteByModel(model);
            return "Car successfully delete!";
        }

        return "Error! Car not found";
    }
}
