package com.example.parking.repository;

import com.example.parking.entity.Parqueadero;
import com.example.parking.entity.RegistroParqueo;
import com.example.parking.entity.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistroParqueoRepository extends JpaRepository<RegistroParqueo, Long> {

    int countByParqueaderoAndFechaHoraSalidaIsNull(Parqueadero parqueadero);
    List<RegistroParqueo> findByVehiculo_Placa(String placa);
    Optional<RegistroParqueo> findByVehiculoAndFechaHoraSalidaIsNull(Vehiculo vehiculo);
}
