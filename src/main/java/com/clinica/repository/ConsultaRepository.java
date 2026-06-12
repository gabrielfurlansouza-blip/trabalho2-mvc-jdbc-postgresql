package com.clinica.repository;

import com.clinica.model.Consulta;
import com.clinica.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsultaRepository {

    public Consulta save(Consulta consulta) {
        String sql = "INSERT INTO consultas (id_animal, data, motivo, valor) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, consulta.getIdAnimal());
            stmt.setDate(2, Date.valueOf(consulta.getData()));
            stmt.setString(3, consulta.getMotivo());
            stmt.setDouble(4, consulta.getValor());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                consulta.setId(rs.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar consulta: " + e.getMessage(), e);
        }
        return consulta;
    }

    public Optional<Consulta> findById(Long id) {
        String sql = "SELECT * FROM consultas WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar consulta: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public List<Consulta> findAll() {
        String sql = "SELECT * FROM consultas ORDER BY data DESC";
        List<Consulta> consultas = new ArrayList<>();
        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                consultas.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar consultas: " + e.getMessage(), e);
        }
        return consultas;
    }

    public List<Consulta> findByAnimal(Long idAnimal) {
        String sql = "SELECT * FROM consultas WHERE id_animal = ? ORDER BY data DESC";
        List<Consulta> consultas = new ArrayList<>();
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idAnimal);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                consultas.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar consultas do animal: " + e.getMessage(), e);
        }
        return consultas;
    }

    public void update(Consulta consulta) {
        String sql = "UPDATE consultas SET id_animal = ?, data = ?, motivo = ?, valor = ? WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, consulta.getIdAnimal());
            stmt.setDate(2, Date.valueOf(consulta.getData()));
            stmt.setString(3, consulta.getMotivo());
            stmt.setDouble(4, consulta.getValor());
            stmt.setLong(5, consulta.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar consulta: " + e.getMessage(), e);
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM consultas WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar consulta: " + e.getMessage(), e);
        }
    }

    private Consulta mapResultSet(ResultSet rs) throws SQLException {
        Consulta consulta = new Consulta();
        consulta.setId(rs.getLong("id"));
        consulta.setIdAnimal(rs.getLong("id_animal"));
        consulta.setData(rs.getDate("data").toLocalDate());
        consulta.setMotivo(rs.getString("motivo"));
        consulta.setValor(rs.getDouble("valor"));
        return consulta;
    }
}
