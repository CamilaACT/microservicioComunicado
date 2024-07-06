package com.udla.evaluaytor.businessdomain.evaluacion.services;

import com.udla.evaluaytor.businessdomain.evaluacion.dto.EstadoFormularioDTO;
import java.util.List;
public interface EstadoFormularioService {
    List<EstadoFormularioDTO> getAllEstadosFormulario();
    EstadoFormularioDTO getEstadoFormularioById(Long id);
    EstadoFormularioDTO createEstadoFormulario(EstadoFormularioDTO estadoFormularioDTO);
    EstadoFormularioDTO updateEstadoFormulario(Long id, EstadoFormularioDTO estadoFormularioDTO);
    void deleteEstadoFormulario(Long id);
}
