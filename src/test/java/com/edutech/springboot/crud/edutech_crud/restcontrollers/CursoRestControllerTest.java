package com.edutech.springboot.crud.edutech_crud.restcontrollers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import com.edutech.springboot.crud.edutech_crud.factory.CursoTestFactory;
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
    private CursoTestFactory cursoTestFactory = new CursoTestFactory();

    @Test
    public void listCursosTest() throws Exception {
        when(cursoServiceImpl.findByAll()).thenReturn(cursosLista);
        mockMvc.perform(get("/api/cursos"))
            .andExpect(status().isOk());
    }

    @Test
    public void verCursoTest() throws Exception {
        Long id = 73L;
        Curso curso = cursoTestFactory.defaultCurso(id);
        when(cursoServiceImpl.findById(73L)).thenReturn(Optional.of(curso));
        mockMvc.perform(get("/api/cursos/73"))
            .andExpect(status().isOk());
    }

    @Test
    public void cursoNoExisteTest() throws Exception {
        when(cursoServiceImpl.findById(73L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/cursos/73"))
            .andExpect(status().isNotFound());
    }

    @Test
    public void cursoCrearTest() throws Exception {
        Long id = 45L;
        Curso inputCurso = cursoTestFactory.inputCurso();
        Curso outputCurso = cursoTestFactory.defaultCurso(id);
        when(cursoServiceImpl.save(any(Curso.class))).thenReturn(outputCurso);
        mockMvc.perform(post("/api/cursos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(inputCurso)))
            .andExpect(status().isCreated());
    }

    @Test
    public void cursoModificarTest() throws Exception {
        Long id = 45L;
        Curso existingCurso = cursoTestFactory.defaultCurso(id);
        Curso inputCurso = cursoTestFactory.inputUpdateCurso();
        Curso outputCurso = cursoTestFactory.outputUpdateCurso(id);

        when(cursoServiceImpl.findById(id)).thenReturn(Optional.of(existingCurso));
        when(cursoServiceImpl.save(any(Curso.class))).thenReturn(outputCurso);

        mockMvc.perform(put("/api/cursos/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(inputCurso)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(id))
            .andExpect(jsonPath("$.titulo").value(existingCurso.getTitulo()))
            .andExpect(jsonPath("$.nombreInstructor").value(inputCurso.getNombreInstructor()));
    }

    @Test
    public void cursoModificarNotFoundTest() throws Exception {
        Long id = 97L;
        Curso inputCurso = cursoTestFactory.inputUpdateCurso();

        when(cursoServiceImpl.findById(id)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/cursos/" + id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(inputCurso)))
            .andExpect(status().isNotFound());
    }

    @Test
    public void cursoEliminarTest() throws Exception {
        Long id = 57L;
        Curso outputCurso = cursoTestFactory.defaultCurso(id);

        when(cursoServiceImpl.delete(any(Curso.class))).thenReturn(Optional.of(outputCurso));

        mockMvc.perform(delete("/api/cursos/" + id))
            .andExpect(status().isOk());
    }

    @Test
    public void cursoEliminarNotFoundTest() throws Exception {
        when(cursoServiceImpl.delete(any(Curso.class))).thenReturn(Optional.empty());
        mockMvc.perform(delete("/api/cursos/27"))
            .andExpect(status().isNotFound());
    }

}