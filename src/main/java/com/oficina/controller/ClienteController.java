package com.oficina.controller;

import com.oficina.model.Cliente;
import com.oficina.model.Veiculo;
import com.oficina.service.ClienteService;

import java.util.List;
import java.util.Optional;

public class ClienteController {

    private final ClienteService clienteService = new ClienteService();

    public Cliente cadastrar(String nome, String telefone) {
        Cliente cliente = new Cliente(nome, telefone);
        Cliente salvo = clienteService.cadastrar(cliente);
        System.out.println("[OK] Cliente cadastrado: " + salvo);
        return salvo;
    }

    public void buscarPorId(Long id) {
        Optional<Cliente> cliente = clienteService.buscarPorId(id);
        cliente.ifPresentOrElse(
                c -> System.out.println("[OK] Cliente encontrado: " + c),
                () -> System.out.println("[INFO] Cliente com id " + id + " não encontrado.")
        );
    }

    public void listarTodos() {
        List<Cliente> clientes = clienteService.listarTodos();
        System.out.println("=== Clientes Cadastrados (" + clientes.size() + ") ===");
        clientes.forEach(System.out::println);
    }

    public void listarVeiculosDoCliente(Long idCliente) {
        List<Veiculo> veiculos = clienteService.listarVeiculosDoCliente(idCliente);
        System.out.println("=== Veículos do Cliente ID " + idCliente + " (" + veiculos.size() + ") ===");
        if (veiculos.isEmpty()) {
            System.out.println("[INFO] Nenhum veículo cadastrado para este cliente.");
        } else {
            veiculos.forEach(System.out::println);
        }
    }
}
