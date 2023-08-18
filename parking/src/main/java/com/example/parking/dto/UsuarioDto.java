package com.example.parking.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDto {

    private Long id;

    @NotBlank(message = "email es requerido")
    private String email;

    @NotBlank(message = "contraseña es requerida")
    private String password;
}
