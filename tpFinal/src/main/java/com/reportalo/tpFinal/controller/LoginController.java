package com.reportalo.tpFinal.controller;


import com.reportalo.tpFinal.DTO.LoginDTO;
import com.reportalo.tpFinal.JWT.JwtUtil;
import com.reportalo.tpFinal.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class LoginController {
    private final JwtUtil jwtUtil;
    private final LoginService loginService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        boolean valido = loginService.validarUsuario(loginDTO.getUsername(), loginDTO.getPassword());
        if(!valido) {
            return ResponseEntity.status(401).body("Usuario o contraseña inválidos");
        }
        String token = jwtUtil.generateToken(loginDTO.getUsername());
        return ResponseEntity.ok(token);
    }

}
