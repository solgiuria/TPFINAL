package com.reportalo.tpFinal.repository;
import java.util.Optional;
import com.reportalo.tpFinal.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
   //averiguar COMO entiende QUE DEBE hacer
    Optional<Usuario> findByUsername(String username);
}
