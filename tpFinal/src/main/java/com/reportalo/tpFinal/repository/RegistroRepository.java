package com.reportalo.tpFinal.repository;

import com.reportalo.tpFinal.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistroRepository extends JpaRepository<Usuario,Long> {

}
