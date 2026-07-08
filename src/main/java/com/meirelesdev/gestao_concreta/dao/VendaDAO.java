package com.meirelesdev.gestao_concreta.dao;

import com.meirelesdev.gestao_concreta.factory.ConnectionFactory;
import com.meirelesdev.gestao_concreta.model.ItemVenda;
import com.meirelesdev.gestao_concreta.model.Venda;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VendaDAO {

    public void registrarVenda(Venda venda, List<ItemVenda> itens) {

        String sqlVenda = """
                INSERT INTO vendas
                (data_venda, forma_pagamento, valor_total)
                VALUES (?, ?, ?)
                """;

        String sqlItem = """
                INSERT INTO itens_venda
                (venda_id, produto_id, quantidade, preco_unitario, subtotal)
                VALUES (?, ?, ?, ?, ?)
                """;

        String sqlEstoque = """
                UPDATE produtos
                SET estoque = estoque - ?
                WHERE id = ?
                """;

        Connection conexao = null;

        try {

            conexao = ConnectionFactory.getConnection();

            conexao.setAutoCommit(false);

            //--------------------------------------
            // SALVA A VENDA
            //--------------------------------------

            PreparedStatement stmtVenda = conexao.prepareStatement(
                    sqlVenda,
                    Statement.RETURN_GENERATED_KEYS
            );

            stmtVenda.setDate(1, Date.valueOf(venda.getDataVenda()));
            stmtVenda.setString(2, venda.getFormaPagamento());
            stmtVenda.setDouble(3, venda.getValorTotal());

            stmtVenda.executeUpdate();

            ResultSet rs = stmtVenda.getGeneratedKeys();

            int idVenda = 0;

            if (rs.next()) {

                idVenda = rs.getInt(1);

            }

            //--------------------------------------
            // SALVA OS ITENS
            //--------------------------------------

            PreparedStatement stmtItem =
                    conexao.prepareStatement(sqlItem);

            PreparedStatement stmtEstoque =
                    conexao.prepareStatement(sqlEstoque);

            for (ItemVenda item : itens) {

                stmtItem.setInt(1, idVenda);
                stmtItem.setInt(2, item.getProduto().getId());
                stmtItem.setInt(3, item.getQuantidade());
                stmtItem.setDouble(4, item.getPrecoUnitario());
                stmtItem.setDouble(5, item.getSubtotal());

                stmtItem.executeUpdate();

                //----------------------------------
                // BAIXA DO ESTOQUE
                //----------------------------------

                stmtEstoque.setInt(1, item.getQuantidade());
                stmtEstoque.setInt(2, item.getProduto().getId());

                stmtEstoque.executeUpdate();

            }

            conexao.commit();

        } catch (Exception e) {

            try {

                if (conexao != null) {

                    conexao.rollback();

                }

            } catch (SQLException ex) {

                ex.printStackTrace();

            }

            throw new RuntimeException("Erro ao registrar venda.", e);

        } finally {

            try {

                if (conexao != null) {

                    conexao.setAutoCommit(true);

                    conexao.close();

                }

            } catch (SQLException e) {

                e.printStackTrace();

            }

        }

    }

    public List<Venda> listarTodas() {

        List<Venda> vendas = new ArrayList<>();

        String sql = """
            SELECT *
            FROM vendas
            ORDER BY data_venda DESC
            """;

        try (
                Connection conexao = ConnectionFactory.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                Venda venda = new Venda();

                venda.setId(rs.getInt("id"));
                venda.setDataVenda(rs.getDate("data_venda").toLocalDate());
                venda.setFormaPagamento(rs.getString("forma_pagamento"));
                venda.setValorTotal(rs.getDouble("valor_total"));

                vendas.add(venda);

            }

        } catch (SQLException e) {

            throw new RuntimeException("Erro ao listar vendas.", e);

        }

        return vendas;

    }

    public List<Object[]> listarProdutosMaisVendidos() {

        List<Object[]> lista = new ArrayList<>();

        String sql = """
            SELECT
                p.nome,
                SUM(iv.quantidade) quantidade
            FROM itens_venda iv
            INNER JOIN produtos p
                ON p.id = iv.produto_id
            GROUP BY p.nome
            ORDER BY quantidade DESC
            """;

        try (
                Connection conexao = ConnectionFactory.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                lista.add(new Object[]{

                        rs.getString("nome"),
                        rs.getInt("quantidade")

                });

            }

        } catch (SQLException e) {

            throw new RuntimeException(e);

        }

        return lista;

    }

}