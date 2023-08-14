package com.example.parking.service;

import com.example.parking.dto.ParqueaderoDto;
import com.example.parking.entity.Parqueadero;
import com.example.parking.exception.NoSuchElementFoundException;
import com.example.parking.repository.ParqueaderoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParqueaderoService {
    private final ParqueaderoRepository parqueaderoRepository;
    private final UsuarioService usuarioService;

    @Autowired
    public ParqueaderoService(ParqueaderoRepository parqueaderoRepository,UsuarioService usuarioService) {
        this.parqueaderoRepository = parqueaderoRepository;
        this.usuarioService = usuarioService;
    }

    //Método para crear el parqueadero
    public Parqueadero crearParqueadero(String nombre, int capacidad, float costoHora)  throws NoSuchElementFoundException {
        Parqueadero parqueadero = new Parqueadero();
        parqueadero.setNombre(nombre);
        parqueadero.setCapacidad(capacidad);
        parqueadero.setCostoHora(costoHora);

        if(false) {
            throw new NoSuchElementFoundException("Guardado exitosamente");
        }

        return parqueaderoRepository.save(parqueadero);
    }

    //Método para eliminar el parqueadero
    public void eliminarParqueadero(Long id) {
        parqueaderoRepository.deleteById(id);
    }

    //Método para modificar parqueadero
   public void modificarParqueadero(Long id, ParqueaderoDto parqueaderoDto) {

        Parqueadero parqueadero = buscarId(id);

        if(parqueadero!=null) {
            parqueadero.setNombre(parqueaderoDto.getNombre());
            parqueadero.setCapacidad(parqueaderoDto.getCapacidad());
            parqueadero.setCostoHora(parqueaderoDto.getCostoHora());
            parqueaderoRepository.save(parqueadero);
        }
    }

    //Obtener todos los parqueaderos
    public List<Parqueadero> obtenerParqueaderos() {
        List<Parqueadero> parqueaderos = parqueaderoRepository.findAll();

        return parqueaderos;
    }

    public Parqueadero buscarId(Long id){
        return parqueaderoRepository.findById(id).orElseThrow(() -> new NoSuchElementFoundException("parqueadero no encontrado"));
    }


}
