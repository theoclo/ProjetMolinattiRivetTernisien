package com.projet.appAsso;

import com.projet.Main;
import com.projet.appMembres.AppMembre;
import com.projet.appMembres.ConnexionMembreView;
import com.projet.appMembres.InitialisationAppMembre;
import com.projet.entite.Association;
import com.projet.entite.Personne;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

public class MembresAssoView {
    @FXML
    private Label nom_asso;

    @FXML
    private Button refresh;

    @FXML
    private Button deconnecter;

    @FXML
    private Button retour;

    @FXML
    private ListView list;

    @FXML
    private Button virer;

    @FXML
    private Button recettes;

    @FXML
    private Button ajouter;


    @FXML
    public void initialize() {
        nom_asso.setText(InitialisationAppAsso.associationActuelle.toString());

        //PB POUR LE MEMBRE => réinitialisations de ses valeurs

        list.setItems(FXCollections.observableArrayList(Association.getAssociation(InitialisationAppAsso.associationActuelle.getNom()).getListeMembre()));

        recettes.setDisable(true);
        virer.setDisable(true);

        list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
           if (newValue != null) {
               recettes.setDisable(false);
               virer.setDisable(false);
           }
        });

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

        retour.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Retour' cliqué");
            Stage stage = (Stage) retour.getScene().getWindow();
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

        ajouter.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Ajouter un membre' cliqué");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ajout d'un membre");
            alert.setHeaderText("Quelle personne voulez vous ajouter ?");
            alert.setContentText("Choisissez une personne dans la liste suivante, ces personnes ne sont pas dans des associations :");
            alert.setResizable(false);

            ComboBox<String> comboBox = new ComboBox<>();

            //comboBox.getItems().addAll(personnes sans asso);
            comboBox.setItems(FXCollections.observableArrayList(Personne.personnesSansAsso()));

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
                    String selectedPerson = comboBox.getValue();
                    if (selectedPerson != null) {
                        System.out.println("Personne ajoutée : " + selectedPerson);
                        Personne p = Personne.obtenirPersonne(selectedPerson);
                        p.rejoindreAsso(Association.getAssociation(InitialisationAppAsso.associationActuelle.getNom()));
                        try {

                            Main.MaJFichierJSONPersonnes();
                            Main.MaJFichierJSONAssociation();
                            list.getItems().clear();
                            list.setItems(FXCollections.observableArrayList(Association.getAssociation(InitialisationAppAsso.associationActuelle.getNom()).getListeMembre()));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        System.out.println("Aucune personne sélectionnée !");
                    }
                } else {
                    System.out.println("Ajout annulé.");
                }
            });
        });

        recettes.setOnMouseClicked(event -> {

            Personne p = (Personne) list.getSelectionModel().getSelectedItem();

            System.out.println("Bouton 'Recettes de ce membre' cliqué");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Recettes d'un membre");
            alert.setHeaderText("Voici les différences recettes de ce membre :");
            ListView<String> listView = new ListView<>();
            listView.setItems(FXCollections.observableArrayList(p.cotisations()));
            alert.getDialogPane().setContent(listView);
            listView.setPrefHeight(150);
            listView.setPrefWidth(300);
            alert.showAndWait();

        });

        virer.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Virer un membre' cliqué");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Virer un membre");
            alert.setHeaderText("Êtes-vous sûr de vouloir virer ce membre ?");
            alert.setContentText("");
            ButtonType buttonTypeYes = new ButtonType("Oui");
            ButtonType buttonTypeNo = new ButtonType("Non");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            alert.showAndWait().ifPresent(buttonType -> {

                //EN GROS LE BOUTON EST GRISE TANT QUE RIEN N'EST SELECTIONNE

                if (buttonType == buttonTypeYes) { // AND MEMBER SELECTED , CAR ON NE PEUT PAS VIRER UN MEMBRE SANS LE SELECTIONNER

                    Personne p = (Personne) list.getSelectionModel().getSelectedItem();
                    p = Personne.obtenirPersonne(p.getPseudo());

                    try {
                        boolean fait = p.quitterAsso();
                        if(!fait){
                            Alert alert2 = new Alert(Alert.AlertType.ERROR);
                            alert2.setTitle("Erreur");
                            alert2.setHeaderText("Erreur lors de la demande d'exclusion de l'association");
                            alert2.setContentText("Vous ne pouvez pas exclure le président");
                            alert2.showAndWait();
                        }
                        else{
                            System.out.println("Membre viré");
                            list.getItems().clear();
                            list.setItems(FXCollections.observableArrayList(Association.getAssociation(InitialisationAppAsso.associationActuelle.getNom()).getListeMembre()));

                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    System.out.println("Annulation de l'exclusion.");
                }
            });
        });
    }
}
