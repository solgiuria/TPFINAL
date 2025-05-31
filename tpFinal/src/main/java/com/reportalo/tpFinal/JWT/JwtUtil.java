package com.reportalo.tpFinal.JWT;



import io.jsonwebtoken.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET;
    @Value("${EXPIRATION_TIME}")
    private long EXPIRATION_TIME; // 1 día en milisegundos, tiempo que dura válido el token:

    // Genera un JWT con username, fecha de emisión y expiración
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // "dueño" del token
                .setIssuedAt(new Date()) // cuándo fue creado
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // cuándo expira
                .signWith(SignatureAlgorithm.HS256, SECRET) // firma con algoritmo y clave secreta
                .compact(); // compacta el token a String
    }

    // Extrae el username (subject) de un token válido
    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Valida si un token está bien firmado y no expiró
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
