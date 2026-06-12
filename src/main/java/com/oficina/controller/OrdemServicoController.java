package com.oficina.controller;

import com.oficina.model.OrdemServico;
import com.oficina.service.OrdemServicoService;

import java.util.List;

public class OrdemServicoController {

    private final OrdemServicoService ordemServicoService = new OrdemServicoService();

    public OrdemServico abrir(Long idVeiculo, String descricao, double valor) {
        OrdemServico os = new OrdemServico(idVeiculo, descricao, valor, OrdemServico.Status.ABERTA);
        OrdemServico salva = ordemServicoService.abrir(os);
        System.out.println("[OK] Ordem de serviço aberta: " + salva);
        return salva;
    }

    public void concluir(Long id) {
        ordemServicoService.concluir(id);
        System.out.println("[OK] Ordem de serviço ID " + id + " marcada como CONCLUIDA.");
    }

    public void historicoPorVeiculo(Long idVeiculo) {
        List<OrdemServico> lista = ordemServicoService.historicoPorVeiculo(idVeiculo);
        System.out.println("=== Histórico de Manutenções – Veículo ID " + idVeiculo + " (" + lista.size() + " registro(s)) ===");
        if (lista.isEmpty()) {
            System.out.println("[INFO] Nenhuma ordem de serviço encontrada para este veículo.");
        } else {
            lista.forEach(System.out::println);
        }
    }

    public void listarTodas() {
        List<OrdemServico> lista = ordemServicoService.listarTodas();
        System.out.println("=== Todas as Ordens de Serviço (" + lista.size() + ") ===");
        lista.forEach(System.out::println);
    }
}
