package com.projet.appAsso;

import com.projet.Arbre;
import com.projet.appMembres.InitialisationAppMembre;
import com.projet.entite.Association;
import com.projet.entite.Personne;

import java.io.IOException;

import static com.projet.Main.*;


public class InitialisationAppAsso {

    public static Association associationActuelle;

    public static void main(String[] args) throws IOException {
        MaJFichierJSONArbres();
        MaJFichierJSONPersonnes();
        MaJFichierServiceEV();
        MaJFichierJSONAssociation();
        MaJFichierJSONPersonnes();
        InitialisationAppMembre.associations.clear();
        InitialisationAppMembre.associations.addAll(Association.listeAssociations);
        InitialisationAppMembre.arbres.clear();
        InitialisationAppMembre.arbres.addAll(Arbre.listeArbres);
        InitialisationAppMembre.arbresNonRemarquables.clear();
        InitialisationAppMembre.arbresNonRemarquables.addAll(Arbre.obtenirNonRemarquables());
    }
}
