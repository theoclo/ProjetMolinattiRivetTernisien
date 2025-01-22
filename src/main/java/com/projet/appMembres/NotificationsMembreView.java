package com.projet.appMembres;

import com.projet.Main;
import com.projet.entite.Personne;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class NotificationsMembreView {
    @FXML
    private Label nom_membre;

    @FXML
    private Button refresh;

    @FXML
    private Button deconnecter;

    @FXML
    private Button supprimer;

    @FXML
    private Button retour;

    @FXML
    private Button notifsButton;

    @FXML
    private ListView listview;

    @FXML
    public void initialize(){
        nom_membre.setText(InitialisationAppMembre.membreActuel.toString());

        ObservableList<String> notifs = FXCollections.observableArrayList();
        Personne p = Personne.obtenirPersonne(InitialisationAppMembre.membreActuel.getPseudo());
        for(String notif :p.getListeNotif()){
            notifs.add(notif);
        }
        listview.setItems(notifs);

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

        refresh.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Refresh' cliqué");
            Stage stage = (Stage) refresh.getScene().getWindow();
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

        supprimer.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Tout supprimer' cliqué");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Suppression des notifications");
            alert.setHeaderText("Êtes-vous sûr de vouloir supprimer toutes vos notifications ?");
            alert.setContentText("Cela entraînera la suppression de vos notifications");
            ButtonType buttonTypeYes = new ButtonType("Oui");
            ButtonType buttonTypeNo = new ButtonType("Non");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == buttonTypeYes) {
                    listview.getItems().clear();
                    p.getListeNotif().clear();
                    try {
                        Main.MaJFichierJSONPersonnes();
                        Main.MaJFichierJSONAssociation();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("L'utilisateur a cliqué sur Oui");
                } else {
                    System.out.println("L'utilisateur a cliqué sur Non");
                }
            });
        });

        retour.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Retour' cliqué");
            Stage stage = (Stage) retour.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppMembre.class.getResource("/com.projet.appMembres/membre_connecte.fxml"));
                fxmlLoader.setController(new ConnecteMembreView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Membre");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        notifsButton.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Notifications' cliqué");
            Stage stage = (Stage) retour.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppMembre.class.getResource("/com.projet.appMembres/membre_connecte.fxml"));
                fxmlLoader.setController(new ConnecteMembreView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Membre");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
