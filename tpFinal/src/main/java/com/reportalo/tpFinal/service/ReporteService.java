package com.reportalo.tpFinal.service;

import com.reportalo.tpFinal.model.Reporte;
import com.reportalo.tpFinal.repository.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReporteService {
    private ReporteRepository reporteRepository;

    @Autowired
    public ReporteService(ReporteRepository reporteRepository) {
        this.reporteRepository = reporteRepository;
    }

    //metodo para buscar por id
    public Reporte getReporteById(Long id){
        if(id == null || id <= 0){
            throw new IllegalArgumentException("El id del reporte no puede ser nulo o negativo");
        }
        Optional<Reporte> reporteOptional = reporteRepository.findById(id);
        if(reporteOptional.isEmpty()){
            throw new IllegalArgumentException("No existe un reporte con el id: " + id);
        }
        return reporteOptional.get();
    }

    //Lista de los reporte
    public List<Reporte> getAllReportes(){
        try{
           List<Reporte>  reportes = reporteRepository.findAll();
            if(reportes.isEmpty()){
                throw new IllegalArgumentException("No hay reportes registrados");
            }
            return reportes;
        }catch(RuntimeException e){
            throw new RuntimeException("No hay reportes registrados" + e.getMessage());
        }
    }

    //Crear
    public Reporte addReporte(Reporte reporte){
        if(reporte == null){
            throw new IllegalArgumentException("El reporte no puede ser nulo");
        }
        try {
            return  reporteRepository.save(reporte);
        }catch (RuntimeException e){
            throw new RuntimeException("No se pudo agregar el reporte" + e.getMessage());
        }
    }

    //Eliminar por id
    public void deleteReporte(Long id){
        if(id == null || id <= 0){
            throw new IllegalArgumentException("El id del reporte no puede ser nulo o negativo");
        }
        try {
           if(!reporteRepository.existsById(id)){
                throw new IllegalArgumentException("No existe un reporte con el id: " + id);
            }
             reporteRepository.deleteById(id);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error al eliminar el reporte con el id: " + id);
        }
    }

}
