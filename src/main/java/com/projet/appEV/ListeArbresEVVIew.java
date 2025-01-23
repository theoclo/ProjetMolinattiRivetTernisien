package com.projet.appEV;

import com.projet.Arbre;
import com.projet.appMembres.InitialisationAppMembre;
import com.projet.espacesVerts.ServiceEV;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ListeArbresEVVIew {

    @FXML
    private Button deconnecter;

    @FXML
    private Button refresh;

    @FXML
    private ListView listview;

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
        //listview.setItems(InitialisationAppMembre.arbres);


        id.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getIdBase()));
        nom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        genre.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenre()));
        espece.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEspece()));
        circ.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCirconference()));
        haut.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getHauteur()));
        stadedev.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStadeDev().toString()));
        adresse.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAdresseAcces()));
        coord.setCellValueFactory(cellData -> {
            Map<String, Double> coordGPS = cellData.getValue().getCoordGPS();
            Double latitude = coordGPS.get("latitude");
            Double longitude = coordGPS.get("longitude");

            // Vérifier si les valeurs existent pour éviter les erreurs
            String formattedCoord = (latitude != null && longitude != null)
                    ? String.format("(%.6f, %.6f)", latitude, longitude)
                    : "Coordonnées indisponibles";

            return new SimpleStringProperty(formattedCoord);
        });
        classifie.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClassifie() ? "OUI" : "NON"));
        date.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getDateClassification().map(LocalDate::toString).orElse("")));

        table.setItems(FXCollections.observableArrayList(ServiceEV.listeServiceEV.get(0).getListeArbre()));


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
            Stage stage = (Stage) retour.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppEV.class.getResource("/com.projet.appEV/ev_listeArbres.fxml"));
                fxmlLoader.setController(new ListeArbresEVVIew());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Espaces Verts");
            } catch (IOException e) {
                e.printStackTrace();
            }
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
    }
}
