package com.reportalo.tpFinal.repository;

import com.reportalo.tpFinal.enums.EstadoReporte;
import com.reportalo.tpFinal.model.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    List<Reporte> findByUsuarioId(Long usuarioId);
    List<Reporte> findByEstado(EstadoReporte estado);
}
