package com.reportalo.tpFinal.repository;

import com.reportalo.tpFinal.model.Subtipo_Reporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubtipoRepository extends JpaRepository<Subtipo_Reporte, Long> {
    //Esta extension agrega lo basico de la comunicacion con la base de datos(Buscar por id, eliminar por id, ETC)
    /**findById()
      findAll()
      save()
      deleteById()
      count()
      existsById() */
}
