package com.example.parking.service;

import com.example.parking.dto.ParqueaderoDto;
import com.example.parking.dto.RegistroParqueoDto;
import com.example.parking.dto.VehiculoDto;
import com.example.parking.dto.VehiculoIndicadorResponse;
import com.example.parking.entity.Historial;
import com.example.parking.entity.Parqueadero;
import com.example.parking.entity.RegistroParqueo;
import com.example.parking.entity.Vehiculo;
import com.example.parking.exception.NoSuchElementFoundException;
import com.example.parking.mapper.ParqueaderoMapper;
import com.example.parking.mapper.VehiculoMapper;
import com.example.parking.repository.ParqueaderoRepository;
import com.example.parking.repository.RegistroParqueoRepository;
import com.example.parking.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RegistroParqueoService {
    private final RegistroParqueoRepository registroParqueoRepository;
    private VehiculoRepository vehiculoRepository;
    private ParqueaderoRepository parqueaderoRepository;
    private VehiculoMapper vehiculoMapper;
    private ParqueaderoMapper parqueaderoMapper;
    @Autowired
    public RegistroParqueoService(RegistroParqueoRepository registroParqueoRepository, VehiculoRepository vehiculoRepository, ParqueaderoRepository parqueaderoRepository,
    VehiculoMapper vehiculoMapper, ParqueaderoMapper parqueaderoMapper){
        this.registroParqueoRepository= registroParqueoRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.parqueaderoRepository= parqueaderoRepository;
        this.vehiculoMapper = vehiculoMapper;
        this.parqueaderoMapper= parqueaderoMapper;

    }

    // Método para obtener los 10 vehículos más registrados en los diferentes parqueaderos
    public List<VehiculoIndicadorResponse> obtenerTop10VehiculosMasRegistrados() {
        List<Vehiculo> resultados = registroParqueoRepository.findTop10VehiculosMasRegistrados();
        return mapearResultados(resultados);
    }

    // Método para obtener los 10 vehículos más registrados en un parqueadero específico
    public List<VehiculoIndicadorResponse> obtenerTop10VehiculosMasRegistradosPorParqueadero(Long idParqueadero) {
        List<Vehiculo> resultados = registroParqueoRepository.findTop10VehiculosMasRegistradosPorParqueadero(idParqueadero);
        return mapearResultados(resultados);
    }

    // Método para verificar qué vehículos están siendo registrados por primera vez en un parqueadero
    public List<VehiculoIndicadorResponse> obtenerVehiculosRegistradosPorPrimeraVezEnParqueadero(Long idParqueadero) {
        List<Vehiculo> resultados = registroParqueoRepository.findVehiculosRegistradosPorPrimeraVezEnParqueadero(idParqueadero);
        return mapearResultados(resultados);
    }
    // Método privado para mapear los resultados obtenidos de las consultas
    private List<VehiculoIndicadorResponse> mapearResultados(List<Vehiculo> resultados) {
        List<VehiculoIndicadorResponse> response = new ArrayList<>();

        for (Vehiculo vehiculo : resultados) {
            Long vehiculoId = vehiculo.getId();
            Long vecesRegistrado = vehiculo.getVecesRegistrado();
            response.add(new VehiculoIndicadorResponse(vehiculoId, vecesRegistrado));
        }

        return response;
    }

    /*
    // Método para registrar el ingreso de un vehículo a un parqueadero
    public RegistroParqueo registrarIngreso(VehiculoDto vehiculoDto, ParqueaderoDto parqueaderoDto) {


        // Validar que la placa tenga 6 caracteres y no contenga la letra "ñ"
        String placa = vehiculoDto.getPlaca();
        if (placa.length() != 6 || placa.toLowerCase().contains("ñ")) {
            throw new NoSuchElementFoundException("El formato de la placa es inválido, debe tener 6 caracteres sin incluir la ñ.");
        }
        // Validar si el vehículo ya está registrado en algún parqueadero
        List<RegistroParqueo> registrosExistentes = registroParqueoRepository.findByVehiculo(vehiculoDto);
        if (!registrosExistentes.isEmpty()) {
            throw new NoSuchElementFoundException("No se puede Registrar Ingreso, ya existe la placa en este u otro parqueadero");
        }

        RegistroParqueo registro = RegistroParqueo.builder()
                .vehiculo(vehiculoMapper.dtoToEntity(vehiculoDto)) // Mapear DTO a entidad Vehiculo
                .parqueadero(parqueaderoMapper.toEntity(parqueaderoDto)) // Mapear DTO a entidad Parqueadero
                .fechaHoraIngreso(LocalDateTime.now())
                .build();

        return registroParqueoRepository.save(registro);
    }

    //Método para registrar la salida de un carro
    public void registrarSalida(Vehiculo vehiculo, Parqueadero parqueadero) {
        Vehiculo vehiculo = vehiculoRepository.findByPlaca(vehiculo.getPlaca())
                .orElseThrow(() -> new NoSuchElementFoundException("Vehículo no encontrado"));

        RegistroParqueo registroEntrada = registroParqueoRepository.findByVehiculoAndFechaHoraSalidaIsNull(vehiculo)
                .orElseThrow(() -> new NoSuchElementFoundException("Registro de entrada no encontrado"));

        registro.setFechaHoraSalida(LocalDateTime.now());
        registroParqueoRepository.save(registro);
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
    */
}
