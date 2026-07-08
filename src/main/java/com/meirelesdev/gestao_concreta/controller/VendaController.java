package com.meirelesdev.gestao_concreta.controller;

import com.meirelesdev.gestao_concreta.dao.ProdutoDAO;
import com.meirelesdev.gestao_concreta.dao.VendaDAO;
import com.meirelesdev.gestao_concreta.model.ItemVenda;
import com.meirelesdev.gestao_concreta.model.Produto;
import com.meirelesdev.gestao_concreta.model.Venda;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class VendaController implements Initializable {

    //==========================
    // CAMPOS
    //==========================

    @FXML
    private TextField txtPesquisar;

    @FXML
    private TextField txtQuantidade;

    @FXML
    private Label lblProdutoSelecionado;

    @FXML
    private Label lblValorTotal;

    @FXML
    private ComboBox<String> cbPagamento;

    @FXML
    private DatePicker dpDataVenda;

    @FXML
    private Button btnPesquisar;

    @FXML
    private Button btnAdicionar;

    @FXML
    private Button btnRemoverItem;

    @FXML
    private Button btnLimpar;

    @FXML
    private Button btnRegistrarVenda;

    //==========================
    // TABELA PRODUTOS
    //==========================

    @FXML
    private TableView<Produto> tableProdutos;

    @FXML
    private TableColumn<Produto, Integer> colCodigo;

    @FXML
    private TableColumn<Produto, String> colNome;

    @FXML
    private TableColumn<Produto, Double> colPreco;

    @FXML
    private TableColumn<Produto, Integer> colEstoque;

    @FXML
    private TableColumn<Produto, String> colStatus;

    //==========================
    // TABELA ITENS
    //==========================

    @FXML
    private TableView<ItemVenda> tableItensVenda;

    @FXML
    private TableColumn<ItemVenda, String> colProdutoVenda;

    @FXML
    private TableColumn<ItemVenda, Integer> colQtdVenda;

    @FXML
    private TableColumn<ItemVenda, Double> colValorUnitario;

    @FXML
    private TableColumn<ItemVenda, Double> colSubtotal;

    //==========================
    // DAOs
    //==========================

    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    private final VendaDAO vendaDAO = new VendaDAO();

    //==========================
    // LISTAS
    //==========================

    private final ObservableList<Produto> listaProdutos =
            FXCollections.observableArrayList();

    private final ObservableList<ItemVenda> listaItens =
            FXCollections.observableArrayList();

    private Produto produtoSelecionado;

    //==========================
    // INITIALIZE
    //==========================

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        configurarTabelaProdutos();

        configurarTabelaItens();

        carregarProdutos();

        carregarComboPagamento();

        dpDataVenda.setValue(LocalDate.now());

        lblValorTotal.setText("R$ 0,00");

        tableProdutos.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, antigo, novo) -> {

                    if (novo != null) {

                        produtoSelecionado = novo;

                        lblProdutoSelecionado.setText(
                                novo.getNome()
                        );

                    }

                });

        btnAdicionar.setOnAction(e -> adicionarItem());

        btnRemoverItem.setOnAction(e -> removerItem());

        btnLimpar.setOnAction(e -> limparVenda());

        btnRegistrarVenda.setOnAction(e -> registrarVenda());

        btnPesquisar.setOnAction(e -> pesquisarProdutos());

        txtPesquisar.setOnAction(e -> pesquisarProdutos());

    }

    private void configurarTabelaProdutos() {

        colCodigo.setCellValueFactory(new PropertyValueFactory<>("id"));

        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        colPreco.setCellValueFactory(new PropertyValueFactory<>("precoVenda"));

        colEstoque.setCellValueFactory(new PropertyValueFactory<>("estoque"));

        colStatus.setCellValueFactory(cell -> {

            Produto produto = cell.getValue();

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

    private void configurarTabelaItens() {

        colProdutoVenda.setCellValueFactory(cell ->

                new SimpleStringProperty(
                        cell.getValue().getProduto().getNome()
                )

        );

        colQtdVenda.setCellValueFactory(
                new PropertyValueFactory<>("quantidade")
        );

        colValorUnitario.setCellValueFactory(
                new PropertyValueFactory<>("precoUnitario")
        );

        colSubtotal.setCellValueFactory(
                new PropertyValueFactory<>("subtotal")
        );

        tableItensVenda.setItems(listaItens);

    }

    private void carregarProdutos() {

        listaProdutos.clear();

        listaProdutos.addAll(produtoDAO.listarTodos());

        tableProdutos.setItems(listaProdutos);

    }

    private void carregarComboPagamento() {

        cbPagamento.getItems().addAll(

                "Dinheiro",
                "PIX",
                "Cartão de Débito",
                "Cartão de Crédito"

        );

        cbPagamento.getSelectionModel().selectFirst();

    }

    private void adicionarItem() {

        if (produtoSelecionado == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Selecione um produto.");
            alert.showAndWait();

            return;

        }

        if (txtQuantidade.getText().isBlank()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Informe a quantidade.");
            alert.showAndWait();

            return;

        }

        try {

            int quantidade = Integer.parseInt(txtQuantidade.getText());

            if (quantidade <= 0) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setContentText("Quantidade inválida.");
                alert.showAndWait();

                return;

            }

            if (quantidade > produtoSelecionado.getEstoque()) {

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setContentText("Estoque insuficiente.");
                alert.showAndWait();

                return;

            }

            ItemVenda item = new ItemVenda();

            item.setProduto(produtoSelecionado);

            item.setQuantidade(quantidade);

            item.setPrecoUnitario(produtoSelecionado.getPrecoVenda());

            listaItens.add(item);

            atualizarValorTotal();

            txtQuantidade.clear();

            lblProdutoSelecionado.setText("Nenhum produto selecionado");

            tableProdutos.getSelectionModel().clearSelection();

            produtoSelecionado = null;

        } catch (NumberFormatException e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Digite apenas números.");
            alert.showAndWait();

        }

    }

    private void removerItem() {

        ItemVenda item = tableItensVenda.getSelectionModel().getSelectedItem();

        if (item == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Selecione um item da venda.");
            alert.showAndWait();

            return;

        }

        listaItens.remove(item);

        atualizarValorTotal();

    }

    private void atualizarValorTotal() {

        double total = 0;

        for (ItemVenda item : listaItens) {

            total += item.getSubtotal();

        }

        lblValorTotal.setText(
                String.format("R$ %.2f", total)
        );

    }

    private void limparVenda() {

        listaItens.clear();

        txtPesquisar.clear();

        txtQuantidade.clear();

        lblProdutoSelecionado.setText("Nenhum produto selecionado");

        produtoSelecionado = null;

        tableProdutos.getSelectionModel().clearSelection();

        tableItensVenda.getSelectionModel().clearSelection();

        cbPagamento.getSelectionModel().selectFirst();

        dpDataVenda.setValue(LocalDate.now());

        atualizarValorTotal();

        carregarProdutos();

    }

    private void registrarVenda() {

        if (listaItens.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Adicione pelo menos um item à venda.");
            alert.showAndWait();

            return;

        }

        if (cbPagamento.getValue() == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Selecione a forma de pagamento.");
            alert.showAndWait();

            return;

        }

        Venda venda = new Venda();

        venda.setDataVenda(dpDataVenda.getValue());

        venda.setFormaPagamento(cbPagamento.getValue());

        double total = 0;

        for (ItemVenda item : listaItens) {

            total += item.getSubtotal();

        }

        venda.setValorTotal(total);

        try {

            vendaDAO.registrarVenda(venda, listaItens);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Venda registrada com sucesso!");
            alert.showAndWait();

            limparVenda();

            carregarProdutos();

        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro");

            alert.setContentText("Erro ao registrar venda.");

            alert.showAndWait();

            e.printStackTrace();

        }

    }

    private void pesquisarProdutos() {

        String pesquisa = txtPesquisar.getText().trim();

        if (pesquisa.isEmpty()) {

            carregarProdutos();

            return;

        }

        listaProdutos.clear();

        listaProdutos.addAll(produtoDAO.pesquisar(pesquisa));

    }
}