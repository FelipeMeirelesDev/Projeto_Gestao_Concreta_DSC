package com.meirelesdev.gestao_concreta.dao;

import com.meirelesdev.gestao_concreta.factory.ConnectionFactory;
import com.meirelesdev.gestao_concreta.model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public void salvar(Produto produto) {

        String sql = """
            INSERT INTO produtos
            (nome, preco_compra, preco_venda, estoque, estoque_minimo)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (
                Connection conexao = ConnectionFactory.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPrecoCompra());
            stmt.setDouble(3, produto.getPrecoVenda());
            stmt.setInt(4, produto.getEstoque());
            stmt.setInt(5, produto.getEstoqueMinimo());

            stmt.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException("Erro ao salvar produto.", e);

        }

    }

    public List<Produto> listarTodos() {

        List<Produto> produtos = new ArrayList<>();

        String sql = """
            SELECT *
            FROM produtos
            ORDER BY nome
            """;

        try (
                Connection conexao = ConnectionFactory.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                Produto produto = new Produto();

                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setPrecoCompra(rs.getDouble("preco_compra"));
                produto.setPrecoVenda(rs.getDouble("preco_venda"));
                produto.setEstoque(rs.getInt("estoque"));
                produto.setEstoqueMinimo(rs.getInt("estoque_minimo"));

                produtos.add(produto);

            }

        } catch (SQLException e) {

            throw new RuntimeException("Erro ao listar produtos.", e);

        }

        return produtos;

    }

    public void atualizar(Produto produto) {

        String sql = """
        UPDATE produtos
        SET nome = ?,
            preco_compra = ?,
            preco_venda = ?,
            estoque = ?,
            estoque_minimo = ?
        WHERE id = ?
        """;

        try (
                Connection conexao = ConnectionFactory.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPrecoCompra());
            stmt.setDouble(3, produto.getPrecoVenda());
            stmt.setInt(4, produto.getEstoque());
            stmt.setInt(5, produto.getEstoqueMinimo());
            stmt.setInt(6, produto.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException("Erro ao atualizar produto.", e);

        }

    }

    public void remover(int id) {

        String sql = "DELETE FROM produtos WHERE id = ?";

        try (
                Connection conexao = ConnectionFactory.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException("Erro ao remover produto.", e);

        }

    }

    public void adicionarEstoque(int idProduto, int quantidade) {

        String sql = """
            UPDATE produtos
            SET estoque = estoque + ?
            WHERE id = ?
            """;

        try (
                Connection conexao = ConnectionFactory.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {

            stmt.setInt(1, quantidade);
            stmt.setInt(2, idProduto);

            stmt.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException("Erro ao adicionar estoque.", e);

        }

    }

    public List<Produto> pesquisar(String pesquisa) {

        List<Produto> produtos = new ArrayList<>();

        String sql = """
            SELECT *
            FROM produtos
            WHERE nome LIKE ?
               OR id LIKE ?
            ORDER BY nome
            """;

        try (
                Connection conexao = ConnectionFactory.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {

            stmt.setString(1, "%" + pesquisa + "%");
            stmt.setString(2, "%" + pesquisa + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Produto produto = new Produto();

                produto.setId(rs.getInt("id"));
                produto.setNome(rs.getString("nome"));
                produto.setPrecoCompra(rs.getDouble("preco_compra"));
                produto.setPrecoVenda(rs.getDouble("preco_venda"));
                produto.setEstoque(rs.getInt("estoque"));
                produto.setEstoqueMinimo(rs.getInt("estoque_minimo"));

                produtos.add(produto);

            }

        } catch (SQLException e) {

            throw new RuntimeException("Erro ao pesquisar produtos.", e);

        }

        return produtos;

    }
}
