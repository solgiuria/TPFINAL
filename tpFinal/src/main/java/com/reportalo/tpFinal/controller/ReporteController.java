package com.reportalo.tpFinal.controller;

import com.reportalo.tpFinal.model.Reporte;
import com.reportalo.tpFinal.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reportes")
@CrossOrigin(origins = "*")
public class ReporteController {

    private final ReporteService reporteService;

    @Autowired
    public ReporteController(ReporteService reporteService) {
        this.reporteService = reporteService;
    }

    @PostMapping
    public ResponseEntity<String> crearReporte(@RequestBody ReporteDTO reporte) {
        try {
            reporteService.addReporte(reporte);
            return ResponseEntity.status(HttpStatus.CREATED).body("Reporte creado exitosamente!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Reporte>> obtenerTodosLosReportes() {
        try {
            List<Reporte> reportes = reporteService.getAllReportes();
            return ResponseEntity.ok(reportes);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/obtener/{id}")
    public ResponseEntity<Reporte> obtenerReportePorId(@PathVariable Long id) {
        try {
            Reporte reporte = reporteService.getReporteById(id);
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<String> actualizarReporte(@PathVariable Long id, @RequestBody Reporte reporte) {
        try {
            Reporte reporteActualizado = reporteService.updateReporte(id, reporte);
            return ResponseEntity.ok("Reporte actualizado exitosamente!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarReporte(@PathVariable Long id) {
        try {
            reporteService.deleteReporte(id);
            return ResponseEntity.ok("Reporte eliminado exitosamente!");
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}