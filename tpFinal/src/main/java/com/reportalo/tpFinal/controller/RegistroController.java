package com.reportalo.tpFinal.controller;

import com.reportalo.tpFinal.service.RegistroService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/registro")
public class RegistroController {
    private RegistroService service;

    public RegistroController(RegistroService rs){ //esto es una inyeccion de dependencias. Gracias a que reg controller y reg service son @component (uno @controller y otro @service) spring hace el new objeto() x nosotros sin necesidad ni de q lo veamos y como solo vamos a tener 1 constru ni hace falta el @autowired q permite inyectar la depen si nada mas (si hubiese mas de un constru ahi si)
        this.service = rs;
    }

    @PostMapping("/registrar")
    public void registrar(){
        //holamundo
    }
}
