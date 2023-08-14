package com.example.parking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class RegistroParqueo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parqueadero_id")
    private Parqueadero parqueadero;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

    @CreationTimestamp
    @Column(name= "fecha_ingreso")
    private LocalDateTime fechaHoraIngreso;

    private LocalDateTime fechaHoraSalida;
}

