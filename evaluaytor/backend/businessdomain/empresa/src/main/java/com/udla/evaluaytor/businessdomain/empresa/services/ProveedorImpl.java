package com.udla.evaluaytor.businessdomain.empresa.services;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.udla.evaluaytor.businessdomain.empresa.dto.CategoriaDTO;
import com.udla.evaluaytor.businessdomain.empresa.dto.ProveedorDTO;
import com.udla.evaluaytor.businessdomain.empresa.dto.ProveedorResponseDTO;
import com.udla.evaluaytor.businessdomain.empresa.models.Categoria;
import com.udla.evaluaytor.businessdomain.empresa.models.Proveedor;
import com.udla.evaluaytor.businessdomain.empresa.repositories.CategoriaRepository;
import com.udla.evaluaytor.businessdomain.empresa.repositories.ProveedorRepository;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProveedorImpl implements ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<ProveedorResponseDTO> getAllProveedores() {
        List<Proveedor> proveedores = proveedorRepository.findAll();
        return proveedores.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public ProveedorResponseDTO getProveedorById(Long id) {
        Optional<Proveedor> optionalProveedor = proveedorRepository.findByIdWithCategorias(id);
         if (!optionalProveedor.isPresent()) {
                new RuntimeException("Proveedor no encontrado con id " + id);
         }
        Proveedor proveedor = optionalProveedor.get();
        return convertToDTO(proveedor);
    }

    @Transactional
    public ProveedorResponseDTO createProveedor(ProveedorDTO proveedorDTO) {
        Proveedor proveedor = new Proveedor();
        proveedor.setNombre(proveedorDTO.getNombre());
        proveedor.setDireccion(proveedorDTO.getDireccion());
        proveedor.setTelefono(proveedorDTO.getTelefono());

        List<Long> categoriaIds = proveedorDTO.getCategoriaIds();
        List<Categoria> categorias = categoriaRepository.findAllById(categoriaIds);
        proveedor.setCategorias(categorias);

        Proveedor proveedorGuardado = proveedorRepository.save(proveedor);
        return convertToDTO(proveedorGuardado);
    }

     @Transactional
    public ProveedorResponseDTO updateProveedor(Long id, ProveedorDTO proveedorUpdateDTO) {
        Optional<Proveedor> optionalProveedor = proveedorRepository.findById(id);
            
        if (!optionalProveedor.isPresent()) {
            throw new RuntimeException("Proveedor no encontrado con id " + id);
        }
            
        Proveedor proveedor = optionalProveedor.get();
        proveedor.setNombre(proveedorUpdateDTO.getNombre());
        proveedor.setDireccion(proveedorUpdateDTO.getDireccion());
        proveedor.setTelefono(proveedorUpdateDTO.getTelefono());
    
        List<Long> categoriaIds = proveedorUpdateDTO.getCategoriaIds();
        List<Categoria> categorias = categoriaRepository.findAllById(categoriaIds);
        proveedor.setCategorias(categorias);
    
        Proveedor updatedProveedor = proveedorRepository.save(proveedor);
        return convertToDTO(updatedProveedor);
    }
    


    private ProveedorResponseDTO convertToDTO(Proveedor proveedor) {
        ProveedorResponseDTO dto = new ProveedorResponseDTO();
        dto.setId(proveedor.getId());
        dto.setNombre(proveedor.getNombre());
        dto.setDireccion(proveedor.getDireccion());
        dto.setTelefono(proveedor.getTelefono());

        Set<CategoriaDTO> categoriasDTO = proveedor.getCategorias().stream()
                .map(categoria -> {
                    CategoriaDTO categoriaDTO = new CategoriaDTO();
                    categoriaDTO.setId(categoria.getId());
                    categoriaDTO.setDescripcion(categoria.getDescripcion());
                    return categoriaDTO;
                }).collect(Collectors.toSet());

        dto.setCategorias(categoriasDTO);

        return dto;
    }
}
