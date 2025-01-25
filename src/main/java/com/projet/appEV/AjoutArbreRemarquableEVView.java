package com.projet.appEV;

import com.projet.Arbre;
import com.projet.Main;
import com.projet.appMembres.InitialisationAppMembre;
import com.projet.entite.Association;
import com.projet.entite.Personne;
import com.projet.espacesVerts.ServiceEV;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AjoutArbreRemarquableEVView {
    @FXML
    private Button deconnecter;

    @FXML
    private Button refresh;

    @FXML
    private Button retour;

    @FXML
    private Button valider;

    @FXML
    private ListView listview;

    @FXML
    private TextField text;

    @FXML
    public void initialize(){
        refresh.setVisible(false);

        ArrayList<String>arbreNonRemarquable= new ArrayList<>();
        ArrayList<Integer> idNonRemarquable = new ArrayList<>();

        Map<Arbre, Integer> arbreVotes = ServiceEV.listeServiceEV.getFirst().obtenirVotesNonRemarquables();
        List<Map.Entry<Arbre, Integer>> sortedEntries = arbreVotes.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toList());

        // Print the sorted entries
        for (Map.Entry<Arbre, Integer> entry : sortedEntries) {
            arbreNonRemarquable.add("Votes: " + entry.getValue() +", Arbre: " + entry.getKey() );
            idNonRemarquable.add(entry.getKey().idBase());
        }




        listview.setItems(FXCollections.observableArrayList(arbreNonRemarquable));

        valider.setDisable(true);

        text.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                text.setText(oldValue);
            }
            if(text.getText().equals("") && listview.getSelectionModel().getSelectedItem() == null){
                valider.setDisable(true);
            }
            else{
                valider.setDisable(false);
            }
        });

        listview.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(text.getText().equals("") && listview.getSelectionModel().getSelectedItem() == null){
                valider.setDisable(true);
            }
            else{
                valider.setDisable(false);
            }
        });

        deconnecter.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Se déconnecter' cliqué");
            Stage stage = (Stage) deconnecter.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppEV.class.getResource("/com.projet.appEV/ev_base.fxml"));
                fxmlLoader.setController(new BaseEVView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Espaces Verts");
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
                FXMLLoader fxmlLoader = new FXMLLoader(AppEV.class.getResource("/com.projet.appEV/ev_gestionArbres.fxml"));
                fxmlLoader.setController(new GestionArbresEVView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Espaces Verts");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        valider.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Valider' cliqué");
            String aS = (String) listview.getSelectionModel().getSelectedItem();
            Integer idA=0;
            if(!(aS == null)){

                Pattern pattern = Pattern.compile("Arbre N°(\\d+)");
                Matcher matcher = pattern.matcher(aS);
                if (matcher.find()) {
                    idA = Integer.parseInt(matcher.group(1));
                }
            }
            Arbre arbreSelectionne = Arbre.obtenirArbre(idA);

            if(arbreSelectionne == null){
                int id = Integer.valueOf(text.getText());
                for(Integer i : idNonRemarquable){
                    if(id == i){
                        arbreSelectionne = Arbre.obtenirArbre(i);
                    }
                }
            }
            else{
                arbreSelectionne = Arbre.obtenirArbre(arbreSelectionne.idBase());
            }
            if(arbreSelectionne == null){
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Erreur");
                alert2.setHeaderText("Erreur lors de la demande ce classification de l'arbre");
                alert2.setContentText("L'arbre ayant l'id "+ text.getText() + " n'existe pas dans la base de données");
                alert2.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Rendre remarquable un arbre");
                alert.setHeaderText("Êtes-vous sûr de vouloir rendre remarquable cet arbre ?");
                alert.setContentText("Son id dans la base de données est " + arbreSelectionne.idBase());
                ButtonType buttonTypeYes = new ButtonType("Oui");
                ButtonType buttonTypeNo = new ButtonType("Non");
                alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
                Arbre finalArbreSelectionne = arbreSelectionne;
                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType == buttonTypeYes) {
                        System.out.println("L'utilisateur a cliqué sur Oui");

                        for(Arbre a : ServiceEV.listeServiceEV.getFirst().getListeArbre()){
                            if(a.idBase() == finalArbreSelectionne.idBase()){
                                ServiceEV.listeServiceEV.getFirst().getListeArbre().remove(a);
                                ServiceEV.listeServiceEV.getFirst().getListeArbre().add(finalArbreSelectionne.withClassifie(true).withDateClassification(Optional.of(LocalDate.now())));
                                break;
                            }
                        }
                        for(Arbre a : Arbre.listeArbres){
                            if(a.idBase() == finalArbreSelectionne.idBase()){
                                Arbre.listeArbres.remove(a);
                                Arbre.listeArbres.add(finalArbreSelectionne.withClassifie(true).withDateClassification(Optional.of(LocalDate.now())));
                                break;
                            }
                        }
                        for (Association asso : ServiceEV.obtenirAssosAbonne()) {
                            try {
                                asso.ajouterNotification("L'arbre " + finalArbreSelectionne.idBase() + " a été classifié le " + LocalDate.now());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        for (Personne p : ServiceEV.obtenirParticuliersAbonne()) {
                            try {
                                p.ajouterNotification("L'arbre " + finalArbreSelectionne.idBase() + " a été classifié le " + LocalDate.now());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        InitialisationAppMembre.arbresNonRemarquables.remove(finalArbreSelectionne);
                        InitialisationAppMembre.arbresRemarquables.add(finalArbreSelectionne.withClassifie(true).withDateClassification(Optional.of(LocalDate.now())));
                        try {
                            Main.MaJFichierJSONArbres();
                            Main.MaJFichierServiceEV();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Stage stage = (Stage) retour.getScene().getWindow();
                        try {
                            FXMLLoader fxmlLoader = new FXMLLoader(AppEV.class.getResource("/com.projet.appEV/ev_gestionArbres.fxml"));
                            fxmlLoader.setController(new GestionArbresEVView());
                            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                            stage.setScene(scene);
                            stage.setTitle("Application Espaces Verts");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("L'utilisateur a cliqué sur Non");
                    }

                });
            }
        });


    }
}
