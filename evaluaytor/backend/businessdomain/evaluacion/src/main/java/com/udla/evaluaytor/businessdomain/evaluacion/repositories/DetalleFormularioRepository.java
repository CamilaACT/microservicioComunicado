package com.udla.evaluaytor.businessdomain.evaluacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udla.evaluaytor.businessdomain.evaluacion.models.FormularioEvaluacionDetalle;

public interface DetalleFormularioRepository extends JpaRepository<FormularioEvaluacionDetalle, Long >{

}
