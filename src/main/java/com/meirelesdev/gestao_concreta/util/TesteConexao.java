package com.meirelesdev.gestao_concreta.util;

import com.meirelesdev.gestao_concreta.factory.ConnectionFactory;

public class TesteConexao {

    public static void main(String[] args) {

        if (ConnectionFactory.getConnection() != null) {

            System.out.println("Conectado com sucesso!");

        }
    }

}
