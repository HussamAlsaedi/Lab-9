package com.example.capstone1.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor

public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Name must not be empty.")
    @Size(min = 4, message = "Name must be more than 3 characters long")
    @Column(columnDefinition = "varchar(100) not null")
    private String name;

    @NotNull(message = "Price must not be empty, must be positive number")
    @Positive(message = "Number must be positive")
    @Column(columnDefinition = "double not null")
    private Double price;

    @NotNull(message = "Category_id must be not empty")
    @Column(columnDefinition = "int not null")
    private Integer  categoryId;

}