package com.oficina.repository;

import com.oficina.model.Cliente;
import com.oficina.util.Conexao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteRepository {

    public Cliente save(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome, telefone) VALUES (?, ?) RETURNING id";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cliente.setId(rs.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar cliente: " + e.getMessage(), e);
        }
        return cliente;
    }

    public Optional<Cliente> findById(Long id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return Optional.of(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar cliente: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    public List<Cliente> findAll() {
        String sql = "SELECT * FROM clientes ORDER BY nome";
        List<Cliente> clientes = new ArrayList<>();
        try (Connection conn = Conexao.getConexao();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                clientes.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar clientes: " + e.getMessage(), e);
        }
        return clientes;
    }

    public void update(Cliente cliente) {
        String sql = "UPDATE clientes SET nome = ?, telefone = ? WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getTelefone());
            stmt.setLong(3, cliente.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar cliente: " + e.getMessage(), e);
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM clientes WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar cliente: " + e.getMessage(), e);
        }
    }

    private Cliente mapResultSet(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setId(rs.getLong("id"));
        c.setNome(rs.getString("nome"));
        c.setTelefone(rs.getString("telefone"));
        return c;
    }
}
