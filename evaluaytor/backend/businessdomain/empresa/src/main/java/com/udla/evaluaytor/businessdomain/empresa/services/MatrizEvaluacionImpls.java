package com.udla.evaluaytor.businessdomain.empresa.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udla.evaluaytor.businessdomain.empresa.dto.CategoriaDTO;
import com.udla.evaluaytor.businessdomain.empresa.dto.MatrizEvaluacionCreateUpdateDTO;
import com.udla.evaluaytor.businessdomain.empresa.dto.MatrizEvaluacionDTO;
import com.udla.evaluaytor.businessdomain.empresa.models.Categoria;
import com.udla.evaluaytor.businessdomain.empresa.models.MatrizEvaluacion;
import com.udla.evaluaytor.businessdomain.empresa.repositories.CategoriaRepository;
import com.udla.evaluaytor.businessdomain.empresa.repositories.MatrizEvaluacionRepository;

@Service
public class MatrizEvaluacionImpls implements MatrizEvaluacionService {


     @Autowired
    private MatrizEvaluacionRepository matrizEvaluacionRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<MatrizEvaluacionDTO> getAllMatricesEvaluacion() {
        List<MatrizEvaluacion> matrices = matrizEvaluacionRepository.findAll();
        return matrices.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public MatrizEvaluacionDTO getMatrizEvaluacionById(Long id) {
        Optional<MatrizEvaluacion> matrizOpt = matrizEvaluacionRepository.findById(id);
        return matrizOpt.map(this::convertToDTO).orElse(null); // O lanza una excepción
    }

    @Override
    public MatrizEvaluacionDTO createMatrizEvaluacion(MatrizEvaluacionCreateUpdateDTO matrizEvaluacionDTO) {
        MatrizEvaluacion matriz = convertToEntity(matrizEvaluacionDTO);
        MatrizEvaluacion savedMatriz = matrizEvaluacionRepository.save(matriz);
        return convertToDTO(savedMatriz);
    }

    @Override
    public MatrizEvaluacionDTO updateMatrizEvaluacion(Long id, MatrizEvaluacionCreateUpdateDTO matrizEvaluacionDTO) {
        Optional<MatrizEvaluacion> matrizOpt = matrizEvaluacionRepository.findById(id);
        if (matrizOpt.isPresent()) {
            MatrizEvaluacion matriz = matrizOpt.get();
            matriz.setPregunta(matrizEvaluacionDTO.getPregunta());
            matriz.setPuntos(matrizEvaluacionDTO.getPuntos());
            matriz.setRequiereDocumento(matrizEvaluacionDTO.getRequiereDocumento());

            Optional<Categoria> categoriaOpt = categoriaRepository.findById(matrizEvaluacionDTO.getCategoriaId());
            if (categoriaOpt.isPresent()) {
                matriz.setCategoria(categoriaOpt.get());
            }

            MatrizEvaluacion updatedMatriz = matrizEvaluacionRepository.save(matriz);
            return convertToDTO(updatedMatriz);
        }
        return null; // O lanza una excepción
    }

    private MatrizEvaluacionDTO convertToDTO(MatrizEvaluacion matrizEvaluacion) {
        MatrizEvaluacionDTO dto = new MatrizEvaluacionDTO();
        dto.setId(matrizEvaluacion.getId());
        dto.setPregunta(matrizEvaluacion.getPregunta());
        dto.setPuntos(matrizEvaluacion.getPuntos());
        dto.setRequiereDocumento(matrizEvaluacion.getRequiereDocumento());

        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setId(matrizEvaluacion.getCategoria().getId());
        categoriaDTO.setDescripcion(matrizEvaluacion.getCategoria().getDescripcion());
        dto.setCategorias(categoriaDTO);

        return dto;
    }

    private MatrizEvaluacion convertToEntity(MatrizEvaluacionCreateUpdateDTO dto) {
        MatrizEvaluacion matriz = new MatrizEvaluacion();
        matriz.setPregunta(dto.getPregunta());
        matriz.setPuntos(dto.getPuntos());
        matriz.setRequiereDocumento(dto.getRequiereDocumento());

        Optional<Categoria> categoriaOpt = categoriaRepository.findById(dto.getCategoriaId());
        categoriaOpt.ifPresent(matriz::setCategoria);

        return matriz;
    }
}
