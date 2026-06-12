package com.clinica.controller;

import com.clinica.model.Animal;
import com.clinica.service.AnimalService;

import java.util.List;
import java.util.Optional;

public class AnimalController {

    private final AnimalService animalService = new AnimalService();

    public Animal cadastrar(String nome, String especie, String raca, Long idTutor) {
        Animal animal = new Animal(nome, especie, raca, idTutor);
        Animal salvo = animalService.cadastrar(animal);
        System.out.println("[OK] Animal cadastrado: " + salvo);
        return salvo;
    }

    public void buscarPorId(Long id) {
        Optional<Animal> animal = animalService.buscarPorId(id);
        animal.ifPresentOrElse(
                a -> System.out.println("[OK] Animal encontrado: " + a),
                () -> System.out.println("[INFO] Animal com id " + id + " não encontrado.")
        );
    }

    public void listarTodos() {
        List<Animal> animais = animalService.listarTodos();
        System.out.println("=== Animais Cadastrados (" + animais.size() + ") ===");
        animais.forEach(System.out::println);
    }
}
