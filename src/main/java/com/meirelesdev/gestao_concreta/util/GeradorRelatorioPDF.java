package com.meirelesdev.gestao_concreta.util;

import com.meirelesdev.gestao_concreta.dao.VendaDAO;
import com.meirelesdev.gestao_concreta.model.Venda;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.meirelesdev.gestao_concreta.dao.DashboardDAO;
import java.text.NumberFormat;
import java.util.Locale;

import com.meirelesdev.gestao_concreta.dao.ProdutoDAO;
import com.meirelesdev.gestao_concreta.model.Produto;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class GeradorRelatorioPDF {

    public static void gerarProdutos(File arquivo) {

        Document document = new Document();

        try {

            PdfWriter.getInstance(document, new FileOutputStream(arquivo));

            document.open();

            Font titulo =
                    new Font(Font.HELVETICA, 18, Font.BOLD);

            document.add(new Paragraph("RELATÓRIO DE PRODUTOS", titulo));

            document.add(new Paragraph(" "));

            PdfPTable tabela = new PdfPTable(5);

            tabela.setWidthPercentage(100);

            tabela.addCell(new PdfPCell(new Phrase("Código")));
            tabela.addCell(new PdfPCell(new Phrase("Produto")));
            tabela.addCell(new PdfPCell(new Phrase("Compra")));
            tabela.addCell(new PdfPCell(new Phrase("Venda")));
            tabela.addCell(new PdfPCell(new Phrase("Estoque")));

            ProdutoDAO produtoDAO = new ProdutoDAO();

            List<Produto> produtos = produtoDAO.listarTodos();

            for (Produto produto : produtos) {

                tabela.addCell(String.valueOf(produto.getId()));

                tabela.addCell(produto.getNome());

                tabela.addCell(String.format("R$ %.2f",
                        produto.getPrecoCompra()));

                tabela.addCell(String.format("R$ %.2f",
                        produto.getPrecoVenda()));

                tabela.addCell(String.valueOf(produto.getEstoque()));

            }

            document.add(tabela);

            document.close();

        } catch (Exception e) {

            throw new RuntimeException("Erro ao gerar PDF.", e);

        }

    }

    public static void gerarVendas(File arquivo) {

        Document document = new Document();

        try {

            PdfWriter.getInstance(document, new FileOutputStream(arquivo));

            document.open();

            Font titulo =
                    new Font(Font.HELVETICA, 18, Font.BOLD);

            document.add(new Paragraph("RELATÓRIO DE VENDAS", titulo));

            document.add(new Paragraph(" "));

            PdfPTable tabela = new PdfPTable(4);

            tabela.setWidthPercentage(100);

            tabela.addCell(new PdfPCell(new Phrase("Código")));
            tabela.addCell(new PdfPCell(new Phrase("Data")));
            tabela.addCell(new PdfPCell(new Phrase("Pagamento")));
            tabela.addCell(new PdfPCell(new Phrase("Valor Total")));

            VendaDAO vendaDAO = new VendaDAO();

            List<Venda> vendas = vendaDAO.listarTodas();

            for (Venda venda : vendas) {

                tabela.addCell(String.valueOf(venda.getId()));

                tabela.addCell(venda.getDataVenda().toString());

                tabela.addCell(venda.getFormaPagamento());

                tabela.addCell(String.format("R$ %.2f",
                        venda.getValorTotal()));

            }

            document.add(tabela);

            document.close();

        } catch (Exception e) {

            throw new RuntimeException("Erro ao gerar relatório.", e);

        }

    }

    public static void gerarFaturamento(File arquivo) {

        Document document = new Document();

        try {

            PdfWriter.getInstance(document, new FileOutputStream(arquivo));

            document.open();

            Font titulo = new Font(Font.HELVETICA, 18, Font.BOLD);

            Font texto = new Font(Font.HELVETICA, 14);

            document.add(new Paragraph("RELATÓRIO DE FATURAMENTO", titulo));

            document.add(new Paragraph(" "));

            DashboardDAO dashboardDAO = new DashboardDAO();

            double faturamento = dashboardDAO.getFaturamentoTotal();

            int vendas = dashboardDAO.getTotalVendas();

            NumberFormat formato =
                    NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

            document.add(new Paragraph(
                    "Faturamento Total: " + formato.format(faturamento), texto));

            document.add(new Paragraph(
                    "Quantidade de Vendas: " + vendas, texto));

            if (vendas > 0) {

                document.add(new Paragraph(
                        "Ticket Médio: " +
                                formato.format(faturamento / vendas), texto));

            }

            document.close();

        } catch (Exception e) {

            throw new RuntimeException("Erro ao gerar relatório.", e);

        }

    }

    public static void gerarEstoqueBaixo(File arquivo) {

        Document document = new Document();

        try {

            PdfWriter.getInstance(document, new FileOutputStream(arquivo));

            document.open();

            Font titulo =
                    new Font(Font.HELVETICA, 18, Font.BOLD);

            document.add(new Paragraph("RELATÓRIO DE ESTOQUE BAIXO", titulo));

            document.add(new Paragraph(" "));

            PdfPTable tabela = new PdfPTable(4);

            tabela.setWidthPercentage(100);

            tabela.addCell(new PdfPCell(new Phrase("Código")));
            tabela.addCell(new PdfPCell(new Phrase("Produto")));
            tabela.addCell(new PdfPCell(new Phrase("Estoque")));
            tabela.addCell(new PdfPCell(new Phrase("Estoque Mínimo")));

            DashboardDAO dashboardDAO = new DashboardDAO();

            List<Produto> produtos =
                    dashboardDAO.listarProdutosEstoqueBaixo();

            for (Produto produto : produtos) {

                tabela.addCell(String.valueOf(produto.getId()));

                tabela.addCell(produto.getNome());

                tabela.addCell(String.valueOf(produto.getEstoque()));

                tabela.addCell(String.valueOf(produto.getEstoqueMinimo()));

            }

            document.add(tabela);

            document.close();

        } catch (Exception e) {

            throw new RuntimeException("Erro ao gerar relatório.", e);

        }

    }

    public static void gerarProdutosMaisVendidos(File arquivo) {

        Document document = new Document();

        try {

            PdfWriter.getInstance(document, new FileOutputStream(arquivo));

            document.open();

            Font titulo =
                    new Font(Font.HELVETICA, 18, Font.BOLD);

            document.add(new Paragraph("RELATÓRIO DE PRODUTOS MAIS VENDIDOS", titulo));

            document.add(new Paragraph(" "));

            PdfPTable tabela = new PdfPTable(3);

            tabela.setWidthPercentage(100);

            tabela.addCell(new PdfPCell(new Phrase("Posição")));
            tabela.addCell(new PdfPCell(new Phrase("Produto")));
            tabela.addCell(new PdfPCell(new Phrase("Quantidade Vendida")));

            VendaDAO vendaDAO = new VendaDAO();

            List<Object[]> lista = vendaDAO.listarProdutosMaisVendidos();

            int posicao = 1;

            for (Object[] item : lista) {

                tabela.addCell(String.valueOf(posicao++));

                tabela.addCell(item[0].toString());

                tabela.addCell(item[1].toString());

            }

            document.add(tabela);

            document.close();

        } catch (Exception e) {

            throw new RuntimeException("Erro ao gerar relatório.", e);

        }

    }

}