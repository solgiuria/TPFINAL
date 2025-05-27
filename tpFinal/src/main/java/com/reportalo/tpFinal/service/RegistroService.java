package com.reportalo.tpFinal.service;

import com.reportalo.tpFinal.DTO.RegistroDTO;
import com.reportalo.tpFinal.model.Usuario;
import com.reportalo.tpFinal.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class RegistroService implements UsuarioRepository{
    private final UsuarioRepository usuarioRepository;

    public RegistroService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void registrarUsuario(RegistroDTO dto){
        Usuario usuario = Usuario.builder()
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .email(dto.getEmail())
                .dni(dto.getDni())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .build();

        usuarioRepository.save(usuario);

    }
}
