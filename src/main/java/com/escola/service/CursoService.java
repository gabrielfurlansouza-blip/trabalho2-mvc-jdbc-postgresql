package com.escola.service;

import com.escola.model.Aluno;
import com.escola.model.Curso;
import com.escola.repository.AlunoRepository;
import com.escola.repository.CursoRepository;

import java.util.List;
import java.util.Optional;

public class CursoService {

    private final CursoRepository cursoRepository = new CursoRepository();
    private final AlunoRepository alunoRepository = new AlunoRepository();

    public Curso cadastrar(Curso curso) {
        if (curso.getNome() == null || curso.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do curso é obrigatório.");
        }
        if (curso.getVagasTotais() <= 0) {
            throw new IllegalArgumentException("O número de vagas deve ser maior que zero.");
        }
        return cursoRepository.save(curso);
    }

    public Optional<Curso> buscarPorId(Long id) {
        return cursoRepository.findById(id);
    }

    public List<Curso> listarTodos() {
        return cursoRepository.findAll();
    }

    public void atualizar(Curso curso) {
        if (curso.getNome() == null || curso.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do curso é obrigatório.");
        }
        cursoRepository.update(curso);
    }

    public void remover(Long id) {
        cursoRepository.delete(id);
    }

    public List<Aluno> listarAlunosDoCurso(Long idCurso) {
        cursoRepository.findById(idCurso)
                .orElseThrow(() -> new IllegalArgumentException("Curso não encontrado com id: " + idCurso));
        return alunoRepository.findByCurso(idCurso);
    }
}
