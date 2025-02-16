package com.projet.launcher;

import com.projet.Arbre;
import com.projet.appMembres.InitialisationAppMembre;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

import static com.projet.Main.*;

public class Launcher extends Application {

    public static void main(String[] args) throws IOException {
        //creationJSON(); //DECOMMENTER CETTE LIGNE POUR RESET LES DONNEES
        MaJFichierJSONPersonnes();
        MaJFichierJSONArbres();
        MaJFichierServiceEV();
        MaJFichierJSONAssociation();
        MaJFichierJSONPersonnes();
        InitialisationAppMembre.arbres.clear();
        InitialisationAppMembre.arbres.addAll(Arbre.listeArbres);
        InitialisationAppMembre.arbresNonRemarquables.clear();
        InitialisationAppMembre.arbresNonRemarquables.addAll(Arbre.obtenirNonRemarquables());
        InitialisationAppMembre.arbresRemarquables.clear();
        InitialisationAppMembre.arbresRemarquables.addAll(Arbre.obtenirArbreRemarquables());

        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FileInputStream inputstream = new FileInputStream("src/main/resources/Acme-Regular.ttf");
        Font.loadFont(inputstream,12);
        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("/com.projet.launcher/desktop.fxml"));
        fxmlLoader.setController(new LauncherView());
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Windows XP");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
