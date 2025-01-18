package com.projet.appEV;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class AppEV extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FileInputStream inputstream = new FileInputStream("src/main/resources/Acme-Regular.ttf");
        Font.loadFont(inputstream,12);
        FXMLLoader fxmlLoader = new FXMLLoader(AppEV.class.getResource("/com.projet.appEV/ev_base.fxml"));
        fxmlLoader.setController(new BaseEVView());
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Application Espaces Verts");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
