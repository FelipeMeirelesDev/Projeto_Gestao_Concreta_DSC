package com.meirelesdev.gestao_concreta.controller;

import com.meirelesdev.gestao_concreta.dao.ProdutoDAO;
import com.meirelesdev.gestao_concreta.model.Produto;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;

import java.net.URL;
import java.util.ResourceBundle;

public class ProdutoController implements Initializable {

    @FXML
    private TextField txtNome, txtPrecoCompra, txtPrecoVenda, txtQuantidadeInicial;

    @FXML
    private TextField txtEstoqueMinimo, txtPesquisar;

    @FXML
    private Button btnSalvar, btnLimpar, btnPesquisar, btnEditar;

    @FXML
    private Button btnEntradaEstoque, btnRemover;

    @FXML
    private Label lblProdutos, lblAlerta, lblEsgotados;

    @FXML
    private TableView<Produto> tableProdutos;

    @FXML
    private TableColumn<Produto, Integer> colCodigo;

    @FXML
    private TableColumn<Produto, String> colNome;

    @FXML
    private TableColumn<Produto, Double> colCompra;

    @FXML
    private TableColumn<Produto, Double> colVenda;

    @FXML
    private TableColumn<Produto, Integer> colEstoque;

    @FXML
    private TableColumn<Produto, Integer> colMinimo;

    @FXML
    private TableColumn<Produto, String> colStatus;

    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    private final ObservableList<Produto> listaProdutos =
            FXCollections.observableArrayList();

    private Produto produtoSelecionado;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        configurarTabela();

        carregarProdutos();

        btnSalvar.setOnAction(e -> salvarProduto());

        btnLimpar.setOnAction(e -> limparCampos());

        btnEditar.setOnAction(e -> editarProduto());

        btnRemover.setOnAction(e -> removerProduto());

        tableProdutos.getSelectionModel().selectedItemProperty().addListener(
                (obs, antigo, novo) -> {

                    if (novo != null) {

                        preencherCampos(novo);

                    }

                }
        );

        btnEntradaEstoque.setOnAction(e -> entradaEstoque());

    }



    private void configurarTabela() {

        colCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCompra.setCellValueFactory(new PropertyValueFactory<>("precoCompra"));
        colVenda.setCellValueFactory(new PropertyValueFactory<>("precoVenda"));
        colEstoque.setCellValueFactory(new PropertyValueFactory<>("estoque"));
        colMinimo.setCellValueFactory(new PropertyValueFactory<>("estoqueMinimo"));

        colStatus.setCellValueFactory(cellData -> {

            Produto produto = cellData.getValue();

            String status;

            if (produto.getEstoque() == 0) {

                status = "Esgotado";

            } else if (produto.getEstoque() <= produto.getEstoqueMinimo()) {

                status = "Estoque Baixo";

            } else {

                status = "Disponível";

            }

            return new SimpleStringProperty(status);

        });

    }

    private void carregarProdutos() {

        listaProdutos.clear();

        listaProdutos.addAll(produtoDAO.listarTodos());

        tableProdutos.setItems(listaProdutos);

        atualizarCards();

    }

    private void atualizarCards() {

        int total = listaProdutos.size();

        int alerta = 0;

        int esgotados = 0;

        for (Produto produto : listaProdutos) {

            if (produto.getEstoque() == 0) {

                esgotados++;

            } else if (produto.getEstoque() <= produto.getEstoqueMinimo()) {

                alerta++;

            }

        }

        lblProdutos.setText(String.valueOf(total));
        lblAlerta.setText(String.valueOf(alerta));
        lblEsgotados.setText(String.valueOf(esgotados));

    }

    private void salvarProduto() {

        try {

            Produto produto = new Produto();

            produto.setNome(txtNome.getText());

            produto.setPrecoCompra(
                    Double.parseDouble(txtPrecoCompra.getText().replace(",", "."))
            );

            produto.setPrecoVenda(
                    Double.parseDouble(txtPrecoVenda.getText().replace(",", "."))
            );

            produto.setEstoque(
                    Integer.parseInt(txtQuantidadeInicial.getText())
            );

            produto.setEstoqueMinimo(
                    Integer.parseInt(txtEstoqueMinimo.getText())
            );

            produtoDAO.salvar(produto);

            limparCampos();

            carregarProdutos();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Produto cadastrado com sucesso!");
            alert.showAndWait();

        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro");
            alert.setContentText("Verifique os dados informados.");
            alert.showAndWait();

            e.printStackTrace();

        }

    }

    private void limparCampos() {

        txtNome.clear();
        txtPrecoCompra.clear();
        txtPrecoVenda.clear();
        txtQuantidadeInicial.clear();
        txtEstoqueMinimo.clear();

        txtNome.requestFocus();

        produtoSelecionado = null;

        tableProdutos.getSelectionModel().clearSelection();
    }

    private void preencherCampos(Produto produto) {

        produtoSelecionado = produto;

        txtNome.setText(produto.getNome());

        txtPrecoCompra.setText(String.valueOf(produto.getPrecoCompra()));

        txtPrecoVenda.setText(String.valueOf(produto.getPrecoVenda()));

        txtQuantidadeInicial.setText(String.valueOf(produto.getEstoque()));

        txtEstoqueMinimo.setText(String.valueOf(produto.getEstoqueMinimo()));

    }

    private void editarProduto() {

        Produto produto = tableProdutos.getSelectionModel().getSelectedItem();

        if (produto == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Selecione um produto para editar.");
            alert.showAndWait();

            return;

        }

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/meirelesdev/gestao_concreta/EditarProduto.fxml")
            );

            Parent root = loader.load();

            EditarProdutoController controller = loader.getController();

            controller.setProduto(produto);

            Stage stage = new Stage();

            stage.setTitle("Editar Produto");

            stage.setScene(new Scene(root));

            stage.setResizable(false);

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.showAndWait();

            carregarProdutos();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void removerProduto() {

        if (produtoSelecionado == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Selecione um produto.");
            alert.showAndWait();

            return;

        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);

        confirmacao.setHeaderText("Confirmação");

        confirmacao.setContentText("Deseja realmente remover este produto?");

        if (confirmacao.showAndWait().get().getButtonData().isDefaultButton()) {

            produtoDAO.remover(produtoSelecionado.getId());

            carregarProdutos();

            limparCampos();

            produtoSelecionado = null;

        }

    }

    private void entradaEstoque() {

        Produto produto = tableProdutos.getSelectionModel().getSelectedItem();

        if (produto == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Selecione um produto.");
            alert.showAndWait();

            return;

        }

        TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle("Entrada de Estoque");
        dialog.setHeaderText("Produto: " + produto.getNome());
        dialog.setContentText("Quantidade:");

        Optional<String> resultado = dialog.showAndWait();

        if (resultado.isPresent()) {

            try {

                int quantidade = Integer.parseInt(resultado.get());

                if (quantidade <= 0) {

                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setContentText("Informe uma quantidade válida.");
                    alert.showAndWait();

                    return;

                }

                produtoDAO.adicionarEstoque(produto.getId(), quantidade);

                carregarProdutos();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Estoque atualizado com sucesso!");
                alert.showAndWait();

            } catch (NumberFormatException e) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Digite apenas números.");
                alert.showAndWait();

            }

        }

    }
}