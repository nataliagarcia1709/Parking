package com.example.parking.controller;


import com.example.parking.dto.ParqueaderoDto;
import com.example.parking.entity.Parqueadero;
import com.example.parking.entity.Usuario;
import com.example.parking.mapper.ParqueaderoMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.parking.service.ParqueaderoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RestController
@RequestMapping("/parqueaderos")
public class ParqueaderoController {

    private final ParqueaderoService parqueaderoService;
    private final ParqueaderoMapper parqueaderoMapper;

    @Autowired
    public ParqueaderoController(ParqueaderoService parqueaderoService, ParqueaderoMapper parqueaderoMapper) {
        this.parqueaderoService = parqueaderoService;
        this.parqueaderoMapper = parqueaderoMapper;
    }

    @PostMapping(path = "",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ParqueaderoDto> crearParqueadero(@RequestBody @Valid ParqueaderoDto parqueaderoDto) {
        Parqueadero parqueadero = parqueaderoService.crearParqueadero(
                parqueaderoDto.getNombre(),
                parqueaderoDto.getCapacidad(),
                parqueaderoDto.getCostoHora()
        );
        ParqueaderoMapper parqueaderoMapper = new ParqueaderoMapper();
        ParqueaderoDto crearParqueaderoDto = parqueaderoMapper.toDTO(parqueadero);
        return ResponseEntity.status(HttpStatus.CREATED).body(crearParqueaderoDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarParqueadero(@PathVariable Long id) {
        parqueaderoService.eliminarParqueadero(id);
        return ResponseEntity.ok("Parqueadero eliminado exitosamente");
    }

    @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Parqueadero> listarParqueaderos(){
        return parqueaderoService.obtenerParqueaderos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parqueadero> obtenerParqueaderoPorId(@PathVariable Long id) {
        Parqueadero parqueadero = parqueaderoService.buscarId(id);
        if (parqueadero != null) {
            return new ResponseEntity<>(parqueadero, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

  /*@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
       public ResponseEntity<ParqueaderoDto> modificarParqueadero(@PathVariable Long id, @RequestBody ParqueaderoDto parqueaderoDto) {
        Parqueadero actualizarParqueadero = parqueaderoService.modificarParqueadero(id, parqueaderoDto);
        ParqueaderoDto actualizarParqueaderoDTO = parqueaderoMapper.toDTO(actualizarParqueadero);
        return ResponseEntity.ok(actualizarParqueaderoDTO);
    }*/

}
