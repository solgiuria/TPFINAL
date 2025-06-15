package com.reportalo.tpFinal.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;
import java.util.ArrayList;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)  // min no se valida en BD, length sí
    private String nombre;

    @Column(nullable = false, length = 20)
    private String apellido;

    @Column(nullable = false)
    private Integer edad;

    @Column(nullable = false, unique = true, length = 255)  // email único y no nulo
    private String email;

    @Column(nullable = false, unique = true)  // dni único y no nulo
    private Integer dni;

    @Column(nullable = false, unique = true, length = 20)  // username único, no nulo
    private String username;

    @Column(nullable = false, length = 20)
    private String password;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<Reporte> reportes = new ArrayList<>();
}
