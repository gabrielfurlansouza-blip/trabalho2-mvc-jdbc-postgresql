package com.escola.controller;

import com.escola.model.Matricula;
import com.escola.service.MatriculaService;

import java.time.LocalDate;
import java.util.List;

public class MatriculaController {

    private final MatriculaService matriculaService = new MatriculaService();

    public Matricula matricular(Long idAluno, Long idCurso, double valor) {
        Matricula m = new Matricula(idAluno, idCurso, LocalDate.now(), valor);
        Matricula salva = matriculaService.matricular(m);
        System.out.println("[OK] Matrícula realizada: " + salva);
        return salva;
    }

    public void cancelar(Long id) {
        matriculaService.cancelar(id);
        System.out.println("[OK] Matrícula ID " + id + " cancelada. Vaga devolvida ao curso.");
    }

    public void listarTodas() {
        List<Matricula> lista = matriculaService.listarTodas();
        System.out.println("=== Todas as Matrículas (" + lista.size() + ") ===");
        lista.forEach(System.out::println);
    }

    public void exibirTotalMatriculadosPorCurso(Long idCurso, String nomeCurso) {
        int total = matriculaService.contarMatriculadosPorCurso(idCurso);
        System.out.println("[INFO] Curso '" + nomeCurso + "' possui " + total + " aluno(s) matriculado(s).");
    }
}
