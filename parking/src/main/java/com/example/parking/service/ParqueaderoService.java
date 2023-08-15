package com.example.parking.service;

import com.example.parking.dto.ParqueaderoDto;
import com.example.parking.entity.Parqueadero;
import com.example.parking.entity.Usuario;
import com.example.parking.entity.Vehiculo;
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
    private ParqueaderoMapper parqueaderoMapper;

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

        parqueaderoDto.setId(parqueadero.getId());
        return parqueaderoRepository.save(parqueadero);
    }


    //Método para eliminar el parqueadero
    public void eliminarParqueadero(Long id) {
        parqueaderoRepository.deleteById(id);
    }

    //Método para modificar parqueadero
    public void modificarParqueadero(Long id, ParqueaderoDto parqueaderoDto) {
        ParqueaderoDto parqueaderoExistenteDto = buscarId(id);

        if (parqueaderoExistenteDto != null) {
            parqueaderoExistenteDto.setNombre(parqueaderoDto.getNombre());
            parqueaderoExistenteDto.setCapacidad(parqueaderoDto.getCapacidad());
            parqueaderoExistenteDto.setCostoHora(parqueaderoDto.getCostoHora());

            // Mapear DTO actualizado a entidad
            Parqueadero parqueaderoExistente = parqueaderoMapper.toEntity(parqueaderoExistenteDto);

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
    public ParqueaderoDto buscarId(Long id){
        Parqueadero parqueadero = parqueaderoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException("Parqueadero no encontrado"));

        return parqueaderoMapper.toDTO(parqueadero);
    }

    //Listar parqueadero por socio
    public List<ParqueaderoDto> parqueaderosPorSocio(Usuario usuario) {
        List<Parqueadero> listParqueadero = parqueaderoRepository.findAllByUsuarioId(usuario.getId());
        List<ParqueaderoDto> listParqueaderoDto = new ArrayList<>();
        for (Parqueadero parqueadero : listParqueadero) {
            ParqueaderoDto parqueaderoDTO = ParqueaderoDto
                    .builder()
                    .id(parqueadero.getId())
                    .nombre(parqueadero.getNombre())
                    .capacidad(parqueadero.getCapacidad())
                    .costoHora(parqueadero.getCostoHora())
                    .build();
            listParqueaderoDto.add(parqueaderoDTO);
        }
        return listParqueaderoDto;
    }



    }
