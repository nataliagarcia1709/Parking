package com.example.parking.repository;

import com.example.parking.dto.ParqueaderoDto;
import com.example.parking.entity.Parqueadero;
import com.example.parking.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ParqueaderoRepository extends JpaRepository<Parqueadero, Long> {
    Optional<Parqueadero> findByNombre(String registro);

    List<Parqueadero> findAllByUsuarioId(@Param("usuarioId") Long usuarioId);

    List<Parqueadero> findAllByUsuario(Usuario usuario);
    /*@Query("SELECT p FROM Parqueadero p WHERE p.usuario.id = :usuarioId")
    List<Parqueadero> findAllByUsuarioId(@Param("usuarioId") Long usuarioId);
    */
}

