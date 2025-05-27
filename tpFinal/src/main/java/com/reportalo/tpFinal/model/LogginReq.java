package com.reportalo.tpFinal.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

//

public class LogginReq {
    @NotBlank
    @Size(min = 3, max =20, message = "el username debe tener entre 3 y 20 caracteres")
    private String username;

    @NotBlank
    @Size(min = 3, max =20, message = "el username debe tener entre 3 y 20 caracteres")
    private String password;
}
