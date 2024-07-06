package com.udla.evaluaytor.businessdomain.empresa.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udla.evaluaytor.businessdomain.empresa.dto.CategoriaDTO;
import com.udla.evaluaytor.businessdomain.empresa.dto.MatrizEvaluacionCreateUpdateDTO;
import com.udla.evaluaytor.businessdomain.empresa.dto.MatrizEvaluacionDTO;
import com.udla.evaluaytor.businessdomain.empresa.dto.ProveedorDTO;
import com.udla.evaluaytor.businessdomain.empresa.dto.ProveedorResponseDTO;
import com.udla.evaluaytor.businessdomain.empresa.models.Perito;
import com.udla.evaluaytor.businessdomain.empresa.repositories.CategoriaRepository;
import com.udla.evaluaytor.businessdomain.empresa.repositories.EmpresaRepository;
import com.udla.evaluaytor.businessdomain.empresa.repositories.MatrizEvaluacionRepository;
import com.udla.evaluaytor.businessdomain.empresa.repositories.PeritoRepository;
import com.udla.evaluaytor.businessdomain.empresa.repositories.ProveedorRepository;
import com.udla.evaluaytor.businessdomain.empresa.services.CategoriaService;
import com.udla.evaluaytor.businessdomain.empresa.services.MatrizEvaluacionService;
import com.udla.evaluaytor.businessdomain.empresa.services.ProveedorService;




@RestController
@RequestMapping("/api/empresa")
public class EmpresaController {
    @Autowired
    EmpresaRepository empresaRepository;

    @Autowired
    ProveedorRepository proveedorRepository;

    @Autowired
    PeritoRepository peritoRepository;

    @Autowired 
    CategoriaRepository categoriaRepository;

    @Autowired
    private ProveedorService proveedorService;
 
    @Autowired 
    CategoriaService categoriaService;

    @Autowired
    private MatrizEvaluacionRepository matrizEvaluacionRepository;

    @Autowired
    private MatrizEvaluacionService matrizEvaluacionService;




  /* 
    // Listar todo
    @GetMapping("/findall")
    public List<Empresa> listarEmpresa() {
        return empresaRepository.findAll();
    }


    // Listar por Id
    @GetMapping("/findbyid/{id}")
    public ResponseEntity<Empresa> obtenerEmpresaPorId(@PathVariable Long id) {
        Optional<Empresa> premioOptional = empresaRepository.findById(id);
        return premioOptional.map(premio -> new ResponseEntity<>(premio, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Crear una nueva empresa
    @PostMapping("/save")
    public ResponseEntity<Empresa> crearEmpresa(@RequestBody Empresa nuevoPremio) {
        Empresa premioGuardado = empresaRepository.save(nuevoPremio);
        return new ResponseEntity<>(premioGuardado, HttpStatus.CREATED);
    }

    // Actualizar empresa
    @PutMapping("/updatebyid/{id}")
    public ResponseEntity<Empresa> actualizarEmpresa(@PathVariable Long id, @RequestBody Empresa empresaActual) {
        Optional<Empresa> empresaOptional = empresaRepository.findById(id);
        return empresaOptional.map(empresa -> {
            empresa.setId(id);
            empresa.setNombre(empresaActual.getNombre());
            empresa.setDireccion(empresaActual.getDireccion());
            Empresa empresaActualGuardado = empresaRepository.save(empresa);
            return new ResponseEntity<>(empresaActualGuardado, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Eliminar un empresa por ID
    @DeleteMapping("/deletebyid/{id}")
    public ResponseEntity<Void> eliminarEmpresa(@PathVariable Long id) {
        Optional<Empresa> empresaOptional = empresaRepository.findById(id);
        if (empresaOptional.isPresent()) {
            empresaRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

*/
    // Crear una nuevo proveedor
    @PostMapping("/proveedor/save")
    public ResponseEntity<ProveedorResponseDTO> createProveedor(@RequestBody ProveedorDTO proveedorDTO) {
        ProveedorResponseDTO proveedorGuardado = proveedorService.createProveedor(proveedorDTO);
        return new ResponseEntity<>(proveedorGuardado, HttpStatus.CREATED);
    }
      
    @PutMapping("/proveedor/update/{id}")
    public ResponseEntity<ProveedorResponseDTO> updateProveedor(@PathVariable Long id, @RequestBody ProveedorDTO proveedorUpdateDTO) {
        ProveedorResponseDTO updatedProveedor = proveedorService.updateProveedor(id, proveedorUpdateDTO);
        return ResponseEntity.ok(updatedProveedor);
    }


    @GetMapping("proveedor/findall")
    public ResponseEntity<List<ProveedorResponseDTO>> getAllProveedores() {
        List<ProveedorResponseDTO> proveedores = proveedorService.getAllProveedores();
        return ResponseEntity.ok(proveedores);
    }

    @GetMapping("proveedor/findbyid/{id}")
     public ResponseEntity<ProveedorResponseDTO> getProveedorById(@PathVariable Long id) {
        ProveedorResponseDTO proveedor = proveedorService.getProveedorById(id);
        return ResponseEntity.ok(proveedor);
    }

