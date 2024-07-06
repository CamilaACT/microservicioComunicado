package com.udla.evaluaytor.businessdomain.evaluacion.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
public class FormularioEvaluacionDetalle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int cumplimiento;
    private String observacion; 

    @ManyToOne
    @JoinColumn(name = "id_estado_detalle")
    private EstadoDetalle estadoDetalle;

    @OneToOne
    @JoinColumn(name = "id_documento")
    private Documento documento;

    @ManyToOne
    @JoinColumn(name = "id_formulario")
    //@JsonBackReference
    //@JsonIgnore
    private FormularioEvaluacion formulario;  

    private Long id_matrizevaluacion;

    @Transient
    private MatrizEvaluacion matrizEvaluacion;

    
}