package com.udla.evaluaytor.businessdomain.evaluacion.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udla.evaluaytor.businessdomain.evaluacion.dto.EstadoDetalleDTO;
import com.udla.evaluaytor.businessdomain.evaluacion.models.EstadoDetalle;
import com.udla.evaluaytor.businessdomain.evaluacion.repositories.EstadoDetalleRepository;

@Service
public class EstadoDetalleServiceImpl implements EstadoDetalleService {

    @Autowired
    private EstadoDetalleRepository estadoDetalleRepository;

    @Override
    public List<EstadoDetalleDTO> getAllEstadosDetalle() {
        List<EstadoDetalle> estados = estadoDetalleRepository.findAll();
        return estados.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public EstadoDetalleDTO getEstadoDetalleById(Long id) {
        Optional<EstadoDetalle> estadoOpt = estadoDetalleRepository.findById(id);
        return estadoOpt.map(this::convertToDTO).orElse(null); // O lanza una excepción
    }

    @Override
    public EstadoDetalleDTO createEstadoDetalle(EstadoDetalleDTO estadoDetalleDTO) {
        EstadoDetalle estado = convertToEntity(estadoDetalleDTO);
        EstadoDetalle savedEstado = estadoDetalleRepository.save(estado);
        return convertToDTO(savedEstado);
    }

    @Override
    public EstadoDetalleDTO updateEstadoDetalle(Long id, EstadoDetalleDTO estadoDetalleDTO) {
        Optional<EstadoDetalle> estadoOpt = estadoDetalleRepository.findById(id);
        if (estadoOpt.isPresent()) {
            EstadoDetalle estado = estadoOpt.get();
            estado.setNombre(estadoDetalleDTO.getNombre());
            EstadoDetalle updatedEstado = estadoDetalleRepository.save(estado);
            return convertToDTO(updatedEstado);
        }
        return null; // O lanza una excepción
    }

    @Override
    public void deleteEstadoDetalle(Long id) {
        estadoDetalleRepository.deleteById(id);
    }

    private EstadoDetalleDTO convertToDTO(EstadoDetalle estado) {
        EstadoDetalleDTO dto = new EstadoDetalleDTO();
        dto.setId(estado.getId());
        dto.setNombre(estado.getNombre());
        return dto;
    }

    private EstadoDetalle convertToEntity(EstadoDetalleDTO dto) {
        EstadoDetalle estado = new EstadoDetalle();
        estado.setNombre(dto.getNombre());
        return estado;
    }
}
