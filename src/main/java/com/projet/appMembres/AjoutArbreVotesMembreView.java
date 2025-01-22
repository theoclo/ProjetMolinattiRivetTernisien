package com.projet.appMembres;

import com.projet.Arbre;
import com.projet.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AjoutArbreVotesMembreView {
    @FXML
    private Label nom_membre;

    @FXML
    private Button refresh;

    @FXML
    private Button deconnecter;

    @FXML
    private Button retour;

    @FXML
    private ListView listview = new ListView<>();

    @FXML
    private Button valider;

    @FXML
    public void initialize() {
        nom_membre.setText(InitialisationAppMembre.membreActuel.toString());
        listview.setItems(InitialisationAppMembre.arbresNonRemarquables);

        valider.setDisable(true);

        listview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            valider.setDisable(false);
        });

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
                FXMLLoader fxmlLoader = new FXMLLoader(AppMembre.class.getResource("/com.projet.appMembres/membre_ajoutArbre.fxml"));
                fxmlLoader.setController(new AjoutArbreVotesMembreView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Membre");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        retour.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Retour' cliqué");
            Stage stage = (Stage) retour.getScene().getWindow();
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

        valider.setOnMouseClicked(event -> {
            Arbre arbreSelectionne = (Arbre) listview.getSelectionModel().getSelectedItem();
            arbreSelectionne = Arbre.obtenirArbre(arbreSelectionne.getIdBase());
            System.out.println("Bouton 'Valider' cliqué");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Ajout arbre aux votes");
            alert.setHeaderText("Êtes-vous sûr de vouloir ajouter l'arbre "+arbreSelectionne.getIdBase()+ " dans la liste de vos votes ?");
            alert.setContentText("");
            ButtonType buttonTypeYes = new ButtonType("Oui");
            ButtonType buttonTypeNo = new ButtonType("Non");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            Arbre finalArbreSelectionne = arbreSelectionne;
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == buttonTypeYes) {
                    System.out.println("L'utilisateur a cliqué sur Oui");
                    try {
                        boolean fait = InitialisationAppMembre.membreActuel.nominerArbre(finalArbreSelectionne);
                        if(!fait){
                            Alert alert2 = new Alert(Alert.AlertType.ERROR);
                            alert2.setTitle("Erreur");
                            alert2.setHeaderText("Erreur lors de l'ajout de l'arbre aux votes");
                            alert2.setContentText("L'arbre est déjà dans votre liste");
                            alert2.showAndWait();
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Stage stage = (Stage) retour.getScene().getWindow();
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(AppMembre.class.getResource("/com.projet.appMembres/membre_votes.fxml"));
                        fxmlLoader.setController(new VotesMembreView());
                        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                        stage.setScene(scene);
                        stage.setTitle("Application Membre");
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
