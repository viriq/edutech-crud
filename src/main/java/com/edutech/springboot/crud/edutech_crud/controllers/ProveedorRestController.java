package com.edutech.springboot.crud.edutech_crud.controllers;

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

import com.edutech.springboot.crud.edutech_crud.entities.Proveedor;
import com.edutech.springboot.crud.edutech_crud.services.ProveedorService;

@RestController
@RequestMapping("api/proveedores")
public class ProveedorRestController {

    @Autowired
    private ProveedorService proveedorService;
    
    @GetMapping
    public List<Proveedor> List(){
        return proveedorService.findByAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> verProveedor(@PathVariable Long id){
        Optional<Proveedor> proveedorOptional = proveedorService.findById(id);
        if (proveedorOptional.isPresent()){
            return ResponseEntity.ok(proveedorOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<Proveedor> crear (@RequestBody Proveedor unProveedor){
        return ResponseEntity.status(HttpStatus.CREATED).body(proveedorService.save(unProveedor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody Proveedor unProveedor){
        Optional <Proveedor> proveedorOptional = proveedorService.findById(id);
        if (proveedorOptional.isPresent()){
            Proveedor proveedorExistente = proveedorOptional.get();
            proveedorExistente.setRut(unProveedor.getRut());
            proveedorExistente.setRazonSocial(unProveedor.getRazonSocial());
            proveedorExistente.setEmail(unProveedor.getEmail());
            proveedorExistente.setGiro(unProveedor.getGiro());
            Proveedor proveedorModificado = proveedorService.save(proveedorExistente);
            return ResponseEntity.ok(proveedorModificado);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Proveedor unProveedor = new Proveedor();
        unProveedor.setId(id);
        Optional<Proveedor> proveedorOptional = proveedorService.delete(unProveedor);
        if(proveedorOptional.isPresent()){
            return ResponseEntity.ok(proveedorOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
}
