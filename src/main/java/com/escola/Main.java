package com.escola;

import com.escola.controller.AlunoController;
import com.escola.controller.CursoController;
import com.escola.controller.MatriculaController;
import com.escola.model.Aluno;
import com.escola.model.Curso;
import com.escola.model.Matricula;

public class Main {

    public static void main(String[] args) {
        AlunoController alunoController = new AlunoController();
        CursoController cursoController = new CursoController();
        MatriculaController matriculaController = new MatriculaController();

        System.out.println("============================================");
        System.out.println("   SISTEMA DE ESCOLA DE CURSOS LIVRES");
        System.out.println("============================================\n");

        // -----------------------------------------------
        // FLUXO: aluno → curso → matrícula
        // -----------------------------------------------

        // 1. Cadastrar Alunos
        System.out.println("--- [1] Cadastrando Alunos ---");
        Aluno aluno1 = alunoController.cadastrar("Lucas Ferreira", "lucas@email.com", "(41) 91111-2222");
        Aluno aluno2 = alunoController.cadastrar("Fernanda Costa", "fernanda@email.com", "(41) 93333-4444");
        Aluno aluno3 = alunoController.cadastrar("Thiago Souza", "thiago@email.com", "(41) 95555-6666");

        // 2. Cadastrar Cursos
        System.out.println("\n--- [2] Cadastrando Cursos ---");
        Curso cursoJava = cursoController.cadastrar("Java Avançado", "Programação orientada a objetos com Java", 40, 2);
        Curso cursoSQL  = cursoController.cadastrar("SQL e PostgreSQL", "Banco de dados relacional com SQL", 20, 1);

        // 3. Matricular alunos em cursos
        System.out.println("\n--- [3] Matriculando Alunos ---");
        Matricula m1 = matriculaController.matricular(aluno1.getId(), cursoJava.getId(), 500.00);
        matriculaController.matricular(aluno2.getId(), cursoJava.getId(), 500.00);
        matriculaController.matricular(aluno1.getId(), cursoSQL.getId(), 300.00);

        // 4. Listar alunos de cada curso
        System.out.println("\n--- [4] Alunos do Curso Java Avançado ---");
        cursoController.listarAlunosDoCurso(cursoJava.getId());

        System.out.println("\n--- [5] Alunos do Curso SQL ---");
        cursoController.listarAlunosDoCurso(cursoSQL.getId());

        // 6. Listar cursos de um aluno
        System.out.println("\n--- [6] Cursos do Aluno Lucas ---");
        alunoController.listarCursosDoAluno(aluno1.getId());

        System.out.println("\n--- [7] Cursos do Aluno Thiago – deve estar vazio ---");
        alunoController.listarCursosDoAluno(aluno3.getId());

        // -----------------------------------------------
        // TESTES DAS REGRAS DE NEGÓCIO
        // -----------------------------------------------

        System.out.println("\n============================================");
        System.out.println("   TESTES DE REGRAS DE NEGÓCIO");
        System.out.println("============================================\n");

        // Regra: curso sem vagas (SQL tem vagas=1, já está lotado)
        System.out.println("--- [RN1] Tentando matricular em curso sem vagas (SQL lotado) ---");
        try {
            matriculaController.matricular(aluno2.getId(), cursoSQL.getId(), 300.00);
        } catch (IllegalArgumentException e) {
            System.out.println("[REGRA APLICADA] " + e.getMessage());
        }

        // Regra: matrícula duplicada (aluno1 já está em Java)
        System.out.println("\n--- [RN2] Tentando matricular o mesmo aluno duas vezes no curso Java ---");
        try {
            matriculaController.matricular(aluno1.getId(), cursoJava.getId(), 500.00);
        } catch (IllegalArgumentException e) {
            System.out.println("[REGRA APLICADA] " + e.getMessage());
        }

        // Regra: valor negativo
        System.out.println("\n--- [RN3] Tentando matricular com valor negativo ---");
        try {
            matriculaController.matricular(aluno3.getId(), cursoJava.getId(), -100.00);
        } catch (IllegalArgumentException e) {
            System.out.println("[REGRA APLICADA] " + e.getMessage());
        }

        // Regra: aluno inexistente
        System.out.println("\n--- [RN4] Tentando matricular aluno inexistente ---");
        try {
            matriculaController.matricular(9999L, cursoJava.getId(), 500.00);
        } catch (IllegalArgumentException e) {
            System.out.println("[REGRA APLICADA] " + e.getMessage());
        }

        // 8. Cancelar matrícula e verificar devolução de vaga
        System.out.println("\n--- [8] Cancelando matrícula de Lucas no Java e rematriculando Thiago ---");
        matriculaController.cancelar(m1.getId());

        System.out.println("\n[Após cancelamento] Vagas do Java Avançado:");
        cursoController.buscarPorId(cursoJava.getId());

        matriculaController.matricular(aluno3.getId(), cursoJava.getId(), 500.00);

        System.out.println("\n--- [9] Alunos do Java Avançado após substituição ---");
        cursoController.listarAlunosDoCurso(cursoJava.getId());

        System.out.println("\n============================================");
        System.out.println("   FIM DA SIMULAÇÃO");
        System.out.println("============================================");
    }
}
