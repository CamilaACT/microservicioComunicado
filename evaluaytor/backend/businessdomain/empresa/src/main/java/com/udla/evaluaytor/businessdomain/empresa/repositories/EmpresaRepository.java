package com.udla.evaluaytor.businessdomain.empresa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udla.evaluaytor.businessdomain.empresa.models.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long >{

}
