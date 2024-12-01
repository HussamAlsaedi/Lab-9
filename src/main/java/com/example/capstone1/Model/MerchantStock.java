package com.example.capstone1.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor

public class MerchantStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "productId must be not empty")
    @Column(columnDefinition = "int not null")
    private Integer productId;

    @NotNull(message = "merchantId must be  not empty")
    @Column(columnDefinition = "int not null")
    private Integer merchantId;

    @NotNull(message = "Stock must be  not empty")
    @Min(value = 10 ,message = "stock must not be empty, have to be more than 10 at start).")
    @Column(columnDefinition = "int not null")
    private Integer stock;

}