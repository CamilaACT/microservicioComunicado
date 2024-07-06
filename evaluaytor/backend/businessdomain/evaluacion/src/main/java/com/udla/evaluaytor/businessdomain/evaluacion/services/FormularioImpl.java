package com.udla.evaluaytor.businessdomain.evaluacion.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.udla.evaluaytor.businessdomain.evaluacion.dto.EstadoFormularioDTO;
import com.udla.evaluaytor.businessdomain.evaluacion.dto.FormularioCreateUpdateDTO;
import com.udla.evaluaytor.businessdomain.evaluacion.dto.FormularioDTO;
import com.udla.evaluaytor.businessdomain.evaluacion.models.Categoria;
import com.udla.evaluaytor.businessdomain.evaluacion.models.EstadoFormulario;
import com.udla.evaluaytor.businessdomain.evaluacion.models.FormularioEvaluacion;
import com.udla.evaluaytor.businessdomain.evaluacion.models.Perito;
import com.udla.evaluaytor.businessdomain.evaluacion.models.Proveedor;
import com.udla.evaluaytor.businessdomain.evaluacion.repositories.EstadoFormularioRepository;
import com.udla.evaluaytor.businessdomain.evaluacion.repositories.FormularioRepository;

import reactor.core.publisher.Mono;

@Service
public class FormularioImpl implements FormularioService{

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private FormularioRepository formularioRepository;

    @Autowired
    private EstadoFormularioRepository estadoFormularioRepository;

    @Override
public List<FormularioDTO> getAllFormularios() {
    List<FormularioEvaluacion> formularios = formularioRepository.findAll();
    return formularios.stream()
        .map(this::completeAndConvertToDTO)
        .collect(Collectors.toList());
}

private FormularioEvaluacion completeFormularioWithExternalData(FormularioEvaluacion formulario) {
    Long proveedorId = formulario.getProveedor_id();
    Long categoriaId = formulario.getCategorida_id();
    Long peritoId = formulario.getPerito_id();

    WebClient webClient = webClientBuilder.build();

    // Llamada a microservicio para obtener Proveedor
    Proveedor proveedor = webClient.get()
        .uri("http://localhost:8086/api/empresa/proveedor/findbyid/{id}", proveedorId)
        .retrieve()
        .bodyToMono(Proveedor.class)
        .block();
    formulario.setProveedor(proveedor);

    // Llamada a microservicio para obtener Categoria
    Categoria categoria = webClient.get()
        .uri("http://localhost:8086/api/empresa/categoria/find/{id}", categoriaId)
        .retrieve()
        .bodyToMono(Categoria.class)
        .block();
    formulario.setCategoria(categoria);

    // Llamada a microservicio para obtener Perito
    Perito perito = webClient.get()
        .uri("http://localhost:8086/api/empresa/perito/findbyid/{id}", peritoId)
        .retrieve()
        .bodyToMono(Perito.class)
        .block();
    formulario.setPerito(perito);

    return formulario;
}

private FormularioDTO completeAndConvertToDTO(FormularioEvaluacion formulario) {
    // Completar la información del formulario con datos externos
    FormularioEvaluacion completedFormulario = completeFormularioWithExternalData(formulario);

    // Convertir a DTO
    return convertToDTO(completedFormulario);
}


    @Override
    public FormularioDTO getFormularioById(Long id) {
        Optional<FormularioEvaluacion> formularioOpt = formularioRepository.findById(id);
        return formularioOpt.map(this::convertToDTO).orElse(null); // O lanza una excepción
    }

    @Override
    public FormularioDTO createFormulario(FormularioCreateUpdateDTO formularioDTO) {
        FormularioEvaluacion formulario = convertToEntity(formularioDTO);
        FormularioEvaluacion savedFormulario = formularioRepository.save(formulario);
        return convertToDTO(savedFormulario);
    }

    @Override
    public FormularioDTO updateFormulario(Long id, FormularioCreateUpdateDTO formularioDTO) {
        FormularioEvaluacion formulario = formularioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Formulario no encontrado"));
    
        formulario.setFecha(Optional.ofNullable(formularioDTO.getFecha()).orElse(new Date()));
        formulario.setNumero(formularioDTO.getNumero());
        formulario.setEvaluacion(formularioDTO.getEvaluacion());
    
        // Asignar EstadoFormulario
        EstadoFormulario estadoFormulario = estadoFormularioRepository.findById(formularioDTO.getEstadoFormularioId())
            .orElseThrow(() -> new RuntimeException("EstadoFormulario no encontrado"));
        formulario.setEstadoFormulario(estadoFormulario);
    
        // Asignar IDs de Perito, Proveedor y Categoria
        formulario.setPerito_id(formularioDTO.getPeritoId());
        formulario.setProveedor_id(formularioDTO.getProveedorId());
        formulario.setCategorida_id(formularioDTO.getCategoridaId());
    
        FormularioEvaluacion savedFormulario = formularioRepository.save(formulario);
        return convertToDTO(savedFormulario);
    }
    

