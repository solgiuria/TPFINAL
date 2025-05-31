package com.reportalo.tpFinal.service;

import com.reportalo.tpFinal.DTO.RegistroDTO;
import com.reportalo.tpFinal.exceptions.UsuarioRepetidoException;
import com.reportalo.tpFinal.model.Usuario;
import com.reportalo.tpFinal.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor //crea un constru diciendo q los atribs q son final si o si son requeridos para crear un objeto RegistroService, es una forma de inyeccion de dependencias. Si hicieramos un constru y se los pasamos seria otra
public class RegistroService{

    private final UsuarioRepository usuarioRepository;
    private final JdbcUserDetailsManager userDetailsManager;
    private final PasswordEncoder passwordEncoder;


    public void registrarUsuario(RegistroDTO dto) throws UsuarioRepetidoException {              //fijarme si quiero un try catch p atraparla o hacer un controllador de exceptions (la primera)

        if (userDetailsManager.userExists(dto.getUsername())) {                                   //si este usuario ya existe
            throw new UsuarioRepetidoException("este nombre de usuario ya esta siendo utilizado");
        }
            UserDetails user = User.builder()
                    .username(dto.getUsername())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .roles("USER")
                    .build();
            userDetailsManager.createUser(user);

            Usuario usuario = Usuario.builder()
                    .nombre(dto.getNombre())
                    .apellido(dto.getApellido())
                    .email(dto.getEmail())
                    .dni(dto.getDni())
                    .username(dto.getUsername())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .build();

            usuarioRepository.save(usuario);
    }

}
