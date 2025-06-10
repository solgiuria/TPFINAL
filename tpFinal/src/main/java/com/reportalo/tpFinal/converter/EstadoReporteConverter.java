package com.reportalo.tpFinal.converter;

import com.reportalo.tpFinal.enums.EstadoReporte;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EstadoReporteConverter implements Converter<String, EstadoReporte> {
    @Override
    public EstadoReporte convert(String source) {
        return EstadoReporte.valueOf(source.toUpperCase());
    }
}
