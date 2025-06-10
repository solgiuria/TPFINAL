package com.reportalo.tpFinal.JWTAuthFilter;

import com.reportalo.tpFinal.JWT.JwtUtil;
import com.reportalo.tpFinal.model.Usuario;
import com.reportalo.tpFinal.repository.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {


        // Obtenemos el header "Authorization" del request
        final String authHeader = request.getHeader("Authorization");


        // Verificamos si el header es nulo o no empieza con "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;                                                      //no hay token, dejamos q el resto de la cadena siga
        }

        //extraemos el token sin el prefijo Bearer
        String token = authHeader.substring(7);

        //obtenemos el nombre del usuario desde el token
        String username = jwtUtil.extractUsername(token);

        //si obtenemos un username y no hay aunteticacion en contexto, validamos el token

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            //cargamos los datos del usuario desde db
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            //verificamos si el token es valido
            if (jwtUtil.isTokenValid(token,userDetails)){
                // Creamos el token de autenticación de Spring
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // Cargamos información adicional del request
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Establecemos el usuario autenticado en el contexto de Spring
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        //continuamos la cadena de filtros
        filterChain.doFilter(request, response);
    }
}