package com.example.parking.entity;

import com.example.parking.enumeraciones.Rol;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id ;

    @Column(name = "email", unique = true)
    @NotBlank(message = "email es requerido")
    private String email;

    @NotBlank(message = "contraseña es requerida")
    @NotEmpty
    private String password;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    @JsonBackReference
    @OneToMany(mappedBy = "usuario")
    private List<Parqueadero> parqueadero;


}
