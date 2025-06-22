package com.edutech.springboot.crud.edutech_crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edutech.springboot.crud.edutech_crud.entities.Usuario;
import com.edutech.springboot.crud.edutech_crud.repository.UsuarioRepository;



@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository repository;


    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findByAll() {  
        return (List<Usuario>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findById(Long id) {
                return repository.findById(id);
    }



    @Override
    @Transactional
    public Usuario save(Usuario unUsuario) {
                return repository.save(unUsuario);
    }

    @Override
    @Transactional
    public Optional<Usuario> delete(Usuario unUsuario) {
        Optional<Usuario> userOpcional = repository.findById(unUsuario.getId());
        userOpcional.ifPresent(usuarioDb->{
            repository.delete(usuarioDb);
        });
        return userOpcional;
    }


 

}
