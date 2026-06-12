package com.clinica.repository;

import com.clinica.model.Tutor;
import com.clinica.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TutorRepository {

    public Tutor save(Tutor tutor) {
        String sql = "INSERT INTO tutores (nome, endereco, telefone) VALUES (?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tutor.getNome());
            stmt.setString(2, tutor.getEndereco());
            stmt.setString(3, tutor.getTelefone());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tutor.setId(rs.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar tutor: " + e.getMessage(), e);
        }
        return tutor;
    }

    public Optional<Tutor> findById(Long id) {
        String sql = "SELECT * FROM tutores WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar tutor: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public List<Tutor> findAll() {
        String sql = "SELECT * FROM tutores ORDER BY nome";
        List<Tutor> tutores = new ArrayList<>();
        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tutores.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar tutores: " + e.getMessage(), e);
        }
        return tutores;
    }

    public void update(Tutor tutor) {
        String sql = "UPDATE tutores SET nome = ?, endereco = ?, telefone = ? WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tutor.getNome());
            stmt.setString(2, tutor.getEndereco());
            stmt.setString(3, tutor.getTelefone());
            stmt.setLong(4, tutor.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar tutor: " + e.getMessage(), e);
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM tutores WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar tutor: " + e.getMessage(), e);
        }
    }

    private Tutor mapResultSet(ResultSet rs) throws SQLException {
        Tutor tutor = new Tutor();
        tutor.setId(rs.getLong("id"));
        tutor.setNome(rs.getString("nome"));
        tutor.setEndereco(rs.getString("endereco"));
        tutor.setTelefone(rs.getString("telefone"));
        return tutor;
    }
}
