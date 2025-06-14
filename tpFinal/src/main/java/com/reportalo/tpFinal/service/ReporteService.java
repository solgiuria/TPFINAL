package com.reportalo.tpFinal.service;

import com.reportalo.tpFinal.enums.EstadoReporte;
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




    //validamos q el id de usuario corresponda al id del usuario que se logeo y no a

    public boolean validarIdUsuario(Long idPath,String username){

        System.out.println("[REPORTE SERVICE: VALIDAR ID USUARIO] soy el id de usuario que ingresaste en la url: " + idPath + "soy el username del usuario que se autentico: " + username);

        Optional<Usuario> optionalUsuario =usuarioRepository.findByUsername(username);
        System.out.println("encontro el usuario x el username del token autenticado? " + optionalUsuario.isPresent());

        return optionalUsuario
                .map(usuario -> usuario.getId().equals(idPath))
                .orElse(false);
    }



    //validamos que el reporte pertenezca al usuario autenticado

    public boolean validarReportePerteneceUsuario(Long idPath, String username) {
        System.out.println("soy el id de reporte que ingresaste en la url: " + idPath + "soy el username del usuario que se autentico: " + username);
        return reporteRepository.findById(idPath)
                .map(reporte -> reporte.getUsuario().getUsername().equals(username))//en la tabla no tengo EL USUARIO como tal sino q tengo el id, pero en java si miro la relacion en las tablas SI lo tengo
                .orElse(false);
    }



    //validamos que el usuario exista y retornamos sus reportes
    public List<Reporte> getReportesPorIdUsuario(Long id){
        if(!usuarioRepository.existsById(id)){
            throw new UsuarioNoEncontradoException("no existe ese usuario");
        }
        return reporteRepository.findByUsuarioId(id);
    }


    //validamos que el reporte exista ylo retornamos
    public Reporte getReporteById(long id){
       return reporteRepository.findById(id)
               .orElseThrow(()-> new ReporteNoEncontradoException("no existe ese reporte"));
    }

    public List<Reporte> getReportesPorEstado(EstadoReporte estado){
        return reporteRepository.findByEstado(estado);
    }


    //Lista de los reporte QUE NO HAYA REGISTRADOS NO ES UN ERROR POR ENDE NO TIRA EXCEP
    public List<Reporte> getAllReportes(){
           return reporteRepository.findAll();
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
    public void deleteReporte(Long id){ //no evaluo q sea null porq eso ya pasa con el @PathVariable
        if(!reporteRepository.existsById(id)){
            throw new ReporteNoEncontradoException("no existe ese reporte");
        }
        reporteRepository.deleteById(id);

    }

    //Actualizar reporte  SE SUPONE QUE ACTUALIZAS EL ESTADO NO T0DO (ES MAS FACIL HACER SI EL REPORTE EXISTE ACTUALIZO Y SINO TIRO EXCEP REPORTE NO ENCONTRADO
    public Reporte updateReporte(long id, EstadoReporte estadoNuevo){
        Reporte reporte = reporteRepository.findById(id)
                .orElseThrow(() -> new ReporteNoEncontradoException("no existe ese reporte"));
        reporte.setEstado(estadoNuevo);
        return reporte;
    }

}
