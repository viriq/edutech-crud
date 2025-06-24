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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Proveedores", description = "Operaciones REST relacionadas con Proveedores")
@RestController
@RequestMapping("api/proveedores")
public class ProveedorRestController {

    @Autowired
    private ProveedorService proveedorService;
    
    @Operation(summary = "Obtener lista de proveedores", description = "Devuelve todos los proveedores disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de proveedores retornada correctamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Proveedor.class)))
    @GetMapping
    public List<Proveedor> List(){
        return proveedorService.findByAll();
    }

    @Operation(summary = "Obtener proveedor por ID", description = "Devuelve el detalle de un proveedor específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proveedor encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Proveedor.class))),
        @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> verProveedor(@PathVariable Long id){
        Optional<Proveedor> proveedorOptional = proveedorService.findById(id);
        if (proveedorOptional.isPresent()){
            return ResponseEntity.ok(proveedorOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    
    @Operation(summary = "Ingresar nuevo proveedor", description = "Crea un proveedor con los datos proporcionados")
    @ApiResponse(responseCode = "201", description = "Proveedor creado correctamente",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Proveedor.class)))
    @PostMapping
    public ResponseEntity<Proveedor> crear (@RequestBody Proveedor unProveedor){
        return ResponseEntity.status(HttpStatus.CREATED).body(proveedorService.save(unProveedor));
    }

    @Operation(summary = "Modificar un proveedor", description = "Actualiza los datos de un proveedor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proveedor modificado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Proveedor.class))),
        @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    })
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

    @Operation(summary = "Eliminar un proveedor", description = "Elimina al proveedor especificado por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proveedor eliminado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Proveedor.class))),
        @ApiResponse(responseCode = "404", description = "Proveedor no encontrado")
    })
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
