package com.oficina;

import com.oficina.controller.ClienteController;
import com.oficina.controller.OrdemServicoController;
import com.oficina.controller.VeiculoController;
import com.oficina.model.Cliente;
import com.oficina.model.Veiculo;

public class Main {

    public static void main(String[] args) {
        ClienteController clienteController = new ClienteController();
        VeiculoController veiculoController = new VeiculoController();
        OrdemServicoController osController = new OrdemServicoController();

        System.out.println("============================================");
        System.out.println("   SISTEMA DE OFICINA MECÂNICA");
        System.out.println("============================================\n");

        // -----------------------------------------------
        // FLUXO: cliente → veículo → ordem de serviço
        // -----------------------------------------------

        // 1. Cadastrar Cliente
        System.out.println("--- [1] Cadastrando Cliente ---");
        Cliente cliente = clienteController.cadastrar("Carlos Pereira", "(41) 97777-3333");

        // 2. Cadastrar segundo cliente
        System.out.println("\n--- [2] Cadastrando segundo Cliente ---");
        Cliente cliente2 = clienteController.cadastrar("Ana Lima", "(41) 96666-4444");
        veiculoController.cadastrar("DEF-5555", "Volkswagen Gol", 2015, cliente2.getId());

        // 3. Cadastrar Veículo vinculado ao primeiro Cliente
        System.out.println("\n--- [3] Cadastrando Veículo para Carlos ---");
        Veiculo veiculo = veiculoController.cadastrar("ABC-1234", "Honda Civic", 2020, cliente.getId());

        // 4. Cadastrar segundo Veículo para o mesmo Cliente
        System.out.println("\n--- [4] Cadastrando segundo Veículo para Carlos ---");
        Veiculo veiculo2 = veiculoController.cadastrar("XYZ-9876", "Toyota Corolla", 2018, cliente.getId());

        // 5. Abrir Ordem de Serviço para o primeiro veículo
        System.out.println("\n--- [5] Abrindo Ordem de Serviço para o Civic ---");
        var os1 = osController.abrir(veiculo.getId(), "Troca de óleo e filtro", 250.00);

        // 6. Abrir segunda Ordem de Serviço para o mesmo veículo
        System.out.println("\n--- [6] Abrindo segunda Ordem de Serviço para o Civic ---");
        osController.abrir(veiculo.getId(), "Revisão completa – 50.000 km", 800.00);

        // 7. Concluir a primeira OS
        System.out.println("\n--- [7] Concluindo a primeira OS ---");
        osController.concluir(os1.getId());

        // 8. Listar veículos do cliente
        System.out.println("\n--- [8] Veículos do Cliente Carlos ---");
        clienteController.listarVeiculosDoCliente(cliente.getId());

        // 9. Histórico de manutenções por veículo
        System.out.println("\n--- [9] Histórico de Manutenções – Civic ---");
        osController.historicoPorVeiculo(veiculo.getId());

        System.out.println("\n--- [10] Histórico do Corolla – deve estar vazio ---");
        osController.historicoPorVeiculo(veiculo2.getId());

        // -----------------------------------------------
        // TESTES DAS REGRAS DE NEGÓCIO
        // -----------------------------------------------

        System.out.println("\n============================================");
        System.out.println("   TESTES DE REGRAS DE NEGÓCIO");
        System.out.println("============================================\n");

        // Regra: valor negativo deve ser rejeitado
        System.out.println("--- [RN1] Tentando abrir OS com valor negativo ---");
        try {
            osController.abrir(veiculo.getId(), "Serviço inválido", -100.00);
        } catch (IllegalArgumentException e) {
            System.out.println("[REGRA APLICADA] " + e.getMessage());
        }

        // Regra: veículo inexistente deve ser rejeitado
        System.out.println("\n--- [RN2] Tentando abrir OS para veículo inexistente ---");
        try {
            osController.abrir(9999L, "OS inválida", 500.00);
        } catch (IllegalArgumentException e) {
            System.out.println("[REGRA APLICADA] " + e.getMessage());
        }

        // Regra: cliente sem nome deve ser rejeitado
        System.out.println("\n--- [RN3] Tentando cadastrar cliente sem nome ---");
        try {
            clienteController.cadastrar("", "(41) 00000-0000");
        } catch (IllegalArgumentException e) {
            System.out.println("[REGRA APLICADA] " + e.getMessage());
        }

        System.out.println("\n============================================");
        System.out.println("   FIM DA SIMULAÇÃO");
        System.out.println("============================================");
    }
}
