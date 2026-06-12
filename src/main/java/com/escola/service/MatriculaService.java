package com.escola.service;

import com.escola.model.Matricula;
import com.escola.repository.AlunoRepository;
import com.escola.repository.CursoRepository;
import com.escola.repository.MatriculaRepository;

import java.util.List;
import java.util.Optional;

public class MatriculaService {

    private final MatriculaRepository matriculaRepository = new MatriculaRepository();
    private final AlunoRepository alunoRepository = new AlunoRepository();
    private final CursoRepository cursoRepository = new CursoRepository();

    public Matricula matricular(Matricula matricula) {
        // Regra: aluno deve estar cadastrado
        alunoRepository.findById(matricula.getIdAluno())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Aluno com id " + matricula.getIdAluno() + " não está cadastrado."));

        // Regra: curso deve estar cadastrado
        var curso = cursoRepository.findById(matricula.getIdCurso())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Curso com id " + matricula.getIdCurso() + " não está cadastrado."));

        // Regra: não pode matricular o mesmo aluno duas vezes no mesmo curso
        if (matriculaRepository.existsByAlunoAndCurso(matricula.getIdAluno(), matricula.getIdCurso())) {
            throw new IllegalArgumentException(
                    "Aluno já está matriculado neste curso.");
        }

        // Regra: curso não pode ter vagas esgotadas
        if (curso.getVagasDisponiveis() <= 0) {
            throw new IllegalArgumentException(
                    "Curso sem vagas disponíveis. Vagas: " + curso.getVagasDisponiveis());
        }

        // Regra: valor não pode ser negativo
        if (matricula.getValor() < 0) {
            throw new IllegalArgumentException("O valor da matrícula não pode ser negativo.");
        }

        Matricula salva = matriculaRepository.save(matricula);
        cursoRepository.decrementarVaga(matricula.getIdCurso());
        return salva;
    }

    public Optional<Matricula> buscarPorId(Long id) {
        return matriculaRepository.findById(id);
    }

    public List<Matricula> listarTodas() {
        return matriculaRepository.findAll();
    }

    public void cancelar(Long id) {
        Matricula m = matriculaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Matrícula não encontrada com id: " + id));
        matriculaRepository.delete(id);
        cursoRepository.incrementarVaga(m.getIdCurso());
    }
}
