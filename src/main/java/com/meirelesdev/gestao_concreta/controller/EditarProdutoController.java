package com.meirelesdev.gestao_concreta.controller;

import com.meirelesdev.gestao_concreta.dao.ProdutoDAO;
import com.meirelesdev.gestao_concreta.model.Produto;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditarProdutoController {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtPrecoCompra;

    @FXML
    private TextField txtPrecoVenda;

    @FXML
    private TextField txtEstoque;

    @FXML
    private TextField txtEstoqueMinimo;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnCancelar;

    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    private Produto produto;

    @FXML
    private void initialize() {

        btnSalvar.setOnAction(e -> salvarEdicao());

        btnCancelar.setOnAction(e -> fecharJanela());

    }

    public void setProduto(Produto produto) {

        this.produto = produto;

        txtNome.setText(produto.getNome());

        txtPrecoCompra.setText(String.valueOf(produto.getPrecoCompra()));

        txtPrecoVenda.setText(String.valueOf(produto.getPrecoVenda()));

        txtEstoque.setText(String.valueOf(produto.getEstoque()));

        txtEstoqueMinimo.setText(String.valueOf(produto.getEstoqueMinimo()));

    }

    private void salvarEdicao() {

        try {

            produto.setNome(txtNome.getText());

            produto.setPrecoCompra(
                    Double.parseDouble(txtPrecoCompra.getText().replace(",", "."))
            );

            produto.setPrecoVenda(
                    Double.parseDouble(txtPrecoVenda.getText().replace(",", "."))
            );

            produto.setEstoque(
                    Integer.parseInt(txtEstoque.getText())
            );

            produto.setEstoqueMinimo(
                    Integer.parseInt(txtEstoqueMinimo.getText())
            );

            produtoDAO.atualizar(produto);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Produto atualizado com sucesso!");
            alert.showAndWait();

            fecharJanela();

        } catch (Exception e) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro");
            alert.setContentText("Verifique os dados informados.");
            alert.showAndWait();

            e.printStackTrace();

        }

    }

    private void fecharJanela() {

        Stage stage = (Stage) btnCancelar.getScene().getWindow();

        stage.close();

    }

}