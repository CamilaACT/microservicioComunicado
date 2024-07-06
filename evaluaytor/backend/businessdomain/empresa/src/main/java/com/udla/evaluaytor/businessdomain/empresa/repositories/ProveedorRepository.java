package com.udla.evaluaytor.businessdomain.empresa.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.udla.evaluaytor.businessdomain.empresa.models.Proveedor;

import java.util.Optional;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long >{
  
    @Query("SELECT p FROM Proveedor p LEFT JOIN FETCH p.categorias WHERE p.id = :id")
    Optional<Proveedor> findByIdWithCategorias(@Param("id") Long id);

}
