package com.example.parking.mapper;

import com.example.parking.dto.UsuarioDto;
import com.example.parking.dto.VehiculoDto;
import com.example.parking.entity.Usuario;
import com.example.parking.entity.Vehiculo;
import org.springframework.stereotype.Component;

@Component
public class VehiculoMapper {

    public VehiculoDto entityToDTO(Vehiculo vehiculo) {
        VehiculoDto vehiculoDto = new VehiculoDto();
        vehiculoDto.setPlaca(vehiculo.getPlaca());
        return vehiculoDto;
    }

    public Vehiculo dtoToEntity(VehiculoDto vehiculoDto) {
        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setPlaca(vehiculoDto.getPlaca());
        return vehiculo;
    }
}
