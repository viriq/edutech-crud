package com.edutech.springboot.crud.edutech_crud.restcontrollers;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import javax.print.attribute.standard.Media;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.mockito.ArgumentMatchers.any;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.edutech.springboot.crud.edutech_crud.entities.Curso;
import com.edutech.springboot.crud.edutech_crud.entities.Usuario;
import com.edutech.springboot.crud.edutech_crud.services.UsuarioServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc

public class UsuarioRestControllersTest {


    @Autowired
    private MockMvc mockmvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private UsuarioServiceImpl usuarioserviceimpl;

    private List<Usuario> usuarioLista;  

    @Test
    public void verUsuariosTest() throws Exception{
        when(usuarioserviceimpl.findByAll()).thenReturn(usuarioLista);
        mockmvc.perform(get("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void verunUsuarioTest(){
        Usuario unUsuario = new Usuario(1L, "Maria Gonzales","maria.gonza@gmail.com", "1234Segura!", "Estudiante");
        try{
            when(usuarioserviceimpl.findById(1L)).thenReturn(Optional.of(unUsuario));
            mockmvc.perform(get("/api/usuarios/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
                
        }
        catch (Exception ex){
            fail("El testing lanzó un error" + ex.getMessage());
        }

    }

    @Test
    public void usuarioNoExisteTest() throws Exception{
        when(usuarioserviceimpl.findById(10L)).thenReturn(Optional.empty());
        mockmvc.perform(get("/api/usuarios/10")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void crearUsuarioTest() throws Exception{
        Usuario unUsuario = new Usuario(null, "Oliver Sykes", "bmthsucks@gmail.com", "1tn3v3r3nd5", "Profesor");
        Usuario otroUsuario = new Usuario(4L, "Matty Healy", "1975sucks@gmail.com", "l0v1ngs0m30n3", "Estudiante"); 
        when(usuarioserviceimpl.save(any(Usuario.class))).thenReturn(otroUsuario);
        mockmvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unUsuario)))
                .andExpect(status().isCreated());
    }

    @Test
    public void listUsuariosTest() throws Exception {
        when(usuarioserviceimpl.findByAll()).thenReturn(usuarioLista);
        mockmvc.perform(get("/api/usuarios"))
            .andExpect(status().isOk());
    }

    @Test
    public void usuarioModificarTest() throws Exception {
        Usuario existingUsuario = new Usuario(22L, "Felipe Perez", "fepe@live.cl", "fepe123", "Estudiante");
        Usuario unUsuario = new Usuario(null, existingUsuario.getNombreCompleto(), existingUsuario.getEmail(), existingUsuario.getContrasena(), existingUsuario.getTipoUsuario());
        Usuario otroUsuario = new Usuario(22L, unUsuario.getNombreCompleto(), unUsuario.getEmail(), unUsuario.getContrasena(), "Profesor");
        when(usuarioserviceimpl.findById(22L)).thenReturn(Optional.of(existingUsuario));
        when(usuarioserviceimpl.save(any(Usuario.class))).thenReturn(otroUsuario);

        mockmvc.perform(put("/api/usuarios/22")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(unUsuario)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(22L))
            .andExpect(jsonPath("$.nombreCompleto").value(existingUsuario.getNombreCompleto()))
            .andExpect(jsonPath("$.tipoUsuario").value(otroUsuario.getTipoUsuario()));
    }


    @Test
    public void usuarioModificarNoEncontradoTest() throws Exception {
        Usuario unUsuario = new Usuario(23L, "Felipe Perez", "fepe@live.cl", "fepe123", "Profesor");

        when(usuarioserviceimpl.findById(null)).thenReturn(Optional.empty());

        mockmvc.perform(put("/api/usuarios/23")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unUsuario)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void usuarioDeleteTest() throws Exception{
        Usuario otroUsuario = new Usuario(24L, "Felipe Perez", "fepe@live.cl", "fepe123", "Profesor");

        when(usuarioserviceimpl.delete(any(Usuario.class))).thenReturn(Optional.of(otroUsuario));

        mockmvc.perform(delete("/api/usuarios/24"))
                .andExpect(status().isOk());

    }

    @Test
    public void usuarioDeleteNoEncontrado() throws Exception{

        when(usuarioserviceimpl.delete(any(Usuario.class))).thenReturn(Optional.empty());
        mockmvc.perform(delete("/api/usuarios/78"))
        .andExpect(status().isNotFound());
    }





    



   
    



}
