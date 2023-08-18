package com.example.parking.controller;

import com.example.parking.dto.ParqueaderoDto;
import com.example.parking.dto.RegistroParqueoDto;
import com.example.parking.dto.VehiculoDto;
import com.example.parking.entity.Parqueadero;
import com.example.parking.entity.Usuario;
import com.example.parking.exception.NoSuchElementFoundException;
import com.example.parking.permisos.Ingreso;
import com.example.parking.service.ParqueaderoService;
import com.example.parking.service.RegistroParqueoService;
import com.example.parking.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/socios")
public class SocioController {

    private final ParqueaderoService parqueaderoService;
    private final UsuarioService usuarioService;
    private final RegistroParqueoService registroParqueoService;

    @Autowired
    public SocioController(ParqueaderoService parqueaderoService, UsuarioService usuarioService, RegistroParqueoService registroParqueoService ) {
        this.parqueaderoService = parqueaderoService;
        this.usuarioService = usuarioService;
        this.registroParqueoService = registroParqueoService;
    }


    @Ingreso({"SOCIO"})
    @PostMapping(path = "/entrada",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Object registrarEntrada(@RequestBody @Valid RegistroParqueoDto registroParqueoDto ) {
        registroParqueoService.registrarIngreso(registroParqueoDto);
        return new ResponseEntity<>(Map.of("id", registroParqueoDto.getIdPark()), HttpStatus.CREATED);
    }
    @Ingreso({"SOCIO"})
    @PostMapping(path ="/salida",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> registrarSalida(@RequestBody VehiculoDto vehiculoDto) {
        registroParqueoService.salidaVehiculo(vehiculoDto);
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Salida registrada");

        return response;
    }
    @Ingreso({"SOCIO"})
    @GetMapping(path = "/parqueaderos",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ParqueaderoDto> listarParqueaderosPorSocio(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioActual = (Usuario) authentication.getPrincipal();
        //  Usuario usuario = usuarioService.findByEmail(SecurityUtil.obtenerEmail());
        return parqueaderoService.parqueaderosPorSocio(usuarioActual);
    }
    @Ingreso({"SOCIO"})
    @GetMapping(path = "/vehiculo/{idParqueadero}",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<VehiculoDto> listarVehiculoPorParqueadero(@PathVariable (value="idParqueadero")Long idParqueadero){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioActual = (Usuario) authentication.getPrincipal();
        Parqueadero parqueadero = parqueaderoService.buscarId(idParqueadero);
        if (!parqueadero.getUsuario().equals(usuarioActual)) {
            throw new NoSuchElementFoundException("No tienes permiso para acceder a este parqueadero.");
        }
        List<VehiculoDto> vehiculosDto = registroParqueoService.vehiculosPorParqueadero(parqueadero);
        return vehiculosDto;
    }
}
