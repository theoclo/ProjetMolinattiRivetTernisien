package com.projet.appMembres;

import com.projet.Main;
import com.projet.entite.Association;
import com.projet.entite.Personne;
import com.projet.espacesVerts.Visite;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class VisitesCompteRenduView {


    @FXML
    private Label nom_membre;

    @FXML
    private Button refresh;

    @FXML
    private Button deconnecter;

    @FXML
    private Button retour;

    @FXML
    private ComboBox combobox;

    @FXML
    private Button valider;

    @FXML
    private TextArea textCR;

    @FXML
    public void initialize() {
        ArrayList<Visite> visites= Association.obtenirVisitesParticipant(InitialisationAppMembre.membreActuel.getAssociation().get(), InitialisationAppMembre.membreActuel.getPseudo());
        visites.sort(Comparator.comparing(Visite::date));

        combobox.setItems(FXCollections.observableList(visites));

        combobox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            valider.setDisable(newValue == null || combobox.getValue() == null || textCR.getText().equals(""));
        });

        textCR.textProperty().addListener((observable, oldValue, newValue) -> {
            valider.setDisable(newValue == null || combobox.getValue() == null || textCR.getText().equals(""));
        });

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
            Stage stage = (Stage) refresh.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppMembre.class.getResource("/com.projet.appMembres/membre_compteRendu.fxml"));
                fxmlLoader.setController(new VisitesCompteRenduView());
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
                FXMLLoader fxmlLoader = new FXMLLoader(AppMembre.class.getResource("/com.projet.appMembres/membre_visites.fxml"));
                fxmlLoader.setController(new VisitesMembreView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Membre");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        valider.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Valider' cliqué");

            Visite v = (Visite) combobox.getValue();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Rédaction d'un compte-rendu");
            alert.setHeaderText("Voulez-vous confirmer l'envoi de ce compte-rendu ?");
            //Peut etre afficher la date etc
            alert.setContentText("Date de la visite : " + v.date().getDayOfYear() + "-" + v.date().getMonthValue() + "-" + v.date().getYear());
            ButtonType buttonTypeYes = new ButtonType("Oui");
            ButtonType buttonTypeNo = new ButtonType("Non");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == buttonTypeYes) {
                    System.out.println("L'utilisateur a cliqué sur Oui");

                    String text = textCR.getText();
                    Association a = Association.getAssociation(InitialisationAppMembre.membreActuel.getAssociation().get());
                    for(Visite visite : a.getListeVisite()){
                        if(v.equals(visite)){
                            Personne p = Personne.obtenirPersonne(InitialisationAppMembre.membreActuel.getPseudo());
                            p.setSolde(p.getSolde()+a.getMontantDefraiement());
                            for(Personne membre :a.getListeMembre()){
                                if(membre.getPseudo().equals(p.getPseudo())){
                                    membre.setSolde(p.getSolde());
                                    break;
                                }
                            }
                            a.setBudget(a.getBudget()-a.getMontantDefraiement());
                            InitialisationAppMembre.membreActuel = p;
                            a.getListeVisite().add(visite.withPayee(true).withCr(text));
                            a.getListeVisite().remove(visite);
                            break;
                        }

                    }
                    try {
                        Main.MaJFichierJSONPersonnes();
                        Main.MaJFichierJSONAssociation();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Stage stage = (Stage) retour.getScene().getWindow();
                    try {

                        FXMLLoader fxmlLoader = new FXMLLoader(AppMembre.class.getResource("/com.projet.appMembres/membre_visites.fxml"));
                        fxmlLoader.setController(new VisitesMembreView());
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

