package com.projet.appAsso;

import com.projet.Arbre;
import com.projet.Main;
import com.projet.appMembres.InitialisationAppMembre;
import com.projet.entite.Association;
import com.projet.entite.Personne;
import com.projet.espacesVerts.ServiceEV;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FacturesNonRegleesAssoView {
    @FXML
    private Label nom_asso;

    @FXML
    private Button refresh;

    @FXML
    private Button deconnecter;

    @FXML
    private Button retour;

    @FXML
    private Button regler;

    @FXML
    private ListView facturesNonReglees;

    @FXML
    public void initialize() {
        nom_asso.setText(InitialisationAppAsso.associationActuelle.toString());

        Association a = Association.getAssociation(String.valueOf(nom_asso.getText()));
        facturesNonReglees.getItems().setAll(a.getListeFacturesNonPayees());

        regler.setDisable(true);

        facturesNonReglees.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                regler.setDisable(false);
            }else {
                regler.setDisable(true);
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
                FXMLLoader fxmlLoader = new FXMLLoader(AppAsso.class.getResource("/com.projet.appAsso/asso_facturesNonReglees.fxml"));
                fxmlLoader.setController(new FacturesNonRegleesAssoView());
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
                FXMLLoader fxmlLoader = new FXMLLoader(AppAsso.class.getResource("/com.projet.appAsso/asso_factures.fxml"));
                fxmlLoader.setController(new FacturesAssoView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Association");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        regler.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Régler' cliqué");
            String facture = (String) facturesNonReglees.getSelectionModel().getSelectedItem();
            int j = facture.indexOf('€');
            int montant = Integer.parseInt(facture.substring(0, j));

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Payer Facture");
            alert.setHeaderText("Êtes-vous sûr de vouloir payer cette facture de "+montant+"€ ?");
            alert.setContentText("");
            ButtonType buttonTypeYes = new ButtonType("Oui");
            ButtonType buttonTypeNo = new ButtonType("Non");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == buttonTypeYes) {
                    System.out.println("L'utilisateur a cliqué sur Oui");

                    a.getListeFacturesPayees().add(facture);
                    a.setBudget(a.getBudget() - montant);
                    InitialisationAppAsso.associationActuelle = a;
                    for(String f : a.getListeFacturesNonPayees()) {
                        if(facture.equals(f)) {
                            a.getListeFacturesNonPayees().remove(f);
                            break;
                        }
                    }
                    try {
                        Main.MaJFichierJSONAssociation();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    Stage stage = (Stage) refresh.getScene().getWindow();
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(AppAsso.class.getResource("/com.projet.appAsso/asso_facturesNonReglees.fxml"));
                        fxmlLoader.setController(new FacturesNonRegleesAssoView());
                        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                        stage.setScene(scene);
                        stage.setTitle("Application Association");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    System.out.println("L'utilisateur a cliqué sur Non");
                }

            });
        });
    };
}
