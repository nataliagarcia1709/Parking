package com.example.parking.mapper;

import com.example.parking.dto.ParqueaderoDto;
import com.example.parking.entity.Parqueadero;
import org.springframework.stereotype.Component;

@Component
public class ParqueaderoMapper {
    public ParqueaderoDto toDTO(Parqueadero parqueadero) {
        ParqueaderoDto parqueaderoDto = new ParqueaderoDto();
        parqueaderoDto.setNombre(parqueadero.getNombre());
        parqueaderoDto.setCapacidad(parqueadero.getCapacidad());
        parqueaderoDto.setCostoHora(parqueadero.getCostoHora());
        return parqueaderoDto;
    }

    public Parqueadero toEntity(ParqueaderoDto parqueaderoDto) {
        Parqueadero parqueadero = new Parqueadero();
        parqueadero.setNombre(parqueaderoDto.getNombre());
        parqueadero.setCapacidad(parqueaderoDto.getCapacidad());
        parqueadero.setCostoHora(parqueaderoDto.getCostoHora());
        return parqueadero;
    }
}
