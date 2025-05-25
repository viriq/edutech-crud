package com.edutech.springboot.crud.edutech_crud.services;

import java.util.List;
import java.util.Optional;

import com.edutech.springboot.crud.edutech_crud.entities.Curso;

public interface CursoService {

    List<Curso> findByAll();

    Optional<Curso> findById(Long id);

    Curso save(Curso unCurso);

    Optional<Curso> delete(Curso unCurso);

}
