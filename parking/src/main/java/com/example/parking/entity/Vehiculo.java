package com.example.parking.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id ;

    @Column(name = "placa", unique = true)
    @NotBlank
    private String placa;

    @OneToMany(mappedBy = "vehiculo")
    private List<RegistroParqueo> registros;



}
