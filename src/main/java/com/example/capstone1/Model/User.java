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

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Username must not be empty")
    @Size(min = 6, message = "Username must be more than 5 characters long")
    @Column(columnDefinition = "varchar(10) unique not null")
    private String username;

    @NotEmpty(message = "Password must not be empty")
    @Size(min = 7, message = "Password must be more than 6 characters long")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$", message = "Password must contain both letters and digits")
    @Column(columnDefinition = "varchar(100) not null")
    private String password;

    @NotEmpty(message = "Email must not be empty")
    @Email(message = "Email must be valid")
    @Column(columnDefinition = "varchar(100) unique not null")
    private String email;

    @NotEmpty(message = "Role must not be empty")
    @Pattern(regexp = "^(Admin|Customer)$", message = "Role must be either 'Admin' or 'Customer'")
    @Column(columnDefinition = "varchar(10)  not null")
    private String role;

    @NotNull(message = "Balance must not be empty")
    @Positive(message = "Balance must be positive")
    @Column(columnDefinition = "double not null")
    private Double balance;

}
