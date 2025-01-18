package com.projet.appMembres;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;


import java.io.FileInputStream;
import java.io.IOException;


public class AppMembre extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FileInputStream inputstream = new FileInputStream("src/main/resources/Acme-Regular.ttf");
        Font.loadFont(inputstream,12);
        FXMLLoader fxmlLoader = new FXMLLoader(AppMembre.class.getResource("/com.projet.appMembres/membre_connexion.fxml"));
        fxmlLoader.setController(new ConnexionMembreView());
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Application Membre");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        InitialisationAppMembre.main(new String[]{});
        launch();
    }
}