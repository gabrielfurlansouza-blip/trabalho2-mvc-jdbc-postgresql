package com.oficina.repository;

import com.oficina.model.Veiculo;
import com.oficina.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VeiculoRepository {

    public Veiculo save(Veiculo veiculo) {
        String sql = "INSERT INTO veiculos (placa, modelo, ano, id_cliente) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setInt(3, veiculo.getAno());
            stmt.setLong(4, veiculo.getIdCliente());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                veiculo.setId(rs.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar veículo: " + e.getMessage(), e);
        }
        return veiculo;
    }

    public Optional<Veiculo> findById(Long id) {
        String sql = "SELECT * FROM veiculos WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar veículo: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public List<Veiculo> findAll() {
        String sql = "SELECT * FROM veiculos ORDER BY modelo";
        List<Veiculo> veiculos = new ArrayList<>();
        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                veiculos.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar veículos: " + e.getMessage(), e);
        }
        return veiculos;
    }

    public List<Veiculo> findByCliente(Long idCliente) {
        String sql = "SELECT * FROM veiculos WHERE id_cliente = ? ORDER BY modelo";
        List<Veiculo> veiculos = new ArrayList<>();
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                veiculos.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar veículos do cliente: " + e.getMessage(), e);
        }
        return veiculos;
    }

    public void update(Veiculo veiculo) {
        String sql = "UPDATE veiculos SET placa = ?, modelo = ?, ano = ?, id_cliente = ? WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, veiculo.getPlaca());
            stmt.setString(2, veiculo.getModelo());
            stmt.setInt(3, veiculo.getAno());
            stmt.setLong(4, veiculo.getIdCliente());
            stmt.setLong(5, veiculo.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar veículo: " + e.getMessage(), e);
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM veiculos WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar veículo: " + e.getMessage(), e);
        }
    }

    private Veiculo mapResultSet(ResultSet rs) throws SQLException {
        Veiculo v = new Veiculo();
        v.setId(rs.getLong("id"));
        v.setPlaca(rs.getString("placa"));
        v.setModelo(rs.getString("modelo"));
        v.setAno(rs.getInt("ano"));
        v.setIdCliente(rs.getLong("id_cliente"));
        return v;
    }
}
