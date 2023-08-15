package com.example.parking.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoIndicadorResponse {
    private Long vehiculoId;
    private Long vecesRegistrado;
}
