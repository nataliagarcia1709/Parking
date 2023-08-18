package com.example.parking.service;

import com.example.parking.dto.RegistroParqueoDto;
import com.example.parking.dto.VehiculoDto;
import com.example.parking.entity.*;
import com.example.parking.exception.NoSuchElementFoundException;
import com.example.parking.exception.UnauthorizedAccessException;
import com.example.parking.repository.*;
import com.example.parking.security.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class RegistroParqueoService {
    private final RegistroParqueoRepository registroParqueoRepository;
    private final ParqueaderoRepository parqueaderoRepository;
    private final VehiculoService vehiculoService;
    private final HistorialRepository historialRepository;
    private final UsuarioService usuarioService;

    @Autowired
    public RegistroParqueoService(RegistroParqueoRepository registroParqueoRepository, ParqueaderoRepository parqueaderoRepository,
                                  VehiculoService vehiculoService, HistorialRepository historialRepository, UsuarioService usuarioService) {
        this.registroParqueoRepository = registroParqueoRepository;
        this.parqueaderoRepository = parqueaderoRepository;
        this.vehiculoService = vehiculoService;
        this.historialRepository = historialRepository;
        this.usuarioService = usuarioService;
    }

    @Transactional
    // Método para registrar el ingreso de un vehículo a un parqueadero
    public void registrarIngreso(RegistroParqueoDto registroParqueoDto) {

        //Verifica que exista
        Parqueadero parqueaderoEncontrado = parqueaderoRepository.findById(registroParqueoDto.getIdPark())
                .orElseThrow(() -> new NoSuchElementFoundException("Parqueadero no encontrado"));

       /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuarioActual = (Usuario) authentication.getPrincipal();
        List<Parqueadero> parqueaderosAsignados = usuarioActual.getParqueadero();
        boolean idCoincide = false;
        // Verificar que el parqueadero pertenezca al usuario
        for ( Parqueadero parqueaderoAsignado : parqueaderosAsignados) {
            Long parqueaderoId = parqueaderoAsignado.getId();
            if (parqueaderoId.equals(registroParqueoDto.getIdPark())) {
                idCoincide = true;
                break;
            }
        }
        if(!idCoincide){
            throw new IllegalArgumentException("El ID especificado no coincide con ningún parqueadero asignado al usuario.");
        }*/
        //Verifica la capacidad
        if (parqueaderoEncontrado.getCapacidad() <= 0) {
            throw new IllegalStateException("El parqueadero no tiene capacidad disponible.");
        }
        Vehiculo vehiculo = new Vehiculo();
        if (vehiculoService.existeVehiculo(registroParqueoDto.getPlaca())) {
            vehiculo = vehiculoService.findVehiculoByPlaca(registroParqueoDto.getPlaca());
        } else {
            vehiculo = Vehiculo
                    .builder()
                    .placa(registroParqueoDto.getPlaca())
                    .build();
            vehiculoService.agregarVehiculo(vehiculo);
        }
        if (ingresoVehiculoParqueadero(vehiculo)) {
            throw new NoSuchElementFoundException("No se puede Registrar Ingreso, ya existe la placa en este u otro parqueadero");
        } else {
            // Registrar la entrada del vehículo
            RegistroParqueo registro = new RegistroParqueo();
            registro.setVehiculo(vehiculo);
            registro.setParqueadero(parqueaderoEncontrado);
            registro.setFechaHoraIngreso(LocalDateTime.now());
            registroParqueoRepository.save(registro);
            // Decrementar la capacidad del parqueadero
            parqueaderoEncontrado.setCapacidad(parqueaderoEncontrado.getCapacidad() - 1);
        }
    }

    public boolean ingresoVehiculoParqueadero(Vehiculo vehiculo) {
        RegistroParqueo registroParqueo = findByVehiculo(vehiculo);
        return registroParqueo != null;
    }

    private RegistroParqueo findByVehiculo(Vehiculo vehiculo) {
        return registroParqueoRepository.findByVehiculo(vehiculo);
    }

    //Método para registrar la salida de un carro

    @Transactional

    public void salidaVehiculo(VehiculoDto vehiculoDto) {
        Vehiculo vehiculo = vehiculoService.findVehiculoByPlaca(vehiculoDto.getPlaca());
        Usuario user = usuarioService.findByEmail(SecurityUtil.obtenerEmail());
        RegistroParqueo registroParqueo = findByVehiculo(vehiculo);
        if (registroParqueo == null) {
            throw new NoSuchElementFoundException("Vehiculo no encontrado");
        }
        // Obtener el usuario del registroParqueo
        Usuario usuarioRegistroParqueo = registroParqueo.getParqueadero().getUsuario();
        // Obtener el usuario del user
        Usuario usuarioActual = user;
        // Comparar los usuarios usando el método equals de Usuario
        if (!usuarioRegistroParqueo.equals(usuarioActual)) {
            throw new UnauthorizedAccessException("El vehiculo se encuentra en un parqueadero no asociado");
        }
        Historial historial = Historial.builder()
                .vehiculo(registroParqueo.getVehiculo())
                .parqueadero(registroParqueo.getParqueadero())
                .fechaHoraIngreso(registroParqueo.getFechaHoraIngreso())
                .build();
        historialRepository.save(historial);
        registroParqueoRepository.delete(registroParqueo);
    }

    public List<VehiculoDto> vehiculosPorParqueadero(Parqueadero parqueadero){
        List<Vehiculo> listaVehiculos = registroParqueoRepository.findByParqueadero(parqueadero);
        List<VehiculoDto> listaVehiculosDto =new ArrayList<>();
        for (Vehiculo vehiculo : listaVehiculos){
            VehiculoDto vehiculoDto = VehiculoDto.builder()
                    .placa(vehiculo.getPlaca())
                    .build();
            listaVehiculosDto.add(vehiculoDto);
        }
        return listaVehiculosDto;
    }

    public List<VehiculoDto> vehiculosPorCoincidencia(String placa){
        List<Vehiculo> listaVehiculos = historialRepository.findByCoincidencia(placa);
        List<VehiculoDto> listaVehiculosDto =new ArrayList<>();
        for (Vehiculo vehiculo : listaVehiculos){
            VehiculoDto vehiculoDto = VehiculoDto.builder()
                    .placa(vehiculo.getPlaca())
                    .build();
            listaVehiculosDto.add(vehiculoDto);
        }
        return listaVehiculosDto;
    }

    // Método para obtener los 10 vehículos más registrados en los diferentes parqueaderos
    public List<Object[]> obtenerTop10VehiculosMasRegistrados() {
        return historialRepository.findTop10VehiculosMasRegistrados();
    }

    // Método para obtener los 10 vehículos más registrados en un parqueadero específico
    public List<Object[]> obtenerTop10VehiculosMasRegistradosEnParqueadero(Long parqueaderoId) {
        return historialRepository.findTop10VehiculosMasRegistradosEnParqueadero(parqueaderoId);
    }

    // Método para verificar qué vehículos están siendo registrados por primera vez en un parqueadero
    public List<Vehiculo> obtenerVehiculosParqueadosPrimeraVezEnParqueadero(Long parqueaderoId) {
        return historialRepository.findVehiculosParqueadosPrimeraVezEnParqueadero(parqueaderoId);
    }
}

