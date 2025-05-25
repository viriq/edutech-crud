package com.edutech.springboot.crud.edutech_crud.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edutech.springboot.crud.edutech_crud.entities.Curso;
import com.edutech.springboot.crud.edutech_crud.repository.CursoRepository;

@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    CursoRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> findByAll() {
        return (List<Curso>)repository.findAll();
    }

    @Override
    @Transactional
    public Optional<Curso> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Curso save(Curso unCurso) {
        return repository.save(unCurso);
    }

    @Override
    @Transactional
    public Optional<Curso> delete(Curso unCurso) {
        Optional<Curso> cursoOptional = repository.findById(unCurso.getId());
        cursoOptional.ifPresent(productoDb -> {
            repository.delete(unCurso);
        });
        return cursoOptional;
    }

}
