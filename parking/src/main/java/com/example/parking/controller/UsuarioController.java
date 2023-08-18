package com.example.parking.controller;

import com.example.parking.dto.UsuarioDto;
import com.example.parking.entity.Usuario;
import com.example.parking.permisos.Ingreso;
import com.example.parking.repository.UsuarioRepository;
import com.example.parking.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @Ingreso({"ADMIN"})
    @PostMapping(path= "",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Object registrarUsuario(@Valid @RequestBody UsuarioDto usuarioDto) {
        Usuario usuario = usuarioService.registrarUsuario(usuarioDto);
        return new ResponseEntity<>(Map.of("Id:", usuario.getId()), HttpStatus.CREATED);
    }

    @Ingreso({"ADMIN"})
    @GetMapping()
    public ResponseEntity<List<UsuarioDto>> listarUsuarios(){
        return new ResponseEntity<>(usuarioService.obtenerTodosLosUsuarios(), HttpStatus.OK);
    }

    @Ingreso({"ADMIN"})
    @GetMapping("{id}")
    public ResponseEntity<UsuarioDto> obtenerUsuarioPorId(@PathVariable Long id) {
        UsuarioDto usuario = usuarioService.obtenerUsuarioPorId(id);
        if (usuario != null) {
            return new ResponseEntity<>(usuario, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Ingreso({"ADMIN"})
    @DeleteMapping("{id}")
    public Object eliminarUsuario(@PathVariable Long id) {
       usuarioService.eliminarUsuario(id);
            return new ResponseEntity<>(Map.of("Id eliminado:", id), HttpStatus.OK);
    }
    @Ingreso({"ADMIN"})
    @PostMapping("/{idUsuario}/parqueadero/{idParqueadero}")
    public Object asignarParqueaderoASocio(
            @PathVariable Long idUsuario,
            @PathVariable Long idParqueadero) {
        usuarioService.asignarParqueaderoASocio(idUsuario, idParqueadero);
        return ResponseEntity.ok("Parqueadero asignado exitosamente al usuario.");
    }
}
