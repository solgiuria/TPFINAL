package com.reportalo.tpFinal.JWT;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;


@Service
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET;
    @Value("${EXPIRATION_TIME}")
    private long EXPIRATION_TIME; // 1 día en milisegundos, tiempo que dura válido el token:

    // Genera un JWT con username, fecha de emisión y expiración
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // "dueño" del token
                .claim("roles", userDetails.getAuthorities())
                .setIssuedAt(new Date(System.currentTimeMillis())) // cuándo fue creado
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // cuándo expira
                .signWith(getKey(),SignatureAlgorithm.HS256) // firma con algoritmo y clave secreta
                .compact(); // compacta el token a String
    }


    //  Verifica si un token es válido para un usuario dado
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token); // extrae el usuario desde el token
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // Extrae el nombre de usuario desde el token (campo "sub")
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Verifica si el token está vencido (exp < fecha actual)
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    // Extrae todos los datos (claims) del token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey()) // Le dice con qué clave validar la firma
                .build()
                .parseClaimsJws(token) // Parsea el JWT completo y lo valida
                .getBody(); // Devuelve el contenido (payload)
    }

    //Convierte la clave secreta en un objeto Key para usar con la librería jjwt
    private Key getKey() {
        byte[] keyBytes = SECRET.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes); // Usa HMAC con SHA-256
    }


}
