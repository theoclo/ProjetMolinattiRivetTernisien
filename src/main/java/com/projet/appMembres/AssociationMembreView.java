package com.projet.appMembres;

import com.projet.Arbre;
import com.projet.entite.Association;
import com.projet.entite.Personne;
import com.projet.espacesVerts.Visite;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class AssociationMembreView {
    @FXML
    private Label nom_membre;

    @FXML
    private Button refresh;

    @FXML
    private Button deconnecter;

    @FXML
    private Button retour;

    @FXML
    private Button cotisations;

    @FXML
    private Button quitter;

    @FXML
    private Button rgpd;

    @FXML
    public void initialize(){
        refresh.setVisible(false);
        nom_membre.setText(InitialisationAppMembre.membreActuel.toString());

        deconnecter.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Se déconnecter' cliqué");
            Stage stage = (Stage) deconnecter.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppMembre.class.getResource("/com.projet.appMembres/membre_connexion.fxml"));
                fxmlLoader.setController(new ConnexionMembreView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Membre");
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
                FXMLLoader fxmlLoader = new FXMLLoader(AppMembre.class.getResource("/com.projet.appMembres/membre_connecte.fxml"));
                fxmlLoader.setController(new ConnecteMembreView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Membre");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        cotisations.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Cotisations' cliqué");
            Stage stage = (Stage) cotisations.getScene().getWindow();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(AppMembre.class.getResource("/com.projet.appMembres/membre_cotisations.fxml"));
                fxmlLoader.setController(new CotisationsMembreView());
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                stage.setScene(scene);
                stage.setTitle("Application Membre");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        quitter.setOnMouseClicked(event -> {
            System.out.println("Bouton 'Quitter l'association' cliqué");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Quitter l'association");
            alert.setHeaderText("Êtes-vous sûr de vouloir quitter l'association ?");
            alert.setContentText("Cela entraînera la suppression de vos données");
            ButtonType buttonTypeYes = new ButtonType("Oui");
            ButtonType buttonTypeNo = new ButtonType("Non");
            alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
            alert.showAndWait().ifPresent(buttonType -> {
                if (buttonType == buttonTypeYes) {
                    System.out.println("L'utilisateur a cliqué sur Oui");
                    try {
                        Personne p = Personne.obtenirPersonne(InitialisationAppMembre.membreActuel.getPseudo());
                        boolean fait = p.quitterAsso();
                        if(!fait){
                            Alert alert2 = new Alert(Alert.AlertType.ERROR);
                            alert2.setTitle("Erreur");
                            alert2.setHeaderText("Erreur lors de la demande de quitter l'association");
                            alert2.setContentText("Vous ne pouvez pas quitter l'association car vous en êtes le président");
                            alert2.showAndWait();
                        }
                        else{
                            Stage stage = (Stage) retour.getScene().getWindow();
                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader(AppMembre.class.getResource("/com.projet.appMembres/membre_connexion.fxml"));
                                fxmlLoader.setController(new ConnexionMembreView());
                                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                                stage.setScene(scene);
                                stage.setTitle("Application Membre");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.out.println("L'utilisateur a cliqué sur Non");
                }
            });
        });

        rgpd.setOnMouseClicked(event -> {
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Données Personnelles");
            alert2.setHeaderText("Voici la liste de toutes les données contenues par l'association");
            Personne p = Personne.obtenirPersonne(InitialisationAppMembre.membreActuel.getPseudo());
            ArrayList< String> v = new ArrayList<>();
            for(Visite visite : Association.getAssociation(p.getAssociation().get()).getListeVisite()){
                if(visite.participant().equals(p.getPseudo())){
                    v.add("\n"+visite);
                }
            }
            ArrayList<String> listeReco = new ArrayList<>();
            for(Map.Entry<String, ArrayList<Arbre>> pseudo : Association.getAssociation(p.getAssociation().get()).getListeReco().entrySet()){
                if(pseudo.getKey().equals(p.getPseudo())){
                    for(Arbre arbre : pseudo.getValue()){
                        listeReco.add("\nArbre N° "+arbre.idBase()+ ", Nom : "+arbre.nom()+ ", Espece "+ arbre.espece()+", Genre : "+arbre.genre());
                    }
                }
            }

            TextFlow textFlow = new TextFlow();
            textFlow.getChildren().addAll(
                    new Text("Données sur vous : \n\n"){{ setUnderline(true); }},
                    new Text("Nom") {{ setUnderline(true); }}, // Soulignement de "Nom"
                    new Text(" : " +p.getNom()+", "),
                    new Text("Prenom") {{ setUnderline(true); }},
                    new Text(" : "+p.getPrenom()+", "),
                    new Text("Adresse") {{ setUnderline(true); }},
                    new Text(" : "+p.getAdresse()+", "),
                    new Text("Solde") {{ setUnderline(true); }},
                    new Text(" : "+p.getSolde()+"€ \n\n\n"),
                    new Text("Données liées à l'association"){{ setUnderline(true); }},
                    new Text(": \n\n"),
                    new Text("Liste cotisation"){{ setUnderline(true); }},
                    new Text(" : " + p.getListeCotisation()+"\n"),
                    new Text("Cotisation sur l'exercice budgétaire en cours"){{ setUnderline(true); }},
                    new Text(" : "+p.getaCotise()+"\n"),
                    new Text("Liste visites"){{ setUnderline(true);}},
                    new Text(" : "+ v),
                    new Text("\nNombre de visites sur l'exercice budgétaire en cours"){{setUnderline(true);}},
                    new Text(" : "+p.getNbVisites()+"\n"),
                    new Text("Liste de recommandations"){{ setUnderline(true);}},
                    new Text(" : "+ listeReco),
                    new Text("\n\n\nToutes ces données seront supprimées si vous quittez l'association ou êtes expulsés"){{setUnderline(true);}}
            );
            alert2.getDialogPane().setContent(textFlow);
            alert2.showAndWait();

        });
    }
}
