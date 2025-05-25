package com.edutech.springboot.crud.edutech_crud.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

}
