package com.projet.appAsso;

import com.projet.appEV.AppEV;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import javafx.stage.Stage;

import java.io.IOException;

public class ConnexionAssoView {

    @FXML
    private Button connexion;

    @FXML
    private ComboBox combobox;

    @FXML
    public void initialize() throws IOException {

        connexion.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Se connecter' cliqué");
            Stage stage = (Stage) connexion.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppEV.class.getResource("/com.projet.appEV/asso_home.fxml"));
                fxmlLoader.setController(new HomeAssoView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Espaces Verts");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
