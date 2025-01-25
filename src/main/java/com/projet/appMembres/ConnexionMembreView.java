package com.projet.appMembres;

import com.projet.entite.Association;
import com.projet.entite.Personne;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.projet.appMembres.InitialisationAppMembre.membreActuel;


public class ConnexionMembreView {
    @FXML
    private Text text_association;

    @FXML
    private Button bouton_connexion;

    @FXML
    private ComboBox combobox;

    @FXML
    private ComboBox combobox_asso;

    @FXML
    private ImageView image;


    @FXML
    public void initialize(){
        combobox_asso.setItems(FXCollections.observableArrayList(Association.listeAssociations));
        combobox.setVisible(false);

        if(combobox_asso.getSelectionModel().getSelectedItem() == null){
            bouton_connexion.setDisable(true);
        }
        else{
            bouton_connexion.setDisable(false);
        }
        if(combobox.getSelectionModel().getSelectedItem() == null){
            bouton_connexion.setDisable(true);
        }
        else{
            bouton_connexion.setDisable(false);
        }

        combobox_asso.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                combobox.getItems().clear();
                combobox.setVisible(true);

                Association selectedAsso = (Association) newValue;
                List<Personne> membresAsso = new ArrayList<>();
                for (Personne membre : selectedAsso.getListeMembre()) {
                    membresAsso.add(membre);
                }
                combobox.setItems(FXCollections.observableArrayList(membresAsso));
            } else {
                combobox.setVisible(false);
            }
        });
        combobox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                bouton_connexion.setDisable(false);
            }else {
                bouton_connexion.setDisable(true);
            }
        });

                bouton_connexion.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Se connecter' cliqué");
            membreActuel = (Personne) combobox.getValue();

            if (membreActuel != null && combobox_asso.getValue() != null) {
                Stage stage = (Stage) bouton_connexion.getScene().getWindow();
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(AppMembre.class.getResource("/com.projet.appMembres/membre_connecte.fxml"));
                    fxmlLoader.setController(new ConnecteMembreView()); // Pass the selected member
                    Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                    stage.setScene(scene);
                    stage.setTitle("Application Membre");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Aucun membre OU ASSO (A FAIRE) sélectionné");
                // Handle case where no member is selected (e.g., show an alert)
            }
        });
    }
}