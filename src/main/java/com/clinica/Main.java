package com.clinica;

import com.clinica.controller.AnimalController;
import com.clinica.controller.ConsultaController;
import com.clinica.controller.TutorController;
import com.clinica.model.Animal;
import com.clinica.model.Tutor;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        TutorController tutorController = new TutorController();
        AnimalController animalController = new AnimalController();
        ConsultaController consultaController = new ConsultaController();

        System.out.println("============================================");
        System.out.println("   SISTEMA DE CLÍNICA VETERINÁRIA");
        System.out.println("============================================\n");

        // -----------------------------------------------
        // FLUXO: tutor → animal → consulta
        // -----------------------------------------------

        // 1. Cadastrar Tutor
        System.out.println("--- [1] Cadastrando Tutor ---");
        Tutor tutor = tutorController.cadastrar("João Silva", "Rua das Flores, 123 – Curitiba", "(41) 99999-1111");

        // 2. Cadastrar segundo tutor para demonstrar isolamento
        System.out.println("\n--- [2] Cadastrando segundo Tutor ---");
        Tutor tutor2 = tutorController.cadastrar("Maria Oliveira", "Av. Brasil, 456 – Curitiba", "(41) 88888-2222");

        // 3. Cadastrar Animal vinculado ao primeiro Tutor
        System.out.println("\n--- [3] Cadastrando Animal para o Tutor João ---");
        Animal animal = animalController.cadastrar("Rex", "Cachorro", "Labrador", tutor.getId());

        // 4. Cadastrar outro Animal com o mesmo nome, mas vinculado ao segundo Tutor
        System.out.println("\n--- [4] Cadastrando Animal 'Rex' para o Tutor Maria (nome igual, dono diferente) ---");
        Animal animal2 = animalController.cadastrar("Rex", "Cachorro", "Poodle", tutor2.getId());

        // 5. Registrar Consulta para o animal do Tutor João
        System.out.println("\n--- [5] Registrando Consulta para Rex (João) ---");
        consultaController.registrar(animal.getId(), LocalDate.now(), "Check-up anual", 150.00);

        // 6. Registrar mais uma consulta para o mesmo animal
        System.out.println("\n--- [6] Registrando segunda Consulta para Rex (João) ---");
        var consulta2 = consultaController.registrar(animal.getId(), LocalDate.now().minusDays(30), "Vacina antirrábica", 80.00);

        // 7. Listar animais de cada tutor
        System.out.println("\n--- [7] Animais do Tutor João ---");
        tutorController.listarAnimaisDeTutor(tutor.getId());

        System.out.println("\n--- [8] Animais da Tutora Maria ---");
        tutorController.listarAnimaisDeTutor(tutor2.getId());

        // 9. Busca de tutor por parte do nome
        System.out.println("\n--- [9] Buscando tutor pelo nome 'Silva' ---");
        tutorController.buscarPorNome("Silva");

        // 10. Histórico de consultas por animal
        System.out.println("\n--- [10] Histórico de Consultas – Rex (João) ---");
        consultaController.historicoDoAnimal(animal.getId());

        System.out.println("\n--- [10] Histórico de Consultas – Rex (Maria) – deve estar vazio ---");
        consultaController.historicoDoAnimal(animal2.getId());

        // -----------------------------------------------
        // TESTES DAS REGRAS DE NEGÓCIO
        // -----------------------------------------------

        System.out.println("\n============================================");
        System.out.println("   TESTES DE REGRAS DE NEGÓCIO");
        System.out.println("============================================\n");

        // Regra: valor negativo deve ser rejeitado
        System.out.println("--- [RN1] Tentando registrar consulta com valor negativo ---");
        try {
            consultaController.registrar(animal.getId(), LocalDate.now(), "Teste inválido", -50.00);
        } catch (IllegalArgumentException e) {
            System.out.println("[REGRA APLICADA] " + e.getMessage());
        }

        // Regra: animal inexistente deve ser rejeitado
        System.out.println("\n--- [RN2] Tentando registrar consulta para animal inexistente ---");
        try {
            consultaController.registrar(9999L, LocalDate.now(), "Consulta inválida", 100.00);
        } catch (IllegalArgumentException e) {
            System.out.println("[REGRA APLICADA] " + e.getMessage());
        }

        // Regra: tutor com nome em branco deve ser rejeitado
        System.out.println("\n--- [RN3] Tentando cadastrar tutor sem nome ---");
        try {
            tutorController.cadastrar("", "Rua X", "(41) 00000-0000");
        } catch (IllegalArgumentException e) {
            System.out.println("[REGRA APLICADA] " + e.getMessage());
        }

        System.out.println("\n============================================");
        System.out.println("   CRUD COMPLETO – UPDATE E DELETE");
        System.out.println("============================================\n");

        // UPDATE: atualizar endereço e telefone do tutor
        System.out.println("--- [U1] Atualizando dados do Tutor João ---");
        tutorController.atualizar(tutor.getId(), "João Silva", "Av. das Araucárias, 500 – Curitiba", "(41) 99900-0001");

        // LIST ALL após update
        System.out.println("\n--- [U2] Listando todos os tutores após atualização ---");
        tutorController.listarTodos();

        // UPDATE: atualizar raça do animal
        System.out.println("\n--- [U3] Atualizando raça do Rex (Labrador → Golden Retriever) ---");
        animalController.atualizar(animal.getId(), "Rex", "Cachorro", "Golden Retriever");

        // DELETE: remover a segunda consulta
        System.out.println("\n--- [U4] Removendo consulta de vacinação (DELETE) ---");
        consultaController.remover(consulta2.getId());

        // Verificar histórico após remoção
        System.out.println("\n--- [U5] Histórico do Rex após remoção – deve ter apenas 1 registro ---");
        consultaController.historicoDoAnimal(animal.getId());

        System.out.println("\n============================================");
        System.out.println("   FIM DA SIMULAÇÃO");
        System.out.println("============================================");
    }
}
