package com.example.parking.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Parqueadero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre",unique = true)
    private String nombre;

    @Column(name= "capacidad")
    private int capacidad;

    @Column(name="costo")
    private float costoHora;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


}
