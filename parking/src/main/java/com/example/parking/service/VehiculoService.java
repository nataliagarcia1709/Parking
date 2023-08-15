package com.example.parking.service;

import com.example.parking.dto.VehiculoDto;
import com.example.parking.dto.VehiculoIndicadorResponse;
import com.example.parking.entity.Historial;
import com.example.parking.entity.Vehiculo;
import com.example.parking.exception.NoSuchElementFoundException;
import com.example.parking.repository.HistorialRepository;
import com.example.parking.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehiculoService {
    private final VehiculoRepository vehiculoRepository;
    private HistorialRepository historialRepository ;

    @Autowired
    public VehiculoService(VehiculoRepository vehiculoRepository, HistorialRepository historialRepository) {
        this.vehiculoRepository = vehiculoRepository;
        this.historialRepository = historialRepository;
    }

    //Método para agregar un carro
    public void agregarVehiculo(Vehiculo vehiculo) {
        vehiculoRepository.save(vehiculo);
    }

    //Método para buscar un carro por el id
    public Vehiculo buscarVehiculoId(Long id){
        return vehiculoRepository.findById(id).orElseThrow(() -> new NoSuchElementFoundException("Vehiculo no encontrado"));
    }

    //Método para buscar un carro por la placa
    public Vehiculo findVehiculoByPlaca(String placa){
        return vehiculoRepository.findByPlaca(placa).orElseThrow(() -> new NoSuchElementFoundException("Vehiculo no encontrado"));
    }

    // Método para buscar vehículos parqueados mediante coincidencia en la placa
    public List<VehiculoDto> buscarVehiculosDtoPorPlaca(String placa) {
        List<Vehiculo> vehiculos = vehiculoRepository.findVehiculoByPlaca(placa);

        List<VehiculoDto> vehiculosDto = new ArrayList<>();
        for (Vehiculo vehiculo : vehiculos) {
            vehiculosDto.add(new VehiculoDto(vehiculo.getId(),
                    vehiculo.getPlaca(),
                    vehiculo.getVecesRegistrado()));
        }

        return vehiculosDto;
    }



}
