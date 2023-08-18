package com.example.parking.service;

import com.example.parking.dto.ParqueaderoDto;
import com.example.parking.entity.Parqueadero;
import com.example.parking.entity.Usuario;
import com.example.parking.exception.NoSuchElementFoundException;
import com.example.parking.mapper.ParqueaderoMapper;
import com.example.parking.repository.ParqueaderoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ParqueaderoService {
    private final ParqueaderoRepository parqueaderoRepository;
    private final UsuarioService usuarioService;
    private final ParqueaderoMapper parqueaderoMapper;

    @Autowired
    public ParqueaderoService(ParqueaderoRepository parqueaderoRepository,UsuarioService usuarioService, ParqueaderoMapper parqueaderoMapper) {
        this.parqueaderoRepository = parqueaderoRepository;
        this.usuarioService = usuarioService;
        this.parqueaderoMapper = parqueaderoMapper;
    }

    //Método para crear el parqueadero
    public Parqueadero crearParqueadero(ParqueaderoDto parqueaderoDto)  throws NoSuchElementFoundException {

        Parqueadero parqueadero = new Parqueadero();
        parqueadero.setNombre(parqueaderoDto.getNombre());
        parqueadero.setCapacidad(parqueaderoDto.getCapacidad());
        parqueadero.setCostoHora(parqueaderoDto.getCostoHora());


        parqueaderoRepository.save(parqueadero);
        return parqueadero;
    }


    //Método para eliminar el parqueadero
    public void eliminarParqueadero(Long id) {
        parqueaderoRepository.deleteById(id);
    }

    //Método para modificar parqueadero
    public void modificarParqueadero(Long id, ParqueaderoDto parqueaderoDto) {
        Parqueadero parqueaderoExistente = buscarId(id);

        if (parqueaderoExistente != null) {
            parqueaderoExistente.setNombre(parqueaderoDto.getNombre());
            parqueaderoExistente.setCapacidad(parqueaderoDto.getCapacidad());
            parqueaderoExistente.setCostoHora(parqueaderoDto.getCostoHora());

            // Guardar la entidad actualizada en el repositorio
            parqueaderoRepository.save(parqueaderoExistente);
        }
    }


    //Obtener todos los parqueaderos
    public List<ParqueaderoDto> obtenerParqueaderos() {
        List<Parqueadero> parqueaderos = parqueaderoRepository.findAll();
        List<ParqueaderoDto> parqueaderosDto = new ArrayList<>();
        for (Parqueadero parqueadero : parqueaderos) {
            ParqueaderoDto parqueaderoDto = parqueaderoMapper.toDTO(parqueadero);
            parqueaderosDto.add(parqueaderoDto);
        }
        return parqueaderosDto;
        }

    //Método para buscar parqueadero por id
    public Parqueadero buscarId(Long id){
        Parqueadero parqueadero = parqueaderoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException("Parqueadero no encontrado"));

        return parqueadero;
    }

    //Listar parqueadero por socio
    public List<ParqueaderoDto> parqueaderosPorSocio(Usuario usuario) {

        List<Parqueadero> listParqueadero = parqueaderoRepository.findAllByUsuario(usuario);
        List<ParqueaderoDto> listParqueaderoDto = new ArrayList<>();
        for (Parqueadero parqueadero : listParqueadero) {
            ParqueaderoDto parqueaderoDTO = ParqueaderoDto
                    .builder()
                    .nombre(parqueadero.getNombre())
                    .capacidad(parqueadero.getCapacidad())
                    .costoHora(parqueadero.getCostoHora())
                    .build();
            listParqueaderoDto.add(parqueaderoDTO);
        }
        return listParqueaderoDto;
    }



    }
