package com.oficina.repository;

import com.oficina.model.OrdemServico;
import com.oficina.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrdemServicoRepository {

    public OrdemServico save(OrdemServico os) {
        String sql = "INSERT INTO ordens_servico (id_veiculo, descricao, valor, status) VALUES (?, ?, ?, ?) RETURNING id";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, os.getIdVeiculo());
            stmt.setString(2, os.getDescricao());
            stmt.setDouble(3, os.getValor());
            stmt.setString(4, os.getStatus().name());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                os.setId(rs.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar ordem de serviço: " + e.getMessage(), e);
        }
        return os;
    }

    public Optional<OrdemServico> findById(Long id) {
        String sql = "SELECT * FROM ordens_servico WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar ordem de serviço: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public List<OrdemServico> findAll() {
        String sql = "SELECT * FROM ordens_servico ORDER BY id DESC";
        List<OrdemServico> lista = new ArrayList<>();
        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar ordens de serviço: " + e.getMessage(), e);
        }
        return lista;
    }

    public List<OrdemServico> findByVeiculo(Long idVeiculo) {
        String sql = "SELECT * FROM ordens_servico WHERE id_veiculo = ? ORDER BY id DESC";
        List<OrdemServico> lista = new ArrayList<>();
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, idVeiculo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar ordens do veículo: " + e.getMessage(), e);
        }
        return lista;
    }

    public void update(OrdemServico os) {
        String sql = "UPDATE ordens_servico SET id_veiculo = ?, descricao = ?, valor = ?, status = ? WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, os.getIdVeiculo());
            stmt.setString(2, os.getDescricao());
            stmt.setDouble(3, os.getValor());
            stmt.setString(4, os.getStatus().name());
            stmt.setLong(5, os.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar ordem de serviço: " + e.getMessage(), e);
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM ordens_servico WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar ordem de serviço: " + e.getMessage(), e);
        }
    }

    private OrdemServico mapResultSet(ResultSet rs) throws SQLException {
        OrdemServico os = new OrdemServico();
        os.setId(rs.getLong("id"));
        os.setIdVeiculo(rs.getLong("id_veiculo"));
        os.setDescricao(rs.getString("descricao"));
        os.setValor(rs.getDouble("valor"));
        os.setStatus(OrdemServico.Status.valueOf(rs.getString("status")));
        return os;
    }
}
