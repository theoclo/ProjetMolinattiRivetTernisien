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

    private String donsActuels(Association a){
        StringBuilder res = new StringBuilder();
        ArrayList<String> dons = a.getListeDemandeDons();
        for(String don : dons){
            res.append(don+",");
        }
        if(!res.isEmpty()){
            res.setLength(res.length() - 1);
        }
        return res.toString();
    }

    private String exclusActuels(Association a){
        StringBuilder res = new StringBuilder();
        ArrayList<Pair<Personne, Boolean>> cotisants = a.verifierCotisations();
        for(Pair<Personne, Boolean> p : cotisants) {
            if(! p.getValue() && ! p.getKey().getPseudo().equals(a.getPresident().getPseudo())) {
                res.append(p.getKey()+",");
            }
        }
        if(!res.isEmpty()){
            res.setLength(res.length() - 1);
        }
        return res.toString();
    }

    private String votesActuels(Association a){
        StringBuilder res = new StringBuilder();
        HashMap<Arbre,Integer> v = a.selectTop5Nomination();
        for( Map.Entry<Arbre, Integer> valeurs: v.entrySet()){
            res.append("Arbre n° : "+valeurs.getKey().getIdBase()+" | "+valeurs.getValue()+',');
        }
        if(!res.isEmpty()){
            res.setLength(res.length() - 1);
        }
        return res.toString();
    }

    private String rapportActuel(Association a){
        StringBuilder res = new StringBuilder();
        int recettes = 0;
        int cotisations =0;
        for(int i = 0; i<a.getListeDemandeDons().size();i++){
            int j = a.getListeDemandeDons().get(i).indexOf(':');
            String word = a.getListeDemandeDons().get(i).substring(0, j);
            recettes=recettes+Integer.parseInt(word);
        }

        for(Personne membre : a.getListeMembre()){
            if(membre.getaCotise()){
                cotisations=cotisations+a.getPrixCotisation();
            }
        }

        int depenses = 0;
        for(Visite vis : a.getListeVisite()){
            if(!vis.getCr().equals("")){
                depenses++;
            }
        }

        int facture = 0;
        for(String f : a.getListeFacturesPayees()){
            int j = f.indexOf('€');
            int montant = Integer.parseInt(f.substring(0, j));
            facture=facture+montant;
        }
        for(String f : a.getListeFacturesNonPayees()){
            int j = f.indexOf('€');
            int montant = Integer.parseInt(f.substring(0, j));
            facture=facture+montant;
        }



        res.append("Nb visites prévues : "+a.getListeVisite().size()+",");
        res.append("Nb visites réalisées : "+depenses+",");
        res.append("Dépenses : "+(depenses*a.getMontantDefraiement()+facture) +"€,");
        res.append("dont défraiement : "+depenses*a.getMontantDefraiement()+"€,");
        res.append("dont factures : "+facture+"€,");
        res.append("Recettes : "+(recettes+cotisations)+"€,");
        res.append("dont cotisations : "+cotisations+"€,");
        res.append("dont dons : "+recettes+"€");
        return res.toString();
    }

    @FXML
    public void initialize() {
        nom_asso.setText(InitialisationAppAsso.associationActuelle.toString());
        Association a = Association.getAssociation(nom_asso.getText());

        membres.getItems().clear();
        membres.getItems().addAll(exclusActuels(a).split(","));

        dons.getItems().clear();
        dons.getItems().addAll(donsActuels(a).split(","));

        votes.getItems().clear();
        votes.getItems().addAll(votesActuels(a).split(","));

        rapport.getItems().clear();
        rapport.getItems().addAll(rapportActuel(a).split(","));

       annee.getItems().clear();
        ArrayList<String> anneesB = new ArrayList<>();
        System.out.println(a.getAnneeBudgetaire());
        for(int i = 0; i<=a.getAnneeBudgetaire();i++){
            anneesB.add("Annee Budgétaire "+i);
        }
        annee.setItems(FXCollections.observableArrayList(anneesB));
        annee.setValue("Annee Budgetaire actuelle ("+a.getAnneeBudgetaire()+")");

        annee.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals("Annee Budgétaire "+a.getAnneeBudgetaire())) {
                membres.getItems().clear();
                membres.getItems().addAll(exclusActuels(a).split(","));

                dons.getItems().clear();
                dons.getItems().addAll(donsActuels(a).split(","));

                votes.getItems().clear();
                votes.getItems().addAll(votesActuels(a).split(","));

                rapport.getItems().clear();
                rapport.getItems().addAll(rapportActuel(a).split(","));
            }else if (! a.getListeExercicesBudgetaires().isEmpty()){
                String an = (String) annee.getValue();
                int anneeBudget = an.charAt(an.length()-1) - '0';
                ArrayList<String> valeurs = a.getListeExercicesBudgetaires().get(anneeBudget);

                membres.getItems().clear();
                membres.getItems().addAll(valeurs.get(1).split(","));

                dons.getItems().clear();
                dons.getItems().addAll(valeurs.get(0).split(","));

                votes.getItems().clear();
                votes.getItems().addAll(valeurs.get(2).split(","));

                rapport.getItems().clear();
                rapport.getItems().addAll(valeurs.get(3).split(","));
            }
            else{
                membres.getItems().clear();
                votes.getItems().clear();
                rapport.getItems().clear();
                dons.getItems().clear();
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
                        ArrayList<String> valeurs = new ArrayList<>();
                        valeurs.add(donsActuels(a));
                        valeurs.add(exclusActuels(a));
                        valeurs.add(votesActuels(a));
                        valeurs.add(rapportActuel(a));
                        a.getListeExercicesBudgetaires().add(valeurs);
                        a.getListeReco().clear();
                        a.setAnneeBudgetaire(a.getAnneeBudgetaire()+1);
                        a.getListeDemandeDons().clear();
                        a.getListeVisite().clear();

                        for(String facture : a.getListeFacturesNonPayees()) {
                            int j = facture.indexOf('€');
                            int montant = Integer.parseInt(facture.substring(0, j));
                            a.getListeFacturesPayees().add(facture);
                            a.setBudget(a.getBudget() - montant);
                        }
                        a.getListeFacturesNonPayees().clear();
                        a.ajouterFacturesBase();
                        a.getListeFacturesPayees().clear();

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
