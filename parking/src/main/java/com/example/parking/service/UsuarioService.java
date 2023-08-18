package com.example.parking.service;

import com.example.parking.dto.UsuarioDto;
import com.example.parking.entity.Parqueadero;
import com.example.parking.entity.Usuario;
import com.example.parking.enumeraciones.Rol;
import com.example.parking.exception.NoSuchElementFoundException;
import com.example.parking.mapper.UsuarioMapper;
import com.example.parking.repository.ParqueaderoRepository;
import com.example.parking.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final ParqueaderoRepository parqueaderoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, ParqueaderoRepository parqueaderoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.parqueaderoRepository = parqueaderoRepository;
    }

    // Método para registrar un nuevo usuario
    public Usuario registrarUsuario(UsuarioDto usuarioDto ) throws NoSuchElementFoundException {

        Usuario usuario = new Usuario();
        // usuario = usuarioRepository.findByEmail(usuario.getEmail()).orElseThrow();
        Optional<Usuario> existingUser = usuarioRepository.findByEmail(usuario.getEmail());
        if (existingUser.isPresent()) {
            throw new NoSuchElementFoundException("Email ya existe");
        }
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setPassword(passwordEncoder.encode(usuarioDto.getPassword()));
        usuario.setRol(Rol.SOCIO);
        usuarioRepository.save(usuario);
        usuarioDto.setId(usuario.getId());
        return usuario;
    }

    //Método para ver todos los usuarios registrados

    public List<UsuarioDto> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        List<UsuarioDto> usuarioDtos = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            usuarioDtos.add(usuarioMapper.entityToDTO(usuario));
        }
        return usuarioDtos;
    }


    public UsuarioDto obtenerUsuarioPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementFoundException("Usuario no encontrado"));

        return usuarioMapper.entityToDTO(usuario);
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
        if(parqueadero.getUsuario()!=null){
            throw new NoSuchElementFoundException("Parqueadero ya asignado a usuario");
        }
        usuario.getParqueadero().add(parqueadero);
        parqueadero.setUsuario(usuario);

        System.out.println("IDs de parqueaderos del usuario después de agregar:");
        for (Parqueadero p : usuario.getParqueadero()) {
            System.out.println("ID de parqueadero: " + p.getId());
        }

        usuarioRepository.save(usuario);
    }
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementFoundException("Usuario no encontrado"));
    }

    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Parqueadero> obtenerParqueaderosDeUsuario(Usuario usuario) {
        return usuario.getParqueadero();
    }
}
