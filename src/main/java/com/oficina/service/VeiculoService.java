package com.oficina.service;

import com.oficina.model.Veiculo;
import com.oficina.repository.ClienteRepository;
import com.oficina.repository.VeiculoRepository;

import java.util.List;
import java.util.Optional;

public class VeiculoService {

    private final VeiculoRepository veiculoRepository = new VeiculoRepository();
    private final ClienteRepository clienteRepository = new ClienteRepository();

    public Veiculo cadastrar(Veiculo veiculo) {
        if (veiculo.getPlaca() == null || veiculo.getPlaca().isBlank()) {
            throw new IllegalArgumentException("Placa do veículo é obrigatória.");
        }
        clienteRepository.findById(veiculo.getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com id: " + veiculo.getIdCliente()));
        return veiculoRepository.save(veiculo);
    }

    public Optional<Veiculo> buscarPorId(Long id) {
        return veiculoRepository.findById(id);
    }

    public List<Veiculo> listarTodos() {
        return veiculoRepository.findAll();
    }

    public List<Veiculo> listarPorCliente(Long idCliente) {
        return veiculoRepository.findByCliente(idCliente);
    }

    public void atualizar(Veiculo veiculo) {
        if (veiculo.getPlaca() == null || veiculo.getPlaca().isBlank()) {
            throw new IllegalArgumentException("Placa do veículo é obrigatória.");
        }
        veiculoRepository.update(veiculo);
    }

    public void remover(Long id) {
        veiculoRepository.delete(id);
    }

    public Optional<Veiculo> buscarPorPlaca(String placa) {
        if (placa == null || placa.isBlank()) {
            throw new IllegalArgumentException("Placa não pode ser vazia.");
        }
        return veiculoRepository.findByPlaca(placa.trim());
    }
}
