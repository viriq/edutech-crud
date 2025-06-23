package com.edutech.springboot.crud.edutech_crud.factory;

import com.edutech.springboot.crud.edutech_crud.entities.Curso;

public class CursoTestFactory {

    public Curso defaultCurso(Long id) {
        return new Curso(id, "Arte y Código", "Taller de programación creativa con Python y arte generativo.", "Martín Reyes");
    }

    public Curso inputCurso() {
        return defaultCurso(null);
    }

    public Curso inputUpdateCurso() {
        Curso curso = inputCurso();
        curso.setNombreInstructor("Eric Richardson");
        return curso;
    }

    public Curso outputUpdateCurso(Long id) {
        Curso curso = inputUpdateCurso();
        curso.setId(id);
        return curso;
    }

}
