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

import com.edutech.springboot.crud.edutech_crud.entities.Curso;
import com.edutech.springboot.crud.edutech_crud.services.CursoService;

@RestController
@RequestMapping("api/cursos")
public class CursoRestController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    public List<Curso> list() {
        return cursoService.findByAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> verCurso(@PathVariable Long id) {
        Optional<Curso> cursoOptional = cursoService.findById(id);
        if (cursoOptional.isPresent()) {
            return ResponseEntity.ok(cursoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping
    public ResponseEntity<Curso> crear(@RequestBody Curso curso) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.save(curso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable long id, @RequestBody Curso curso) {
        Optional<Curso> cursoOptional = cursoService.findById(id);

        if (cursoOptional.isPresent()) {
            Curso cursoExistente = cursoOptional.get();

            cursoExistente.setTitulo(curso.getTitulo());
            cursoExistente.setDescripción(curso.getDescripción());
            cursoExistente.setNombreInstructor(curso.getNombreInstructor());

            Curso cursoModificada = cursoService.save(cursoExistente);
            return ResponseEntity.ok(cursoModificada);
        }

        return ResponseEntity.notFound().build();
    }

}
