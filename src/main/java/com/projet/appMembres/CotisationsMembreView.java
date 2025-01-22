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
import java.time.LocalDate;

public class CotisationsMembreView {
    @FXML
    private Label nom_membre;

    @FXML
    private Button refresh;

    @FXML
    private Button deconnecter;

    @FXML
    private Button retour;

    @FXML
    private ListView listview;

    @FXML
    private Button cotiser;

    @FXML
    private Label solde;

    @FXML
    public void initialize(){

        Personne m = InitialisationAppMembre.membreActuel;
        if(m.getSolde() < m.obtenirAssociationObjet().get().getPrixCotisation() || m.getListeCotisation().containsKey(LocalDate.now().getYear())){
            cotiser.setDisable(true);
        }
        else{
            cotiser.setDisable(false);
        }
        nom_membre.setText(InitialisationAppMembre.membreActuel.toString());
        solde.setText(String.valueOf(InitialisationAppMembre.membreActuel.getSolde()));

        ObservableList<String> cotisations = FXCollections.observableArrayList();
        for(Integer annee : m.getListeCotisation().keySet()){
            String cotisation = annee.toString() + " " + m.getListeCotisation().get(annee);
            cotisations.add(cotisation);
        }
        listview.setItems(cotisations);

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
                FXMLLoader fxmlLoader = new FXMLLoader(AppMembre.class.getResource("/com.projet.appMembres/membre_cotisations.fxml"));
                fxmlLoader.setController(new CotisationsMembreView());
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
                FXMLLoader fxmlLoader = new FXMLLoader(AppMembre.class.getResource("/com.projet.appMembres/membre_association.fxml"));
                fxmlLoader.setController(new AssociationMembreView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Membre");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        cotiser.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Cotiser' cliqué");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cotisation à l'association");
            alert.setHeaderText("Êtes-vous sûr de vouloir cotiser pour cette association ?");
            alert.setContentText("Le prix est de " + InitialisationAppMembre.membreActuel.obtenirAssociationObjet().get().getPrixCotisation());
            ButtonType buttonTypeYes = new ButtonType("Oui");
            ButtonType buttonTypeNo = new ButtonType("Non");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == buttonTypeYes) {
                    System.out.println("L'utilisateur a cliqué sur Oui");
                    try {
                        InitialisationAppMembre.membreActuel.payerAsso();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    cotiser.setDisable(true);
                    solde.setText(String.valueOf(InitialisationAppMembre.membreActuel.getSolde()));
                    for(Integer annee : m.getListeCotisation().keySet()){
                        String cotisation = annee.toString() + " " + m.getListeCotisation().get(annee);
                        cotisations.add(cotisation);
                    }
                    listview.setItems(cotisations);
                } else {
                    System.out.println("L'utilisateur a cliqué sur Non");
                }
            });
        });
    }
}
