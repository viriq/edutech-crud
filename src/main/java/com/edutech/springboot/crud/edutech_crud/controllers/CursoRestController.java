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

import com.edutech.springboot.crud.edutech_crud.entities.Curso;
import com.edutech.springboot.crud.edutech_crud.services.CursoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Cursos", description = "Operaciones REST relacionadas con Cursos")
@RestController
@RequestMapping("api/cursos")
public class CursoRestController {

    @Autowired
    private CursoService cursoService;

    @Operation(summary = "Obtener lista de cursos", description = "Devuelve todos los cursos disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de cursos retornada correctamente",
        content = @Content(mediaType = "application/json",
        schema = @Schema(implementation = Curso.class)))
    @GetMapping
    public List<Curso> list() {
        return cursoService.findByAll();
    }

    @Operation(summary = "Obtener curso por ID", description = "Devuelve el detalle de un curso específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Curso encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Curso.class))),
        @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> verCurso(@PathVariable Long id) {
        Optional<Curso> cursoOptional = cursoService.findById(id);
        if (cursoOptional.isPresent()) {
            return ResponseEntity.ok(cursoOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }
    
    @Operation(summary = "Crear un nuevo curso", description = "Crea un curso con los datos proporcionados")
    @ApiResponse(responseCode = "201", description = "Curso creado correctamente",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Curso.class)))
    @PostMapping
    public ResponseEntity<Curso> crear(@RequestBody Curso curso) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.save(curso));
    }

    @Operation(summary = "Modificar un curso", description = "Actualiza los datos del curso con datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Curso modificado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Curso.class))),
        @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    })
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

    @Operation(summary = "Eliminar un curso", description = "Elimina un curso específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Curso eliminado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Curso.class))),
        @ApiResponse(responseCode = "404", description = "Curso no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable long id) {
        Curso unCurso = new Curso();
        unCurso.setId(id);
        Optional<Curso> optionalCurso = cursoService.delete(unCurso);
        if (optionalCurso.isPresent()) {
            return ResponseEntity.ok(optionalCurso.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

}
