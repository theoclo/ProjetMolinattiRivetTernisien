package com.projet.appAsso;

import com.projet.appMembres.AppMembre;
import com.projet.appMembres.ConnecteMembreView;
import com.projet.appMembres.InitialisationAppMembre;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeAssoView {

    @FXML
    private Label nom_asso;

    @FXML
    private Button notifs;


    @FXML
    public void initialize() {
        nom_asso.setText(InitialisationAppAsso.associationActuelle.toString());

        notifs.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Notifications' cliqué");
            Stage stage = (Stage) notifs.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppAsso.class.getResource("/com.projet.appAsso/asso_notifications.fxml"));
                fxmlLoader.setController(new NotificationsAssoView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Association");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    };
}
