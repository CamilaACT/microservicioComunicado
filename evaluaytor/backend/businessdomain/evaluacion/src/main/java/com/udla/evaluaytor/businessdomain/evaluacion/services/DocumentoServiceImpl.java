package com.udla.evaluaytor.businessdomain.evaluacion.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udla.evaluaytor.businessdomain.evaluacion.dto.DocumentoDTO;
import com.udla.evaluaytor.businessdomain.evaluacion.models.Documento;
import com.udla.evaluaytor.businessdomain.evaluacion.repositories.DocumentoRepository;

@Service
public class DocumentoServiceImpl implements DocumentoService {

    @Autowired
    private DocumentoRepository documentoRepository;

    @Override
    public List<DocumentoDTO> getAllDocumentos() {
        List<Documento> documentos = documentoRepository.findAll();
        return documentos.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public DocumentoDTO getDocumentoById(Long id) {
        Optional<Documento> documentoOpt = documentoRepository.findById(id);
        return documentoOpt.map(this::convertToDTO).orElse(null); // O lanza una excepción
    }

    @Override
    public DocumentoDTO createDocumento(DocumentoDTO documentoDTO) {
        Documento documento = convertToEntity(documentoDTO);
        Documento savedDocumento = documentoRepository.save(documento);
        return convertToDTO(savedDocumento);
    }

    @Override
    public DocumentoDTO updateDocumento(Long id, DocumentoDTO documentoDTO) {
        Optional<Documento> documentoOpt = documentoRepository.findById(id);
        if (documentoOpt.isPresent()) {
            Documento documento = documentoOpt.get();
            documento.setFormato(documentoDTO.getFormato());
            //documento.setPath(documentoDTO.getPath());
            Documento updatedDocumento = documentoRepository.save(documento);
            return convertToDTO(updatedDocumento);
        }
        return null; // O lanza una excepción
    }

    @Override
    public void deleteDocumento(Long id) {
        documentoRepository.deleteById(id);
    }

    private DocumentoDTO convertToDTO(Documento documento) {
        DocumentoDTO dto = new DocumentoDTO();
        dto.setId(documento.getId());
        dto.setFormato(documento.getFormato());
        //dto.setPath(documento.getPath());
        return dto;
    }

    private Documento convertToEntity(DocumentoDTO dto) {
        Documento documento = new Documento();
        documento.setFormato(dto.getFormato());
       // documento.setPath(dto.getPath());
        return documento;
    }
}