package com.example.parking.controller;

import com.example.parking.dto.VehiculoDto;
import com.example.parking.entity.Parqueadero;
import com.example.parking.permisos.Ingreso;
import com.example.parking.service.ParqueaderoService;
import com.example.parking.service.RegistroParqueoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehiculos")
public class VehiculoController {
    @Autowired
    private ParqueaderoService parqueaderoService;
    @Autowired
    private RegistroParqueoService registroParqueoService;

    @Ingreso({"ADMIN"})
    @GetMapping(path = "/parqueaderos/{idParqueadero}/vehiculos",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<VehiculoDto> listarVehiculosEnParqueadero(@PathVariable Long idParqueadero) {
        Parqueadero parqueadero = parqueaderoService.buscarId(idParqueadero);
        return registroParqueoService.vehiculosPorParqueadero(parqueadero);
    }

}
