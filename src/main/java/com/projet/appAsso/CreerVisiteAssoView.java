package com.projet.appAsso;

import com.projet.Arbre;
import com.projet.Main;
import com.projet.appEV.AppEV;
import com.projet.appEV.GestionArbresEVView;
import com.projet.appMembres.InitialisationAppMembre;
import com.projet.entite.Association;
import com.projet.entite.Personne;
import com.projet.espacesVerts.Visite;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import net.datafaker.providers.base.App;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

public class CreerVisiteAssoView {
    @FXML
    private Label nom_asso;

    @FXML
    private Button refresh;

    @FXML
    private Button deconnecter;

    @FXML
    private Button creer;

    @FXML
    private Button cr;

    @FXML
    private Button retour;

    @FXML
    private ListView list;

    @FXML
    private DatePicker date;


    @FXML
    public void initialize() {
        nom_asso.setText(InitialisationAppAsso.associationActuelle.toString());
        list.setItems(InitialisationAppMembre.arbresRemarquables);

        creer.setDisable(true);

        list.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            creer.setDisable(newValue == null || date.getValue() == null || date.getValue().isBefore(LocalDate.now()));
        });


        //pour comprendre voir documentation sur les datepicker javafx
        Callback<DatePicker, DateCell> dayCellFactory = new Callback<>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(LocalDate.now())) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffcccc;");
                            setTooltip(new Tooltip("Date passée non sélectionnable"));
                        }
                    }
                };
            }
        };

        date.setDayCellFactory(dayCellFactory);

        date.valueProperty().addListener((observable, oldValue, newValue) -> {
            creer.setDisable(newValue == null || list.getSelectionModel().getSelectedItem() == null|| date.getValue().isBefore(LocalDate.now()));
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
        });

        retour.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Retour' cliqué");
            Stage stage = (Stage) retour.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppAsso.class.getResource("/com.projet.appAsso/asso_visites.fxml"));
                fxmlLoader.setController(new VisitesAssoView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Association");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        creer.setOnMouseClicked(event -> {

            LocalDate dateSelectionnee = date.getValue();
            System.out.println(dateSelectionnee);
            Arbre arbreSelectionne = (Arbre) list.getSelectionModel().getSelectedItem();
            arbreSelectionne = Arbre.obtenirArbre(arbreSelectionne.getIdBase());
            System.out.println(arbreSelectionne);


            System.out.println("Bouton 'Créer' cliqué");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Création d'une visite");
            alert.setHeaderText("Êtes-vous sûr de vouloir créer cette visite ?");
            alert.setContentText("");
            ButtonType buttonTypeYes = new ButtonType("Oui");
            ButtonType buttonTypeNo = new ButtonType("Non");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            Arbre finalArbreSelectionne = arbreSelectionne;
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == buttonTypeYes) {
                    System.out.println("L'utilisateur a cliqué sur Oui");
                    Stage stage = (Stage) creer.getScene().getWindow();

                    Visite v = new Visite(InitialisationAppAsso.associationActuelle.getNom(), finalArbreSelectionne, dateSelectionnee.atStartOfDay());
                    Association.getAssociation(InitialisationAppAsso.associationActuelle.getNom()).getListeVisite().add(v);
                    try {
                        Main.MaJFichierJSONAssociation();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(AppAsso.class.getResource("/com.projet.appAsso/asso_visites.fxml"));
                        fxmlLoader.setController(new VisitesAssoView());
                        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                        stage.setScene(scene);
                        stage.setTitle("Application Association");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("L'utilisateur a cliqué sur Non");
                }
            });
        });
    };
}
