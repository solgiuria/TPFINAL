package com.reportalo.tpFinal.repository;

import com.reportalo.tpFinal.model.Tipo_Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoRepository extends JpaRepository<Tipo_Reporte, Long> {
}
