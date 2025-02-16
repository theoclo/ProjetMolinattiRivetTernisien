package com.projet.appMembres;

import com.projet.Arbre;
import com.projet.entite.Association;
import com.projet.entite.Personne;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class VotesMembreView {
    @FXML
    private Label nom_membre;

    @FXML
    private Button refresh;

    @FXML
    private Button deconnecter;

    @FXML
    private Button retour;

    @FXML
    public ListView listview;

    @FXML
    private Button ajouterArbre;

    @FXML
    public void initialize(){
        nom_membre.setText(InitialisationAppMembre.membreActuel.toString());
        InitialisationAppMembre.listeRecommandations.clear();
        Personne p = InitialisationAppMembre.membreActuel;
        Association a = p.obtenirAssociationObjet().get();
        ArrayList<Arbre> lR= a.getListeReco().get(p.getPseudo());
        if(lR != null){
            InitialisationAppMembre.listeRecommandations.addAll(lR);
        }
        listview.setItems(InitialisationAppMembre.listeRecommandations);


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
                FXMLLoader fxmlLoader = new FXMLLoader(AppMembre.class.getResource("/com.projet.appMembres/membre_votes.fxml"));
                fxmlLoader.setController(new VotesMembreView());
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
                FXMLLoader fxmlLoader = new FXMLLoader(AppMembre.class.getResource("/com.projet.appMembres/membre_connecte.fxml"));
                fxmlLoader.setController(new ConnecteMembreView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Membre");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        ajouterArbre.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Ajouter Arbre' cliqué");
            Stage stage = (Stage) ajouterArbre.getScene().getWindow();
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
    }
}
