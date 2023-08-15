package com.example.parking.service;

import com.example.parking.dto.UsuarioDto;
import com.example.parking.entity.Parqueadero;
import com.example.parking.entity.Usuario;
import com.example.parking.enumeraciones.Rol;
import com.example.parking.exception.NoSuchElementFoundException;
import com.example.parking.repository.ParqueaderoRepository;
import com.example.parking.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ParqueaderoRepository parqueaderoRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, ParqueaderoRepository parqueaderoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.parqueaderoRepository = parqueaderoRepository;
    }

    // Método para registrar un nuevo usuario
    public Usuario registrarUsuario(UsuarioDto usuarioDto ) throws NoSuchElementFoundException {

        Usuario usuario = new Usuario();
        Optional<Usuario> existingUser = usuarioRepository.findByEmail(usuario.getEmail());
        if (existingUser.isPresent()) {
            throw new NoSuchElementFoundException("Email ya existe");
        }
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRol(Rol.SOCIO);
        usuarioDto.setId(usuario.getId());
        return usuarioRepository.save(usuario);
    }

    //Método para ver todos los usuarios registrados
    public List<Usuario> obtenerTodosLosUsuarios() {
        /*List<Usuario> usuarios = usuarioRepository.findAll();
        for(Usuario usuario : usuarios){
            usuario.getParqueadero();
        }*/
        return usuarioRepository.findAll();
    }

    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException("Usuario no encontrado" ));
    }

    //Método para eliminar un usuario
    public void eliminarUsuario(Long id){
        usuarioRepository.deleteById(id);
    }

    //Método para asignar parqueadero a un socio
    public void asignarParqueaderoASocio(Long idUsuario, Long idParqueadero) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new NoSuchElementFoundException("Usuario no encontrado"));

        Parqueadero parqueadero = parqueaderoRepository.findById(idParqueadero)
                .orElseThrow(() -> new NoSuchElementFoundException("Parqueadero no encontrado"));

        usuario.getParqueadero().add(parqueadero);
        parqueadero.setUsuario(usuario);
        usuarioRepository.save(usuario);
    }

}
