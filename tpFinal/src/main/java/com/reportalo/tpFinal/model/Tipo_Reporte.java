package com.reportalo.tpFinal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Tipos_Reportes")
public class Tipo_Reporte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    @Size(min = 2, max = 20, message = "El nombre debe tener entre 3 y 20 caracteres")
    private String nombre;


    @OneToMany(mappedBy = "tipo_reporte", cascade = CascadeType.ALL) //un tipo puede estar asociado a muchos subtipos
    private Long id;

}
