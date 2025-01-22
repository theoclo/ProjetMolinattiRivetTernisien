package com.projet.appEV;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class BaseEVView {
    @FXML
    private Button entrer;

    @FXML
    public void initialize(){

        entrer.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Entrer' cliqué");
            Stage stage = (Stage) entrer.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppEV.class.getResource("/com.projet.appEV/ev_home.fxml"));
                fxmlLoader.setController(new HomeEVView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Espaces Verts");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
