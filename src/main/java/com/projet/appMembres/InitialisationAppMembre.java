package com.projet.appMembres;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.projet.Arbre;
import com.projet.entite.Association;
import com.projet.entite.Personne;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import static com.projet.Main.*;
import static com.projet.Main.MaJFichierJSONPersonnes;
import static com.projet.entite.Association.listeAssociations;

public class InitialisationAppMembre {
    public static Personne membreActuel;
    public static ObservableList<Arbre> items = FXCollections.observableArrayList();
    public static  ObservableList<Association> associations = FXCollections.observableArrayList();


    public static void main(String[] args) throws IOException{
        //creationJSON();
        MaJFichierJSONArbres();
        MaJFichierJSONPersonnes(); //met à jour le fichier json si modification (si ajout d'une valeur dans Personne.listePersonnes
        MaJFichierServiceEV();
        MaJFichierJSONAssociation();
        MaJFichierJSONPersonnes();
        associations.clear();
        items.clear();
        associations.addAll(listeAssociations);
        items.addAll(Arbre.listeArbres);
    }

}
