package com.edutech.springboot.crud.edutech_crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edutech.springboot.crud.edutech_crud.entities.Clase;
import com.edutech.springboot.crud.edutech_crud.repository.ClaseRepository;

@Service
public class ClaseServiceImpl implements ClaseService {

    @Autowired
    ClaseRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Clase> findByAll() {
        return (List<Clase>)repository.findAll();
    }

    @Override
    @Transactional
    public Optional<Clase> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Clase save(Clase unaClase) {
        return repository.save(unaClase);
    }

    @Override
    @Transactional
    public Optional<Clase> delete(Clase unaClase) {
        Optional<Clase> claseOptional = repository.findById(unaClase.getId());
        claseOptional.ifPresent(productoDb -> {
            repository.delete(unaClase);
        });
        return claseOptional;
    }

}
