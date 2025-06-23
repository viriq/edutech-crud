package com.edutech.springboot.crud.edutech_crud.services;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.edutech.springboot.crud.edutech_crud.entities.Proveedor;
import com.edutech.springboot.crud.edutech_crud.repository.ProveedorRepository;

public class ProveedorServiceImplTest {

    @InjectMocks
    private ProveedorServiceImpl service;

    @Mock
    private ProveedorRepository repository;

    List<Proveedor> list = new ArrayList<>();

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);

        this.chargeProveedor();
    }

    @Test
    public void findByAllTest() {

        when(repository.findAll()).thenReturn(list);

        List<Proveedor> response = service.findByAll();

        assertEquals(3, response.size());

        verify(repository, times(1)).findAll();
    }

    public void chargeProveedor() {
        Proveedor prov1 = new Proveedor(Long.valueOf(1), "76.124.876-5", "Muebles para oficina Valledor", "mueblesvalledor@gmail.com", "Venta de muebles");
        Proveedor prov2 = new Proveedor(Long.valueOf(2), "87.324.765-6", "Artículos de oficina López", "articuloslopez@gmail.com", "Venta de artículos de oficina");
        Proveedor prov3 = new Proveedor(Long.valueOf(3), "84.876.987-4", "Impresoras de Juanito", "juanitoimpresoras@gmail.com", "Venta de impresoras");

        list.add(prov1);
        list.add(prov2);
        list.add(prov3);
    }
}
