package com.example.parking.controller;

import com.example.parking.dto.UsuarioDto;
import com.example.parking.dto.response.MensajeResponse;
import com.example.parking.dto.response.RegistroResponse;
import com.example.parking.entity.Usuario;
import com.example.parking.permisos.Ingreso;
import com.example.parking.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Ingreso({"ADMIN"})
    @PostMapping(path= "",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public RegistroResponse registrarUsuario(@Valid @RequestBody UsuarioDto usuarioDto) {
        Usuario usuario = usuarioService.registrarUsuario(usuarioDto);
        return new RegistroResponse(usuario.getId());
    }

    @Ingreso({"ADMIN"})
    @GetMapping(path="",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<UsuarioDto> listarUsuarios(){
        return usuarioService.obtenerTodosLosUsuarios();
    }

    @Ingreso({"ADMIN"})
    @GetMapping(path="{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public UsuarioDto obtenerUsuarioPorId(@PathVariable Long id) {
        UsuarioDto usuario = usuarioService.obtenerUsuarioPorId(id);
        if (usuario != null) {
            return usuario;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
    }

    @Ingreso({"ADMIN"})
    @DeleteMapping(path="{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public RegistroResponse eliminarUsuario(@PathVariable Long id) {
       usuarioService.eliminarUsuario(id);
            return new RegistroResponse(id);
    }
    @Ingreso({"ADMIN"})
    @PostMapping(path="/{idUsuario}/parqueadero/{idParqueadero}",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public MensajeResponse asignarParqueaderoASocio( @PathVariable Long idUsuario, @PathVariable Long idParqueadero) {
        usuarioService.asignarParqueaderoASocio(idUsuario, idParqueadero);
        String mensaje = "Asignado con éxito";
        return new MensajeResponse(mensaje);
    }
}
