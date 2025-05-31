package com.reportalo.tpFinal.repository;

import com.reportalo.tpFinal.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
   //averiguar COMO entiende QUE DEBE hacer
    Optional<Usuario> findByUsername(String username);
}
