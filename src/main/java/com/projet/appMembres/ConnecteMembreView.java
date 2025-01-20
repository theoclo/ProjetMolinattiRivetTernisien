package com.projet.appMembres;

import com.projet.espacesVerts.Visite;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Comparator;

public class ConnecteMembreView {
    @FXML
    private Label nom_membre;

    @FXML
    private Button refresh;

    @FXML
    private Button deconnecter;

    @FXML
    private Button asso;

    @FXML
    private Button visites;

    @FXML
    private Button votes;

    @FXML
    private Button notifs;

    @FXML
    public void initialize(){
        nom_membre.setText(InitialisationAppMembre.membreActuel.toString());
        InitialisationAppMembre.listeVisites.clear();
        InitialisationAppMembre.listeVisites.addAll(Visite.obtenirVisitesAsso(InitialisationAppMembre.membreActuel.getAssociation().get()));
        InitialisationAppMembre.listeVisites.sort(Comparator.comparing(Visite::getDate));

        deconnecter.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Se déconnecter' cliqué");
            Stage stage = (Stage) deconnecter.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppMembre.class.getResource("/com.projet.appMembres/membre_connexion.fxml"));
                fxmlLoader.setController(new ConnexionMembreView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Membre");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        notifs.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Notifications' cliqué");
            Stage stage = (Stage) notifs.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppMembre.class.getResource("/com.projet.appMembres/membre_notifications.fxml"));
                fxmlLoader.setController(new NotificationsMembreView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Membre");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        refresh.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Refresh' cliqué");
        });

        asso.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Association' cliqué");
            Stage stage = (Stage) asso.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppMembre.class.getResource("/com.projet.appMembres/membre_association.fxml"));
                fxmlLoader.setController(new AssociationMembreView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Membre");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        visites.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Visites' cliqué");
            Stage stage = (Stage) visites.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppMembre.class.getResource("/com.projet.appMembres/membre_visites.fxml"));
                fxmlLoader.setController(new VisitesMembreView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Membre");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        votes.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Votes' cliqué");
            Stage stage = (Stage) votes.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppMembre.class.getResource("/com.projet.appMembres/membre_votes.fxml"));
                fxmlLoader.setController(new VotesMembreView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Membre");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
