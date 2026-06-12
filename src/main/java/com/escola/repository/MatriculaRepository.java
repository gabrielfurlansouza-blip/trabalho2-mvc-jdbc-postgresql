package com.escola.repository;

import com.escola.model.Matricula;
import com.escola.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MatriculaRepository {

    public Matricula save(Matricula matricula) {
        String sql = "INSERT INTO matriculas (id_aluno, id_curso, data_matricula, valor) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, matricula.getIdAluno());
            stmt.setLong(2, matricula.getIdCurso());
            stmt.setDate(3, Date.valueOf(matricula.getDataMatricula()));
            stmt.setDouble(4, matricula.getValor());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                matricula.setId(rs.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar matrícula: " + e.getMessage(), e);
        }
        return matricula;
    }

    public Optional<Matricula> findById(Long id) {
        String sql = "SELECT * FROM matriculas WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar matrícula: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public boolean existsByAlunoAndCurso(Long idAluno, Long idCurso) {
        String sql = "SELECT COUNT(*) FROM matriculas WHERE id_aluno = ? AND id_curso = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idAluno);
            stmt.setLong(2, idCurso);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar matrícula duplicada: " + e.getMessage(), e);
        }
        return false;
    }

    public List<Matricula> findAll() {
        String sql = "SELECT * FROM matriculas ORDER BY data_matricula DESC";
        List<Matricula> lista = new ArrayList<>();
        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar matrículas: " + e.getMessage(), e);
        }
        return lista;
    }

    public void delete(Long id) {
        String sql = "DELETE FROM matriculas WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar matrícula: " + e.getMessage(), e);
        }
    }

    private Matricula mapResultSet(ResultSet rs) throws SQLException {
        Matricula m = new Matricula();
        m.setId(rs.getLong("id"));
        m.setIdAluno(rs.getLong("id_aluno"));
        m.setIdCurso(rs.getLong("id_curso"));
        m.setDataMatricula(rs.getDate("data_matricula").toLocalDate());
        m.setValor(rs.getDouble("valor"));
        return m;
    }
}
