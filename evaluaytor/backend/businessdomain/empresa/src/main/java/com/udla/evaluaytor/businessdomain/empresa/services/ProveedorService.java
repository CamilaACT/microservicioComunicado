package com.udla.evaluaytor.businessdomain.empresa.services;

import java.util.List;

import com.udla.evaluaytor.businessdomain.empresa.dto.ProveedorDTO;
import com.udla.evaluaytor.businessdomain.empresa.dto.ProveedorResponseDTO;

public interface ProveedorService {
    List<ProveedorResponseDTO> getAllProveedores();
    ProveedorResponseDTO getProveedorById(Long id);
    ProveedorResponseDTO createProveedor(ProveedorDTO proveedorDTO);
    ProveedorResponseDTO updateProveedor(Long id, ProveedorDTO proveedorUpdateDTO);
}
