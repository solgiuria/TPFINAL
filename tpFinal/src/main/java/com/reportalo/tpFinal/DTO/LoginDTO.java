package com.reportalo.tpFinal.DTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LoginDTO {
    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(min = 3, max = 20, message = "El nombre de usuario debe tener entre 3 y 20 caracteres")
    private String username;


    @NotBlank(message = "La contrasena es obligatorio")
    @Size(min = 3, max = 20, message = "La contrasena debe tener entre 3 y 20 caracteres")
    private String password;
}
