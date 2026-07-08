package com.meirelesdev.gestao_concreta.controller;

import com.meirelesdev.gestao_concreta.util.GeradorRelatorioPDF;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class RelatorioController implements Initializable {

    @FXML
    private Button btnProdutos;

    @FXML
    private Button btnVendas;

    @FXML
    private Button btnFaturamento;

    @FXML
    private Button btnEstoque;

    @FXML
    private Button btnMaisVendidos;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btnProdutos.setOnAction(e -> gerarRelatorioProdutos());

        btnVendas.setOnAction(e -> gerarRelatorioVendas());

        btnFaturamento.setOnAction(e -> gerarRelatorioFaturamento());

        btnEstoque.setOnAction(e -> gerarRelatorioEstoque());

        btnMaisVendidos.setOnAction(e -> gerarRelatorioMaisVendidos());

    }

    private void gerarRelatorioProdutos() {

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Salvar Relatório");

        fileChooser.setInitialFileName("Relatorio_Produtos.pdf");

        fileChooser.getExtensionFilters().add(

                new FileChooser.ExtensionFilter(
                        "Arquivo PDF",
                        "*.pdf"
                )

        );

        File arquivo = fileChooser.showSaveDialog(null);

        if (arquivo == null) {

            return;

        }

        try {

            GeradorRelatorioPDF.gerarProdutos(arquivo);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setHeaderText(null);

            alert.setContentText("Relatório gerado com sucesso!");

            alert.showAndWait();

        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setHeaderText("Erro");

            alert.setContentText("Não foi possível gerar o relatório.");

            alert.showAndWait();

            e.printStackTrace();

        }

    }

    private void gerarRelatorioVendas() {

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Salvar Relatório");

        fileChooser.setInitialFileName("Relatorio_Vendas.pdf");

        fileChooser.getExtensionFilters().add(

                new FileChooser.ExtensionFilter(
                        "Arquivo PDF",
                        "*.pdf"
                )

        );

        File arquivo = fileChooser.showSaveDialog(null);

        if (arquivo == null) {

            return;

        }

        try {

            GeradorRelatorioPDF.gerarVendas(arquivo);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setHeaderText(null);

            alert.setContentText("Relatório gerado com sucesso!");

            alert.showAndWait();

        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setHeaderText("Erro");

            alert.setContentText("Não foi possível gerar o relatório.");

            alert.showAndWait();

            e.printStackTrace();

        }

    }

    private void gerarRelatorioFaturamento() {

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Salvar Relatório");

        fileChooser.setInitialFileName("Relatorio_Faturamento.pdf");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Arquivo PDF", "*.pdf")
        );

        File arquivo = fileChooser.showSaveDialog(null);

        if (arquivo == null) {
            return;
        }

        try {

            GeradorRelatorioPDF.gerarFaturamento(arquivo);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setHeaderText(null);

            alert.setContentText("Relatório gerado com sucesso!");

            alert.showAndWait();

        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setHeaderText("Erro");

            alert.setContentText("Não foi possível gerar o relatório.");

            alert.showAndWait();

            e.printStackTrace();

        }

    }

    private void gerarRelatorioEstoque() {

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Salvar Relatório");

        fileChooser.setInitialFileName("Relatorio_Estoque_Baixo.pdf");

        fileChooser.getExtensionFilters().add(

                new FileChooser.ExtensionFilter(
                        "Arquivo PDF",
                        "*.pdf"
                )

        );

        File arquivo = fileChooser.showSaveDialog(null);

        if (arquivo == null) {

            return;

        }

        try {

            GeradorRelatorioPDF.gerarEstoqueBaixo(arquivo);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setHeaderText(null);

            alert.setContentText("Relatório gerado com sucesso!");

            alert.showAndWait();

        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setHeaderText("Erro");

            alert.setContentText("Não foi possível gerar o relatório.");

            alert.showAndWait();

            e.printStackTrace();

        }

    }

    private void gerarRelatorioMaisVendidos() {

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Salvar Relatório");

        fileChooser.setInitialFileName("Relatorio_Produtos_Mais_Vendidos.pdf");

        fileChooser.getExtensionFilters().add(

                new FileChooser.ExtensionFilter(
                        "Arquivo PDF",
                        "*.pdf"
                )

        );

        File arquivo = fileChooser.showSaveDialog(null);

        if (arquivo == null) {

            return;

        }

        try {

            GeradorRelatorioPDF.gerarProdutosMaisVendidos(arquivo);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setHeaderText(null);

            alert.setContentText("Relatório gerado com sucesso!");

            alert.showAndWait();

        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setHeaderText("Erro");

            alert.setContentText("Não foi possível gerar o relatório.");

            alert.showAndWait();

            e.printStackTrace();

        }

    }

}