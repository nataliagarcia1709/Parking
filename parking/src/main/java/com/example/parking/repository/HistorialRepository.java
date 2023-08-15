package com.example.parking.repository;

import com.example.parking.entity.Historial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface HistorialRepository extends JpaRepository<Historial, Long> {


}

