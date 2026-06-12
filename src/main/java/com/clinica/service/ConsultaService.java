package com.clinica.service;

import com.clinica.model.Consulta;
import com.clinica.repository.AnimalRepository;
import com.clinica.repository.ConsultaRepository;

import java.util.List;
import java.util.Optional;

public class ConsultaService {

    private final ConsultaRepository consultaRepository = new ConsultaRepository();
    private final AnimalRepository animalRepository = new AnimalRepository();

    public Consulta registrar(Consulta consulta) {
        // Regra: animal deve estar cadastrado
        animalRepository.findById(consulta.getIdAnimal())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Não é possível registrar consulta: animal com id " + consulta.getIdAnimal() + " não está cadastrado."));
        // Regra: valor não pode ser negativo
        if (consulta.getValor() < 0) {
            throw new IllegalArgumentException("O valor da consulta não pode ser negativo.");
        }
        return consultaRepository.save(consulta);
    }

    public Optional<Consulta> buscarPorId(Long id) {
        return consultaRepository.findById(id);
    }

    public List<Consulta> listarTodas() {
        return consultaRepository.findAll();
    }

    public List<Consulta> historicoDoAnimal(Long idAnimal) {
        animalRepository.findById(idAnimal)
                .orElseThrow(() -> new IllegalArgumentException("Animal não encontrado com id: " + idAnimal));
        return consultaRepository.findByAnimal(idAnimal);
    }

    public void atualizar(Consulta consulta) {
        if (consulta.getValor() < 0) {
            throw new IllegalArgumentException("O valor da consulta não pode ser negativo.");
        }
        consultaRepository.update(consulta);
    }

    public void remover(Long id) {
        consultaRepository.delete(id);
    }
}
