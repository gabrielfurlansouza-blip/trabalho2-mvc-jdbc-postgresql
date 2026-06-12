package com.clinica.service;

import com.clinica.model.Animal;
import com.clinica.model.Tutor;
import com.clinica.repository.AnimalRepository;
import com.clinica.repository.TutorRepository;

import java.util.List;
import java.util.Optional;

public class TutorService {

    private final TutorRepository tutorRepository = new TutorRepository();
    private final AnimalRepository animalRepository = new AnimalRepository();

    public Tutor cadastrar(Tutor tutor) {
        if (tutor.getNome() == null || tutor.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do tutor é obrigatório.");
        }
        return tutorRepository.save(tutor);
    }

    public Optional<Tutor> buscarPorId(Long id) {
        return tutorRepository.findById(id);
    }

    public List<Tutor> listarTodos() {
        return tutorRepository.findAll();
    }

    public void atualizar(Tutor tutor) {
        if (tutor.getNome() == null || tutor.getNome().isBlank()) {
            throw new IllegalArgumentException("Nome do tutor é obrigatório.");
        }
        tutorRepository.update(tutor);
    }

    public void remover(Long id) {
        tutorRepository.delete(id);
    }

    public List<Tutor> buscarPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Informe ao menos parte do nome para a busca.");
        }
        return tutorRepository.findByNome(nome);
    }

    public List<Animal> listarAnimaisDeTutor(Long idTutor) {
        tutorRepository.findById(idTutor)
                .orElseThrow(() -> new IllegalArgumentException("Tutor não encontrado com id: " + idTutor));
        return animalRepository.findByTutor(idTutor);
    }
}
