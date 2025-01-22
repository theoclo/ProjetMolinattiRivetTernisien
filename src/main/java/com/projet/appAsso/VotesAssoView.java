package com.projet.appAsso;

import com.projet.Arbre;
import com.projet.entite.Association;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class VotesAssoView {

    @FXML
    private Label nom_asso;

    @FXML
    private Button refresh;

    @FXML
    private Button deconnecter;

    @FXML
    private Button retour;

    @FXML
    private Button classement;

    @FXML
    private ListView list;


    @FXML
    public void initialize() {
        nom_asso.setText(InitialisationAppAsso.associationActuelle.toString());
        Association a = Association.getAssociation(InitialisationAppAsso.associationActuelle.getNom());
        LinkedHashMap<Arbre, Integer> votesAsso = a.selectTop5Nomination();
        ObservableList<String> items = FXCollections.observableArrayList();

        for (Map.Entry<Arbre, Integer> entry : votesAsso.entrySet()) {
            Arbre arbre = entry.getKey();
            int votes = entry.getValue();
            items.add("Votes : "+ votes + " - Arbre : " + arbre);
        }

        list.setItems(items);


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

        classement.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Classement complet' cliqué");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Liste des votes");
            alert.setHeaderText("Voici la liste complète des votes actuels :");
            ListView<String> listView = new ListView<>();
            alert.getDialogPane().setContent(listView);
            LinkedHashMap<Arbre, Integer> votesAsso2 = a.selectAllNomination();
            ObservableList<String> items2 = FXCollections.observableArrayList();

            for (Map.Entry<Arbre, Integer> entry : votesAsso2.entrySet()) {
                Arbre arbre = entry.getKey();
                int votes = entry.getValue();
                items2.add("Votes : "+ votes + " - Arbre : " + arbre);
            }
            listView.setItems(items2);
            listView.setPrefHeight(300);
            listView.setPrefWidth(600);
            alert.showAndWait();
        });
    }
}
