package com.udla.evaluaytor.businessdomain.evaluacion.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udla.evaluaytor.businessdomain.evaluacion.models.Documento;

public interface DocumentoRepository extends JpaRepository<Documento, Long >{

}