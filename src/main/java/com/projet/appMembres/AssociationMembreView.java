package com.projet.appMembres;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class AssociationMembreView {
    @FXML
    private Label nom_membre;

    @FXML
    private Button refresh;

    @FXML
    private Button deconnecter;

    @FXML
    private Button retour;

    @FXML
    private Button cotisations;

    @FXML
    private Button quitter;

    @FXML
    public void initialize(){
        nom_membre.setText(InitialisationAppMembre.membreActuel.toString());

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

        cotisations.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Cotisations' cliqué");
            Stage stage = (Stage) cotisations.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppMembre.class.getResource("/com.projet.appMembres/membre_cotisations.fxml"));
                fxmlLoader.setController(new CotisationsMembreView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Membre");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        quitter.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Quitter l'association' cliqué");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Quitter l'association");
            alert.setHeaderText("Êtes-vous sûr de vouloir quitter l'association ?");
            alert.setContentText("Cela entraînera la suppression de vos données");
            ButtonType buttonTypeYes = new ButtonType("Oui");
            ButtonType buttonTypeNo = new ButtonType("Non");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == buttonTypeYes) {
                    System.out.println("L'utilisateur a cliqué sur Oui");
                    try {
                        boolean fait = InitialisationAppMembre.membreActuel.quitterAsso();
                        if(!fait){
                            Alert alert2 = new Alert(Alert.AlertType.ERROR);
                            alert2.setTitle("Erreur");
                            alert2.setHeaderText("Erreur lors de la demande de quitter l'association");
                            alert2.setContentText("Vous ne pouvez pas quitter l'association");
                            alert2.showAndWait();
                        }
                        else{
                            Stage stage = (Stage) retour.getScene().getWindow();
                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader(AppMembre.class.getResource("/com.projet.appMembres/membre_connexion.fxml"));
                                fxmlLoader.setController(new ConnexionMembreView());
                                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                                stage.setScene(scene);
                                stage.setTitle("Application Membre");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }



                } else {
                    System.out.println("L'utilisateur a cliqué sur Non");
                }
            });
        });
    }
}
