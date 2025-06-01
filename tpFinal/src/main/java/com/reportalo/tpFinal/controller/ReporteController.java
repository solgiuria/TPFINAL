package com.reportalo.tpFinal.controller;

import com.reportalo.tpFinal.exceptions.ReporteNoEncontradoException;
import com.reportalo.tpFinal.exceptions.UsuarioNoEncontradoException;
import com.reportalo.tpFinal.model.Reporte;
import com.reportalo.tpFinal.service.ReporteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/reportes")
@CrossOrigin(origins = "*")
public class ReporteController {

    private final ReporteService reporteService;


    //PARA EL CIVIL UNICAMENTE
    @PreAuthorize("hasRole('USUARIO')")
    @PostMapping
    public ResponseEntity<String> reportar(@Valid @RequestBody Reporte reporte, BindingResult resultDTOvalidation) {
        if(resultDTOvalidation.hasErrors()){
            String err = resultDTOvalidation.getFieldError().getDefaultMessage();                                    //agarra el 1er error y el msj q nosotros definimos en el dto, ej: "el username no puede tener - de 3 caracts
            return ResponseEntity.badRequest().body(err);
        }
        try{
            reporteService.addReporte(reporte);
            return ResponseEntity.status(HttpStatus.CREATED).body("Reporte registrado exitosamente");
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error interno de sistema");
        }
    }


    //PARA EL ADMIN UNICAMENTE, VE TODOS LOS REPORTES
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Reporte>> obtenerTodosLosReportes() {
        try {
            List<Reporte> reportes = reporteService.getAllReportes();
            return ResponseEntity.ok(reportes);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    //PARA QUE EL CIVIL VEA SUS REPORTES Y PARA QUE EL ADMIN VEA TODOS LOS REPORTES PERO FILTRADO X USUARIO   @ReporteService.perteneceAlUsuario...  @ invocamos un metodo de un bean (la clase ReporteServicio "se vuelve" un bean cuando le ponemos el @Service, Spring interpreta q se debe hacer cargo de la creacion e inyeccion de ese objeto. El @ReporteService.metodo solo lo podemos hacer aca adentro

    @PreAuthorize("@reporteService.validarIdUsuario(#id_usuario, authentication.principal.id) or hasRole('ADMIN')")   //si no pones esto cualquier usuario loggeado (no admin) podria ver los reportes de cualquier otro usuario poniendo el id, y esto es incorrecto, tiene q poder ver solo SUS reportes, entonces verificamos q el id q pone sea el mismo q el id del usuario q esta intentando hacer una peticion (es decir, el usuario loggeado) o que sea un admin, q ahi si puede ver los reportes de cualquier usuario de manera filtrada
    @GetMapping("/usuario/{id_usuario}")
    public ResponseEntity<Reporte> obtenerReportesPorUsuario(@PathVariable Long id) {
        try {
            Reporte reporte = reporteService.getReportesById_Usuario(id);
            return ResponseEntity.ok(reporte);                                                                  //si no encuentra reportes para ese usuario no lo trato como error, devuelvo un ok (200) con lista vacia
        }
        catch(UsuarioNoEncontradoException e){
            return ResponseEntity.notFound().build();
        }
        catch(AccessDeniedException e){                                                                       //si se ingresa un id que no es el del usuario autenticado/activo/el q se logeo
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        catch(Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    //PARA QUE CIVIL/ADMIN FILTREN X ID DE REPORTE (VERIFICAR QUE EL CIVIL INTENTA VER 1 REPORTE QUE ES SUYO)

    @PreAuthorize("@reporteService.validarReportePerteneceUsuario(#idReporte, authentication.principal.id) or hasRole('ADMIN')")
    @GetMapping("/{id_reporte}")
    public ResponseEntity<Reporte> obtenerUnSoloReporte(@PathVariable Long id) {
        try {
            Reporte reporte = reporteService.getReporteById(id);
            return ResponseEntity.ok(reporte);
        }
        catch (ReporteNoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
        catch(AccessDeniedException e){
           return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    //SOLO PARA EL ADMIN

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{estado_reporte}")
    public  ResponseEntity<List<Reporte>> obtenerReportePorEstado(@PathVariable String estado){
        try{
            List<Reporte> reportes = reporteService.getReportesPorEstado(estado);
            return ResponseEntity.ok(reportes);
        }catch(ReporteNoEncontradoException e){
            return ResponseEntity.notFound().build();
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    //SOLO PARA EL ADMIN

    @PutMapping("/{id}/{estado_nuevo)")
    public ResponseEntity<Reporte> actualizarEstadoReporte(@PathVariable Long id, @PathVariable String estadoNuevo) {
        try {
            Reporte reporteActualizado = reporteService.updateReporte(id, estadoNuevo);
            return ResponseEntity.ok(reporteActualizado);
        }
        catch(ReporteNoEncontradoException e){
            return ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    //SOLO PARA EL ADMIN (AUNQUE UNA VEZ Q SE HACE EL ARREGLO EL ESTADO DEBE PASAR A FINALIZADO, EVENTUALMENTE IMAGINO QUE SE TIENEN QUE PODER BORRAR REPORTES PARA LIBERAR LUGAR)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReporte(@PathVariable Long id) {
        try {
            reporteService.deleteReporte(id);
            return ResponseEntity.ok().build();
        }
        catch(ReporteNoEncontradoException e){
            return ResponseEntity.notFound().build();
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    //FALTA METODO OBTENER REPORTES POR ESTADO  (listo en controller no en service)
    //FALTA METODO ACTUALIZAR ESTADO REPORTES  (listo en controller no en service)
    //FALTA METODO VER REPORTES POR TIPO DE REPORTE
    //FALTA METODO VER REPORTES POR SUBTIPO DE REPORTE (IDEALMENTE)
    //FALTA METODO PARA VALIDAR QUE EL ID_USUARIO INGRESADO CORRESPONDA EL ID_USUARIO DEL USUARIO QUE SE AUTENTICO (OSEA DEL QUE ESTA USANDO EL PROGRAMA) (en service)
    //FALTA METODO PARA VALIDAR QUE EL ID_REPORTE PERTENEZCA AL USUARIO QUE HIZO LA PETICION (ES DECIR: QUE EL ID_USUARIO ASOCIADO A ESE REPORTE SEA == AL ID_USUARIO DEL USUARIO AUTENTICADO)

}