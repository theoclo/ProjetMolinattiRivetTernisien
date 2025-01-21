package com.projet.appAsso;

import com.projet.appMembres.InitialisationAppMembre;
import com.projet.entite.Association;
import com.projet.espacesVerts.Visite;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Comparator;

public class HomeAssoView {

    @FXML
    private Label nom_asso;

    @FXML
    private Button refresh;

    @FXML
    private Button deconnecter;

    @FXML
    private Button notifs;

    @FXML
    private Button asso;

    @FXML
    private Button visites;


    @FXML
    public void initialize() {
        nom_asso.setText(InitialisationAppAsso.associationActuelle.toString());
        InitialisationAppMembre.listeVisites.clear();
        Association a = Association.getAssociation(InitialisationAppAsso.associationActuelle.getNom());
        InitialisationAppMembre.listeVisites.addAll(a.getListeVisite());
        InitialisationAppMembre.listeVisites.sort(Comparator.comparing(Visite::getDate));

        deconnecter.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Se déconnecter' cliqué");
            Stage stage = (Stage) deconnecter.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppAsso.class.getResource("/com.projet.appAsso/asso_connexion.fxml"));
                fxmlLoader.setController(new ConnexionAssoView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Association");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        refresh.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Refresh' cliqué");
        });

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

        visites.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Visites' cliqué");
            Stage stage = (Stage) visites.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppAsso.class.getResource("/com.projet.appAsso/asso_visites.fxml"));
                fxmlLoader.setController(new VisitesAssoView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Association");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        asso.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Association' cliqué");
            Stage stage = (Stage) asso.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppAsso.class.getResource("/com.projet.appAsso/asso_association.fxml"));
                fxmlLoader.setController(new AssociationAssoView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Association");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


    };
}
