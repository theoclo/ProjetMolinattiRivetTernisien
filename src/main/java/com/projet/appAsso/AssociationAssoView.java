package com.projet.appAsso;

import com.projet.appMembres.InitialisationAppMembre;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class AssociationAssoView {

    @FXML
    private Label nom_asso;

    @FXML
    private Button refresh;

    @FXML
    private Button deconnecter;

    @FXML
    private Button retour;

    @FXML
    private Button membres;

    @FXML
    private Button factures;

    @FXML
    private Button donateurs;

    @FXML
    private Button votes;

    @FXML
    private Button budget;

    @FXML
    private Text solde;

    @FXML
    public void initialize(){
        refresh.setVisible(false);

        String s;
        s = String.valueOf(InitialisationAppAsso.associationActuelle.getBudget());
        solde.setText("ASSOCIATION - "+ s+"€");

        nom_asso.setText(InitialisationAppAsso.associationActuelle.toString());

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
                FXMLLoader fxmlLoader = new FXMLLoader(AppAsso.class.getResource("/com.projet.appAsso/asso_home.fxml"));
                fxmlLoader.setController(new HomeAssoView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Association");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        membres.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Membres' cliqué");
            Stage stage = (Stage) membres.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppAsso.class.getResource("/com.projet.appAsso/asso_membres.fxml"));
                fxmlLoader.setController(new MembresAssoView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Association");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        factures.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Factures' cliqué");
            Stage stage = (Stage) factures.getScene().getWindow();
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

        donateurs.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Donateurs' cliqué");
            Stage stage = (Stage) donateurs.getScene().getWindow();
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

        votes.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Votes' cliqué");
            Stage stage = (Stage) votes.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppAsso.class.getResource("/com.projet.appAsso/asso_votes.fxml"));
                fxmlLoader.setController(new VotesAssoView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Association");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        budget.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Budget' cliqué");
            Stage stage = (Stage) budget.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppAsso.class.getResource("/com.projet.appAsso/asso_exoBudgetaire.fxml"));
                fxmlLoader.setController(new ExoBudgetaireAssoView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Association");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
