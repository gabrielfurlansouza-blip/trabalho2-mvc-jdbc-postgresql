package com.oficina.controller;

import com.oficina.model.Veiculo;
import com.oficina.service.VeiculoService;

import java.util.List;
import java.util.Optional;

public class VeiculoController {

    private final VeiculoService veiculoService = new VeiculoService();

    public Veiculo cadastrar(String placa, String modelo, int ano, Long idCliente) {
        Veiculo veiculo = new Veiculo(placa, modelo, ano, idCliente);
        Veiculo salvo = veiculoService.cadastrar(veiculo);
        System.out.println("[OK] Veículo cadastrado: " + salvo);
        return salvo;
    }

    public void buscarPorId(Long id) {
        Optional<Veiculo> veiculo = veiculoService.buscarPorId(id);
        veiculo.ifPresentOrElse(
                v -> System.out.println("[OK] Veículo encontrado: " + v),
                () -> System.out.println("[INFO] Veículo com id " + id + " não encontrado.")
        );
    }

    public void listarTodos() {
        List<Veiculo> veiculos = veiculoService.listarTodos();
        System.out.println("=== Veículos Cadastrados (" + veiculos.size() + ") ===");
        veiculos.forEach(System.out::println);
    }

    public void buscarPorPlaca(String placa) {
        veiculoService.buscarPorPlaca(placa).ifPresentOrElse(
                v -> System.out.println("[OK] Veículo encontrado pela placa: " + v),
                () -> System.out.println("[INFO] Nenhum veículo encontrado com a placa: " + placa)
        );
    }
}
