package com.example.parking.service;


import com.example.parking.dto.EmailRequest;
import com.example.parking.dto.response.MensajeResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final RestTemplate restTemplate;

    public MensajeResponse enviarEmail(EmailRequest emailRequest) {
        // Llamar al microservicio de envío de correos
        ResponseEntity<MensajeResponse> correoResponse = restTemplate.postForEntity(
                "http://localhost:8081/api/enviar-correo", emailRequest, MensajeResponse.class);
        // MensajeResponse response = correoResponse.getBody();
        return new MensajeResponse("Correo Enviado");
    }
}
