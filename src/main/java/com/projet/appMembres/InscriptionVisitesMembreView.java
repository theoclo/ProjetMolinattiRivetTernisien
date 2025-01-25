package com.projet.appMembres;

import com.projet.Main;
import com.projet.entite.Association;
import com.projet.entite.Personne;
import com.projet.espacesVerts.Visite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InscriptionVisitesMembreView {

    private Visite visiteChoisie;

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
    public void initialize() {

        ArrayList<Visite> visites= Association.obtenirVisitesSansParticipant(InitialisationAppMembre.membreActuel.getAssociation().get());
        visites.sort(Comparator.comparing(Visite::getDate));

        combobox.setItems(FXCollections.observableList(visites));

        valider.setDisable(true);

        combobox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                valider.setDisable(false);
            }else {
                valider.setDisable(true);
            }
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
                FXMLLoader fxmlLoader = new FXMLLoader(AppMembre.class.getResource("/com.projet.appMembres/membre_inscriptionVisite.fxml"));
                fxmlLoader.setController(new InscriptionVisitesMembreView());
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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Inscription à une visite");
            alert.setHeaderText("Êtes-vous sûr de vouloir vous inscrire pour faire cette visite ?");
            //Peut etre afficher la date etc
            Text attentionText = new Text("ATTENTION, IL FAUT FAIRE LE COMPTE RENDU POUR VALIDER LA VISITE ET POUR ÊTRE PAYÉ");
            attentionText.setStyle("-fx-fill: red; -fx-font-weight: bold;");

            TextFlow content = new TextFlow(attentionText);

            alert.getDialogPane().setContent(content);

            ButtonType buttonTypeYes = new ButtonType("Oui");
            ButtonType buttonTypeNo = new ButtonType("Non");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == buttonTypeYes) {
                    System.out.println("L'utilisateur a cliqué sur Oui");

                    visiteChoisie = (Visite) combobox.getValue();
                    for(Visite v : visites){
                        if(visiteChoisie.equals(v)){
                            v.affecterParticipant(InitialisationAppMembre.membreActuel.getPseudo());
                            try {
                                Main.MaJFichierJSONAssociation();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }

                    Personne p = Personne.obtenirPersonne(InitialisationAppMembre.membreActuel.getPseudo());
                    p.setNbVisites(p.getNbVisites() + 1);
                    for(Personne pers : Association.getAssociation(p.getAssociation().get()).getListeMembre()){
                        if(pers.getPseudo().equals(p.getPseudo())){
                            pers.setNbVisites(pers.getNbVisites() + 1);
                        }
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
