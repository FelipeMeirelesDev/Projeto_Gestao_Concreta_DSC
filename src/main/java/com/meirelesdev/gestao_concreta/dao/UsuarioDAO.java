package com.meirelesdev.gestao_concreta.dao;

import com.meirelesdev.gestao_concreta.factory.ConnectionFactory;
import com.meirelesdev.gestao_concreta.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    //=========================================
    // AUTENTICAR USUÁRIO
    //=========================================

    public Usuario autenticar(String login, String senha) {

        String sql = """
                SELECT *
                FROM usuarios
                WHERE login = ?
                AND senha = ?
                """;

        try (
                Connection conexao = ConnectionFactory.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {

            stmt.setString(1, login);
            stmt.setString(2, senha);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Usuario usuario = new Usuario();

                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setLogin(rs.getString("login"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setPerfil(rs.getString("perfil"));

                return usuario;

            }

        } catch (SQLException e) {

            throw new RuntimeException("Erro ao autenticar usuário.", e);

        }

        return null;

    }

    //=========================================
    // CADASTRAR USUÁRIO
    //=========================================

    public void salvar(Usuario usuario) {

        String sql = """
                INSERT INTO usuarios
                (nome, login, senha, perfil)
                VALUES (?, ?, ?, ?)
                """;

        try (
                Connection conexao = ConnectionFactory.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getLogin());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getPerfil());

            stmt.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException("Erro ao salvar usuário.", e);

        }

    }

    //=========================================
    // ATUALIZAR USUÁRIO
    //=========================================

    public void atualizar(Usuario usuario) {

        String sql = """
                UPDATE usuarios
                SET nome = ?,
                    login = ?,
                    senha = ?,
                    perfil = ?
                WHERE id = ?
                """;

        try (
                Connection conexao = ConnectionFactory.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getLogin());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getPerfil());
            stmt.setInt(5, usuario.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException("Erro ao atualizar usuário.", e);

        }

    }

    //=========================================
    // REMOVER USUÁRIO
    //=========================================

    public void remover(int id) {

        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (
                Connection conexao = ConnectionFactory.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(sql)
        ) {

            stmt.setInt(1, id);

            stmt.executeUpdate();

        } catch (SQLException e) {

            throw new RuntimeException("Erro ao remover usuário.", e);

        }

    }

    //=========================================
// LISTAR USUÁRIOS
//=========================================

    public List<Usuario> listarTodos() {

        List<Usuario> usuarios = new ArrayList<>();

        String sql = """
            SELECT *
            FROM usuarios
            ORDER BY nome
            """;

        try (
                Connection conexao = ConnectionFactory.getConnection();
                PreparedStatement stmt = conexao.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                Usuario usuario = new Usuario();

                usuario.setId(rs.getInt("id"));
                usuario.setNome(rs.getString("nome"));
                usuario.setLogin(rs.getString("login"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setPerfil(rs.getString("perfil"));

                usuarios.add(usuario);

            }

        } catch (SQLException e) {

            throw new RuntimeException("Erro ao listar usuários.", e);

        }

        return usuarios;

    }

}