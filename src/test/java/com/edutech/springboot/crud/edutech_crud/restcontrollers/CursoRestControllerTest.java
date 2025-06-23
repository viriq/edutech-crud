package com.edutech.springboot.crud.edutech_crud.restcontrollers;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.edutech.springboot.crud.edutech_crud.entities.Curso;
import com.edutech.springboot.crud.edutech_crud.services.CursoServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class CursoRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CursoServiceImpl cursoServiceImpl;

    private List<Curso> cursosLista;

    @Test
    public void listCursosTest() throws Exception {
        when(cursoServiceImpl.findByAll()).thenReturn(cursosLista);
        mockMvc.perform(get("/api/cursos")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void verCursoTest() {
        Curso unCurso = new Curso(73L, "Ciberseguridad Básica", "Curso básico de ciberseguridad y protección de datos personales.", "Fernando Salinas");
        try {
            when(cursoServiceImpl.findById(73L)).thenReturn(Optional.of(unCurso));
            mockMvc.perform(get("/api/cursos/73")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        }
        catch(Exception ex) {
            fail("Test 'verCursoTest' lanzó un error: " + ex.getMessage());
        }
    }


    @Test
    public void cursoNoExisteTest() throws Exception {
        when(cursoServiceImpl.findById(96L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/cursos/73")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void cursoSaveTest() throws Exception {
        Curso inputCurso = new Curso(null, "Arte y Código", "Taller de programación creativa con Python y arte generativo.", "Martín Reyes");
        Curso outputCurso = new Curso(45L, inputCurso.getTitulo(), inputCurso.getDescripción(), inputCurso.getNombreInstructor());
        when(cursoServiceImpl.save(any(Curso.class))).thenReturn(outputCurso);
        mockMvc.perform(post("/api/cursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(inputCurso)))
            .andExpect(status().isCreated());
    }

}
