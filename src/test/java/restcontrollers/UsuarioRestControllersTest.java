package restcontrollers;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import javax.print.attribute.standard.Media;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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

    









}
