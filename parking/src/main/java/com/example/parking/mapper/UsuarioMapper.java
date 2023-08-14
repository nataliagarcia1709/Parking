package com.example.parking.mapper;

import com.example.parking.dto.UsuarioDto;
import com.example.parking.entity.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioDto entityToDTO(Usuario usuario) {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setEmail(usuario.getEmail());
        usuarioDto.setPassword(usuario.getPassword());
        return usuarioDto;
    }

    public Usuario dtoToEntity(UsuarioDto usuarioDto) {
        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setPassword(usuarioDto.getPassword());
        return usuario;
    }
}
