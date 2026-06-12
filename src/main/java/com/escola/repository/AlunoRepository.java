package com.escola.repository;

import com.escola.model.Aluno;
import com.escola.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlunoRepository {

    public Aluno save(Aluno aluno) {
        String sql = "INSERT INTO alunos (nome, email, telefone) VALUES (?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getEmail());
            stmt.setString(3, aluno.getTelefone());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                aluno.setId(rs.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar aluno: " + e.getMessage(), e);
        }
        return aluno;
    }

    public Optional<Aluno> findById(Long id) {
        String sql = "SELECT * FROM alunos WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar aluno: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public List<Aluno> findAll() {
        String sql = "SELECT * FROM alunos ORDER BY nome";
        List<Aluno> alunos = new ArrayList<>();
        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                alunos.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar alunos: " + e.getMessage(), e);
        }
        return alunos;
    }

    public List<Aluno> findByCurso(Long idCurso) {
        String sql = "SELECT a.* FROM alunos a "
                   + "JOIN matriculas m ON a.id = m.id_aluno "
                   + "WHERE m.id_curso = ? ORDER BY a.nome";
        List<Aluno> alunos = new ArrayList<>();
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idCurso);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                alunos.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar alunos do curso: " + e.getMessage(), e);
        }
        return alunos;
    }

    public void update(Aluno aluno) {
        String sql = "UPDATE alunos SET nome = ?, email = ?, telefone = ? WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, aluno.getNome());
            stmt.setString(2, aluno.getEmail());
            stmt.setString(3, aluno.getTelefone());
            stmt.setLong(4, aluno.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar aluno: " + e.getMessage(), e);
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM alunos WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar aluno: " + e.getMessage(), e);
        }
    }

    private Aluno mapResultSet(ResultSet rs) throws SQLException {
        Aluno a = new Aluno();
        a.setId(rs.getLong("id"));
        a.setNome(rs.getString("nome"));
        a.setEmail(rs.getString("email"));
        a.setTelefone(rs.getString("telefone"));
        return a;
    }
}
