package com.edutech.springboot.crud.edutech_crud.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.edutech.springboot.crud.edutech_crud.entities.Curso;
import com.edutech.springboot.crud.edutech_crud.repository.CursoRepository;

public class CursoServiceImplTest {

    @InjectMocks
    private CursoServiceImpl cursoService;

    @Mock
    private CursoRepository cursoRepository;

    List<Curso> cursoList = new ArrayList<Curso>();

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        chargeCursos();
    }

    @Test
    public void findByAllTest() {
        when(cursoRepository.findAll()).thenReturn(cursoList);
        List<Curso> response = cursoService.findByAll();
        // esperamos que traiga los 3 resultados que se ingresaron en chargeCursos()
        assertEquals(3, response.size());
        // verificar que el método cursoRepository.findAll() se haya llamado
        verify(cursoRepository, times(1)).findAll();
    }

    @Test
    public void findByIdTest() {
        // inicializa una instancia de Curso para esta prueba
        Long id = 25L;

        String titulo = "Programación Avanzada";
        String descripcion = "Curso sobre conceptos avanzados de programación en Java";
        String nombreInstructor = "Carlos Méndez";

        Curso unCurso = new Curso(id, titulo, descripcion, nombreInstructor);

        // mockea el repositorio para que retorne el curso con la ID correspondiente
        when(cursoRepository.findById(id)).thenReturn(Optional.of(unCurso));

        // verifica que la respuesta sea correcta

        Optional<Curso> respuesta = cursoService.findById(id);
        assertTrue(respuesta.isPresent());  // existe?
        assertEquals(id, respuesta.get().getId());  // misma id?

        // verificar que el método cursoRepository.findById() se haya llamado
        verify(cursoRepository, times(1)).findById(id);
    }

    @Test
    public void findByIdNotFoundTest() {
        // mockea el repositorio para que retorne un Optional vacio
        Long id = 47L;
        when(cursoRepository.findById(id)).thenReturn(Optional.empty());

        // verifica que la respuesta sea correcta

        Optional<Curso> respuesta = cursoService.findById(id);
        assertTrue(respuesta.isEmpty());

        // verificar que el método cursoRepository.findById() se haya llamado
        verify(cursoRepository, times(1)).findById(id);
    }

    @Test
    public void saveTest() {
        // inicializa las instancias de Curso para esta prueba
        // la idea: se ingresa un curso sin ID (enviado por el cliente, por ejemplo), y se retorna el mismo curso con una ID asignada
        Curso inputCurso = new Curso(null, "Arte y Código", "Taller de programación creativa con Python y arte generativo.", "Martín Reyes");
        Curso outputCurso = new Curso(45L, inputCurso.getTitulo(), inputCurso.getDescripción(), inputCurso.getNombreInstructor());

        // mockea el repositodio para que, al ingresar inputCurso, retorne outputCurso
        when(cursoRepository.save(inputCurso)).thenReturn(outputCurso);

        // verifica que la respuesta sea correcta
        Curso respuesta = cursoService.save(inputCurso);
        assertNotNull(respuesta);  // objeto existe
        assertNotNull(respuesta.getTitulo());  // objeto tiene el mismo título
        assertEquals(outputCurso.getId(), respuesta.getId());  // objeto tiene ID

        // verificar que el método cursoRepository.save() se haya llamado
        verify(cursoRepository, times(1)).save(inputCurso);
    }

    public void chargeCursos() {
        cursoList.addAll(List.of(
            new Curso(14l, "Bases de Datos", "Curso introductorio sobre bases de datos relacionales.", "Laura Gómez"),
            new Curso(47l, "Desarrollo Web Frontend", "Aprende los fundamentos del desarrollo web moderno.", "Carlos Muñoz"),
            new Curso(92l, "Edición de Audio Digital", "Curso práctico de edición de audio digital.", "Ana Pereira")
        ));
    }

}
