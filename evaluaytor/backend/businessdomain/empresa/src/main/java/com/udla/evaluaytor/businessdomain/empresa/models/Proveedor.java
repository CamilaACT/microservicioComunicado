package com.udla.evaluaytor.businessdomain.empresa.models;


import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
public class Proveedor extends Empresa {


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "proveedor_categoria",
        joinColumns = @JoinColumn(name = "id_proveedor"),
        inverseJoinColumns = @JoinColumn(name = "id_categoria")
    )
    private List<Categoria> categorias;

}
