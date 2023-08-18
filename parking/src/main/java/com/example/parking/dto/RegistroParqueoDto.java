package com.example.parking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroParqueoDto {

    @NotNull
    private Long idPark;

    @NotBlank
    @Size(min = 6, max = 6)
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "La placa no cumple con las reglas de validación")
    private String placa;
}
