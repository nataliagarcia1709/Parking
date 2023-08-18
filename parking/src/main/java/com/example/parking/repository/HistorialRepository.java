package com.example.parking.repository;

import com.example.parking.entity.Historial;
import com.example.parking.entity.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HistorialRepository extends JpaRepository<Historial, Long> {

    @Query("SELECT h.vehiculo, COUNT(h) FROM Historial h GROUP BY h.vehiculo ORDER BY COUNT(h) DESC LIMIT 10")
    List<Object[]> findTop10VehiculosMasRegistrados();

    @Query("SELECT h.vehiculo, COUNT(h) FROM Historial h WHERE h.parqueadero.id = :parqueaderoId GROUP BY h.vehiculo ORDER BY COUNT(h) DESC LIMIT 10")
    List<Object[]> findTop10VehiculosMasRegistradosEnParqueadero(@Param("parqueaderoId") Long parqueaderoId);

    @Query("SELECT h.vehiculo FROM Historial h WHERE h.parqueadero.id = :parqueaderoId GROUP BY h.vehiculo HAVING COUNT(h) = 1")
    List<Vehiculo> findVehiculosParqueadosPrimeraVezEnParqueadero(@Param("parqueaderoId") Long parqueaderoId);

    @Query("SELECT h.vehiculo FROM Historial h WHERE h.vehiculo.placa LIKE %:placa%")
    List<Vehiculo> findByCoincidencia(@Param("placa") String placa);
}
