package com.meirelesdev.gestao_concreta.controller;

import com.meirelesdev.gestao_concreta.Application;
import com.meirelesdev.gestao_concreta.dao.UsuarioDAO;
import com.meirelesdev.gestao_concreta.model.Usuario;
import com.meirelesdev.gestao_concreta.util.SessaoUsuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private Button btnEntrar;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    public void initialize() {

        btnEntrar.setOnAction(e -> realizarLogin());

        txtSenha.setOnAction(e -> realizarLogin());

    }

    private void realizarLogin() {

        String login = txtUsuario.getText().trim();

        String senha = txtSenha.getText();

        if (login.isEmpty() || senha.isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Preencha o usuário e a senha.");
            alert.showAndWait();

            return;

        }

        Usuario usuario = usuarioDAO.autenticar(login, senha);

        if (usuario == null) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Usuário ou senha inválidos.");
            alert.showAndWait();

            txtSenha.clear();
            txtSenha.requestFocus();

            return;

        }

        SessaoUsuario.setUsuarioLogado(usuario);

        abrirTelaPrincipal();

    }

    private void abrirTelaPrincipal() {

        try {

            FXMLLoader loader = new FXMLLoader(
                    Application.class.getResource("TelaPrincipal.fxml")
            );

            Parent root = loader.load();

            Stage stage = (Stage) btnEntrar.getScene().getWindow();

            stage.setScene(new Scene(root));

            stage.centerOnScreen();

        } catch (Exception e) {

            e.printStackTrace();

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Erro");
            alert.setContentText("Não foi possível abrir a tela principal.");
            alert.showAndWait();

        }

    }

}