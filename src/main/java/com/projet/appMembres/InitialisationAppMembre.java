package com.projet.appMembres;

import com.projet.Arbre;
import com.projet.entite.Personne;
import com.projet.espacesVerts.Visit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

public class InitialisationAppMembre {
    public static Personne membreActuel;
    public static ObservableList<Arbre> arbres = FXCollections.observableArrayList();
    public static ObservableList<Arbre> arbresNonRemarquables = FXCollections.observableArrayList();
    public static ObservableList<Arbre> arbresRemarquables = FXCollections.observableArrayList();
    //public static  ObservableList<Association> associations = FXCollections.observableArrayList();
    public static ObservableList<Arbre> listeRecommandations = FXCollections.observableArrayList();
    public static ObservableList<Visit> listeVisites = FXCollections.observableArrayList();


    public static void main(String[] args) throws IOException{
        /**
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
         **/
    }

}
