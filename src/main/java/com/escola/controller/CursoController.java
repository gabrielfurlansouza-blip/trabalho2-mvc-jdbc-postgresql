package com.escola.controller;

import com.escola.model.Aluno;
import com.escola.model.Curso;
import com.escola.service.CursoService;

import java.util.List;
import java.util.Optional;

public class CursoController {

    private final CursoService cursoService = new CursoService();

    public Curso cadastrar(String nome, String descricao, int cargaHoraria, int vagas) {
        Curso curso = new Curso(nome, descricao, cargaHoraria, vagas);
        Curso salvo = cursoService.cadastrar(curso);
        System.out.println("[OK] Curso cadastrado: " + salvo);
        return salvo;
    }

    public void buscarPorId(Long id) {
        Optional<Curso> curso = cursoService.buscarPorId(id);
        curso.ifPresentOrElse(
                c -> System.out.println("[OK] Curso encontrado: " + c),
                () -> System.out.println("[INFO] Curso com id " + id + " não encontrado.")
        );
    }

    public void listarTodos() {
        List<Curso> cursos = cursoService.listarTodos();
        System.out.println("=== Cursos Cadastrados (" + cursos.size() + ") ===");
        cursos.forEach(System.out::println);
    }

    public void listarAlunosDoCurso(Long idCurso) {
        List<Aluno> alunos = cursoService.listarAlunosDoCurso(idCurso);
        System.out.println("=== Alunos do Curso ID " + idCurso + " (" + alunos.size() + ") ===");
        if (alunos.isEmpty()) {
            System.out.println("[INFO] Nenhum aluno matriculado neste curso.");
        } else {
            alunos.forEach(System.out::println);
        }
    }
}
