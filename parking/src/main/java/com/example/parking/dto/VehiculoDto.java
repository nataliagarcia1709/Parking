package com.example.parking.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class VehiculoDto {

    private Long id;

    @NotBlank(message = "No puede estar vacio")
    private String placa;
}
