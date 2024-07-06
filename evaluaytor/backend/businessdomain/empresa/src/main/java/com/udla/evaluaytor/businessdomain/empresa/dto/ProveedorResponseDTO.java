package com.udla.evaluaytor.businessdomain.empresa.dto;

import java.util.Set;
import lombok.Data;

@Data
public class ProveedorResponseDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private String telefono;
    private Set<CategoriaDTO> categorias;
}
