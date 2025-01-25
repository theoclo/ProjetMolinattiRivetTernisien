package com.projet.appAsso;

import com.projet.Arbre;
import com.projet.appMembres.AppMembre;
import com.projet.appMembres.ArbresMembreView;
import com.projet.appMembres.ConnecteMembreView;
import com.projet.appMembres.InitialisationAppMembre;
import com.projet.espacesVerts.ServiceEV;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;

public class ArbresAssoView {
    @FXML
    private Label nom_asso;

    @FXML
    private Button deconnecter;

    @FXML
    private Button refresh;

    @FXML
    private Button retour;

    @FXML
    private TableView<Arbre> table;

    @FXML
    private TableColumn<Arbre, Integer> id;

    @FXML
    private TableColumn<Arbre, String> nom;

    @FXML
    private TableColumn<Arbre, String> genre;

    @FXML
    private TableColumn<Arbre, String> espece;

    @FXML
    private TableColumn<Arbre, Integer> circ;

    @FXML
    private TableColumn<Arbre, Integer> haut;

    @FXML
    private TableColumn<Arbre, String> stadedev;

    @FXML
    private TableColumn<Arbre, String> adresse;

    @FXML
    private TableColumn<Arbre, String> coord;

    @FXML
    private TableColumn<Arbre, String> classifie;

    @FXML
    private TableColumn<Arbre, String> date;

    @FXML
    public void initialize(){
        nom_asso.setText(InitialisationAppAsso.associationActuelle.toString());

        id.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().idBase()));
        nom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().nom()));
        genre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().genre()));
        espece.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().espece()));
        circ.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().circonference()));
        haut.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().hauteur()));
        stadedev.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().stadeDev().toString()));
        adresse.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().adresseAcces()));
        coord.setCellValueFactory(cellData -> {
            Map<String, Double> coordGPS = cellData.getValue().coordGPS();
            Double latitude = coordGPS.get("latitude");
            Double longitude = coordGPS.get("longitude");

            // Vérifier si les valeurs existent pour éviter les erreurs
            String formattedCoord = (latitude != null && longitude != null)
                    ? String.format("(%.6f, %.6f)", latitude, longitude)
                    : "Coordonnées indisponibles";

            return new SimpleStringProperty(formattedCoord);
        });
        classifie.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().classifie() ? "OUI" : "NON"));
        date.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().dateClassification().map(LocalDate::toString).orElse("")));

        table.setItems(FXCollections.observableArrayList(ServiceEV.listeServiceEV.getFirst().getListeArbre()));


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
                FXMLLoader fxmlLoader = new FXMLLoader(AppAsso.class.getResource("/com.projet.appAsso/asso_listeArbres.fxml"));
                fxmlLoader.setController(new ArbresAssoView());
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
                FXMLLoader fxmlLoader = new FXMLLoader(AppAsso.class.getResource("/com.projet.appAsso/asso_home.fxml"));
                fxmlLoader.setController(new HomeAssoView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Association");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
