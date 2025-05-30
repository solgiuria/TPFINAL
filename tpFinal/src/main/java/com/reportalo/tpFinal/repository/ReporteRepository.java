package com.reportalo.tpFinal.repository;

import com.reportalo.tpFinal.model.Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    @Query("SELECT r FROM Reporte r WHERE r.subtipo.id = :id_sub_tipo_reporte")
    List<Reporte> getReportesFiltrado(@Param("id_sub_tipo_reporte") Long idSubTipoReporte);
}
