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

@RestController
@RequestMapping("api/usuarios")
public class UsuarioRestController {

    @Autowired
    private UsuarioService usuarioservice;

    @GetMapping
    public List<Usuario> List(){
        return usuarioservice.findByAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> verUsuario(@PathVariable Long id){
        Optional<Usuario> userOpcional = usuarioservice.findById(id);
        if (userOpcional.isPresent()){
            return ResponseEntity.ok(userOpcional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
        public ResponseEntity<Usuario> crear (@RequestBody Usuario unUsuario){
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioservice.save(unUsuario));
        }

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
