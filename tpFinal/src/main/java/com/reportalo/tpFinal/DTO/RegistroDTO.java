package com.reportalo.tpFinal.DTO;

import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.Check;

@Data
@AllArgsConstructor
@Builder
public class RegistroDTO {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 20, message = "El nombre debe tener entre 2 y 20 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(min = 2, max = 20, message = "El apellido debe tener entre 2 y 20 caracteres")
    private String apellido;

    @NotNull(message = "La edad es obligatoria")
    @Min(value = 18, message = "La edad mínima es 18 años")
    private Integer edad;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser valido")
    private String email;

    @NotNull(message = "El dni es obligatorio")
    @Min(1000000)
    @Max(99999999)
    private Integer dni;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 3, max = 20, message = "El nombre de usuario debe tener entre 3 y 20 caracteres")
    private String username;


    @NotBlank(message = "La contrasena es obligatoria")
    @Size(min = 3, max = 20, message = "La contrasena debe tener entre 3 y 20 caracteres")
    private String password;
}
