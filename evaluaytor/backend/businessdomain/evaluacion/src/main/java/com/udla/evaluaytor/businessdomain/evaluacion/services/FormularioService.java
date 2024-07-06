package com.udla.evaluaytor.businessdomain.evaluacion.services;

import java.util.List;

import com.udla.evaluaytor.businessdomain.evaluacion.dto.FormularioCreateUpdateDTO;
import com.udla.evaluaytor.businessdomain.evaluacion.dto.FormularioDTO;
import com.udla.evaluaytor.businessdomain.evaluacion.models.FormularioEvaluacion;


public interface FormularioService {
    List<FormularioDTO> getAllFormularios();
    FormularioDTO getFormularioById(Long id);
    FormularioDTO createFormulario(FormularioCreateUpdateDTO formularioDTO);
    FormularioDTO updateFormulario(Long id, FormularioCreateUpdateDTO formularioDTO);
    void deleteFormulario(Long id);
    

    public FormularioDTO getFormularioEvaluacion(Long formularioId);
}
