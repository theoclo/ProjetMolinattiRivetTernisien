package com.projet.appEV;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeEVView {

    @FXML
    private Button deconnecter;

    @FXML
    private Button refresh;

    @FXML
    private Button arbres;

    @FXML
    private Button notifs;

    @FXML
    public void initialize(){

        arbres.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Se connecter' cliqué");
            Stage stage = (Stage) arbres.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppEV.class.getResource("/com.projet.appEV/ev_gestionArbres.fxml"));
                fxmlLoader.setController(new GestionArbresEVView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Espaces Verts");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        notifs.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Se connecter' cliqué");
            Stage stage = (Stage) notifs.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppEV.class.getResource("/com.projet.appEV/ev_gestionNotifs.fxml"));
                fxmlLoader.setController(new GestionNotificationsEVView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Espaces Verts");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
