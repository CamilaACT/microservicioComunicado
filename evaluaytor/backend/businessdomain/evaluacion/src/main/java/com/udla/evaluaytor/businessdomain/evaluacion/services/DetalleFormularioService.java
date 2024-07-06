package com.udla.evaluaytor.businessdomain.evaluacion.services;

import com.udla.evaluaytor.businessdomain.evaluacion.dto.DetalleFormularioCreateUpdateDTO;
import com.udla.evaluaytor.businessdomain.evaluacion.dto.DetalleFormularioDTO;
import com.udla.evaluaytor.businessdomain.evaluacion.models.FormularioEvaluacion;
import com.udla.evaluaytor.businessdomain.evaluacion.models.FormularioEvaluacionDetalle;

import java.util.List;

public interface DetalleFormularioService {
    List<DetalleFormularioDTO> getAllDetallesFormulario();
    DetalleFormularioDTO getDetalleFormularioById(Long id);
    DetalleFormularioDTO createDetalleFormulario(DetalleFormularioCreateUpdateDTO detalleFormularioDTO);
    DetalleFormularioDTO updateDetalleFormulario(Long id, DetalleFormularioCreateUpdateDTO detalleFormularioDTO);
    void deleteDetalleFormulario(Long id);



    public DetalleFormularioDTO getFormularioEvaluacionDetalle(Long formularioDetalleId);
}
