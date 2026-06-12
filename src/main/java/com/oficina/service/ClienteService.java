package com.oficina.service;

import com.oficina.model.Cliente;
import com.oficina.model.Veiculo;
import com.oficina.repository.ClienteRepository;
import com.oficina.repository.VeiculoRepository;

import java.util.List;
import java.util.Optional;

public class ClienteService {

    private final ClienteRepository clienteRepository = new ClienteRepository();
    private final VeiculoRepository veiculoRepository = new VeiculoRepository();

    public Cliente cadastrar(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do cliente é obrigatório.");
        }
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public void atualizar(Cliente cliente) {
        if (cliente.getNome() == null || cliente.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do cliente é obrigatório.");
        }
        clienteRepository.update(cliente);
    }

    public void remover(Long id) {
        clienteRepository.delete(id);
    }

    public List<Veiculo> listarVeiculosDoCliente(Long idCliente) {
        clienteRepository.findById(idCliente)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com id: " + idCliente));
        return veiculoRepository.findByCliente(idCliente);
    }
}
