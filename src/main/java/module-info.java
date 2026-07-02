module com.meirelesdev.gestao_concreta {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.meirelesdev.gestao_concreta to javafx.fxml;
    exports com.meirelesdev.gestao_concreta;
}