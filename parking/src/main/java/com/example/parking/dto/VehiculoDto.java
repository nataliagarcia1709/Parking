package com.example.parking.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoDto {

    @NotBlank(message = "No puede estar vacio")
    @Size(min = 6, max = 6)
    private String placa;

    Long vecesRegistrado;
}
