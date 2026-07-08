package com.meirelesdev.gestao_concreta.util;

import com.meirelesdev.gestao_concreta.model.Usuario;

public class SessaoUsuario {

    private static Usuario usuarioLogado;

    private SessaoUsuario() {
        // Impede a criação de objetos desta classe
    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public static void setUsuarioLogado(Usuario usuario) {
        usuarioLogado = usuario;
    }

    public static void encerrarSessao() {
        usuarioLogado = null;
    }

    public static boolean isAdministrador() {

        return usuarioLogado != null &&
                "Administrador".equals(usuarioLogado.getPerfil());

    }

    public static boolean isFuncionario() {

        return usuarioLogado != null &&
                "Funcionario".equals(usuarioLogado.getPerfil());

    }

}