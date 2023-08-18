package com.example.parking.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParqueaderoDto {


    @NotBlank(message = "nombre es requerido")
    private String nombre;

    @NotNull
    @Min(value= 5)
    private int capacidad;

    private float costoHora;
}
