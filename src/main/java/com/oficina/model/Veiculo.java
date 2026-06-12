package com.oficina.model;

public class Veiculo {

    private Long id;
    private String placa;
    private String modelo;
    private int ano;
    private Long idCliente;

    public Veiculo() {}

    public Veiculo(String placa, String modelo, int ano, Long idCliente) {
        this.placa = placa;
        this.modelo = modelo;
        this.ano = ano;
        this.idCliente = idCliente;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }

    public Long getIdCliente() { return idCliente; }
    public void setIdCliente(Long idCliente) { this.idCliente = idCliente; }

    @Override
    public String toString() {
        return "Veiculo{id=" + id + ", placa='" + placa + "', modelo='" + modelo + "', ano=" + ano + ", idCliente=" + idCliente + "}";
    }
}
