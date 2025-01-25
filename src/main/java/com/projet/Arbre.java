package com.projet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

public record Arbre(int idBase, String nom, String genre, String espece, int circonference, int hauteur, ArbreDEV stadeDev,
                    String adresseAcces, Map<String,Double> coordGPS, boolean classifie, Optional<LocalDate> dateClassification) {
    public enum ArbreDEV {
        Jeune,
        JeuneAdulte,
        Adulte,
        Mature,
        NonRenseigne
    }
    public static ArrayList<Arbre> listeArbres = new ArrayList<>();

    public Arbre withClassifie(boolean classifie) {
        return this.classifie==classifie ? this : new Arbre(idBase, nom, genre, espece, circonference,hauteur,stadeDev,adresseAcces,coordGPS,classifie,dateClassification);
    }
    public Arbre withDateClassification(Optional<LocalDate> dateClassification) {
        return this.dateClassification==dateClassification? this : new Arbre(idBase, nom, genre, espece, circonference,hauteur,stadeDev,adresseAcces,coordGPS,classifie,dateClassification);
    }

    @Override
    public String toString(){
        String str = "Arbre N°" + idBase + " | Nom : " + nom + " | Genre : " + genre + " | Espece : " + espece +
                " | Circonférence : " + circonference +  "cm | Hauteur : " + hauteur + "m | Stade de développement : " +
                stadeDev + " | Adresse : " + adresseAcces;
        if (classifie){  // Key == première valeur de la paire
            if (dateClassification.isEmpty()){  //Value == deuxième valeur de la paire
                str += " | Classifié : OUI | Date de classification : INCONNUE";
            }
            else{
                str += " | Classifié : OUI | Date de classification : " +dateClassification.get();
            }
        }
        else{
            str += " | Classifié : NON";
        }
        return str;
    }

    public static ArrayList<Arbre> obtenirArbreRemarquables(){
        ArrayList<Arbre> arbreRemarquables = new ArrayList<>();
        for(Arbre a : listeArbres){
            if(a.classifie){
                arbreRemarquables.add(a);
            }
        }
        return arbreRemarquables;
    }

    public static ArrayList<Arbre> obtenirNonRemarquables(){
        ArrayList<Arbre> arbreNonRemarquables = new ArrayList<>();
        for(Arbre a : listeArbres){
            if(! a.classifie){
                arbreNonRemarquables.add(a);
            }
        }
        return arbreNonRemarquables;
    }

    public static Arbre obtenirArbre(int id){
        Arbre arbre = null;
        for(Arbre a : listeArbres){
            if(a.idBase == id){
                arbre = a;
            }
        }
        return arbre;
    }

}
