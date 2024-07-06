package com.udla.evaluaytor.businessdomain.evaluacion.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.udla.evaluaytor.businessdomain.evaluacion.dto.DetalleFormularioCreateUpdateDTO;
import com.udla.evaluaytor.businessdomain.evaluacion.dto.DetalleFormularioDTO;
import com.udla.evaluaytor.businessdomain.evaluacion.dto.DocumentoDTO;
import com.udla.evaluaytor.businessdomain.evaluacion.dto.EstadoDetalleDTO;
import com.udla.evaluaytor.businessdomain.evaluacion.dto.EstadoFormularioDTO;
import com.udla.evaluaytor.businessdomain.evaluacion.dto.FormularioDTO;
import com.udla.evaluaytor.businessdomain.evaluacion.models.FormularioEvaluacionDetalle;
import com.udla.evaluaytor.businessdomain.evaluacion.models.MatrizEvaluacion;
import com.udla.evaluaytor.businessdomain.evaluacion.models.Perito;
import com.udla.evaluaytor.businessdomain.evaluacion.models.Proveedor;
import com.udla.evaluaytor.businessdomain.evaluacion.models.Categoria;
import com.udla.evaluaytor.businessdomain.evaluacion.models.Documento;
import com.udla.evaluaytor.businessdomain.evaluacion.models.EstadoDetalle;
import com.udla.evaluaytor.businessdomain.evaluacion.models.FormularioEvaluacion;
import com.udla.evaluaytor.businessdomain.evaluacion.repositories.DetalleFormularioRepository;
import com.udla.evaluaytor.businessdomain.evaluacion.repositories.DocumentoRepository;
import com.udla.evaluaytor.businessdomain.evaluacion.repositories.EstadoDetalleRepository;
import com.udla.evaluaytor.businessdomain.evaluacion.repositories.FormularioRepository;

import reactor.core.publisher.Mono;

@Service
public class DetalleFormularioServiceImpl implements DetalleFormularioService {

    @Autowired
    private DetalleFormularioRepository detalleFormularioRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private EstadoDetalleRepository estadoDetalleRepository;

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private FormularioRepository formularioRepository;

    private DetalleFormularioDTO completeAndConvertToDTO(FormularioEvaluacionDetalle detalle) {
        FormularioEvaluacionDetalle completedDetalle = completeDetalleWithExternalData(detalle);
        return convertToDTO(completedDetalle);
    }
    
    private FormularioEvaluacionDetalle completeDetalleWithExternalData(FormularioEvaluacionDetalle detalle) {
        Long matrizEvaluacionId = detalle.getId_matrizevaluacion();
    
        WebClient webClient = webClientBuilder.build();
    
        // Llamada a microservicio para obtener MatrizEvaluacion
        MatrizEvaluacion matriz = webClient.get()
            .uri("http://localhost:8086/api/empresa/matrizevaluacion/find/{id}", matrizEvaluacionId)
            .retrieve()
            .bodyToMono(MatrizEvaluacion.class)
            .block();
        detalle.setMatrizEvaluacion(matriz);
    
        return detalle;
    }
    
    @Override
    public List<DetalleFormularioDTO> getAllDetallesFormulario() {
        List<FormularioEvaluacionDetalle> detalles = detalleFormularioRepository.findAll();
        return detalles.stream()
            .map(this::completeAndConvertToDTO)
            .collect(Collectors.toList());
    }
    

    @Override
    public DetalleFormularioDTO getDetalleFormularioById(Long id) {
        Optional<FormularioEvaluacionDetalle> detalleOpt = detalleFormularioRepository.findById(id);
        return detalleOpt.map(this::convertToDTO).orElse(null); // O lanza una excepción
    }

    @Override
    public DetalleFormularioDTO createDetalleFormulario(DetalleFormularioCreateUpdateDTO detalleFormularioDTO) {
        FormularioEvaluacionDetalle detalle = convertToEntity(detalleFormularioDTO);
        FormularioEvaluacionDetalle savedDetalle = detalleFormularioRepository.save(detalle);
        return convertToDTO(savedDetalle);
    }
    
    @Override
    public DetalleFormularioDTO updateDetalleFormulario(Long id, DetalleFormularioCreateUpdateDTO detalleFormularioDTO) {
        Optional<FormularioEvaluacionDetalle> detalleOpt = detalleFormularioRepository.findById(id);
        if (detalleOpt.isPresent()) {
            FormularioEvaluacionDetalle detalle = detalleOpt.get();
            detalle.setCumplimiento(detalleFormularioDTO.getCumplimiento());
            detalle.setObservacion(detalleFormularioDTO.getObservacion());
    
            Optional<EstadoDetalle> estadoOpt = estadoDetalleRepository.findById(detalleFormularioDTO.getEstadoDetalleId());
            estadoOpt.ifPresent(detalle::setEstadoDetalle);
    
            Optional<Documento> documentoOpt = documentoRepository.findById(detalleFormularioDTO.getDocumentoId());
            documentoOpt.ifPresent(detalle::setDocumento);
    
            Optional<FormularioEvaluacion> formularioOpt = formularioRepository.findById(detalleFormularioDTO.getFormularioId());
            formularioOpt.ifPresent(detalle::setFormulario);
    
            detalle.setId_matrizevaluacion(detalleFormularioDTO.getId_matrizevaluacion()); // Setear el ID de la matriz de evaluación
    
            FormularioEvaluacionDetalle updatedDetalle = detalleFormularioRepository.save(detalle);
            return convertToDTO(updatedDetalle);
        }
        return null; // O lanza una excepción
    }
    

