package com.edutech.springboot.crud.edutech_crud.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edutech.springboot.crud.edutech_crud.entities.Clase;
import com.edutech.springboot.crud.edutech_crud.services.ClaseService;

@RestController
@RequestMapping("api/clases")
public class ClaseRestController {

    @Autowired
    private ClaseService claseService;

    @GetMapping
    public List<Clase> list() {
        return claseService.findByAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> verClase(@PathVariable Long id) {
        Optional<Clase> claseOptional = claseService.findById(id);
        if (claseOptional.isPresent()) {
            return ResponseEntity.ok(claseOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<Clase> crear(@RequestBody Clase clase) {
        return ResponseEntity.status(HttpStatus.CREATED).body(claseService.save(clase));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable long id, @RequestBody Clase clase) {
        Optional<Clase> claseOptional = claseService.findById(id);

        if (claseOptional.isPresent()) {
            Clase claseExistente = claseOptional.get();

            claseExistente.setTitulo(clase.getTitulo());
            claseExistente.setDescripción(clase.getDescripción());
            claseExistente.setNombreInstructor(clase.getNombreInstructor());

            Clase claseModificada = claseService.save(claseExistente);
            return ResponseEntity.ok(claseModificada);
        }

        return ResponseEntity.notFound().build();
    }

}
