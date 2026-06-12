package com.oficina.model;

public class OrdemServico {

    public enum Status { ABERTA, CONCLUIDA }

    private Long id;
    private Long idVeiculo;
    private String descricao;
    private double valor;
    private Status status;

    public OrdemServico() {}

    public OrdemServico(Long idVeiculo, String descricao, double valor, Status status) {
        this.idVeiculo = idVeiculo;
        this.descricao = descricao;
        this.valor = valor;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getIdVeiculo() { return idVeiculo; }
    public void setIdVeiculo(Long idVeiculo) { this.idVeiculo = idVeiculo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    @Override
    public String toString() {
        return "OrdemServico{id=" + id + ", idVeiculo=" + idVeiculo + ", descricao='" + descricao + "', valor=" + valor + ", status=" + status + "}";
    }
}
