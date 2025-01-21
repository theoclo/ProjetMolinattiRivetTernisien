package com.projet.appEV;

import com.projet.Main;
import com.projet.appMembres.AppMembre;
import com.projet.appMembres.ConnexionMembreView;
import com.projet.appMembres.InitialisationAppMembre;
import com.projet.entite.Association;
import com.projet.entite.Personne;
import com.projet.espacesVerts.ServiceEV;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class GestionNotificationsEVView {
    @FXML
    private Button deconnecter;

    @FXML
    private Button refresh;

    @FXML
    private Button retour;

    @FXML
    private Button asso;

    @FXML
    private Button particulier;

    @FXML
    private ListView listAsso;

    @FXML
    private ListView listPart;

    @FXML
    public void initialize(){

        ServiceEV serviceEv = ServiceEV.listeServiceEV.get(0);

        listAsso.setItems(FXCollections.observableList(ServiceEV.obtenirAssosAbonne()));
        listPart.setItems(FXCollections.observableList(ServiceEV.obtenirParticuliersAbonne()));

        deconnecter.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Se déconnecter' cliqué");
            Stage stage = (Stage) deconnecter.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppEV.class.getResource("/com.projet.appEV/ev_base.fxml"));
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

        asso.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Ajouter une association' cliqué");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Ajouter une association");
            alert.setHeaderText("Sélectionnez une association à ajouter");
            alert.setResizable(false);

            // ComboBox pour choisir l'association
            ComboBox<String> comboBox = new ComboBox<>();

            ArrayList<String> assoSansAbo= new ArrayList<>();
            for(Association a : Association.listeAssociations){
                if(a.getAbonnement().isEmpty()){
                    assoSansAbo.add(a.getNom());
                }
            }

            comboBox.getItems().addAll(assoSansAbo);

            comboBox.setPromptText("(Choisissez une association)");
            comboBox.setStyle(
                    "-fx-background-color: rgba(237, 237, 237, 1);" +
                            "-fx-border-radius: 20;" +
                            "-fx-background-radius: 20;" +
                            "-fx-border-color: black;"
            );

            // Mise en page
            VBox content = new VBox(10, new Label("Association :"), comboBox);
            content.setStyle("-fx-padding: 10;");

            alert.getDialogPane().setContent(content);

            // Boutons OK et Annuler
            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, cancelButton);

            // Gestion du résultat
            alert.showAndWait().ifPresent(response -> {
                if (response == okButton) {
                    String selectedAssociation = comboBox.getValue();
                    Association a = Association.getAssociation(selectedAssociation);
                    if (selectedAssociation != null) {
                        System.out.println("Association ajoutée : " + selectedAssociation);
                        a.setAbonnement(Optional.of(serviceEv.getCommune()));
                        ServiceEV.getServiceEV(serviceEv.getCommune()).addAbonne(a.getNom());

                        try {
                            Main.MaJFichierJSONAssociation();
                            Main.MaJFichierServiceEV();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        listAsso.setItems(FXCollections.observableList(ServiceEV.obtenirAssosAbonne()));
                    } else {
                        System.out.println("Aucune association sélectionnée !");
                    }
                } else {
                    System.out.println("Ajout annulé.");
                }
            });
        });

        particulier.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Ajouter un particulier' cliqué");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Ajouter un particulier");
            alert.setHeaderText("Sélectionnez une personne à ajouter");
            alert.setResizable(false);

            ComboBox<String> comboBox = new ComboBox<>();

            ArrayList<String> listePersonneSansAbo= new ArrayList<>();
            for(Personne p : Personne.listePersonnes){
                if(p.getAbonnement().isEmpty()){
                    listePersonneSansAbo.add(p.getPseudo());
                }
            }

            comboBox.getItems().addAll(listePersonneSansAbo);

            comboBox.setPromptText("(Choisissez une personne)");
            comboBox.setStyle(
                    "-fx-background-color: rgba(237, 237, 237, 1);" +
                            "-fx-border-radius: 20;" +
                            "-fx-background-radius: 20;" +
                            "-fx-border-color: black;"
            );

            VBox content = new VBox(10, new Label("Personne :"), comboBox);
            content.setStyle("-fx-padding: 10;");
            alert.getDialogPane().setContent(content);
            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, cancelButton);

            alert.showAndWait().ifPresent(response -> {
                if (response == okButton) {
                    String selectedParticulier = comboBox.getValue();
                    if (selectedParticulier != null) {
                        System.out.println("Personne ajoutée : " + selectedParticulier);
                        Personne p = Personne.obtenirPersonne(selectedParticulier);
                        p.setAbonnement(Optional.of(serviceEv.getCommune()));
                        ServiceEV.getServiceEV(serviceEv.getCommune()).addAbonne(p.getPseudo());
                        try {
                            Main.MaJFichierJSONAssociation();
                            Main.MaJFichierJSONPersonnes();
                            Main.MaJFichierServiceEV();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        listPart.setItems(FXCollections.observableList(ServiceEV.obtenirParticuliersAbonne()));
                    } else {
                        System.out.println("Aucun particulier sélectionné !");
                    }
                } else {
                    System.out.println("Ajout annulé.");
                }
            });
        });

    }
}
