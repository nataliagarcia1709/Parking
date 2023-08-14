package com.example.parking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
public class UsuarioDto {

    @NotNull
    @NotBlank(message = "email es requerido")
    private String email;

    @NotNull
    @NotBlank(message = "contraseña es requerida")
    private String password;
}
