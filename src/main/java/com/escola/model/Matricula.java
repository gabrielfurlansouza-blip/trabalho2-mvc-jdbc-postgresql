package com.escola.model;

import java.time.LocalDate;

public class Matricula {

    private Long id;
    private Long idAluno;
    private Long idCurso;
    private LocalDate dataMatricula;
    private double valor;

    public Matricula() {}

    public Matricula(Long idAluno, Long idCurso, LocalDate dataMatricula, double valor) {
        this.idAluno = idAluno;
        this.idCurso = idCurso;
        this.dataMatricula = dataMatricula;
        this.valor = valor;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdAluno() { return idAluno; }
    public void setIdAluno(Long idAluno) { this.idAluno = idAluno; }

    public Long getIdCurso() { return idCurso; }
    public void setIdCurso(Long idCurso) { this.idCurso = idCurso; }

    public LocalDate getDataMatricula() { return dataMatricula; }
    public void setDataMatricula(LocalDate dataMatricula) { this.dataMatricula = dataMatricula; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    @Override
    public String toString() {
        return "Matricula{id=" + id + ", idAluno=" + idAluno + ", idCurso=" + idCurso
                + ", data=" + dataMatricula + ", valor=" + valor + "}";
    }
}