    @Override
    public void deleteFormulario(Long id) {
        formularioRepository.deleteById(id);
    }


    private FormularioDTO convertToDTO(FormularioEvaluacion formulario) {
        FormularioDTO dto = new FormularioDTO();
        dto.setId(formulario.getId());
        dto.setFecha(formulario.getFecha());
        dto.setNumero(formulario.getNumero());
        dto.setEvaluacion(formulario.getEvaluacion());
    
        if (formulario.getEstadoFormulario() != null) {
            EstadoFormularioDTO estadoDTO = new EstadoFormularioDTO();
            estadoDTO.setId(formulario.getEstadoFormulario().getId());
            estadoDTO.setNombre(formulario.getEstadoFormulario().getNombre());
            dto.setEstadoFormularioDTO(estadoDTO);
        }
    
        if (formulario.getProveedor() != null) {
            Proveedor proveedorDTO = new Proveedor();
            proveedorDTO.setId(formulario.getProveedor().getId());
            proveedorDTO.setNombre(formulario.getProveedor().getNombre());
            proveedorDTO.setTelefono(formulario.getPerito().getTelefono());
            proveedorDTO.setDireccion(formulario.getPerito().getDireccion());
            dto.setProveedor(proveedorDTO);
        }
    
        if (formulario.getPerito() != null) {
            Perito peritoDTO = new Perito();
            peritoDTO.setId(formulario.getPerito().getId());
            peritoDTO.setNombre(formulario.getPerito().getNombre());
            peritoDTO.setDireccion(formulario.getPerito().getDireccion());
            peritoDTO.setTelefono(formulario.getPerito().getTelefono());
            dto.setPerito(peritoDTO);
        }
    
        if (formulario.getCategoria() != null) {
            Categoria categoriaDTO = new Categoria();
            categoriaDTO.setId(formulario.getCategoria().getId());
            categoriaDTO.setDescripcion(formulario.getCategoria().getDescripcion());
            dto.setCategoria(categoriaDTO);
        }
    
        return dto;
    }
    

    private FormularioEvaluacion convertToEntity(FormularioCreateUpdateDTO dto) {
        FormularioEvaluacion formulario = new FormularioEvaluacion();
        formulario.setFecha(Optional.ofNullable(dto.getFecha()).orElse(new Date()));
        formulario.setNumero(dto.getNumero());
        formulario.setEvaluacion(dto.getEvaluacion());
    
        // Asignar EstadoFormulario
        EstadoFormulario estadoFormulario = estadoFormularioRepository.findById(dto.getEstadoFormularioId())
            .orElseThrow(() -> new RuntimeException("EstadoFormulario no encontrado"));
        formulario.setEstadoFormulario(estadoFormulario);
    
        // Asignar IDs de Perito, Proveedor y Categoria
        formulario.setPerito_id(dto.getPeritoId());
        formulario.setProveedor_id(dto.getProveedorId());
        formulario.setCategorida_id(dto.getCategoridaId());
    
        return formulario;
    }


    public FormularioDTO getFormularioEvaluacion(Long formularioId) {
        // Obtén el FormularioEvaluacion desde el repositorio
        FormularioEvaluacion formularioEvaluacion = formularioRepository.findById(formularioId)
            .orElseThrow(() -> new RuntimeException("Formulario no encontrado"));

        // Obtén los IDs de Proveedor y Perito desde el formulario
        Long proveedorId = formularioEvaluacion.getProveedor_id();
        Long categoriaId = formularioEvaluacion.getCategorida_id();
        Long peritoId = formularioEvaluacion.getPerito_id();

        // Llama al microservicio de proveedor para obtener la información del proveedor
        WebClient webClient = webClientBuilder.build();
        Mono<Proveedor> proveedorMono = webClient.get()
            .uri("http://localhost:8086/api/empresa/proveedor/findbyid/{id}", proveedorId)
            .retrieve()
            .bodyToMono(Proveedor.class);
        Proveedor proveedor = proveedorMono.block();
        formularioEvaluacion.setProveedor(proveedor);

        Mono<Categoria> categoriaMono = webClient.get()
            .uri("http://localhost:8086/api/empresa/categoria/find/{id}", categoriaId)
            .retrieve()
            .bodyToMono(Categoria.class);
        Categoria categoria = categoriaMono.block();
        formularioEvaluacion.setCategoria(categoria);

        Mono<Perito> peritoMono = webClient.get()
            .uri("http://localhost:8086/api/empresa/perito/findbyid/{id}", peritoId)
            .retrieve()
            .bodyToMono(Perito.class);
        Perito perito = peritoMono.block();
        formularioEvaluacion.setPerito(perito);
        
        return convertToDTO(formularioEvaluacion);
    }
}