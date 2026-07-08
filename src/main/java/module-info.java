module com.meirelesdev.gestao_concreta {

        requires javafx.controls;
        requires javafx.fxml;
        requires java.desktop;
        requires java.sql;
        requires com.github.librepdf.openpdf;

        exports com.meirelesdev.gestao_concreta;
        exports com.meirelesdev.gestao_concreta.controller;
        exports com.meirelesdev.gestao_concreta.model;

        opens com.meirelesdev.gestao_concreta to javafx.fxml;
        opens com.meirelesdev.gestao_concreta.controller to javafx.fxml;

        opens com.meirelesdev.gestao_concreta.model to javafx.base;
        exports com.meirelesdev.gestao_concreta.dao;
        opens com.meirelesdev.gestao_concreta.dao to javafx.fxml;
}