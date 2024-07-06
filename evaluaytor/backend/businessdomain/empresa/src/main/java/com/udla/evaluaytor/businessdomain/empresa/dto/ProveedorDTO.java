package com.udla.evaluaytor.businessdomain.empresa.dto;

import java.util.List;

import lombok.Data;

@Data
public class ProveedorDTO {

    private String nombre;
    private String direccion;
    private String telefono;
    private List<Long> categoriaIds;

}
