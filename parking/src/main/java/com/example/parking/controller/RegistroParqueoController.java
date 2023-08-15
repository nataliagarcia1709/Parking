package com.example.parking.controller;

import com.example.parking.dto.RegistroParqueoDto;
import com.example.parking.dto.VehiculoIndicadorResponse;
import com.example.parking.service.RegistroParqueoService;
import com.example.parking.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registros")
public class RegistroParqueoController {

    private RegistroParqueoService registroParqueoService;
    private VehiculoService vehiculoService;

    @Autowired
    public RegistroParqueoController(RegistroParqueoService registroParqueoService, VehiculoService vehiculoService){
        this.registroParqueoService=registroParqueoService;
        this.vehiculoService=vehiculoService;
    }
/*
    @PostMapping("/entrada")
    public ResponseEntity<String> registrarEntrada(@RequestBody RegistroParqueoDto registroEntradaDTO) {
        registroParqueoService.registrarEntrada(registroEntradaDTO);
        return ResponseEntity.ok("Entrada registrada exitosamente");
    }

    @PostMapping("/salida")
    public ResponseEntity<String> registrarSalida(@RequestBody RegistroParqueoDto registroSalidaDTO) {
        registroParqueoService.registrarSalida(registroSalidaDTO);
        return ResponseEntity.ok("Salida registrada exitosamente");
    }
*/
    @GetMapping("/vehiculos-mas-registrados")
    public ResponseEntity<List<VehiculoIndicadorResponse>> obtenerTop10VehiculosMasRegistrados() {
        List<VehiculoIndicadorResponse> top10Vehiculos = registroParqueoService.obtenerTop10VehiculosMasRegistrados();
        return ResponseEntity.ok(top10Vehiculos);
    }

    @GetMapping("/vehiculos-mas-registrados-por-parqueadero/{idParqueadero}")
    public ResponseEntity<List<VehiculoIndicadorResponse>> obtenerTop10VehiculosMasRegistradosPorParqueadero(@PathVariable Long idParqueadero) {
        List<VehiculoIndicadorResponse> top10VehiculosPorParqueadero = registroParqueoService.obtenerTop10VehiculosMasRegistradosPorParqueadero(idParqueadero);
        return ResponseEntity.ok(top10VehiculosPorParqueadero);
    }

    @GetMapping("/vehiculos-primera-vez/{idParqueadero}")
    public ResponseEntity<List<VehiculoIndicadorResponse>> obtenerVehiculosRegistradosPorPrimeraVezEnParqueadero(@PathVariable Long idParqueadero) {
        List<VehiculoIndicadorResponse> vehiculosPorPrimeraVez = registroParqueoService.obtenerVehiculosRegistradosPorPrimeraVezEnParqueadero(idParqueadero);
        return ResponseEntity.ok(vehiculosPorPrimeraVez);
    }

}
