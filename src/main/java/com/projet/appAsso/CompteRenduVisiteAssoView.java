package com.projet.appAsso;

import com.projet.entite.Association;
import com.projet.espacesVerts.Visite;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class CompteRenduVisiteAssoView {

    @FXML
    private Label nom_asso;

    @FXML
    private Button refresh;

    @FXML
    private Button deconnecter;

    @FXML
    private ComboBox combobox;

    @FXML
    private TextArea text;

    @FXML
    private Button retour;


    @FXML
    public void initialize() {
        nom_asso.setText(InitialisationAppAsso.associationActuelle.toString());

        ArrayList<Visite> lvisite = Association.getAssociation(InitialisationAppAsso.associationActuelle.getNom()).getListeVisite();
        ArrayList<Visite> visites = new ArrayList<>();
        for(Visite v : lvisite){
            if(!v.getCr().equals("")){
                visites.add(v);
            }
        }
        visites.sort(Comparator.comparing(Visite::getDate));

        combobox.setItems(FXCollections.observableList(visites));

        combobox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            Visite v = (Visite) combobox.getSelectionModel().getSelectedItem();
            text.setText(v.getCr());
        });

        text.setText("Sélectionnez une visite pour en voir son compte rendu.");

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

        retour.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Retour' cliqué");
            Stage stage = (Stage) retour.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppAsso.class.getResource("/com.projet.appAsso/asso_visites.fxml"));
                fxmlLoader.setController(new VisitesAssoView());
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


    };
}
