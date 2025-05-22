package com.reportalo.tpFinal.model;
import com.reportalo.tpFinal.enums.EstadoReporte;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data //incluye @getter @setter @toString @HashCode @equals
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Reportes")
public class Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id ;

    @ManyToOne
    @JoinColumn(name = "id_subTipoReporte") //el nombre de la columna en mysql
    private Subtipo_Reporte subtipo; //el nombre del atributo en java al cual se asocia la fk

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;


    private String descripcion;
    private LocalDateTime fecha_hora;
    private String ubicacion;
    private EstadoReporte estado;
}
