package com.reportalo.tpFinal.service;

import com.reportalo.tpFinal.model.Reporte;
import com.reportalo.tpFinal.model.Subtipo_Reporte;
import com.reportalo.tpFinal.model.Tipo_Reporte;
import com.reportalo.tpFinal.model.Usuario;
import com.reportalo.tpFinal.repository.ReporteRepository;
import com.reportalo.tpFinal.repository.SubtipoRepository;
import com.reportalo.tpFinal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReporteService {
    private final ReporteRepository reporteRepository;
    private final UsuarioRepository usuarioRepository;
    private final SubtipoRepository subtipoRepository;

    @Autowired
    public ReporteService(ReporteRepository reporteRepository, UsuarioRepository usuariorepository,SubtipoRepository subtipoRepository) {
        this.reporteRepository = reporteRepository;
        this.usuarioRepository = usuariorepository;
        this.subtipoRepository = subtipoRepository;
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

    public void addReporte(ReporteDTO reporteDTO){

        Usuario usuario = usuarioRepository.findById(reporteDTO.getIdusuario())
                .orElseThrow(() -> new IllegalArgumentException("No existe un usuario con ese id"));

        Subtipo_Reporte subtipoReporte = subtipoRepository.findById(reporteDTO.getIdsubtipo_Reporte())
                    .orElseThrow(() -> new RuntimeException("No existe un Subtipo con ese id"));

        Reporte reporte = Reporte.builder()
                .usuario(usuario)
                .subtipo(subtipoReporte)
                .descripcion(reporteDTO.getDescripcion())
                .fecha_hora(reporteDTO.getFecha_hora())
                .ubicacion(reporteDTO.getUbicacion())
                .estado(reporteDTO.getEstado())
                .build();

        reporteRepository.save(reporte);
    }

    //Eliminar por id
    public void deleteReporte(Long id){ //
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
    public Reporte updateReporte(long id, Reporte reporteActualizado){ //Utilizar el metodo @builder aca
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
            Usuario usuario = usuarioRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("No existe un usuario con ese id"));

            Reporte r = Reporte.builder()
                    .id(reporteActualizado.getId())
                    .subtipo(reporteActualizado.getSubtipo())
                    .descripcion(reporteActualizado.getDescripcion())
                    .usuario(reporteActualizado.getUsuario())
                    .fecha_hora(reporteActualizado.getFecha_hora())
                    .ubicacion(reporteActualizado.getUbicacion())
                    .estado(reporteActualizado.getEstado())
                    .build();
            return reporteRepository.save(r);
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Error al validar el usuario: " + e.getMessage());
        }catch (RuntimeException e){
            throw new RuntimeException("No se pudo actualizar el reporte: " + e.getMessage());
        }
    }
    //Traer el historial de reportes de un usuario
    public List<Reporte> historialReportes(Long id){
        if(id == null ||  id <= 0){
            throw new IllegalArgumentException("El id no puede ser nulo");
        }
        try{
            List<Reporte> reportesDeUsuario = new ArrayList<>();
            List<Reporte> reportes = reporteRepository.findAll();
            for(Reporte reporte : reportes){
                if(reporte.getUsuario().getId() == id){
                    reportesDeUsuario.add(reporte);
                }
            }
            if(reportesDeUsuario.isEmpty()) {
                throw new IllegalArgumentException("El usuario no tiene reportes registrados");
            }
            return reportesDeUsuario;
            }catch(RuntimeException e){
            throw new RuntimeException("No hay reportes registrados" + e.getMessage());
        }
    }

    //Traer reportes filtrado x subtipo
    public List<Reporte> historialReportesXSubtipo(Long id, Tipo_Reporte tipoReporte){
       if(id == null || id <= 0){
            throw new IllegalArgumentException("Error en el id");
       }
       try{
           List<Reporte> reportesDeUsuario = new ArrayList<>();
           List<Reporte> reportes = reporteRepository.findAll();
           for(Reporte reporte : reportes){
               if(reporte.getUsuario().getId() == id){
                   if(reporte.getSubtipo().equals(tipoReporte)){
                       reportesDeUsuario.add(reporte);
                   }
               }
           }
           return reportesDeUsuario;
       }catch(RuntimeException e){
           throw new RuntimeException("No hay reportes registrados" + e.getMessage());
       }
    }

    public List<Reporte> historialReportesXSubtipo(Long id){
        if(id == null || id<=0){
            throw new IllegalArgumentException("Error en el id");
        }
        try{
            List<Reporte> reportes = reporteRepository.getReportesFiltrado(id);
            return reportes;
         }catch(RuntimeException e){
            throw new RuntimeException("No hay reportes registrados" + e.getMessage());
        }
    }
}
