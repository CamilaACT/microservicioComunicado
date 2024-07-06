package com.udla.evaluaytor.businessdomain.evaluacion.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udla.evaluaytor.businessdomain.evaluacion.dto.EstadoFormularioDTO;
import com.udla.evaluaytor.businessdomain.evaluacion.models.EstadoFormulario;
import com.udla.evaluaytor.businessdomain.evaluacion.repositories.EstadoFormularioRepository;

@Service
public class EstadoFormularioImpl implements EstadoFormularioService {

    @Autowired
    private EstadoFormularioRepository estadoFormularioRepository;

    @Override
    public List<EstadoFormularioDTO> getAllEstadosFormulario() {
        List<EstadoFormulario> estados = estadoFormularioRepository.findAll();
        return estados.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public EstadoFormularioDTO getEstadoFormularioById(Long id) {
        Optional<EstadoFormulario> estadoOpt = estadoFormularioRepository.findById(id);
        return estadoOpt.map(this::convertToDTO).orElse(null); // O lanza una excepción
    }

    @Override
    public EstadoFormularioDTO createEstadoFormulario(EstadoFormularioDTO estadoFormularioDTO) {
        EstadoFormulario estado = convertToEntity(estadoFormularioDTO);
        EstadoFormulario savedEstado = estadoFormularioRepository.save(estado);
        return convertToDTO(savedEstado);
    }

    @Override
    public EstadoFormularioDTO updateEstadoFormulario(Long id, EstadoFormularioDTO estadoFormularioDTO) {
        Optional<EstadoFormulario> estadoOpt = estadoFormularioRepository.findById(id);
        if (estadoOpt.isPresent()) {
            EstadoFormulario estado = estadoOpt.get();
            estado.setNombre(estadoFormularioDTO.getNombre());
            EstadoFormulario updatedEstado = estadoFormularioRepository.save(estado);
            return convertToDTO(updatedEstado);
        }
        return null; // O lanza una excepción
    }

    @Override
    public void deleteEstadoFormulario(Long id) {
        estadoFormularioRepository.deleteById(id);
    }

    private EstadoFormularioDTO convertToDTO(EstadoFormulario estado) {
        EstadoFormularioDTO dto = new EstadoFormularioDTO();
        dto.setId(estado.getId());
        dto.setNombre(estado.getNombre());
        return dto;
    }

    private EstadoFormulario convertToEntity(EstadoFormularioDTO dto) {
        EstadoFormulario estado = new EstadoFormulario();
        estado.setNombre(dto.getNombre());
        return estado;
    }
}
