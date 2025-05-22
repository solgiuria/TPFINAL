package com.reportalo.tpFinal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data //incluye @getter @setter @toString @HashCode @equals
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Subtipo_Reporte")

public class Subtipo_Reporte {

    @OneToMany(mappedBy = "subtipo", cascade = CascadeType.ALL) //cualq modif a esta tabla afecta a reporte
    private Long id;

    @ManyToOne //subtipo puede tener asociado solo un tipo
    @JoinColumn(name = "id_tipoReporte")
    private Tipo_Reporte tipo_reporte;


}
