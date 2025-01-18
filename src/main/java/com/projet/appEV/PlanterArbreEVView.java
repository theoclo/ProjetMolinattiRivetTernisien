package com.projet.appEV;

import com.projet.appMembres.InitialisationAppMembre;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class PlanterArbreEVView {
    @FXML
    private Button deconnecter;

    @FXML
    private Button refresh;

    @FXML
    private Button retour;

    @FXML
    private Button planter;

    @FXML
    private TextField nom;

    @FXML
    private TextField genre;

    @FXML
    private TextField espece;

    @FXML
    private TextField circonference;

    @FXML
    private TextField hauteur;

    @FXML
    private TextField adresse;

    @FXML
    private TextField latitude;

    @FXML
    private TextField longitude;

    @FXML
    public void initialize(){

        deconnecter.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Se déconnecter' cliqué");
            Stage stage = (Stage) deconnecter.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppEV.class.getResource("/com.projet.appMembres/ev_base.fxml"));
                fxmlLoader.setController(new BaseEVView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Espaces Verts");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        refresh.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Refresh' cliqué");
        });

        retour.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Se connecter' cliqué");
            Stage stage = (Stage) retour.getScene().getWindow();
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

        planter.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Planter un arbre' cliqué");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Planter un arbre");
            alert.setHeaderText("Êtes-vous sûr de vouloir planter cet arbre ?");
            alert.setContentText("");
            ButtonType buttonTypeYes = new ButtonType("Oui");
            ButtonType buttonTypeNo = new ButtonType("Non");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == buttonTypeYes) {
                    System.out.println("L'utilisateur a cliqué sur Oui");

                    //PLANTER L'ARBRE (AJOUTER L'ARBRE A LA LISTE DES ARBRES)

                    Stage stage = (Stage) retour.getScene().getWindow();
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(AppEV.class.getResource("/com.projet.appEV/ev_gestionArbres.fxml"));
                        fxmlLoader.setController(new GestionArbresEVView());
                        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                        stage.setScene(scene);
                        stage.setTitle("Application Espaces Verts");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("L'utilisateur a cliqué sur Non");
                }
            });
        });
    }
}
