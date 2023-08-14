package com.example.parking.service;

import com.example.parking.dto.RegistroParqueoDto;
import com.example.parking.entity.Parqueadero;
import com.example.parking.entity.RegistroParqueo;
import com.example.parking.entity.Vehiculo;
import com.example.parking.exception.NoSuchElementFoundException;
import com.example.parking.repository.ParqueaderoRepository;
import com.example.parking.repository.RegistroParqueoRepository;
import com.example.parking.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RegistroParqueoService {
    private final RegistroParqueoRepository registroParqueoRepository;

    private VehiculoRepository vehiculoRepository;

    private ParqueaderoRepository parqueaderoRepository;
    @Autowired
    public RegistroParqueoService(RegistroParqueoRepository registroParqueoRepository, VehiculoRepository vehiculoRepository, ParqueaderoRepository parqueaderoRepository){
        this.registroParqueoRepository= registroParqueoRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.parqueaderoRepository= parqueaderoRepository;
    }

    public void registrarEntrada(RegistroParqueoDto registroEntradaDto) {
        Vehiculo vehiculo = new Vehiculo();
        Parqueadero parqueadero = new Parqueadero();

       /* // Validar que la placa tenga 6 caracteres y no contenga la letra "ñ"
        if (vehiculo.getPlaca().length() != 6 || vehiculo.getPlaca().toLowerCase().contains("ñ")) {
            throw new NoSuchElementFoundException("El formato de la placa es inválido, debe tener 6 caracteres sin incluir la ñ.");
        }
        // Validar si el vehículo ya está registrado en algún parqueadero
        List<RegistroParqueo> registrosExistentes = registroParqueoRepository.findByVehiculo(vehiculo.getPlaca());
        if (!registrosExistentes.isEmpty()) {
            throw new NoSuchElementFoundException("No se puede Registrar Ingreso, ya existe la placa en este u otro parqueadero");
        }*/

        // Validar la capacidad del parqueadero antes de registrar el ingreso
        int capacidadDisponible = parqueadero.getCapacidad() - registroParqueoRepository.countByParqueaderoAndFechaHoraSalidaIsNull(parqueadero);
        if (capacidadDisponible <= 0) {
            throw new NoSuchElementFoundException("No se puede Registrar Ingreso, el parqueadero está lleno");
        }

        RegistroParqueo registroEntrada = new RegistroParqueo();
        registroEntrada.setVehiculo(vehiculo);
        registroEntrada.setParqueadero(parqueadero);
        registroEntrada.setFechaHoraIngreso(LocalDateTime.now());

        registroParqueoRepository.save(registroEntrada);
    }

    public void registrarSalida(RegistroParqueoDto registroSalidaDto) {
        Vehiculo vehiculo = vehiculoRepository.findByPlaca(registroSalidaDto.getPlaca())
                .orElseThrow(() -> new NoSuchElementFoundException("Vehículo no encontrado"));

        RegistroParqueo registroEntrada = registroParqueoRepository.findByVehiculoAndFechaHoraSalidaIsNull(vehiculo)
                .orElseThrow(() -> new NoSuchElementFoundException("Registro de entrada no encontrado"));

        registroEntrada.setFechaHoraSalida(LocalDateTime.now());
        // Calcular el costo total y actualizar el registro

        registroParqueoRepository.save(registroEntrada);
    }
    // Método para obtener los vehículos más registrados en todos los parqueaderos
   /* public List<Vehiculo> obtenerTop10VehiculosMasRegistrados() {
        return registroParqueoRepository.findTop10VehiculosMasRegistrados();
    }

    // Método para buscar vehículos parqueados mediante coincidencia en la placa
    public List<Vehiculo> buscarVehiculosPorPlaca(String placa) {
        return vehiculoRepository.buscarVehiculoPorPlaca(placa);
    }*/

}
