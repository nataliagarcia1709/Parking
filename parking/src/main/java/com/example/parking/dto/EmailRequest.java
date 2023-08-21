package com.example.parking.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailRequest {

    private String email;
    private String placa;
    private String mensaje;
    private String parqueaderoNombre;
}
