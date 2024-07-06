package com.udla.evaluaytor.businessdomain.evaluacion.services;

import com.udla.evaluaytor.businessdomain.evaluacion.dto.EstadoDetalleDTO;
import java.util.List;
public interface EstadoDetalleService {
    List<EstadoDetalleDTO> getAllEstadosDetalle();
    EstadoDetalleDTO getEstadoDetalleById(Long id);
    EstadoDetalleDTO createEstadoDetalle(EstadoDetalleDTO estadoDetalleDTO);
    EstadoDetalleDTO updateEstadoDetalle(Long id, EstadoDetalleDTO estadoDetalleDTO);
    void deleteEstadoDetalle(Long id);
}
