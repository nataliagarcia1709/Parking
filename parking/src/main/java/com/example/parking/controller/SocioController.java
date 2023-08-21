package com.example.parking.controller;

import com.example.parking.dto.ParqueaderoDto;
import com.example.parking.dto.RegistroParqueoDto;
import com.example.parking.dto.VehiculoDto;
import com.example.parking.dto.response.MensajeResponse;
import com.example.parking.dto.response.RegistroResponse;
import com.example.parking.entity.Parqueadero;
import com.example.parking.entity.Usuario;
import com.example.parking.exception.NoSuchElementFoundException;
import com.example.parking.permisos.Ingreso;
import com.example.parking.service.ParqueaderoService;
import com.example.parking.service.RegistroParqueoService;
import com.example.parking.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("/socios")
public class SocioController {

    private final ParqueaderoService parqueaderoService;

    private final RegistroParqueoService registroParqueoService;


    @Ingreso({"SOCIO"})
    @PostMapping(path = "/entrada",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public RegistroResponse registrarEntrada(@RequestBody @Valid RegistroParqueoDto registroParqueoDto ) {
        registroParqueoService.registrarIngreso(registroParqueoDto);
        return new RegistroResponse(registroParqueoDto.getIdPark());
    }

    @Ingreso({"SOCIO"})
    @PostMapping(path ="/salida",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public MensajeResponse registrarSalida(@RequestBody VehiculoDto vehiculoDto) {
        registroParqueoService.salidaVehiculo(vehiculoDto);
        String mensaje = "Salida registrada";
        return new MensajeResponse(mensaje);
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
    @GetMapping(path = "/vehiculo/{idParqueadero}", produces = MediaType.APPLICATION_JSON_VALUE)
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
