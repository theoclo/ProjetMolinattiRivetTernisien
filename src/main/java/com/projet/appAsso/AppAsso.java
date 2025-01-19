package com.projet.appAsso;

import com.projet.appEV.AppEV;
import com.projet.appEV.BaseEVView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import net.datafaker.providers.base.App;

import java.io.FileInputStream;
import java.io.IOException;

public class AppAsso extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FileInputStream inputstream = new FileInputStream("src/main/resources/Acme-Regular.ttf");
        Font.loadFont(inputstream,12);
        FXMLLoader fxmlLoader = new FXMLLoader(AppAsso.class.getResource("/com.projet.appAsso/asso_connexion.fxml"));
        fxmlLoader.setController(new ConnexionAssoView());
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Application Association");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