    @Override
    public void deleteDetalleFormulario(Long id) {
        detalleFormularioRepository.deleteById(id);
    }


    
    private DetalleFormularioDTO convertToDTO(FormularioEvaluacionDetalle detalle) {
        DetalleFormularioDTO dto = new DetalleFormularioDTO();
        dto.setId(detalle.getId());
        dto.setCumplimiento(detalle.getCumplimiento());
        dto.setObservacion(detalle.getObservacion());
    
        if (detalle.getEstadoDetalle() != null) {
            EstadoDetalleDTO estadoDTO = new EstadoDetalleDTO();
            estadoDTO.setId(detalle.getEstadoDetalle().getId());
            estadoDTO.setNombre(detalle.getEstadoDetalle().getNombre());
            dto.setEstadoDetalleDTO(estadoDTO);
        }
    
        if (detalle.getDocumento() != null) {
            DocumentoDTO documentoDTO = new DocumentoDTO();
            documentoDTO.setId(detalle.getDocumento().getId());
            documentoDTO.setFormato(detalle.getDocumento().getFormato());
            dto.setDocumentoDTO(documentoDTO);
        }
    
        if (detalle.getFormulario() != null) {
            FormularioDTO formularioDTO = new FormularioDTO();
            formularioDTO.setId(detalle.getFormulario().getId());
            formularioDTO.setFecha(detalle.getFormulario().getFecha());
            formularioDTO.setNumero(detalle.getFormulario().getNumero());
            formularioDTO.setEvaluacion(detalle.getFormulario().getEvaluacion());
    
            if (detalle.getFormulario().getEstadoFormulario() != null) {
                EstadoFormularioDTO estadoFormularioDTO = new EstadoFormularioDTO();
                estadoFormularioDTO.setId(detalle.getFormulario().getEstadoFormulario().getId());
                estadoFormularioDTO.setNombre(detalle.getFormulario().getEstadoFormulario().getNombre());
                formularioDTO.setEstadoFormularioDTO(estadoFormularioDTO);
            }
    
            dto.setFormularioDTO(formularioDTO);
        }
    
        if (detalle.getMatrizEvaluacion() != null) {
            MatrizEvaluacion matriz = new MatrizEvaluacion();
            matriz.setId(detalle.getMatrizEvaluacion().getId());
            matriz.setPregunta(detalle.getMatrizEvaluacion().getPregunta());
            matriz.setPuntos(detalle.getMatrizEvaluacion().getPuntos());
            matriz.setRequiereDocumento(detalle.getMatrizEvaluacion().getRequiereDocumento());
            dto.setMatrizEvaluacion(matriz);
        }
    
        return dto;
    }
    

    private FormularioEvaluacionDetalle convertToEntity(DetalleFormularioCreateUpdateDTO dto) {
        FormularioEvaluacionDetalle detalle = new FormularioEvaluacionDetalle();
        detalle.setCumplimiento(dto.getCumplimiento());
        detalle.setObservacion(dto.getObservacion());

        Optional<EstadoDetalle> estadoOpt = estadoDetalleRepository.findById(dto.getEstadoDetalleId());
        estadoOpt.ifPresent(detalle::setEstadoDetalle);

        Optional<Documento> documentoOpt = documentoRepository.findById(dto.getDocumentoId());
        documentoOpt.ifPresent(detalle::setDocumento);

        Optional<FormularioEvaluacion> formularioOpt = formularioRepository.findById(dto.getFormularioId());
        formularioOpt.ifPresent(detalle::setFormulario);

        return detalle;
    }

    @Override
    public DetalleFormularioDTO  getFormularioEvaluacionDetalle(Long formularioDetalleId) {
        // Obtén el FormularioEvaluacion desde el repositorio
        FormularioEvaluacionDetalle formularioEvaluacionDetalle = detalleFormularioRepository.findById(formularioDetalleId)
            .orElseThrow(() -> new RuntimeException("Formulario no encontrado"));

        // Obtener id matriz desde detalle
        Long matrizEvaluacion = formularioEvaluacionDetalle.getId_matrizevaluacion();


        // Llama al microservicio de proveedor para obtener la información del proveedor
        WebClient webClient = webClientBuilder.build();
        Mono<MatrizEvaluacion> matrizMono = webClient.get()
            .uri("http://localhost:8086/api/empresa/matrizevaluacion/find/{id}", matrizEvaluacion)
            .retrieve()
            .bodyToMono(MatrizEvaluacion.class);
            MatrizEvaluacion matriz = matrizMono.block();
            formularioEvaluacionDetalle.setMatrizEvaluacion(matriz);

        
            return convertToDTO(formularioEvaluacionDetalle);
    }
}