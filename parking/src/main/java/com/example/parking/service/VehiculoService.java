package com.example.parking.service;

import com.example.parking.entity.Vehiculo;
import com.example.parking.exception.NoSuchElementFoundException;
import com.example.parking.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehiculoService {
    private final VehiculoRepository vehiculoRepository;

    @Autowired
    public VehiculoService(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }
    public void agregarVehiculo(Vehiculo vehiculo) {
        vehiculoRepository.save(vehiculo);
    }
    public Vehiculo buscarVehiculoId(Long id){
        return vehiculoRepository.findById(id).orElseThrow(() -> new NoSuchElementFoundException("Vehiculo no encontrado"));
    }
    public Vehiculo buscarVehiculoPlaca(String placa){
        return vehiculoRepository.findByPlaca(placa).orElseThrow(() -> new NoSuchElementFoundException("Vehiculo no encontrado"));
    }


}
