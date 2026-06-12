package com.escola.service;

import com.escola.model.Aluno;
import com.escola.model.Curso;
import com.escola.repository.AlunoRepository;
import com.escola.repository.CursoRepository;

import java.util.List;
import java.util.Optional;

public class AlunoService {

    private final AlunoRepository alunoRepository = new AlunoRepository();
    private final CursoRepository cursoRepository = new CursoRepository();

    public Aluno cadastrar(Aluno aluno) {
        if (aluno.getNome() == null || aluno.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do aluno é obrigatório.");
        }
        return alunoRepository.save(aluno);
    }

    public Optional<Aluno> buscarPorId(Long id) {
        return alunoRepository.findById(id);
    }

    public List<Aluno> listarTodos() {
        return alunoRepository.findAll();
    }

    public void atualizar(Aluno aluno) {
        if (aluno.getNome() == null || aluno.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do aluno é obrigatório.");
        }
        alunoRepository.update(aluno);
    }

    public void remover(Long id) {
        alunoRepository.delete(id);
    }

    public List<Curso> listarCursosDoAluno(Long idAluno) {
        alunoRepository.findById(idAluno)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado com id: " + idAluno));
        return cursoRepository.findByAluno(idAluno);
    }
}
