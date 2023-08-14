package com.example.parking.repository;

import com.example.parking.entity.Parqueadero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ParqueaderoRepository extends JpaRepository<Parqueadero, Long> {

    Parqueadero findByNombre(String nombre);


}
