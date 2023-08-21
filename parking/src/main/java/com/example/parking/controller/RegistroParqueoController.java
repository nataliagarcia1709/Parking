package com.example.parking.controller;

import com.example.parking.dto.VehiculoDto;
import com.example.parking.entity.Vehiculo;
import com.example.parking.permisos.Ingreso;
import com.example.parking.repository.HistorialRepository;
import com.example.parking.service.RegistroParqueoService;
import com.example.parking.service.VehiculoService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/registros")
public class RegistroParqueoController {

    private final HistorialRepository historialRepository;

    @Ingreso({"SOCIO", "ADMIN"})
    @GetMapping(path= "/vehiculos-mas-registrados", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Object[]> obtenerTop10VehiculosMasRegistrados() {
        List<Object[]> top10Vehiculos = historialRepository.findTop10VehiculosMasRegistrados();
        return top10Vehiculos;
    }

    @Ingreso({"SOCIO", "ADMIN"})
    @GetMapping(path = "/vehiculos-mas-registrados-por-parqueadero/{idParqueadero}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Object[]> obtenerTop10VehiculosMasRegistradosPorParqueadero(@PathVariable Long idParqueadero) {
        List<Object[]> top10VehiculosPorParqueadero = historialRepository.findTop10VehiculosMasRegistradosEnParqueadero(idParqueadero);
        return top10VehiculosPorParqueadero;
    }
    @Ingreso({"SOCIO", "ADMIN"})
    @GetMapping(path = "/vehiculos-primera-vez/{idParqueadero}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Vehiculo> obtenerVehiculosRegistradosPorPrimeraVezEnParqueadero(@PathVariable Long idParqueadero) {
        return historialRepository.findVehiculosParqueadosPrimeraVezEnParqueadero(idParqueadero);
    }
    @Ingreso({"ADMIN", "SOCIO"})
    @GetMapping(path = "/coincidencia", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<Vehiculo> buscarVehiculosPorCoincidencia(@RequestParam(defaultValue = "") String placa) {
        return historialRepository.findByCoincidencia(placa);
    }

}
