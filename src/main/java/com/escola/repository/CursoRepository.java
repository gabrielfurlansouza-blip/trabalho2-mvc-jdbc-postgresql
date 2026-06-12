package com.escola.repository;

import com.escola.model.Curso;
import com.escola.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CursoRepository {

    public Curso save(Curso curso) {
        String sql = "INSERT INTO cursos (nome, descricao, carga_horaria, vagas_totais, vagas_disponiveis) "
                   + "VALUES (?, ?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getDescricao());
            stmt.setInt(3, curso.getCargaHoraria());
            stmt.setInt(4, curso.getVagasTotais());
            stmt.setInt(5, curso.getVagasDisponiveis());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                curso.setId(rs.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar curso: " + e.getMessage(), e);
        }
        return curso;
    }

    public Optional<Curso> findById(Long id) {
        String sql = "SELECT * FROM cursos WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar curso: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public List<Curso> findAll() {
        String sql = "SELECT * FROM cursos ORDER BY nome";
        List<Curso> cursos = new ArrayList<>();
        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                cursos.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar cursos: " + e.getMessage(), e);
        }
        return cursos;
    }

    public List<Curso> findByAluno(Long idAluno) {
        String sql = "SELECT c.* FROM cursos c "
                   + "JOIN matriculas m ON c.id = m.id_curso "
                   + "WHERE m.id_aluno = ? ORDER BY c.nome";
        List<Curso> cursos = new ArrayList<>();
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idAluno);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cursos.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cursos do aluno: " + e.getMessage(), e);
        }
        return cursos;
    }

    public void decrementarVaga(Long idCurso) {
        String sql = "UPDATE cursos SET vagas_disponiveis = vagas_disponiveis - 1 WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idCurso);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao decrementar vaga: " + e.getMessage(), e);
        }
    }

    public void incrementarVaga(Long idCurso) {
        String sql = "UPDATE cursos SET vagas_disponiveis = vagas_disponiveis + 1 WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idCurso);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao incrementar vaga: " + e.getMessage(), e);
        }
    }

    public void update(Curso curso) {
        String sql = "UPDATE cursos SET nome = ?, descricao = ?, carga_horaria = ?, vagas_totais = ?, vagas_disponiveis = ? WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, curso.getNome());
            stmt.setString(2, curso.getDescricao());
            stmt.setInt(3, curso.getCargaHoraria());
            stmt.setInt(4, curso.getVagasTotais());
            stmt.setInt(5, curso.getVagasDisponiveis());
            stmt.setLong(6, curso.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar curso: " + e.getMessage(), e);
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM cursos WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar curso: " + e.getMessage(), e);
        }
    }

    private Curso mapResultSet(ResultSet rs) throws SQLException {
        Curso c = new Curso();
        c.setId(rs.getLong("id"));
        c.setNome(rs.getString("nome"));
        c.setDescricao(rs.getString("descricao"));
        c.setCargaHoraria(rs.getInt("carga_horaria"));
        c.setVagasTotais(rs.getInt("vagas_totais"));
        c.setVagasDisponiveis(rs.getInt("vagas_disponiveis"));
        return c;
    }
}
