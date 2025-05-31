package com.reportalo.tpFinal.enums;

import lombok.Getter;

@Getter
public enum SubTipoReporte {

    //INFRAESTRUCTURA
    ALUMBRADO_PUBLICO(TipoReporte.INFRAESTRUCTURA),
    CLOACA(TipoReporte.INFRAESTRUCTURA),
    DESAGUE(TipoReporte.INFRAESTRUCTURA),
    ASFALTO(TipoReporte.INFRAESTRUCTURA),
    VEREDA(TipoReporte.INFRAESTRUCTURA),
    DERRUMBE(TipoReporte.INFRAESTRUCTURA),

    //TRANSITO_Y_SENALIZACION
    SEMAFORO(TipoReporte.TRANSITO_Y_SENALIZACION),
    CARTEL(TipoReporte.TRANSITO_Y_SENALIZACION),
    PINTURA_VIAL(TipoReporte.TRANSITO_Y_SENALIZACION),

    //ACCESIBILIDAD
    RAMPAS(TipoReporte.ACCESIBILIDAD),
    SENALIZACION_TACTIL_AUDITIVA(TipoReporte.ACCESIBILIDAD),
    ESTACIONAMIENTO(TipoReporte.ACCESIBILIDAD),
    ELEVADORES(TipoReporte.ACCESIBILIDAD),

    //SEGURIDAD
    CAMARAS_SEGURIDAD(TipoReporte.SEGURIDAD),
    ZONAS_OSCURAS(TipoReporte.SEGURIDAD),
    ACTIVIDAD_ILICITA_O_SOSPECHOSA(TipoReporte.SEGURIDAD),

    //MEDIO_AMBIENTE
    BASURALES(TipoReporte.MEDIO_AMBIENTE),
    CONTAMINACION(TipoReporte.MEDIO_AMBIENTE),
    QUEMA_O_PODA_ILEGAL(TipoReporte.MEDIO_AMBIENTE),
    CONTENEDORES_CESTOS(TipoReporte.MEDIO_AMBIENTE),
    FAUNA_EN_PELIGRO(TipoReporte.MEDIO_AMBIENTE),
    ANIMAL_SUELTO_O_MUERTO(TipoReporte.MEDIO_AMBIENTE),

    //CONVIVENCIA
    RUIDOS_MOLESTOS(TipoReporte.CONVIVENCIA),
    VANDALISMO(TipoReporte.CONVIVENCIA),
    MASCOTAS_SUELTAS(TipoReporte.CONVIVENCIA),
    PROPIEDAD_PRIVADA(TipoReporte.CONVIVENCIA),
    CONDUCTA_ANTISOCIAL(TipoReporte.CONVIVENCIA);


    private final TipoReporte tipoPadre;

    SubTipoReporte(TipoReporte tipoPadre) {
        this.tipoPadre = tipoPadre;
    }
}


    /*
  Analogía con clase
Es como si escribieras esto en una clase normal:

class Subtipo {
    private String nombre;
    private Tipo tipoPadre;

    Subtipo(String nombre, Tipo tipoPadre) {
        this.nombre = nombre;
        this.tipoPadre = tipoPadre;
    }
}

Pero con un enum, todas esas instancias (BACHE, PLAYA_SUCIA, etc.) están definidas de antemano. No se crean con new, sino que ya están creadas cuando el programa arranca.*/

