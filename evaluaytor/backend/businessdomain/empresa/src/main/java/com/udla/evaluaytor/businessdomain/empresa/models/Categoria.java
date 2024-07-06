package com.udla.evaluaytor.businessdomain.empresa.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descripcion;

    @ManyToMany(mappedBy = "categorias")
    private List<Proveedor> proveedores;

    @OneToMany(mappedBy = "categoria")
    private List<MatrizEvaluacion> matricesEvaluacion;


}
