package com.projet.appAsso;

import com.projet.Main;
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
import java.util.ArrayList;

public class DonateursAssoView {

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
    private Button ajouter;


    @FXML
    public void initialize() {
        nom_asso.setText(InitialisationAppAsso.associationActuelle.toString());
        Association a = Association.getAssociation(InitialisationAppAsso.associationActuelle.getNom());
        list.setItems(FXCollections.observableArrayList(a.getListeDonateurs()));

        virer.setDisable(true);

        list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                virer.setDisable(false);
            }else {
                virer.setDisable(true);
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
            Stage stage = (Stage) refresh.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppAsso.class.getResource("/com.projet.appAsso/asso_donateurs.fxml"));
                fxmlLoader.setController(new DonateursAssoView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Association");
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            System.out.println("Bouton 'Ajouter un donateur' cliqué");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Ajout d'un donateur");
            alert.setHeaderText("Quelle donateur voulez vous ajouter ?");
            alert.setContentText("Choisissez une personne dans la liste suivante :");
            alert.setResizable(false);

            ComboBox<String> comboBox = new ComboBox<>();
            ArrayList<String> personnes = new ArrayList<>();
            for(Personne p : Personne.listePersonnes){
                if(! a.getListeDonateurs().contains(p.getPseudo())){
                    personnes.add(p.getPseudo());
                }
            }
            comboBox.setItems(FXCollections.observableArrayList(personnes));
            //comboBox.getItems().addAll(personnes sans asso);

            comboBox.setPromptText("(Choisissez une personne)");
            comboBox.setStyle(
                    "-fx-background-color: rgba(237, 237, 237, 1);" +
                            "-fx-border-radius: 20;" +
                            "-fx-background-radius: 20;" +
                            "-fx-border-color: black;"
            );

            VBox content = new VBox(10, new Label("Donateur :"), comboBox);
            content.setStyle("-fx-padding: 10;");
            alert.getDialogPane().setContent(content);
            ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            ButtonType cancelButton = new ButtonType("Annuler", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(okButton, cancelButton);

            alert.showAndWait().ifPresent(response -> {
                if (response == okButton) {
                    String selectedDonateur = comboBox.getValue();
                    Personne donateur = Personne.obtenirPersonne(selectedDonateur);
                    if (selectedDonateur != null) {
                        System.out.println("Donateur ajouté : " + selectedDonateur);
                        a.ajouterDonateur(donateur.getPseudo());

                        try {
                            Main.MaJFichierJSONAssociation();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        for(Personne p : Personne.listePersonnes){
                            if(! a.getListeDonateurs().contains(p.getPseudo())){
                                personnes.add(p.getPseudo());
                            }
                        }
                        comboBox.setItems(FXCollections.observableArrayList(personnes));
                        list.setItems(FXCollections.observableArrayList(a.getListeDonateurs()));
                    } else {
                        System.out.println("Aucun donateur sélectionné !");
                    }
                } else {
                    System.out.println("Ajout annulé.");
                }
            });
        });

        virer.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Supprimer un donateur' cliqué");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Supprimer un donateur");
            alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce donateur ?");
            alert.setContentText("");
            ButtonType buttonTypeYes = new ButtonType("Oui");
            ButtonType buttonTypeNo = new ButtonType("Non");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            alert.showAndWait().ifPresent(buttonType -> {

                //EN GROS LE BOUTON EST GRISE TANT QUE RIEN N'EST SELECTIONNE

                if (buttonType == buttonTypeYes) { // AND MEMBER SELECTED , CAR ON NE PEUT PAS VIRER UN MEMBRE SANS LE SELECTIONNER
                    System.out.println("Donateur supprimé");

                    String donateur = (String) list.getSelectionModel().getSelectedItem();
                    a.supprimerDonateur(donateur);

                    try {
                        Main.MaJFichierJSONAssociation();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    list.setItems(FXCollections.observableArrayList(a.getListeDonateurs()));

                } else {
                    System.out.println("Annulation de la suppression.");
                }
            });

        });
    }

}
