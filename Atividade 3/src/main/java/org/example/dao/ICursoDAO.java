package org.example.dao;

import org.example.model.Curso;
import java.util.List;

public interface ICursoDAO {
    void create(Curso curso);
    void update(Curso curso);
    void delete(Long codigo);
    List<Curso> findAll();
    Curso findById(Long codigo);
    Curso findBySigla(String sigla);
    List<Curso> findByArea(Curso.Area area);
}