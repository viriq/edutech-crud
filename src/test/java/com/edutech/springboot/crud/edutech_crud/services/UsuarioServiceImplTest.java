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

    






}
