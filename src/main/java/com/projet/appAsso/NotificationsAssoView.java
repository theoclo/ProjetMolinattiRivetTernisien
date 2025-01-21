package com.projet.appAsso;

import com.projet.Main;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class NotificationsAssoView {
    @FXML
    private Label nom_asso;

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

        /* CA VIENT DE MEMBRE, FAUT CHANGER POUR QUE CA LE FASSE AVEC ASSO
        nom_membre.setText(InitialisationAppMembre.membreActuel.toString());

        ObservableList<String> notifs = FXCollections.observableArrayList();
        Personne p = Personne.obtenirPersonne(InitialisationAppMembre.membreActuel.getPseudo());
        for(String notif :p.getListeNotif()){
            notifs.add(notif);
        }
        listview.setItems(notifs);

         */

        nom_asso.setText(InitialisationAppAsso.associationActuelle.getNom());
        listview.getItems().clear();
        listview.setItems(FXCollections.observableArrayList(InitialisationAppAsso.associationActuelle.getListeNotif()));



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
                    InitialisationAppAsso.associationActuelle.getListeNotif().clear();
                    try {
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
                FXMLLoader fxmlLoader = new FXMLLoader(AppAsso.class.getResource("/com.projet.appAsso/asso_home.fxml"));
                fxmlLoader.setController(new HomeAssoView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Association");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        notifsButton.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Notifications' cliqué");
            Stage stage = (Stage) notifsButton.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppAsso.class.getResource("/com.projet.appAsso/asso_home.fxml"));
                fxmlLoader.setController(new HomeAssoView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Association");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
