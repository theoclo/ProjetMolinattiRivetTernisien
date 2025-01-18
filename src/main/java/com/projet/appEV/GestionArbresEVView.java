package com.projet.appEV;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class GestionArbresEVView {

    @FXML
    private Button deconnecter;

    @FXML
    private Button refresh;

    @FXML
    private Button retour;

    @FXML
    private Button planter;

    @FXML
    private Button abattre;

    @FXML
    private Button remarquable;

    @FXML
    private Button liste;

    @FXML
    public void initialize(){

        retour.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Retour' cliqué");
            Stage stage = (Stage) retour.getScene().getWindow();
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

        planter.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Retour' cliqué");
            Stage stage = (Stage) planter.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppEV.class.getResource("/com.projet.appEV/ev_planterArbre.fxml"));
                fxmlLoader.setController(new PlanterArbreEVView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Espaces Verts");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });



        /*
        liste.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Liste des arbres' cliqué");
            Stage stage = (Stage) liste.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppEV.class.getResource("/com.projet.appEV/ev_listeArbres.fxml"));
                fxmlLoader.setController(new ListeArbresEVVIew());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Espaces Verts");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

         */
    }
}
