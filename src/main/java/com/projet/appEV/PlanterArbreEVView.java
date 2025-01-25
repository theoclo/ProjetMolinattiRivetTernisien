package com.projet.appEV;

import com.projet.Arbre;
import com.projet.Main;
import com.projet.appMembres.InitialisationAppMembre;
import com.projet.entite.Association;
import com.projet.entite.Personne;
import com.projet.espacesVerts.ServiceEV;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PlanterArbreEVView {
    @FXML
    private Button deconnecter;

    @FXML
    private Button refresh;

    @FXML
    private Button retour;

    @FXML
    private Button planter;

    @FXML
    private TextField nom;

    @FXML
    private TextField genre;

    @FXML
    private TextField espece;

    @FXML
    private TextField circonference;

    @FXML
    private TextField hauteur;

    @FXML
    private TextField adresse;

    @FXML
    private TextField latitude;

    @FXML
    private TextField longitude;

    @FXML
    public void initialize(){
        refresh.setVisible(false);

        BooleanBinding fieldsNotValid = Bindings.createBooleanBinding(() ->
                        nom.getText().trim().isEmpty() ||
                                genre.getText().trim().isEmpty() ||
                                espece.getText().trim().isEmpty() ||
                                circonference.getText().trim().isEmpty() ||
                                hauteur.getText().trim().isEmpty() ||
                                adresse.getText().trim().isEmpty() ||
                                latitude.getText().trim().isEmpty() ||
                                longitude.getText().trim().isEmpty(),
                nom.textProperty(),
                genre.textProperty(),
                espece.textProperty(),
                circonference.textProperty(),
                hauteur.textProperty(),
                adresse.textProperty(),
                latitude.textProperty(),
                longitude.textProperty()
        );

        planter.disableProperty().bind(fieldsNotValid);

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

        nom.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z\\s-]*")) {
                nom.setText(oldValue);
            }
        });
        genre.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z\\s-]*")) {
                genre.setText(oldValue);
            }
        });
        espece.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z\\s-]*")) {
                espece.setText(oldValue);
            }
        });
        circonference.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                circonference.setText(oldValue);
            }
        });
        hauteur.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                hauteur.setText(oldValue);
            }
        });
        adresse.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z0-9\\s]*")) {
                adresse.setText(oldValue);
            }
        });
        latitude.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d*(\\.\\d*)?")) {
                latitude.setText(oldValue);
            }
        });
        longitude.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d*(\\.\\d*)?")) {
                longitude.setText(oldValue);
            }
        });





        planter.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Planter un arbre' cliqué");
            net.datafaker.Faker faker = new net.datafaker.Faker(new java.util.Locale("fr_FR", "FR"));
            int rand = faker.number().numberBetween(1, 99874);
            for(Arbre a : Arbre.listeArbres){
                while(a.idBase() == rand){
                    rand = faker.number().numberBetween(1, 99874);
                }
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Planter un arbre");
            alert.setHeaderText("Êtes-vous sûr de vouloir planter cet arbre ?");
            alert.setContentText("Il aura l'ID " + rand);
            ButtonType buttonTypeYes = new ButtonType("Oui");
            ButtonType buttonTypeNo = new ButtonType("Non");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            int finalRand = rand;
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == buttonTypeYes) {
                    System.out.println("L'utilisateur a cliqué sur Oui");
                    String n = nom.getText();
                    String g = genre.getText();
                    String esp = espece.getText();
                    int circ = Integer.parseInt(circonference.getText());
                    int haut = Integer.parseInt(hauteur.getText());
                    String ad = adresse.getText();
                    double lat = Double.parseDouble(latitude.getText());
                    double lon = Double.parseDouble(longitude.getText());
                    HashMap<String, Double> coordGPS = new HashMap<>();
                    coordGPS.put("latitude", lat);
                    coordGPS.put("longitude", lon);
                    Arbre arbre = new Arbre(finalRand, n, g, esp, circ, haut, Arbre.ArbreDEV.Jeune, ad, coordGPS,false, Optional.empty());
                    Arbre.listeArbres.add(arbre);
                    for(Association asso : ServiceEV.obtenirAssosAbonne()){
                        try {
                            asso.ajouterNotification("L'arbre "+arbre.idBase()+" a été planté le "+ LocalDate.now());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    for(Personne p : ServiceEV.obtenirParticuliersAbonne()){
                        try {
                            p.ajouterNotification("L'arbre "+ arbre.idBase()+" a été planté le "+LocalDate.now());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println("L'arbre " + arbre + " a été créé !");
                    ServiceEV.listeServiceEV.get(0).addArbre(arbre);
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
        });
    }


}
