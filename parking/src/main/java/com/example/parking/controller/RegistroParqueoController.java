package com.example.parking.controller;

import com.example.parking.dto.RegistroParqueoDto;
import com.example.parking.service.RegistroParqueoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registros")
public class RegistroParqueoController {

    private RegistroParqueoService registroParqueoService;

    @Autowired
    public RegistroParqueoController(RegistroParqueoService registroParqueoService){
        this.registroParqueoService=registroParqueoService;
    }

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
}
