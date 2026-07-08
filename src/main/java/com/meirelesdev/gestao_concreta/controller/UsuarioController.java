package com.meirelesdev.gestao_concreta.controller;

import com.meirelesdev.gestao_concreta.dao.UsuarioDAO;
import com.meirelesdev.gestao_concreta.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class UsuarioController implements Initializable {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtLogin;

    @FXML
    private PasswordField txtSenha;

    @FXML
    private ComboBox<String> cbPerfil;

    @FXML
    private TextField txtPesquisar;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnLimpar;

    @FXML
    private Button btnPesquisar;

    @FXML
    private Button btnEditar;

    @FXML
    private Button btnRemover;

    @FXML
    private Label lblUsuarios;

    @FXML
    private Label lblAdministradores;

    @FXML
    private Label lblFuncionarios;

    @FXML
    private TableView<Usuario> tableUsuarios;

    @FXML
    private TableColumn<Usuario, Integer> colCodigo;

    @FXML
    private TableColumn<Usuario, String> colNome;

    @FXML
    private TableColumn<Usuario, String> colLogin;

    @FXML
    private TableColumn<Usuario, String> colPerfil;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    private final ObservableList<Usuario> listaUsuarios =
            FXCollections.observableArrayList();

    private Usuario usuarioSelecionado;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cbPerfil.getItems().addAll(
                "Administrador",
                "Funcionario"
        );

        configurarTabela();

        carregarUsuarios();

        btnSalvar.setOnAction(e -> salvarUsuario());

        btnPesquisar.setOnAction(e -> pesquisarUsuario());

        btnLimpar.setOnAction(e -> limparCampos());

        btnEditar.setOnAction(e -> editarUsuario());

        btnRemover.setOnAction(e -> removerUsuario());

        tableUsuarios.getSelectionModel().selectedItemProperty().addListener(
                (obs, antigo, novo) -> {

                    if (novo != null) {

                        preencherCampos(novo);

                    }

                }
        );

    }

    private void configurarTabela() {

        colCodigo.setCellValueFactory(
                new PropertyValueFactory<>("id")
        );

        colNome.setCellValueFactory(
                new PropertyValueFactory<>("nome")
        );

        colLogin.setCellValueFactory(
                new PropertyValueFactory<>("login")
        );

        colPerfil.setCellValueFactory(
                new PropertyValueFactory<>("perfil")
        );

    }

    private void carregarUsuarios() {

        listaUsuarios.clear();

        listaUsuarios.addAll(usuarioDAO.listarTodos());

        tableUsuarios.setItems(listaUsuarios);

        atualizarCards();

    }

    private void atualizarCards() {

        int total = listaUsuarios.size();

        int administradores = 0;

        int funcionarios = 0;

        for (Usuario usuario : listaUsuarios) {

            if ("Administrador".equals(usuario.getPerfil())) {

                administradores++;

            } else {

                funcionarios++;

            }

        }

        lblUsuarios.setText(String.valueOf(total));

        lblAdministradores.setText(String.valueOf(administradores));

        lblFuncionarios.setText(String.valueOf(funcionarios));

    }

    private void salvarUsuario() {

        try {

            Usuario usuario = new Usuario();

            usuario.setNome(txtNome.getText());

            usuario.setLogin(txtLogin.getText());

            usuario.setSenha(txtSenha.getText());

            usuario.setPerfil(cbPerfil.getValue());

            usuarioDAO.salvar(usuario);

            carregarUsuarios();

            limparCampos();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setHeaderText(null);

            alert.setContentText("Usuário cadastrado com sucesso!");

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

        txtLogin.clear();

        txtSenha.clear();

        cbPerfil.getSelectionModel().clearSelection();

        txtNome.requestFocus();

        usuarioSelecionado = null;

        tableUsuarios.getSelectionModel().clearSelection();

    }

    private void preencherCampos(Usuario usuario) {

        usuarioSelecionado = usuario;

        txtNome.setText(usuario.getNome());

        txtLogin.setText(usuario.getLogin());

        txtSenha.setText(usuario.getSenha());

        cbPerfil.setValue(usuario.getPerfil());

    }

    private void editarUsuario() {

        if (usuarioSelecionado == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);

            alert.setHeaderText(null);

            alert.setContentText("Selecione um usuário na tabela.");

            alert.showAndWait();

            return;

        }

        try {

            usuarioSelecionado.setNome(txtNome.getText());

            usuarioSelecionado.setLogin(txtLogin.getText());

            usuarioSelecionado.setSenha(txtSenha.getText());

            usuarioSelecionado.setPerfil(cbPerfil.getValue());

            usuarioDAO.atualizar(usuarioSelecionado);

            carregarUsuarios();

            limparCampos();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setHeaderText(null);

            alert.setContentText("Usuário atualizado com sucesso.");

            alert.showAndWait();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void removerUsuario() {

        Usuario usuario = tableUsuarios.getSelectionModel().getSelectedItem();

        if (usuario == null) {

            Alert alert = new Alert(Alert.AlertType.WARNING);

            alert.setHeaderText(null);

            alert.setContentText("Selecione um usuário.");

            alert.showAndWait();

            return;

        }

        Alert confirmacao = new Alert(Alert.AlertType.CONFIRMATION);

        confirmacao.setHeaderText(null);

        confirmacao.setContentText(
                "Deseja realmente remover este usuário?"
        );

        if (confirmacao.showAndWait().get() == ButtonType.OK) {

            usuarioDAO.remover(usuario.getId());

            carregarUsuarios();

            limparCampos();

        }

    }

    @FXML
    private void pesquisarUsuario() {

        String texto = txtPesquisar.getText().trim().toLowerCase();

        if (texto.isEmpty()) {

            tableUsuarios.setItems(listaUsuarios);

            return;

        }

        ObservableList<Usuario> filtrada =
                FXCollections.observableArrayList();

        for (Usuario usuario : listaUsuarios) {

            if (usuario.getNome().toLowerCase().contains(texto)
                    || usuario.getLogin().toLowerCase().contains(texto)) {

                filtrada.add(usuario);

            }

        }

        tableUsuarios.setItems(filtrada);

    }

}
