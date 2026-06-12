package com.clinica.service;

import com.clinica.model.Animal;
import com.clinica.repository.AnimalRepository;
import com.clinica.repository.TutorRepository;

import java.util.List;
import java.util.Optional;

public class AnimalService {

    private final AnimalRepository animalRepository = new AnimalRepository();
    private final TutorRepository tutorRepository = new TutorRepository();

    public Animal cadastrar(Animal animal) {
        if (animal.getNome() == null || animal.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do animal é obrigatório.");
        }
        tutorRepository.findById(animal.getIdTutor())
                .orElseThrow(() -> new IllegalArgumentException("Tutor não encontrado com id: " + animal.getIdTutor()));
        return animalRepository.save(animal);
    }

    public Optional<Animal> buscarPorId(Long id) {
        return animalRepository.findById(id);
    }

    public List<Animal> listarTodos() {
        return animalRepository.findAll();
    }

    public List<Animal> listarPorTutor(Long idTutor) {
        return animalRepository.findByTutor(idTutor);
    }

    public void atualizar(Animal animal) {
        if (animal.getNome() == null || animal.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do animal é obrigatório.");
        }
        animalRepository.update(animal);
    }

    public void remover(Long id) {
        animalRepository.delete(id);
    }
}
