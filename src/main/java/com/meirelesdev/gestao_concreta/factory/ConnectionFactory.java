package com.meirelesdev.gestao_concreta.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static final String URL =
            "jdbc:mysql://localhost:3306/gestao_concreta";

    private static final String USUARIO = "root";

    private static final String SENHA = "123456";

    public static Connection getConnection() {

        try {

            return DriverManager.getConnection(URL, USUARIO, SENHA);

        } catch (SQLException e) {

            throw new RuntimeException("Erro ao conectar ao banco de dados.", e);

        }

    }

}