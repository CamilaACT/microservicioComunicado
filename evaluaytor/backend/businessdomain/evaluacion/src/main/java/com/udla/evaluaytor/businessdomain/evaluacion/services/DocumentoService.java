package com.udla.evaluaytor.businessdomain.evaluacion.services;

import java.util.List;

import com.udla.evaluaytor.businessdomain.evaluacion.dto.DocumentoDTO;

public interface DocumentoService {

List<DocumentoDTO> getAllDocumentos();
    DocumentoDTO getDocumentoById(Long id);
    DocumentoDTO createDocumento(DocumentoDTO documentoDTO);
    DocumentoDTO updateDocumento(Long id, DocumentoDTO documentoDTO);
    void deleteDocumento(Long id);

}
