package com.example.parking.controller;


import com.example.parking.dto.ParqueaderoDto;
import com.example.parking.dto.VehiculoDto;
import com.example.parking.entity.Parqueadero;
import com.example.parking.mapper.ParqueaderoMapper;
import com.example.parking.permisos.Ingreso;
import com.example.parking.service.RegistroParqueoService;
import com.example.parking.service.VehiculoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.parking.service.ParqueaderoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/parqueaderos")
public class ParqueaderoController {

    private final ParqueaderoService parqueaderoService;
    private final RegistroParqueoService registroParqueoService;
    private final VehiculoService vehiculoService;

    @Autowired
    public ParqueaderoController(ParqueaderoService parqueaderoService, RegistroParqueoService registroParqueoService, VehiculoService vehiculoService ) {
        this.parqueaderoService = parqueaderoService;
        this.registroParqueoService = registroParqueoService;
        this.vehiculoService = vehiculoService;
    }

    @Ingreso({"ADMIN"})
    @PostMapping(path = "",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Object crearParqueadero(@RequestBody @Valid ParqueaderoDto parqueaderoDto) {
            Parqueadero parqueadero = parqueaderoService.crearParqueadero(parqueaderoDto);
            ParqueaderoMapper parqueaderoMapper = new ParqueaderoMapper();
            ParqueaderoDto crearParqueaderoDto = parqueaderoMapper.toDTO(parqueadero);
            return new ResponseEntity<>(Map.of("id", parqueadero.getId()), HttpStatus.CREATED);
    }

    @Ingreso({"ADMIN"})
    @DeleteMapping("{id}")
    public Object eliminarParqueadero(@PathVariable Long id) {
        parqueaderoService.eliminarParqueadero(id);
        return new ResponseEntity<>(Map.of("Id eliminado:", id), HttpStatus.OK);
    }

    @Ingreso({"ADMIN"})
    @PutMapping("{id}")
    public Object actualizarParqueadero(@PathVariable Long id, @Valid @RequestBody ParqueaderoDto parqueaderoDto) {
        parqueaderoService.modificarParqueadero(id, parqueaderoDto);
        return new ResponseEntity<>(Map.of("Id eliminado:", id), HttpStatus.OK);
    }

    @Ingreso({"ADMIN"})
    @GetMapping("")
    public ResponseEntity<List<ParqueaderoDto>> listarParqueaderos(){
        return new ResponseEntity<>(parqueaderoService.obtenerParqueaderos(), HttpStatus.OK);
    }

    @Ingreso({"ADMIN"})
    @GetMapping("/{id}")
    public ResponseEntity<ParqueaderoDto> obtenerParqueaderoPorId(@PathVariable Long id) {
        Parqueadero parqueadero = parqueaderoService.buscarId(id);
        if (parqueadero != null) {
            ParqueaderoMapper parqueaderoMapper = new ParqueaderoMapper();
            ParqueaderoDto parqueaderoDto = parqueaderoMapper.toDTO(parqueadero);
            return new ResponseEntity<>(parqueaderoDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
