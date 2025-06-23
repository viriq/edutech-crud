package com.edutech.springboot.crud.edutech_crud.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import com.edutech.springboot.crud.edutech_crud.entities.Usuario;
import com.edutech.springboot.crud.edutech_crud.services.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Usuarios", description = "Operaciones REST relacionadas con Usuarios")
@RestController
@RequestMapping("api/usuarios")
public class UsuarioRestController {

    @Autowired
    private UsuarioService usuarioservice;

    @Operation(summary = "Obtener lista de usuarios", description = "Devuelve todos los usuarios disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios retornada correctamente",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class)))
    @GetMapping
    public List<Usuario> List(){
        return usuarioservice.findByAll();
    }

    @Operation(summary = "Obtener usuario por ID", description = "Devuelve el detalle de un usuario específico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> verUsuario(@PathVariable Long id){
        Optional<Usuario> userOpcional = usuarioservice.findById(id);
        if (userOpcional.isPresent()){
            return ResponseEntity.ok(userOpcional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @Operation(summary = "Crear un nuevo usuario", description = "Crea un usuario con los datos proporcionados")
    @ApiResponse(responseCode = "201", description = "Usuario creado correctamente",
        content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class)))
    @PostMapping
    public ResponseEntity<Usuario> crear (@RequestBody Usuario unUsuario){
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioservice.save(unUsuario));
    }

    @Operation(summary = "Modificar un usuario", description = "Actualiza los datos del usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario modificado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}")  
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody Usuario unUsuario){
        Optional<Usuario> userOpcional = usuarioservice.findById(id);
        if (userOpcional.isPresent()){
            Usuario usuarioexistente = userOpcional.get();
            usuarioexistente.setNombreCompleto(unUsuario.getNombreCompleto());
            usuarioexistente.setEmail(unUsuario.getEmail());
            usuarioexistente.setContrasena(unUsuario.getContrasena());
            usuarioexistente.setTipoUsuario(unUsuario.getTipoUsuario());
            Usuario usuariomodificado = usuarioservice.save(usuarioexistente);
            return ResponseEntity.ok(usuariomodificado);
        }
        return ResponseEntity.notFound().build();

    }

    @Operation(summary = "Eliminar un usuario", description = "Elimina al usuario especificado por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario eliminado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar (@PathVariable Long id){
        Usuario unUsuario = new Usuario();
        unUsuario.setId(id);
        Optional<Usuario> userOpcional = usuarioservice.delete(unUsuario);
        if (userOpcional.isPresent()){
            return ResponseEntity.ok(userOpcional.orElseThrow());
        }
        return ResponseEntity.notFound().build();

    }

}
