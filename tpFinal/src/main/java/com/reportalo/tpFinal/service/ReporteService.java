package com.reportalo.tpFinal.service;

import com.reportalo.tpFinal.exceptions.ReporteNoEncontradoException;
import com.reportalo.tpFinal.exceptions.UsuarioNoEncontradoException;
import com.reportalo.tpFinal.model.Reporte;
import com.reportalo.tpFinal.model.Usuario;
import com.reportalo.tpFinal.repository.ReporteRepository;
import com.reportalo.tpFinal.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReporteService {

    private final ReporteRepository reporteRepository;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;


    //validamos q el id de usuario corresponda al id del usuario que se logeo y no a otro
    public boolean validarIdUsuario(Long idPath, Long idUsuarioAutenticado){
        return idPath.equals(idUsuarioAutenticado);
    }



    //validamos que el usuario exista y retornamos sus reportes
    public List<Reporte> getReportesPorIdUsuario(Long id){
       if(!usuarioRepository.existsById(id)){
           throw new UsuarioNoEncontradoException("no existe ese usuario");
       }
        return reporteRepository.findByUsuarioId(id);
    }



    //validamos que el reporte pertenezca al usuario autenticado
    public boolean validarReportePerteneceUsuario(Long idPath, Long idUsuarioAutenticado){
        Optional<Reporte> optReporte = reporteRepository.findById(idPath);
        return optReporte
                .map(reporte -> reporte.getUsuario().getId().equals(idUsuarioAutenticado))
                .orElse(false);
    }


    //validamos que el reporte exista ylo retornamos
    public Reporte getReporteById(long id){
       return reporteRepository.findById(id)
               .orElseThrow(()-> new ReporteNoEncontradoException("no existe ese reporte"));
    }



    //Lista de los reporte QUE NO HAYA REGISTRADOS NO ES UN ERROR POR ENDE NO TIRA EXCEP
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

    //Actualizar reporte  SE SUPONE QUE ACTUALIZAS EL ESTADO NO T0DO (ES MAS FACIL HACER SI EL REPORTE EXISTE ACTUALIZO Y SINO TIRO EXCEP REPORTE NO ENCONTRADO
    public Reporte updateReporte(long id, String estadoNuevo){
        Reporte reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new ReporteNoEncontradoException("no existe ese reporte"));



    }

}
