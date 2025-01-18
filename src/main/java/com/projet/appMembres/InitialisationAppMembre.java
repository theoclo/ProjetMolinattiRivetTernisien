package com.projet.appMembres;

import com.projet.Arbre;
import com.projet.entite.Association;
import com.projet.entite.Personne;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

import static com.projet.Main.*;
import static com.projet.Main.MaJFichierJSONPersonnes;
import static com.projet.entite.Association.listeAssociations;

public class InitialisationAppMembre {
    public static Personne membreActuel;
    public static ObservableList<Arbre> arbres = FXCollections.observableArrayList();
    public static ObservableList<Arbre> arbresNonRemarquables = FXCollections.observableArrayList();
    public static  ObservableList<Association> associations = FXCollections.observableArrayList();
    public static ObservableList<Arbre> listeRecommandations = FXCollections.observableArrayList();


    public static void main(String[] args) throws IOException{
        //creationJSON();
        MaJFichierJSONArbres();
        MaJFichierJSONPersonnes(); //met à jour le fichier json si modification (si ajout d'une valeur dans Personne.listePersonnes
        MaJFichierServiceEV();
        MaJFichierJSONAssociation();
        MaJFichierJSONPersonnes();
        associations.clear();
        arbres.clear();
        associations.addAll(listeAssociations);
        arbres.addAll(Arbre.listeArbres);
        arbresNonRemarquables.clear();
        arbresNonRemarquables.addAll(Arbre.obtenirNonRemarquables());
    }

}
