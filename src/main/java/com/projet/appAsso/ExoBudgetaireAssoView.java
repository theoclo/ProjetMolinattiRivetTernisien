package com.projet.appAsso;

import com.projet.Arbre;
import com.projet.Main;
import com.projet.entite.Association;
import com.projet.entite.Personne;
import com.projet.espacesVerts.ServiceEV;
import com.projet.espacesVerts.Visite;
import javafx.collections.FXCollections;
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
        Association a = Association.getAssociation(nom_asso.getText());

        membres.getItems().clear();
        ArrayList<Pair<Personne, Boolean>> cotisants = a.verifierCotisations();
        ArrayList<Personne> exclus = new ArrayList<>();
        for(Pair<Personne, Boolean> p : cotisants) {
            if(! p.getValue() && ! p.getKey().getPseudo().equals(a.getPresident().getPseudo())) {
                membres.getItems().add(p.getKey());
            }
        }

        votes.getItems().clear();
        HashMap<Arbre,Integer> v = a.selectTop5Nomination();
        for( Map.Entry<Arbre, Integer> valeurs: v.entrySet()){
            votes.getItems().add("Arbre n° : "+valeurs.getKey().getIdBase()+" | "+valeurs.getValue());
        }

        annee.getItems().clear();
        ArrayList<String> anneesB = new ArrayList<>();
        System.out.println(a.getAnneeBudgetaire());
        for(int i = 0; i<=a.getAnneeBudgetaire();i++){
            anneesB.add("Annee Budgétaire "+i);
        }
        annee.setItems(FXCollections.observableArrayList(anneesB));
        annee.setValue("Annee Budgétaire "+a.getAnneeBudgetaire());


        int recettes = 0;
        for(int i = 0; i<a.getListeDemandeDons().size();i++){
            System.out.println(a.getListeDemandeDons().get(i));
            int j = a.getListeDemandeDons().get(i).indexOf(':');
            String word = a.getListeDemandeDons().get(i).substring(0, j);
            recettes=recettes+Integer.parseInt(word);
        }

        int depenses = 0;
        for(Visite vis : a.getListeVisite()){
            if(!vis.getCr().equals("")){
                depenses++;
            }
        }

        rapport.getItems().clear();
        rapport.getItems().add("Nb visites : "+a.getListeVisite().size());
        rapport.getItems().add("Dépenses : "+depenses*a.getMontantDefraiement() +"€");
        rapport.getItems().add("Recettes : "+recettes+"€");

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
                        a.setAnneeBudgetaire(a.getAnneeBudgetaire()+1);
                        a.getListeDemandeDons().clear();
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
