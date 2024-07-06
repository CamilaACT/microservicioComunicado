package com.udla.evaluaytor.businessdomain.empresa.dto;

import lombok.Data;

@Data
public class MatrizEvaluacionCreateUpdateDTO {

    private String pregunta;
    private int puntos;
    private int requiereDocumento;
    private Long categoriaId; // Solo el ID de la categoría

}

