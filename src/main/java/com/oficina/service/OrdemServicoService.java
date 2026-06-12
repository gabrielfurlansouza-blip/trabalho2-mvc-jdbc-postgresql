package com.oficina.service;

import com.oficina.model.OrdemServico;
import com.oficina.repository.OrdemServicoRepository;
import com.oficina.repository.VeiculoRepository;

import java.util.List;
import java.util.Optional;

public class OrdemServicoService {

    private final OrdemServicoRepository ordemServicoRepository = new OrdemServicoRepository();
    private final VeiculoRepository veiculoRepository = new VeiculoRepository();

    public OrdemServico abrir(OrdemServico os) {
        // Regra: veículo deve estar cadastrado
        veiculoRepository.findById(os.getIdVeiculo())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Não é possível abrir OS: veículo com id " + os.getIdVeiculo() + " não está cadastrado."));
        // Regra: valor não pode ser negativo
        if (os.getValor() < 0) {
            throw new IllegalArgumentException("O valor do serviço não pode ser negativo.");
        }
        if (os.getStatus() == null) {
            os.setStatus(OrdemServico.Status.ABERTA);
        }
        return ordemServicoRepository.save(os);
    }

    public Optional<OrdemServico> buscarPorId(Long id) {
        return ordemServicoRepository.findById(id);
    }

    public List<OrdemServico> listarTodas() {
        return ordemServicoRepository.findAll();
    }

    public List<OrdemServico> historicoPorVeiculo(Long idVeiculo) {
        veiculoRepository.findById(idVeiculo)
                .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado com id: " + idVeiculo));
        return ordemServicoRepository.findByVeiculo(idVeiculo);
    }

    public void concluir(Long id) {
        OrdemServico os = ordemServicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ordem de serviço não encontrada com id: " + id));
        if (os.getStatus() == OrdemServico.Status.CONCLUIDA) {
            throw new IllegalArgumentException("Ordem de serviço ID " + id + " já está CONCLUIDA.");
        }
        os.setStatus(OrdemServico.Status.CONCLUIDA);
        ordemServicoRepository.update(os);
    }

    public void atualizar(OrdemServico os) {
        if (os.getValor() < 0) {
            throw new IllegalArgumentException("O valor do serviço não pode ser negativo.");
        }
        ordemServicoRepository.update(os);
    }

    public void remover(Long id) {
        ordemServicoRepository.delete(id);
    }
}
