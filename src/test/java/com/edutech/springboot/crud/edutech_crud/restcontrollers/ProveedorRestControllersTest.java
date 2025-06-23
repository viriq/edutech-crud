package com.edutech.springboot.crud.edutech_crud.restcontrollers;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.edutech.springboot.crud.edutech_crud.entities.Proveedor;
import com.edutech.springboot.crud.edutech_crud.services.ProveedorServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class ProveedorRestControllersTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private  ProveedorServiceImpl proveedorServiceImpl;

    private List<Proveedor> proveedorList;

    @Test
    public void verProveedoresTest() throws Exception {
        when(proveedorServiceImpl.findByAll()).thenReturn(proveedorList);
    }

    @Test
    public void verunProveedorTest() {
        Proveedor unProveedor = new Proveedor(1L, "76.124.876-5", "Muebles para oficina Valledor", "mueblesvalledor@gmail.com", "Venta de muebles");
        try {
            when(proveedorServiceImpl.findById(1L)).thenReturn(Optional.of(unProveedor));
            mockMvc.perform(get("/api/proveedor/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        } catch (Exception ex) {
            fail("El testing lanzó un error " + ex.getMessage());
        }
    }
}
