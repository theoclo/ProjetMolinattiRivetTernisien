package com.projet.appAsso;

import com.projet.Arbre;
import com.projet.appEV.AppEV;

import com.projet.appMembres.InitialisationAppMembre;
import com.projet.entite.Association;
import com.projet.entite.Personne;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import javafx.stage.Stage;

import java.io.IOException;

import static com.projet.Main.*;
import static com.projet.appMembres.InitialisationAppMembre.membreActuel;

public class ConnexionAssoView {

    @FXML
    private Button connexion;

    @FXML
    private ComboBox combobox;

    @FXML
    public void initialize() throws IOException {
        combobox.setItems(InitialisationAppMembre.associations);

        if(combobox.getSelectionModel().getSelectedItem() == null){
            connexion.setDisable(true);
        }
        else{
            connexion.setDisable(false);
        }

        combobox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                connexion.setDisable(false);
            }else {
                connexion.setDisable(true);
            }
        });

        connexion.setOnMouseClicked(event -> {
            InitialisationAppAsso.associationActuelle = (Association) combobox.getValue();
            System.out.println("Bouton 'Se connecter' cliqué");
            Stage stage = (Stage) connexion.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppEV.class.getResource("/com.projet.appAsso/asso_home.fxml"));
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
