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
import com.edutech.springboot.crud.edutech_crud.factory.CursoTestFactory;
import com.edutech.springboot.crud.edutech_crud.repository.CursoRepository;

public class CursoServiceImplTest {

    @InjectMocks
    private CursoServiceImpl cursoService;

    @Mock
    private CursoRepository cursoRepository;

    List<Curso> cursoList = new ArrayList<Curso>();
    CursoTestFactory cursoTestFactory = new CursoTestFactory();

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
        Curso curso = cursoTestFactory.defaultCurso(id);

        // mockea el repositorio para que retorne el curso con la ID correspondiente
        when(cursoRepository.findById(id)).thenReturn(Optional.of(curso));

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
        Long id = 45L;
        Curso inputCurso = cursoTestFactory.inputCurso();
        Curso outputCurso = cursoTestFactory.defaultCurso(id);
        
        // mockea el repositodio para que, al ingresar inputCurso, retorne outputCurso
        when(cursoRepository.save(inputCurso)).thenReturn(outputCurso);

        // verifica que la respuesta sea correcta
        Curso respuesta = cursoService.save(inputCurso);
        assertNotNull(respuesta);  // objeto existe
        assertNotNull(respuesta.getTitulo());  // objeto tiene un título
        assertEquals(id, respuesta.getId());  // objeto tiene ID

        // verifica que el método cursoRepository.save() se haya llamado
        verify(cursoRepository, times(1)).save(inputCurso);
    }

    @Test
    public void deleteTest() {
        // inicializa una instancia de Curso para esta prueba
        Long id = 97L;
        Curso curso = cursoTestFactory.defaultCurso(id);

        // mockea el repositorio para que retorne la misma instancia de Curso, dentro de un Optional
        when(cursoRepository.findById(id)).thenReturn(Optional.of(curso));

        // verifica que la respuesta sea correcta
        Optional<Curso> respuesta = cursoService.delete(curso);
        assertTrue(respuesta.isPresent());  // objeto existe
        assertEquals(id, respuesta.get().getId());  // objeto tiene la misma ID

        // verifica que los métodos se hayan llamado en cursoRepository
        verify(cursoRepository, times(1)).findById(id);
        verify(cursoRepository, times(1)).delete(curso);
    }

    public void chargeCursos() {
        cursoList.addAll(List.of(
            new Curso(14L, "Bases de Datos", "Curso introductorio sobre bases de datos relacionales.", "Laura Gómez"),
            new Curso(47L, "Desarrollo Web Frontend", "Aprende los fundamentos del desarrollo web moderno.", "Carlos Muñoz"),
            new Curso(92L, "Edición de Audio Digital", "Curso práctico de edición de audio digital.", "Ana Pereira")
        ));
    }

}
