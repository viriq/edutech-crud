package com.edutech.springboot.crud.edutech_crud.services;

import java.util.List;
import java.util.Optional;

import com.edutech.springboot.crud.edutech_crud.entities.Clase;

public interface ClaseService {

    List<Clase> findByAll();

    Optional<Clase> findById(Long id);

    Clase save(Clase unaClase);

    Optional<Clase> delete(Clase unaClase);

}
