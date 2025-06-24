package com.edutech.springboot.crud.edutech_crud.restcontrollers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.edutech.springboot.crud.edutech_crud.entities.Proveedor;
import com.edutech.springboot.crud.edutech_crud.services.ProveedorServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

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
            mockMvc.perform(get("/api/proveedores/1").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        } catch (Exception ex) {
            fail("El testing lanzó un error " + ex.getMessage());
        }
    }

    @Test
    public void proveedorNoExisteTest() throws Exception {
        when(proveedorServiceImpl.findById(10L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/proveedores/10").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound());
    }

    @Test
    public void crearProveedorTest() throws Exception {
        Proveedor unProveedor = new Proveedor(4L, "76.124.876-5", "Muebles para oficina Valledor", "mueblesvalledor@gmail.com", "Venta de muebles");
        Proveedor otroProveedor = new Proveedor(null, "87.324.765-6", "Artículos de oficina López", "articuloslopez@gmail.com", "Venta de artículos de oficina");
        when(proveedorServiceImpl.save(any(Proveedor.class))).thenReturn(otroProveedor);
        mockMvc.perform(post("/api/proveedores").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(unProveedor))).andExpect(status().isCreated());
    }

}
