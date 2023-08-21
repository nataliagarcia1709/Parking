package com.example.parking.controller;


import com.example.parking.dto.ParqueaderoDto;
import com.example.parking.dto.VehiculoDto;
import com.example.parking.dto.response.RegistroResponse;
import com.example.parking.entity.Parqueadero;
import com.example.parking.mapper.ParqueaderoMapper;
import com.example.parking.permisos.Ingreso;
import com.example.parking.service.RegistroParqueoService;
import com.example.parking.service.VehiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.parking.service.ParqueaderoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/parqueaderos")
public class ParqueaderoController {

    private final ParqueaderoService parqueaderoService;

    @Ingreso({"ADMIN"})
    @PostMapping(path = "",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public RegistroResponse crearParqueadero(@RequestBody @Valid ParqueaderoDto parqueaderoDto) {
            Parqueadero parqueadero = parqueaderoService.crearParqueadero(parqueaderoDto);
            ParqueaderoMapper parqueaderoMapper = new ParqueaderoMapper();
            ParqueaderoDto crearParqueaderoDto = parqueaderoMapper.toDTO(parqueadero);
        return new RegistroResponse(parqueadero.getId());
    }

    @Ingreso({"ADMIN"})
    @DeleteMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public RegistroResponse eliminarParqueadero(@PathVariable Long id) {
        parqueaderoService.eliminarParqueadero(id);
        return new RegistroResponse(id);
    }

    @Ingreso({"ADMIN"})
    @PutMapping(path= "{id}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public RegistroResponse actualizarParqueadero(@PathVariable Long id, @Valid @RequestBody ParqueaderoDto parqueaderoDto) {
        parqueaderoService.modificarParqueadero(id, parqueaderoDto);
        return new RegistroResponse(id);
    }

    @Ingreso({"ADMIN"})
    @GetMapping(path="",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ParqueaderoDto> listarParqueaderos(){
        return parqueaderoService.obtenerParqueaderos();
    }

    @Ingreso({"ADMIN"})
    @GetMapping(path="/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ParqueaderoDto obtenerParqueaderoPorId(@PathVariable Long id) {
        Parqueadero parqueadero = parqueaderoService.buscarId(id);
        if (parqueadero != null) {
            ParqueaderoMapper parqueaderoMapper = new ParqueaderoMapper();
            ParqueaderoDto parqueaderoDto = parqueaderoMapper.toDTO(parqueadero);
            return parqueaderoDto;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Parqueadero no encontrado");
        }
    }

}
