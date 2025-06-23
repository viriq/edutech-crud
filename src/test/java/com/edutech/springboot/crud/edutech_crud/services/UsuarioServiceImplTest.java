package com.edutech.springboot.crud.edutech_crud.services;

import static org.junit.jupiter.api.Assertions.*;
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
import com.edutech.springboot.crud.edutech_crud.entities.Usuario;
import com.edutech.springboot.crud.edutech_crud.repository.UsuarioRepository;

public class UsuarioServiceImplTest {

    @InjectMocks
    private UsuarioServiceImpl service;


    @Mock
    private UsuarioRepository repository;

    List<Usuario> list = new ArrayList<Usuario>();


    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);

        this.chargeUsuario();
    }

    @Test 
    public void findByAllTest(){
        when (repository.findAll()).thenReturn(list);
        List<Usuario> response = service.findByAll();
        assertEquals(3, response.size());
        verify(repository, times(1)).findAll();
    }


    public void chargeUsuario(){
        Usuario user1 = new Usuario(1L, "Maria Gonzales","maria.gonza@gmail.com", "1234Segura!", "Estudiante");
        Usuario user2 = new Usuario(2L, "Carlos Pérez", "carlos.perez@outlook.com", "Carlos#2024", "Profesor");
        Usuario user3 = new Usuario(3L, "Ana Torres", "ana.torres@adminedutech.com", "AnaAdmin!88", "Admin");

        list.add(user1);
        list.add(user2);
        list.add(user3);
        
    }

    @Test 
    public void findByIdTest() {
        Long id = 88L;
        String nombreCompleto = "Francisco Rojas";
        String email = "fran.rojas@outlook.cl";
        String contrasena = "fr4ncisc0";
        String tipoUsuario = "Estudiante";

        Usuario unUsuario = new Usuario(id, nombreCompleto, email, contrasena, tipoUsuario);

        when(repository.findById(id)).thenReturn(Optional.of(unUsuario));
        Optional<Usuario> response = service.findById(id);
        assertTrue(response.isPresent());
        assertEquals(id, response.get().getId());

        verify(repository, times(1)).findById(id);

    }

    @Test
    public void findByIdNoEncontrado(){
        Long id = 33L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        Optional<Usuario> response = service.findById(id);
        assertTrue(response.isEmpty());

        verify(repository, times(1)).findById(id);

    }

    @Test
    public void saveTest(){
        Usuario unUsuario = new Usuario(null, "Juana Contreras", "jua.con@gmail.com", "jks466301js", "Profesora");
        Usuario otroUsuario = new Usuario(31L, unUsuario.getNombreCompleto(), unUsuario.getEmail(), unUsuario.getContrasena(), unUsuario.getTipoUsuario());

        when(repository.save(unUsuario)).thenReturn(otroUsuario);

        Usuario response = service.save(unUsuario);
        assertNotNull(response);
        assertNotNull(response.getNombreCompleto());
        assertEquals(otroUsuario.getId(), response.getId());

        verify(repository, times(1)).save(unUsuario);
        
    }


    @Test
    public void eliminarTest(){
        Usuario unUsuario = new Usuario(9L, "Javier Altamirano", "altamicrack@udech.cl", "theonepieceisreal", "Estudiante");

        when(repository.findById(unUsuario.getId())).thenReturn(Optional.of(unUsuario));

        Optional<Usuario> response = service.delete(unUsuario);
        assertTrue(response.isPresent());
        assertEquals(unUsuario.getId(), response.get().getId());

        verify(repository, times(1)).findById(unUsuario.getId());
        verify(repository, times(1)).delete(unUsuario);

    }










    






}
