package org.example.dao;

import org.example.model.Aluno;

import java.util.List;

public interface IAlunoDAO {
    void create(Aluno aluno);
    void update(Aluno aluno);
    void delete(Long id);
    List<Aluno> findAll();
    Aluno findById(Long id);
}
