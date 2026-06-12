package com.clinica.repository;

import com.clinica.model.Animal;
import com.clinica.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AnimalRepository {

    public Animal save(Animal animal) {
        String sql = "INSERT INTO animais (nome, especie, raca, id_tutor) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, animal.getNome());
            stmt.setString(2, animal.getEspecie());
            stmt.setString(3, animal.getRaca());
            stmt.setLong(4, animal.getIdTutor());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                animal.setId(rs.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar animal: " + e.getMessage(), e);
        }
        return animal;
    }

    public Optional<Animal> findById(Long id) {
        String sql = "SELECT * FROM animais WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar animal: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public List<Animal> findAll() {
        String sql = "SELECT * FROM animais ORDER BY nome";
        List<Animal> animais = new ArrayList<>();
        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                animais.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar animais: " + e.getMessage(), e);
        }
        return animais;
    }

    public List<Animal> findByTutor(Long idTutor) {
        String sql = "SELECT * FROM animais WHERE id_tutor = ? ORDER BY nome";
        List<Animal> animais = new ArrayList<>();
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idTutor);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                animais.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar animais do tutor: " + e.getMessage(), e);
        }
        return animais;
    }

    public void update(Animal animal) {
        String sql = "UPDATE animais SET nome = ?, especie = ?, raca = ?, id_tutor = ? WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, animal.getNome());
            stmt.setString(2, animal.getEspecie());
            stmt.setString(3, animal.getRaca());
            stmt.setLong(4, animal.getIdTutor());
            stmt.setLong(5, animal.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar animal: " + e.getMessage(), e);
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM animais WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar animal: " + e.getMessage(), e);
        }
    }

    private Animal mapResultSet(ResultSet rs) throws SQLException {
        Animal animal = new Animal();
        animal.setId(rs.getLong("id"));
        animal.setNome(rs.getString("nome"));
        animal.setEspecie(rs.getString("especie"));
        animal.setRaca(rs.getString("raca"));
        animal.setIdTutor(rs.getLong("id_tutor"));
        return animal;
    }
}
