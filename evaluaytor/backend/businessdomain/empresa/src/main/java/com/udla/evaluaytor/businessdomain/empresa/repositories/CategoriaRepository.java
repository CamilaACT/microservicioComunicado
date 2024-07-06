package com.udla.evaluaytor.businessdomain.empresa.repositories;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import com.udla.evaluaytor.businessdomain.empresa.models.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    @Override
    @NonNull
    List<Categoria> findAllById(@NonNull Iterable<Long> ids);
}
