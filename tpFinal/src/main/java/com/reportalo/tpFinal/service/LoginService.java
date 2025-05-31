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
        return usuarioRepository.findByUsername(username)
                .map(user -> passwordEncoder.matches(password, user.getPassword())) // Comparación simple, solo para pruebas
                .orElse(false);
    }
}
