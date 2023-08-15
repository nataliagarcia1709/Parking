package com.example.parking.repository;

import com.example.parking.dto.VehiculoDto;
import com.example.parking.entity.Parqueadero;
import com.example.parking.entity.RegistroParqueo;
import com.example.parking.entity.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistroParqueoRepository extends JpaRepository<RegistroParqueo, Long> {

    int countByParqueadero(Parqueadero parqueadero);
/*
    @Query("SELECT new com.example.parking.VehiculoDto(r.vehiculo.placa) FROM RegistroParqueo r")
    List<VehiculoDto> obtenerVehiculosDto();

    @Query("SELECT new com.example.parking.VehiculoDto(r.vehiculo.placa) FROM RegistroParqueo r WHERE r.vehiculo.placa = :placa")
    List<VehiculoDto> findByPlaca(@Param("placa") String placa);
*/
    Optional<RegistroParqueo> findByVehiculoAndFechaHoraSalidaIsNull(Vehiculo vehiculo);

    // Consulta para obtener los 10 vehículos que más veces se han registrado en los diferentes parqueaderos y cuantas veces han sido
    @Query("SELECT rp.vehiculo.id, COUNT(rp.vehiculo.id) FROM RegistroParqueo rp GROUP BY rp.vehiculo.id ORDER BY COUNT(rp.vehiculo.id) DESC LIMIT 10")
    List<Vehiculo> findTop10VehiculosMasRegistrados();
    /*
    @Query("SELECT Vehiculo(r.placa,COUNT(r)" + "FROM Historial r" + "GROUP BY r.vehiculo.placa" + "ORDER BY COUNT(r) DESC LIMIT 10")
    List<VehiculoDto> findTop10VehiculosMasRegistrados();
    */

    // Consulta para obtener los 10 vehículos que más veces se han registrado en un parqueadero y cuantas veces han sido
    @Query("SELECT rp.vehiculo.id, COUNT(rp.vehiculo.id) FROM RegistroParqueo rp WHERE rp.parqueadero.id = :idParqueadero GROUP BY rp.vehiculo.id ORDER BY COUNT(rp.vehiculo.id) DESC LIMIT 10")
    List<Vehiculo> findTop10VehiculosMasRegistradosPorParqueadero(@Param("idParqueadero") Long idParqueadero);
    /*
    @Query("SELECT new com.example.parking.dto.VehiculoDto(r.vehiculo.placa,COUNT(r)" + "FROM Historial r" + "WHERE r.parqueadero.id = :parqueaderoId" + "GROUP BY r.vehiculo.placa" + "ORDER BY COUNT(r) DESC LIMIT 10")
    List<VehiculoDto> findTop10VehiculosMasRegistradosPorParqueadero(@Param("parqueaderoId") Long parqueaderoId);
    */

    // Consulta para verificar de los vehículos parqueados cuales son por primera vez en ese parqueadero
    @Query("SELECT rp.vehiculo.id, COUNT(rp.vehiculo.id) FROM RegistroParqueo rp WHERE rp.parqueadero.id = :idParqueadero GROUP BY rp.vehiculo.id HAVING COUNT(rp.vehiculo.id) = 1")
    List<Vehiculo> findVehiculosRegistradosPorPrimeraVezEnParqueadero(@Param("idParqueadero") Long idParqueadero);
    /*
    @Query("SELECT new com.example.parking.dto.VehiculoDto(rp.vehiculo.id, COUNT(rp.vehiculo.id))" + " FROM Historial rp " + "WHERE rp.parqueadero.id = :idParqueadero" + "GROUP BY rp.vehiculo.id" + "HAVING COUNT(rp.vehiculo.id) = 1")
    List<VehiculoDto> findVehiculosRegistradosPorPrimeraVezEnParqueadero(@Param("idParqueadero") Long idParqueadero);
    */

}
