package com.reportalo.tpFinal.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistroDTO {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 20, message = "El nombre debe tener entre 3 y 20 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es obligatoria")
    @Size(min = 2, max = 20, message = "El apellido debe tener entre 3 y 20 caracteres")
    private String apellido;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser valido")
    private String email;

    @NotBlank(message = "El dni es obligatorio")
    @Pattern(regexp = "\\d{7,8}", message = "El DNI debe tener entre 7 y 8 dígitos")
    private int dni;

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 3, max = 20, message = "El nombre de usuario debe tener entre 3 y 20 caracteres")
    private String username;


    @NotBlank(message = "La contrasena es obligatorio")
    @Size(min = 3, max = 20, message = "La contrasena debe tener entre 3 y 20 caracteres")
    private String password;
}
