package com.udla.evaluaytor.businessdomain.evaluacion.models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import lombok.Data;


@Entity
@Data
public class FormularioEvaluacion {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date fecha;
    private String numero; 
    private int evaluacion;

     @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estado_formulario_id", nullable = false) // Especifica el nombre de la columna de la FK
    private EstadoFormulario estadoFormulario;
    
    @OneToMany(mappedBy = "formulario")
    @JsonManagedReference
    private List<FormularioEvaluacionDetalle> detallesFormulario;

    private Long proveedor_id;
    private Long categorida_id;
    private Long perito_id;

    @Transient
    private Proveedor proveedor;

    @Transient
    private Perito Perito;

    @Transient
    private Categoria categoria;


}

