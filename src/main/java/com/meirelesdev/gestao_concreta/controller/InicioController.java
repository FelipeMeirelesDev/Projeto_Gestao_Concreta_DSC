package com.meirelesdev.gestao_concreta.controller;

import com.meirelesdev.gestao_concreta.dao.DashboardDAO;
import com.meirelesdev.gestao_concreta.model.Produto;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class InicioController implements Initializable {

    //==================================================
    // LABELS
    //==================================================

    @FXML
    private Label lblProdutos;

    @FXML
    private Label lblVendas;

    @FXML
    private Label lblFaturamento;

    @FXML
    private Label lblEstoque;

    //==================================================
    // GRÁFICO
    //==================================================

    @FXML
    private LineChart<String, Number> lineChart;

    @FXML
    private CategoryAxis xLine;

    @FXML
    private NumberAxis yLine;

    //==================================================
    // TABELA
    //==================================================

    @FXML
    private TableView<Produto> tableEstoque;

    @FXML
    private TableColumn<Produto, String> colProduto;

    @FXML
    private TableColumn<Produto, Integer> colQtd;

    @FXML
    private TableColumn<Produto, Integer> colMinimo;

    //==================================================
    // DAO
    //==================================================

    private final DashboardDAO dashboardDAO = new DashboardDAO();

    //==================================================
    // INITIALIZE
    //==================================================

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        configurarTabela();

        carregarCards();

        carregarTabela();

        carregarGrafico();

    }

    //==================================================
    // CONFIGURA TABELA
    //==================================================

    private void configurarTabela() {

        colProduto.setCellValueFactory(
                new PropertyValueFactory<>("nome")
        );

        colQtd.setCellValueFactory(
                new PropertyValueFactory<>("estoque")
        );

        colMinimo.setCellValueFactory(
                new PropertyValueFactory<>("estoqueMinimo")
        );

    }

    //==================================================
    // CARDS
    //==================================================

    private void carregarCards() {

        lblProdutos.setText(
                String.valueOf(
                        dashboardDAO.getTotalProdutos()
                )
        );

        lblVendas.setText(
                String.valueOf(
                        dashboardDAO.getTotalVendas()
                )
        );

        NumberFormat formato =
                NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

        lblFaturamento.setText(
                formato.format(
                        dashboardDAO.getFaturamentoTotal()
                )
        );

        lblEstoque.setText(
                String.valueOf(
                        dashboardDAO.getTotalEstoqueBaixo()
                )
        );

    }

    //==================================================
    // TABELA
    //==================================================

    private void carregarTabela() {

        tableEstoque.getItems().clear();

        tableEstoque.getItems().addAll(

                dashboardDAO.listarProdutosEstoqueBaixo()

        );

    }

    //==================================================
    // GRÁFICO
    //==================================================

    private void carregarGrafico() {

        lineChart.getData().clear();

        XYChart.Series<String, Number> serie =
                new XYChart.Series<>();

        Map<String, Double> dados =
                dashboardDAO.getVendasUltimosMeses();

        for (Map.Entry<String, Double> item : dados.entrySet()) {

            serie.getData().add(

                    new XYChart.Data<>(

                            item.getKey(),

                            item.getValue()

                    )

            );

        }

        serie.setName("Vendas");

        lineChart.getData().add(serie);

        lineChart.setLegendVisible(false);

    }

}