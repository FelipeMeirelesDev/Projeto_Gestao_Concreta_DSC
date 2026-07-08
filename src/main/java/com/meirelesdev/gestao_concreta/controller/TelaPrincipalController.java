package com.meirelesdev.gestao_concreta.controller;

import com.meirelesdev.gestao_concreta.model.Usuario;
import com.meirelesdev.gestao_concreta.util.SessaoUsuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TelaPrincipalController implements Initializable {

    @FXML
    private AnchorPane painelConteudo;

    @FXML
    private Label lblUsuario, lblPerfil;

    @FXML
    private Button btnInicio, btnProdutos, btnVendas, btnRelatorios, btnConfiguracoes;

    @FXML
    private Button btnSair, btnUsuarios;

    private static final String BOTAO_NORMAL =
            "-fx-background-color:#5E5473;" +
                    "-fx-text-fill:white;" +
                    "-fx-font-size:14px;" +
                    "-fx-background-radius:8;";

    private static final String BOTAO_SELECIONADO =
            "-fx-background-color:#19B5A5;" +
                    "-fx-text-fill:white;" +
                    "-fx-font-size:14px;" +
                    "-fx-font-weight:bold;" +
                    "-fx-background-radius:8;";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Usuario usuario = SessaoUsuario.getUsuarioLogado();

        if (usuario != null) {

            lblUsuario.setText(usuario.getNome());
            lblPerfil.setText(usuario.getPerfil());

            boolean administrador =
                    "Administrador".equals(usuario.getPerfil());

            btnUsuarios.setVisible(administrador);
            btnUsuarios.setManaged(administrador);

        }

        navegar("/com/meirelesdev/gestao_concreta/Inicio.fxml", btnInicio);
    }

    /**
     * Carrega uma tela no painel principal.
     */
    private void navegar(String arquivoFXML, Button botaoSelecionado) {

        selecionarBotao(botaoSelecionado);

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(arquivoFXML));
            Parent root = loader.load();

            painelConteudo.getChildren().setAll(root);

            AnchorPane.setTopAnchor(root, 0.0);
            AnchorPane.setBottomAnchor(root, 0.0);
            AnchorPane.setLeftAnchor(root, 0.0);
            AnchorPane.setRightAnchor(root, 0.0);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    /**
     * Destaca somente o botão selecionado.
     */
    private void selecionarBotao(Button selecionado) {

        Button[] botoes = {
                btnInicio,
                btnProdutos,
                btnVendas,
                btnRelatorios,
                btnUsuarios,
                btnConfiguracoes
        };

        for (Button botao : botoes) {
            botao.setStyle(BOTAO_NORMAL);
        }

        selecionado.setStyle(BOTAO_SELECIONADO);

    }

    @FXML
    private void abrirInicio() {
        navegar("/com/meirelesdev/gestao_concreta/Inicio.fxml", btnInicio);
    }

    @FXML
    private void abrirProdutos() {
        navegar("/com/meirelesdev/gestao_concreta/Produtos.fxml", btnProdutos);
    }

    @FXML
    private void abrirVendas() {
        navegar("/com/meirelesdev/gestao_concreta/Vendas.fxml", btnVendas);
    }

    @FXML
    private void abrirRelatorios() {
        navegar("/com/meirelesdev/gestao_concreta/Relatorio.fxml", btnRelatorios);
    }

    @FXML
    private void abrirConfiguracoes() {
        navegar("/com/meirelesdev/gestao_concreta/Configuracoes.fxml", btnConfiguracoes);
    }

    @FXML
    private void abrirUsuarios() {

        if (!SessaoUsuario.isAdministrador()) {

            javafx.scene.control.Alert alert =
                    new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);

            alert.setHeaderText(null);
            alert.setContentText("Você não possui permissão para acessar esta tela.");
            alert.showAndWait();

            return;

        }

        navegar("/com/meirelesdev/gestao_concreta/Usuarios.fxml", btnUsuarios);

    }

    @FXML
    private void sairSistema() {
        System.exit(0);
    }

}