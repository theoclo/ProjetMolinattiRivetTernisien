package com.projet.appEV;

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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class AbattreArbreEVView {
    @FXML
    private Button deconnecter;

    @FXML
    private Button refresh;

    @FXML
    private ListView listview;

    @FXML
    private Button abattre;

    @FXML
    private Button retour;

    @FXML
    public void initialize() throws IOException {
        refresh.setVisible(false);
        listview.setItems(FXCollections.observableArrayList(ServiceEV.listeServiceEV.get(0).getListeArbre()));
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

        abattre.setOnMouseClicked(event -> {
            Arbre arbreSelectionne = (Arbre) listview.getSelectionModel().getSelectedItem();
            arbreSelectionne = Arbre.obtenirArbre(arbreSelectionne.idBase());
            System.out.println("Bouton 'Abattre' cliqué");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Abattage d'un arbre");
            alert.setHeaderText("Êtes-vous sûr de vouloir abattre cet arbre ?");
            alert.setContentText("");
            ButtonType buttonTypeYes = new ButtonType("Oui");
            ButtonType buttonTypeNo = new ButtonType("Non");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            Arbre finalArbreSelectionne = arbreSelectionne;
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == buttonTypeYes) {
                    System.out.println("L'utilisateur a cliqué sur Oui");
                    for(Association a : Association.listeAssociations){
                        for(Personne p : a.getListeMembre()){
                            try {
                                p.retirerArbre(finalArbreSelectionne);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }

                    for(Association asso : ServiceEV.obtenirAssosAbonne()){
                        try {
                            asso.ajouterNotification("L'arbre "+finalArbreSelectionne.idBase()+" a été abattu le "+LocalDate.now());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    for(Personne p : ServiceEV.obtenirParticuliersAbonne()){
                        try {
                            p.ajouterNotification("L'arbre "+finalArbreSelectionne.idBase()+" a été abattu le "+LocalDate.now());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    for(Arbre a : ServiceEV.listeServiceEV.get(0).getListeArbre()){
                        if(a.idBase() == finalArbreSelectionne.idBase()){
                            ServiceEV.listeServiceEV.get(0).getListeArbre().remove(a);
                            break;
                        }
                    }

                    for(Association a : Association.listeAssociations){
                        for(Visite v : a.getListeVisite()){
                            if(v.arbre().idBase() == finalArbreSelectionne.idBase()){
                                a.getListeVisite().remove(v);
                                break;
                            }
                        }
                    }

                    Arbre.listeArbres.remove(finalArbreSelectionne);

                    try {
                        Main.MaJFichierJSONArbres();
                        Main.MaJFichierServiceEV();
                        Main.MaJFichierJSONAssociation();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }



                    Stage stage = (Stage) abattre.getScene().getWindow();
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
        });
    }
}