    /*@GetMapping("perito/findbyid/{id}")
    public ResponseEntity<Perito> getPeritoById(@PathVariable Long id) {
        Optional<Perito> peritoOptional = peritoRepository.findById(id);
        return peritoOptional.map(perito -> new ResponseEntity<>(perito, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("categoria/findbyid/{id}")
    public ResponseEntity<Categoria> getCategoriaById(@PathVariable Long id) {
        Optional<Categoria> categoriaOptional = categoriaRepository.findById(id);
        return categoriaOptional.map(categoria -> new ResponseEntity<>(categoria, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }*/

    //Categorias
 @GetMapping("/categoria/findall")
    public List<CategoriaDTO> getAllCategorias() {
        return categoriaService.getAllCategorias();
    }

    @GetMapping("/categoria/find/{id}")
    public ResponseEntity<CategoriaDTO> getCategoriaById(@PathVariable Long id) {
        CategoriaDTO categoria = categoriaService.getCategoriaById(id);
        if (categoria != null) {
            return ResponseEntity.ok(categoria);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/categoria/create")
    public ResponseEntity<CategoriaDTO> createCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO createdCategoria = categoriaService.createCategoria(categoriaDTO);
        return ResponseEntity.ok(createdCategoria);
    }

    @PutMapping("/categoria/update/{id}")
    public ResponseEntity<CategoriaDTO> updateCategoria(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO) {
        CategoriaDTO updatedCategoria = categoriaService.updateCategoria(id, categoriaDTO);
        if (updatedCategoria != null) {
            return ResponseEntity.ok(updatedCategoria);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //PERITO
@GetMapping("/perito/findall")
    public List<Perito> listarPeritos() {
        return peritoRepository.findAll();
    }

    @GetMapping("/perito/findbyid/{id}")
    public ResponseEntity<Perito> obtenerPeritoPorId(@PathVariable Long id) {
        Optional<Perito> premioOptional = peritoRepository.findById(id);
        return premioOptional.map(premio -> new ResponseEntity<>(premio, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

  
    @PostMapping("/perito/save")
    public ResponseEntity<Perito> crearPerito(@RequestBody Perito nuevoPremio) {
        Perito premioGuardado = peritoRepository.save(nuevoPremio);
        return new ResponseEntity<>(premioGuardado, HttpStatus.CREATED);
    }

    // Actualizar perito
    @PutMapping("/perito/updatebyid/{id}")
    public ResponseEntity<Perito> actualizarPerito(@PathVariable Long id, @RequestBody Perito peritoActual) {
        Optional<Perito> peritoOptional = peritoRepository.findById(id);
        return peritoOptional.map(perito -> {
            perito.setNombre(peritoActual.getNombre());
            perito.setDireccion(peritoActual.getDireccion());
            perito.setTelefono(peritoActual.getTelefono());
            Perito peritoActualizado = peritoRepository.save(perito);
            return new ResponseEntity<>(peritoActualizado, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Eliminar un perito por ID
    @DeleteMapping("/perito/deletebyid/{id}")
    public ResponseEntity<Void> eliminarPerito(@PathVariable Long id) {
        Optional<Perito> peritoOptional = peritoRepository.findById(id);
        if (peritoOptional.isPresent()) {
            peritoRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //MATRIZ EVALUACIÓN

    @GetMapping("/matrizevaluacion/findall")
    public List<MatrizEvaluacionDTO> getAllMatricesEvaluacion() {
        return matrizEvaluacionService.getAllMatricesEvaluacion();
    }

    @GetMapping("/matrizevaluacion/find/{id}")
    public ResponseEntity<MatrizEvaluacionDTO> getMatrizEvaluacionById(@PathVariable Long id) {
        MatrizEvaluacionDTO matriz = matrizEvaluacionService.getMatrizEvaluacionById(id);
        if (matriz != null) {
            return ResponseEntity.ok(matriz);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

     @PostMapping("/matrizevaluacion/create")
    public ResponseEntity<MatrizEvaluacionDTO> createMatrizEvaluacion(@RequestBody MatrizEvaluacionCreateUpdateDTO matrizEvaluacionDTO) {
        MatrizEvaluacionDTO createdMatriz = matrizEvaluacionService.createMatrizEvaluacion(matrizEvaluacionDTO);
        return ResponseEntity.ok(createdMatriz);
    }

    @PutMapping("/matrizevaluacion/update/{id}")
    public ResponseEntity<MatrizEvaluacionDTO> updateMatrizEvaluacion(@PathVariable Long id, @RequestBody MatrizEvaluacionCreateUpdateDTO matrizEvaluacionDTO) {
        MatrizEvaluacionDTO updatedMatriz = matrizEvaluacionService.updateMatrizEvaluacion(id, matrizEvaluacionDTO);
        if (updatedMatriz != null) {
            return ResponseEntity.ok(updatedMatriz);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

