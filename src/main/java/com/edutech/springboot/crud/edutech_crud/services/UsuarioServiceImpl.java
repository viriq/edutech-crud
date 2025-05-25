package com.edutech.springboot.crud.edutech_crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.edutech.springboot.crud.edutech_crud.entities.Usuario;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Override
    public Optional<Usuario> delete(Usuario unUsuario) {
        return Optional.empty();
    }

    @Override
    public List<Usuario> findByAll() {  
        return null;
    }

    @Override
    public Optional<Usuario> findById(Long id) {
                return Optional.empty();
    }

    @Override
    public Usuario save(Usuario unUsuario) {
                return null;
    }
 

}
