package com.projet.appAsso;

import com.projet.Arbre;
import com.projet.Main;
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

public class ExoBudgetaireAssoView {

    @FXML
    private Label nom_asso;

    @FXML
    private Button refresh;

    @FXML
    private Button deconnecter;

    @FXML
    private Button retour;

    @FXML
    private Button fin;

    @FXML
    private ComboBox annee;

    @FXML
    private ListView dons;

    @FXML
    private ListView membres;

    @FXML
    private ListView votes;

    @FXML
    private ListView rapport;

    @FXML
    public void initialize() {
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
            Stage stage = (Stage) refresh.getScene().getWindow();
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

        fin.setOnMouseClicked(event -> {
            System.out.printf("Bouton fin cliqué");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Quitter l'association");
            alert.setHeaderText("Êtes-vous sûr de vouloir mettre fin à l'exercice budgétaire ?");
            alert.setContentText("");
            ButtonType buttonTypeYes = new ButtonType("Oui");
            ButtonType buttonTypeNo = new ButtonType("Non");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == buttonTypeYes) {
                    System.out.println("L'utilisateur a cliqué sur Oui");
                    Association a = Association.getAssociation(nom_asso.getText());
                    ArrayList<Pair<Personne, Boolean>> cotisations = a.verifierCotisations();
                    for(Pair<Personne, Boolean> pair : cotisations) {
                        Personne p = pair.getKey();
                        p=Personne.obtenirPersonne(p.getPseudo());
                        if(! pair.getValue()){
                            try {
                                if(p.quitterAsso()){
                                    System.out.println(p.getPseudo() + " a été expulsé de l'association.");
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        Map<Integer, Integer> idVotes = new HashMap<>();
                        for(Map.Entry<Arbre,Integer> entree : a.selectTop5Nomination().entrySet()) {
                            idVotes.put(entree.getKey().getIdBase(), entree.getValue() );
                        }
                        ServiceEV.listeServiceEV.get(0).ajouterVotesNonRemarquables(idVotes);
                        a.getListeReco().clear();
                        for(Personne pers : a.getListeMembre()){
                            pers.setaCotise(false);
                            pers.setNbVisites(0);
                            Personne ps = Personne.obtenirPersonne(pers.getPseudo());
                            ps.setaCotise(false);
                            ps.setNbVisites(0);
                        }

                        try {
                            Main.MaJFichierServiceEV();
                            Main.MaJFichierJSONAssociation();
                            Main.MaJFichierJSONPersonnes();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }


                    }


                } else {
                    System.out.println("L'utilisateur a cliqué sur Non");
                }

            });
        });

    };
}
