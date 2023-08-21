package com.example.parking.controller;

import com.example.parking.dto.EmailRequest;
import com.example.parking.dto.response.MensajeResponse;
import com.example.parking.permisos.Ingreso;
import com.example.parking.service.EmailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mensajes")
public class EmailController {

    private final EmailService emailService;

    @Ingreso({"ADMIN", "SOCIO"})
    @PostMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public MensajeResponse sendEmail(@Valid @RequestBody EmailRequest emailRequest){
        return emailService.enviarEmail(emailRequest);
    }

}
