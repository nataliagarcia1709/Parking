package com.example.parking.service;

import com.example.parking.entity.Parqueadero;
import com.example.parking.entity.Usuario;
import com.example.parking.enumeraciones.Rol;
import com.example.parking.exception.NoSuchElementFoundException;
import com.example.parking.repository.ParqueaderoRepository;
import com.example.parking.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ParqueaderoRepository parqueaderoRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, ParqueaderoRepository parqueaderoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.parqueaderoRepository = parqueaderoRepository;
    }

    // Método para registrar un nuevo usuario
    public Usuario registrarUsuario(String email, String password ) throws NoSuchElementFoundException {

        Usuario usuario = new Usuario();
        Optional<Usuario> existingUser = usuarioRepository.findByEmail(usuario.getEmail());
        if (existingUser.isPresent()) {
            throw new NoSuchElementFoundException("Email ya existe");
        }
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setRol(Rol.SOCIO);
        if(false) {
            throw new NoSuchElementFoundException("No se pudo guardar");
        }

        return usuarioRepository.save(usuario);
    }
    public List<Usuario> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        for(Usuario usuario : usuarios){
            usuario.getParqueadero();
        }
        return usuarioRepository.findAll();
    }

    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException("Usuario no encontrado con ID: " + id));
    }

    public void eliminarUsuario(Long id){
        usuarioRepository.deleteById(id);
    }
   /* public void asignarUsuarioAParqueadero(Long userId, Long parqueaderoId) {
        Usuario usuario = usuarioRepository.findById(userId).orElseThrow(() -> new NoSuchElementFoundException("Usuario no encontrado"));
        Parqueadero parqueadero = parqueaderoRepository.findById(parqueaderoId).orElseThrow(() -> new NoSuchElementFoundException("Parqueadero no encontrado"));

        usuario.setParqueadero(parqueadero);
        usuarioRepository.save(usuario);
    }*/

}
