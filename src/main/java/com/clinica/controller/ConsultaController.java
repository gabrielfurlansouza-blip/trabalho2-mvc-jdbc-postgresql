package com.clinica.controller;

import com.clinica.model.Consulta;
import com.clinica.service.ConsultaService;

import java.time.LocalDate;
import java.util.List;

public class ConsultaController {

    private final ConsultaService consultaService = new ConsultaService();

    public Consulta registrar(Long idAnimal, LocalDate data, String motivo, double valor) {
        Consulta consulta = new Consulta(idAnimal, data, motivo, valor);
        Consulta salva = consultaService.registrar(consulta);
        System.out.println("[OK] Consulta registrada: " + salva);
        return salva;
    }

    public void historicoDoAnimal(Long idAnimal) {
        List<Consulta> consultas = consultaService.historicoDoAnimal(idAnimal);
        System.out.println("=== Histórico de Consultas – Animal ID " + idAnimal + " (" + consultas.size() + " registro(s)) ===");
        if (consultas.isEmpty()) {
            System.out.println("[INFO] Nenhuma consulta registrada para este animal.");
        } else {
            consultas.forEach(System.out::println);
        }
    }

    public void listarTodas() {
        List<Consulta> consultas = consultaService.listarTodas();
        System.out.println("=== Todas as Consultas (" + consultas.size() + ") ===");
        consultas.forEach(System.out::println);
    }

    public void remover(Long id) {
        consultaService.remover(id);
        System.out.println("[OK] Consulta ID " + id + " removida com sucesso.");
    }
}
