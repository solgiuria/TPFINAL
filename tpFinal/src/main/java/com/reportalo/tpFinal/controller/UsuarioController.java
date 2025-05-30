package com.reportalo.tpFinal.controller;

import com.reportalo.tpFinal.model.Reporte;
import com.reportalo.tpFinal.model.Usuario;
import com.reportalo.tpFinal.service.ReporteService;
import com.reportalo.tpFinal.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ReporteService reporteService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService , ReporteService reporteService) {
        this.usuarioService = usuarioService;
        this.reporteService = reporteService;
    }

    @PostMapping
    public ResponseEntity<String> crearUsuario(@RequestBody UsuarioDTO usuario) {
        try {
            Usuario nuevoUsuario = usuarioService.addUser(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario creado exitosamente! Id: " + nuevoUsuario.getId());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodosLosUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.getAllUsers();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.getUserById(id);
            return ResponseEntity.ok(usuario);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        try {
            Usuario usuarioActualizado = usuarioService.updateUser(id, usuario);
            return ResponseEntity.ok("Usuario actualizado exitosamente!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long id) {
        try {
            usuarioService.deleteUser(id);
            return ResponseEntity.ok("Usuario eliminado exitosamente!");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/obtenerHistorial/{id}")
    public ResponseEntity<List<Reporte>> obtenerHistorialPorId(@PathVariable Long id) {
        try{
            List <Reporte> reportes = reporteService.historialReportes(id);
            return ResponseEntity.ok(reportes);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/obtenerFiltradoSubtipo/{id}")
    public ResponseEntity<List<Reporte>> obtenerFiltradoSubtipoPorId(@PathVariable Long id) {
        try{
            List <Reporte> reportes = reporteService.historialReportesXSubtipo(id);
            return ResponseEntity.ok(reportes);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }

    }
}