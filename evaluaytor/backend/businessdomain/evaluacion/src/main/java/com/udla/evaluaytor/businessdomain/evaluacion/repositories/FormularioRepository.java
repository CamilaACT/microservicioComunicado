package com.udla.evaluaytor.businessdomain.evaluacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udla.evaluaytor.businessdomain.evaluacion.models.FormularioEvaluacion;

public interface FormularioRepository extends JpaRepository<FormularioEvaluacion, Long >{

}
