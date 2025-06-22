package com.edutech.springboot.crud.edutech_crud.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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
        // verificamos que se llame al método findByAll una sola vez
        verify(cursoRepository, times(1)).findAll();
    }

    public void chargeCursos() {
        cursoList.addAll(List.of(
            new Curso(14l, "Bases de Datos", "Curso introductorio sobre bases de datos relacionales.", "Laura Gómez"),
            new Curso(47l, "Desarrollo Web Frontend", "Aprende los fundamentos del desarrollo web moderno.", "Carlos Muñoz"),
            new Curso(92l, "Edición de Audio Digital", "Curso práctico de edición de audio digital.", "Ana Pereira")
        ));
    }

}
