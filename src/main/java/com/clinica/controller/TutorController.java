package com.clinica.controller;

import com.clinica.model.Animal;
import com.clinica.model.Tutor;
import com.clinica.service.TutorService;

import java.util.List;
import java.util.Optional;

public class TutorController {

    private final TutorService tutorService = new TutorService();

    public Tutor cadastrar(String nome, String endereco, String telefone) {
        Tutor tutor = new Tutor(nome, endereco, telefone);
        Tutor salvo = tutorService.cadastrar(tutor);
        System.out.println("[OK] Tutor cadastrado: " + salvo);
        return salvo;
    }

    public void buscarPorId(Long id) {
        Optional<Tutor> tutor = tutorService.buscarPorId(id);
        tutor.ifPresentOrElse(
                t -> System.out.println("[OK] Tutor encontrado: " + t),
                () -> System.out.println("[INFO] Tutor com id " + id + " não encontrado.")
        );
    }

    public void listarTodos() {
        List<Tutor> tutores = tutorService.listarTodos();
        System.out.println("=== Tutores Cadastrados (" + tutores.size() + ") ===");
        tutores.forEach(System.out::println);
    }

    public void buscarPorNome(String nome) {
        List<Tutor> tutores = tutorService.buscarPorNome(nome);
        System.out.println("=== Busca por nome '" + nome + "' (" + tutores.size() + " resultado(s)) ===");
        if (tutores.isEmpty()) {
            System.out.println("[INFO] Nenhum tutor encontrado com esse nome.");
        } else {
            tutores.forEach(System.out::println);
        }
    }

    public void listarAnimaisDeTutor(Long idTutor) {
        List<Animal> animais = tutorService.listarAnimaisDeTutor(idTutor);
        System.out.println("=== Animais do Tutor ID " + idTutor + " (" + animais.size() + ") ===");
        if (animais.isEmpty()) {
            System.out.println("[INFO] Nenhum animal cadastrado para este tutor.");
        } else {
            animais.forEach(System.out::println);
        }
    }

    public void atualizar(Long id, String nome, String endereco, String telefone) {
        Tutor tutor = tutorService.buscarPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Tutor não encontrado com id: " + id));
        tutor.setNome(nome);
        tutor.setEndereco(endereco);
        tutor.setTelefone(telefone);
        tutorService.atualizar(tutor);
        System.out.println("[OK] Tutor atualizado: " + tutor);
    }

    public void remover(Long id) {
        tutorService.remover(id);
        System.out.println("[OK] Tutor ID " + id + " removido com sucesso.");
    }
}
