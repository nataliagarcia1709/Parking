package com.example.parking.controller;

import com.example.parking.dto.ParqueaderoDto;
import com.example.parking.dto.UsuarioDto;
import com.example.parking.entity.Parqueadero;
import com.example.parking.entity.Usuario;
import com.example.parking.mapper.ParqueaderoMapper;
import com.example.parking.mapper.UsuarioMapper;
import com.example.parking.repository.UsuarioRepository;
import com.example.parking.service.UsuarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioRepository usuarioRepository, UsuarioService usuarioService) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }

    @PostMapping(path= "",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody UsuarioDto usuarioDto) {
        Usuario usuario = usuarioService.registrarUsuario(usuarioDto);
        UsuarioMapper usuarioMapper = new UsuarioMapper();
        UsuarioDto registrarUsuarioDto = usuarioMapper.entityToDTO(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body("El usuario fue registrado con exito" + registrarUsuarioDto);
    }

    //Mostrar todos los usuarios

    @GetMapping()
    public ResponseEntity<List<Usuario>> listarUsuarios(){
        return new ResponseEntity<>(usuarioService.obtenerTodosLosUsuarios(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
        if (usuario != null) {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.ok("Parqueadero eliminado exitosamente");
    }
    @PostMapping("/{idUsuario}/parqueadero/{idParqueadero}")
    public ResponseEntity<String> asignarParqueaderoASocio(
            @PathVariable Long idUsuario,
            @PathVariable Long idParqueadero) {
        usuarioService.asignarParqueaderoASocio(idUsuario, idParqueadero);
        return ResponseEntity.ok("Parqueadero asignado exitosamente al usuario.");
    }
}
