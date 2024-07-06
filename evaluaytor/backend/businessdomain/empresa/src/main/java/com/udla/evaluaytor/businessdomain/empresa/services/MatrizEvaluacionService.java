/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package com.udla.evaluaytor.businessdomain.empresa.services;

import java.util.List;

import com.udla.evaluaytor.businessdomain.empresa.dto.MatrizEvaluacionCreateUpdateDTO;
import com.udla.evaluaytor.businessdomain.empresa.dto.MatrizEvaluacionDTO;

public interface MatrizEvaluacionService {
    List<MatrizEvaluacionDTO> getAllMatricesEvaluacion();
    MatrizEvaluacionDTO getMatrizEvaluacionById(Long id);
    MatrizEvaluacionDTO createMatrizEvaluacion(MatrizEvaluacionCreateUpdateDTO matrizEvaluacionDTO);
    MatrizEvaluacionDTO updateMatrizEvaluacion(Long id, MatrizEvaluacionCreateUpdateDTO matrizEvaluacionDTO);
}