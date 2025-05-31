package com.reportalo.tpFinal.controller;
import com.reportalo.tpFinal.DTO.RegistroDTO;
import com.reportalo.tpFinal.service.RegistroService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class RegistroController {
    private RegistroService service;

    public RegistroController(RegistroService rs){ //esto es una inyeccion de dependencias. Gracias a que reg controller y reg service son @component (uno @controller y otro @service) spring hace el new objeto() x nosotros sin necesidad ni de q lo veamos y como solo vamos a tener 1 constru ni hace falta el @autowired q permite inyectar la depen si nada mas (si hubiese mas de un constru ahi si)
        this.service = rs;
    }

    @PostMapping("/registrar")
    public ResponseEntity<String> registrar(@Valid @RequestBody RegistroDTO dto, BindingResult resultDTOvalidations){ //ResponseEntity<String>, en BindingResult se guardar los rdos del @Valid de las validaciones del DTO (@Size, @Email, etc etc)
        if(resultDTOvalidations.hasErrors()){                                                                         //si hay un error de validacion en el json q llego x postman
            String err = resultDTOvalidations.getFieldError().getDefaultMessage();                                    //agarra el 1er error y el msj q nosotros definimos en el dto, ej: "el username no puede tener - de 3 caracts
            return ResponseEntity.badRequest().body(err);                                                             //esto genera un codigo 400 y de cuerpo le damos nuestro msj
        }
        service.registrarUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Usuario agregado exitosamente");                       //enviamos un response http con codigo 201 (CREATED es una constante de Spring q representa al codigo 201 "CREACION exitosa" y un body de esa response que es el mensaje "Usuario agregado exitosamente"
    }

    /*EL ERROR DE VALIDACION PODRIA VERSE ALGO ASI:
    * HTTP/1.1 400 Bad Request
    Content-Type: text/plain
    El nombre es obligatorio*/
}
