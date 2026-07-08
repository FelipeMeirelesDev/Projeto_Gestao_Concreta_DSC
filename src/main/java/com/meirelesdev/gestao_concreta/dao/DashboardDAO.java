package com.meirelesdev.gestao_concreta.dao;

import com.meirelesdev.gestao_concreta.factory.ConnectionFactory;
import com.meirelesdev.gestao_concreta.model.Produto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashboardDAO {

    //=========================================
    // TOTAL DE PRODUTOS
    //=========================================

    public int getTotalProdutos() {

        String sql = "SELECT COUNT(*) FROM produtos";

        try (
                Connection conexao = ConnectionFactory.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            if (rs.next()) {

                return rs.getInt(1);

            }

        } catch (SQLException e) {

            throw new RuntimeException("Erro ao buscar total de produtos.", e);

        }

        return 0;

    }

    //=========================================
    // TOTAL DE VENDAS
    //=========================================

    public int getTotalVendas() {

        String sql = "SELECT COUNT(*) FROM vendas";

        try (
                Connection conexao = ConnectionFactory.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            if (rs.next()) {

                return rs.getInt(1);

            }

        } catch (SQLException e) {

            throw new RuntimeException("Erro ao buscar total de vendas.", e);

        }

        return 0;

    }

    //=========================================
    // FATURAMENTO
    //=========================================

    public double getFaturamentoTotal() {

        String sql = "SELECT SUM(valor_total) FROM vendas";

        try (
                Connection conexao = ConnectionFactory.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            if (rs.next()) {

                Double total = rs.getDouble(1);

                if (rs.wasNull()) {
                    return 0;
                }

                return total;

            }

        } catch (SQLException e) {

            throw new RuntimeException("Erro ao buscar faturamento.", e);

        }

        return 0;

    }

    //=========================================
    // PRODUTOS COM ESTOQUE BAIXO
    //=========================================

    public int getTotalEstoqueBaixo() {

        String sql = """
                SELECT COUNT(*)
                FROM produtos
                WHERE estoque <= estoque_minimo
                """;

        try (
                Connection conexao = ConnectionFactory.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            if (rs.next()) {

                return rs.getInt(1);

            }

        } catch (SQLException e) {

            throw new RuntimeException("Erro ao buscar produtos com estoque baixo.", e);

        }

        return 0;

    }

    public List<Produto> listarProdutosEstoqueBaixo() {

        List<Produto> produtos = new ArrayList<>();

        String sql = """
            SELECT *
            FROM produtos
            WHERE estoque <= estoque_minimo
            ORDER BY estoque ASC
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

            throw new RuntimeException("Erro ao listar produtos com estoque baixo.", e);

        }

        return produtos;

    }

    public Map<String, Double> getVendasUltimosMeses() {

        Map<String, Double> vendas = new LinkedHashMap<>();

        String sql = """
            SELECT
                YEAR(data_venda) AS ano,
                MONTH(data_venda) AS mesNumero,
                DATE_FORMAT(MIN(data_venda),'%m/%Y') AS mes,
                SUM(valor_total) AS total
            FROM vendas
            WHERE data_venda IS NOT NULL
            GROUP BY YEAR(data_venda), MONTH(data_venda)
            ORDER BY ano DESC, mesNumero DESC
            LIMIT 6
            """;

        List<String> meses = new ArrayList<>();
        List<Double> valores = new ArrayList<>();

        try (
                Connection conexao = ConnectionFactory.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                meses.add(rs.getString("mes"));
                valores.add(rs.getDouble("total"));

            }

            for (int i = meses.size() - 1; i >= 0; i--) {

                vendas.put(meses.get(i), valores.get(i));

            }

        } catch (SQLException e) {

            throw new RuntimeException("Erro ao buscar vendas dos últimos meses.", e);

        }

        return vendas;

    }
}

