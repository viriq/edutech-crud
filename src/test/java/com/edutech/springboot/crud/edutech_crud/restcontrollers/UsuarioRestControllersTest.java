package com.edutech.springboot.crud.edutech_crud.restcontrollers;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.mockito.ArgumentMatchers.any;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

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
    

}
