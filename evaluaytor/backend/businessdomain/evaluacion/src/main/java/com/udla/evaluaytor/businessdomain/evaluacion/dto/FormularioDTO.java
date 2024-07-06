package com.udla.evaluaytor.businessdomain.evaluacion.dto;

import java.util.Date;

import com.udla.evaluaytor.businessdomain.evaluacion.models.Categoria;
import com.udla.evaluaytor.businessdomain.evaluacion.models.Perito;
import com.udla.evaluaytor.businessdomain.evaluacion.models.Proveedor;

import lombok.Data;

@Data
public class FormularioDTO {
private Long id;

    private Date fecha;
    private String numero; 
    private int evaluacion;
    private EstadoFormularioDTO estadoFormularioDTO;
    private Proveedor proveedor;
    private Perito perito;
    private Categoria categoria;
    
}
