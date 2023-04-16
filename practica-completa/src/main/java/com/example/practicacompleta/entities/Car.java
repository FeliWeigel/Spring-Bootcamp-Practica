package com.example.practicacompleta.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cars")
@Entity
public class Car {

    @Id
    @GeneratedValue
    private Long id;
    private String brand;
    private String model;
    private Integer year;
    private Integer cc;

}
