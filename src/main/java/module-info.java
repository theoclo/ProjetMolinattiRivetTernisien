module com.projet.testpersonne {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.datatype.jdk8;
    requires net.datafaker;
    requires com.fasterxml.jackson.datatype.jsr310;
    requires com.fasterxml.jackson.databind;

    opens com.projet.entite to com.fasterxml.jackson.databind;
    opens com.projet to javafx.fxml, com.fasterxml.jackson.databind;
    exports com.projet;
    exports com.projet.entite to com.fasterxml.jackson.databind;
    exports com.projet.espacesVerts to com.fasterxml.jackson.databind;
    opens com.projet.appMembres to javafx.fxml;
    exports com.projet.appMembres to javafx.graphics;
    opens com.projet.espacesVerts to com.fasterxml.jackson.databind;
    opens com.projet.launcher to javafx.fxml;
    exports com.projet.launcher;
    opens com.projet.appEV to javafx.fxml;
    exports com.projet.appEV;

}