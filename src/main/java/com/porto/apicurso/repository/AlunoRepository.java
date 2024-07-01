package com.porto.apicurso.repository;

import com.porto.apicurso.model.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    List<Aluno> findByCurso(String curso);
}
