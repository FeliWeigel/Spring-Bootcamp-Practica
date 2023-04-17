package com.example.practicacompleta.service;

import com.example.practicacompleta.entities.Car;
import com.example.practicacompleta.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
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
        List<Car> carsList = carRepository.findAll();
        if(carRepository.findByModel(car.getModel()) == null){
            return carRepository.save(car);
        }
        return null;
    }

    public void deleteAllCars(){
        carRepository.deleteAll();
    }

    public String deleteCarById(Long id){
        Optional<Car> carDelete = carRepository.findById(id);

        if(carDelete.isPresent()){
            carRepository.deleteById(id);
            return "Car successfully delete!";
        }

        return "Error! Car not found";
    }

    public String deleteCarByModel(String model){
        Car carDelete = carRepository.findByModel(model);

        if(carDelete != null){
            carRepository.delete(carDelete);
            return "Car successfully delete!";
        }

        return "Error! Car not found";
    }
}
