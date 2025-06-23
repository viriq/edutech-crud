package com.edutech.springboot.crud.edutech_crud.restcontrollers;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
    public void verCursoTest() throws Exception {
        Curso unCurso = new Curso(73L, "Ciberseguridad Básica", "Curso básico de ciberseguridad y protección de datos personales.", "Fernando Salinas");
        when(cursoServiceImpl.findById(73L)).thenReturn(Optional.of(unCurso));
        mockMvc.perform(get("/api/cursos/73")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    @Test
    public void cursoNoExisteTest() throws Exception {
        when(cursoServiceImpl.findById(96L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/cursos/73")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void cursoCrearTest() throws Exception {
        Curso inputCurso = new Curso(null, "Arte y Código", "Taller de programación creativa con Python y arte generativo.", "Martín Reyes");
        Curso outputCurso = new Curso(45L, inputCurso.getTitulo(), inputCurso.getDescripción(), inputCurso.getNombreInstructor());
        when(cursoServiceImpl.save(any(Curso.class))).thenReturn(outputCurso);
        mockMvc.perform(post("/api/cursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(inputCurso)))
            .andExpect(status().isCreated());
    }

    @Test
    public void cursoModificarTest() throws Exception {
        Long id = 45L;
        Curso existingCurso = new Curso(id, "Arte y Código", "Taller de programación creativa con Python y arte generativo.", "Martín Reyes");
        Curso inputCurso = new Curso(null, existingCurso.getTitulo(), existingCurso.getDescripción(), "Richard Ericson");
        Curso outputCurso = new Curso(id, inputCurso.getTitulo(), inputCurso.getDescripción(), inputCurso.getNombreInstructor());

        when(cursoServiceImpl.findById(id)).thenReturn(Optional.of(existingCurso));
        when(cursoServiceImpl.save(any(Curso.class))).thenReturn(outputCurso);

        mockMvc.perform(put("/api/cursos/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(inputCurso)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(45L))
            .andExpect(jsonPath("$.titulo").value(existingCurso.getTitulo()))
            .andExpect(jsonPath("$.nombreInstructor").value(inputCurso.getNombreInstructor()));

    }

    @Test
    public void cursoModificarNotFoundTest() throws Exception {
        Long id = 97L;
        Curso inputCurso = new Curso(null, "Arte y Código", "Taller de programación creativa con Python y arte generativo.", "Richard Ericson");

        when(cursoServiceImpl.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/cursos/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(inputCurso)))
            .andExpect(status().isNotFound());
    }

    @Test
    public void cursoEliminarTest() throws Exception {
        Long id = 57L;
        Curso outputCurso = new Curso(id, "Arte y Código", "Taller de programación creativa con Python y arte generativo", "Richard Ericson");

        when(cursoServiceImpl.delete(any(Curso.class))).thenReturn(Optional.of(outputCurso));

        mockMvc.perform(delete("/api/cursos/" + id)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

}