package com.reportalo.tpFinal.service;

import com.reportalo.tpFinal.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean validarUsuario(String username, String password) {
        return usuarioRepository.findByUsername(username)                                   //devuelve un optional de tipo user
                .map(user -> passwordEncoder.matches(password, user.getPassword())) // SI EL OPTIONAL !isEmpty() este .map() DE OPTIONAL evalua si las contrasenias matchean y devuelve true or false (asi, boolean plano)
                .orElse(false);                                                       //SI el optional isEmpty() then return false
    }                                                                                       //no hay diferencia entre si el usuario no es valido x el username o el password, but its okey anyways
}
