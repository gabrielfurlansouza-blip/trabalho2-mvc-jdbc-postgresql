package com.clinica.model;

public class Animal {

    private Long id;
    private String nome;
    private String especie;
    private String raca;
    private Long idTutor;

    public Animal() {}

    public Animal(String nome, String especie, String raca, Long idTutor) {
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idTutor = idTutor;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getRaca() { return raca; }
    public void setRaca(String raca) { this.raca = raca; }

    public Long getIdTutor() { return idTutor; }
    public void setIdTutor(Long idTutor) { this.idTutor = idTutor; }

    @Override
    public String toString() {
        return "Animal{id=" + id + ", nome='" + nome + "', especie='" + especie + "', raca='" + raca + "', idTutor=" + idTutor + "}";
    }
}
