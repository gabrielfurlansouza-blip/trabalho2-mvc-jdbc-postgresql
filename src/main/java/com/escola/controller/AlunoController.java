package com.escola.controller;

import com.escola.model.Aluno;
import com.escola.model.Curso;
import com.escola.service.AlunoService;

import java.util.List;
import java.util.Optional;

public class AlunoController {

    private final AlunoService alunoService = new AlunoService();

    public Aluno cadastrar(String nome, String email, String telefone) {
        Aluno aluno = new Aluno(nome, email, telefone);
        Aluno salvo = alunoService.cadastrar(aluno);
        System.out.println("[OK] Aluno cadastrado: " + salvo);
        return salvo;
    }

    public void buscarPorId(Long id) {
        Optional<Aluno> aluno = alunoService.buscarPorId(id);
        aluno.ifPresentOrElse(
                a -> System.out.println("[OK] Aluno encontrado: " + a),
                () -> System.out.println("[INFO] Aluno com id " + id + " não encontrado.")
        );
    }

    public void listarTodos() {
        List<Aluno> alunos = alunoService.listarTodos();
        System.out.println("=== Alunos Cadastrados (" + alunos.size() + ") ===");
        alunos.forEach(System.out::println);
    }

    public void listarCursosDoAluno(Long idAluno) {
        List<Curso> cursos = alunoService.listarCursosDoAluno(idAluno);
        System.out.println("=== Cursos do Aluno ID " + idAluno + " (" + cursos.size() + ") ===");
        if (cursos.isEmpty()) {
            System.out.println("[INFO] Aluno não está matriculado em nenhum curso.");
        } else {
            cursos.forEach(System.out::println);
        }
    }

    public void atualizar(Long id, String nome, String email, String telefone) {
        Aluno aluno = alunoService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado com id: " + id));
        aluno.setNome(nome);
        aluno.setEmail(email);
        aluno.setTelefone(telefone);
        alunoService.atualizar(aluno);
        System.out.println("[OK] Aluno atualizado: " + aluno);
    }

    public void remover(Long id) {
        alunoService.remover(id);
        System.out.println("[OK] Aluno ID " + id + " removido com sucesso.");
    }
}
