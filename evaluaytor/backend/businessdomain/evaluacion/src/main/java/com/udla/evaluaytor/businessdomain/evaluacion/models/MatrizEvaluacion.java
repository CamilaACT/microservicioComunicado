package com.udla.evaluaytor.businessdomain.evaluacion.models;

import lombok.Data;

@Data
public class MatrizEvaluacion {
    private Long id;
    private String pregunta;
    private int puntos;
    private int requiereDocumento;
}
