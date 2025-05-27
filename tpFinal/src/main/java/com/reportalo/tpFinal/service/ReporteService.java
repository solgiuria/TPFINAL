package com.reportalo.tpFinal.service;

import com.reportalo.tpFinal.model.Reporte;
import com.reportalo.tpFinal.model.Usuario;
import com.reportalo.tpFinal.repository.ReporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ReporteService {
    private final ReporteRepository reporteRepository;
    private final UsuarioService usuarioService;

    @Autowired
    public ReporteService(ReporteRepository reporteRepository, UsuarioService usuarioService) {
        this.reporteRepository = reporteRepository;
        this.usuarioService = usuarioService;
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
        if(reporte.getUsuario() == null){
            throw new IllegalArgumentException("El reporte debe tener un usuario asociado");
        }
        
        try {
            // Validar que el usuario existe
            Usuario usuario = usuarioService.getUserById(reporte.getUsuario().getId());
            reporte.setUsuario(usuario); // Aseguramos que tenemos el usuario completo
            return reporteRepository.save(reporte);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Error al validar el usuario: " + e.getMessage());
        }catch (RuntimeException e){
            throw new RuntimeException("No se pudo agregar el reporte: " + e.getMessage());
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

    //Actualizar reporte
    public Reporte updateReporte(long id, Reporte reporteActualizado){
        if(reporteActualizado == null){
            throw new IllegalArgumentException("El reporte no puede ser nulo");
        }
        if(reporteActualizado.getUsuario() == null){
            throw new IllegalArgumentException("El reporte debe tener un usuario asociado");
        }

        try {
            Optional<Reporte> reporteOptional = reporteRepository.findById(id);
            if(reporteOptional.isEmpty()){
                throw new IllegalArgumentException("No existe un reporte con el id: " + id);
            }

            // Validar que el usuario existe
            Usuario usuario = usuarioService.getUserById(reporteActualizado.getUsuario().getId());
            
            Reporte r = reporteOptional.get();
            r.setSubtipo(reporteActualizado.getSubtipo());
            r.setDescripcion(reporteActualizado.getDescripcion());
            r.setEstado(reporteActualizado.getEstado());
            r.setFecha_hora(reporteActualizado.getFecha_hora());
            r.setUsuario(usuario); // Usamos el usuario validado
            r.setUbicacion(reporteActualizado.getUbicacion());

            return reporteRepository.save(r);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Error al validar el usuario: " + e.getMessage());
        }catch (RuntimeException e){
            throw new RuntimeException("No se pudo actualizar el reporte: " + e.getMessage());
        }
    }

}
