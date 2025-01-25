package com.projet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Arbre {


    /*  ENUM  */

    public enum ArbreDEV {
        Jeune,
        JeuneAdulte,
        Adulte,
        Mature,
        NonRenseigne
    }



    /*  CHAMPS  */

    private final int idBase;
    private String nom;
    private String genre;
    private String espece;
    private int circonference;
    private int hauteur;
    private ArbreDEV stadeDev;
    private String adresseAcces;
    Map<String, Double> coordGPS = new HashMap<>();
    private boolean classifie;
    private Optional<LocalDate> dateClassification;

    //private Pair < Boolean , Optional <LocalDate> > classification;
    private ArrayList <String> listeCR; // INUTILE ????

    public static ArrayList<Arbre> listeArbres=new ArrayList<Arbre>();



    /*  CONSTRUCTEURS  */

    public Arbre(int idBase,
                 String nom,
                 String genre,
                 String espece,
                 int circonference,
                 int hauteur,
                 ArbreDEV stadeDev,
                 String adresseAcces,
                 HashMap<String,Double> coordGPS){

        this.idBase = idBase;
        this.nom = nom;
        this.genre = genre;
        this.espece = espece;
        this.circonference = circonference;
        this.hauteur = hauteur;
        this.stadeDev = stadeDev;
        this.adresseAcces = adresseAcces;
        this.coordGPS = coordGPS;
        this.classifie = false;
        this.dateClassification = Optional.empty();
        this.listeCR = new ArrayList<>();
    }

    public Arbre(){
        this.idBase = 0;
        this.nom = "";
        this.genre = "";
        this.espece = "";
        this.circonference = 0;
        this.hauteur = 0;
        this.stadeDev = ArbreDEV.NonRenseigne;
        this.adresseAcces = "";
        this.coordGPS = new HashMap<>();
        this.classifie = false;
        this.dateClassification = Optional.empty();
        this.listeCR = new ArrayList<>();
    }


    /*  GETTERS  */

    public int getIdBase (){
        return idBase;
    }

    public String getNom (){
        return nom;
    }

    public String getGenre (){
        return genre;
    }

    public String getEspece (){
        return espece;
    }

    public int getCirconference (){
        return circonference;
    }

    public int getHauteur (){
        return hauteur;
    }

    public ArbreDEV getStadeDev (){
        return stadeDev;
    }

    public Map<String,Double> getCoordGPS (){return coordGPS;}

    public boolean getClassifie (){return classifie;}

    public Optional<LocalDate> getDateClassification (){return dateClassification;}

    public String getAdresseAcces (){
        return adresseAcces;
    }

    public ArrayList <String> getListeCR (){
        return listeCR;
    }


    /*  SETTERS  */

    public void setNom(String nom){
        this.nom = nom;
    }

    public void setGenre(String genre){
        this.genre = genre;
    }

    public void setEspece(String espece){
        this.espece = espece;
    }

    public void setCirconference(int circonference){
        this.circonference = circonference;
    }

    public void setHauteur(int hauteur){
        this.hauteur = hauteur;
    }

    public void setStadeDev(ArbreDEV stadeDev){
        this.stadeDev = stadeDev;
    }

    public void setCoordGPS(HashMap<String,Double> coordGPS) {this.coordGPS = coordGPS;}

    public void setClassification(Optional<LocalDate> dateClassification) {this.classifie=true; this.dateClassification = dateClassification;}

    public void setAdresseAcces(String adresseAcces){
        this.adresseAcces = adresseAcces;
    }

    public void setListeCR(ArrayList <String> listeCR){
        this.listeCR = listeCR;
    }



    /*  AFFICHAGE  */

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

    public String showListeCR (){
        String str = "Compte-Rendus sur l'arbre N°" + this.idBase + "\n";
        for (int i = 0; i < listeCR.size() ; i++){
            str = str + "   - Compte-Rendu N°" + i + " : " + listeCR.get(i) + "\n";
        }
        return str;
    }



    /*  METHODES  */

    // On classfie à une date connue
    public void classifier (int day, int month, int year){
        LocalDate date = LocalDate.of(year,month,day);
        Optional<LocalDate>  new_classification = Optional.of(date);
        this.setClassification(new_classification);
    }
    // On classfie à la date d'aujourd'hui
    public void classifier (){
        LocalDate date = LocalDate.now();
       Optional<LocalDate> new_classification = Optional.of(date);
        this.setClassification(new_classification);
    }

    public static ArrayList<Arbre> obtenirArbreRemarquables(){
        ArrayList<Arbre> arbreRemarquables = new ArrayList<Arbre>();
        for(Arbre a : listeArbres){
            if(a.classifie){
                arbreRemarquables.add(a);
            }
        }
        return arbreRemarquables;
    }

    public static ArrayList<Arbre> obtenirNonRemarquables(){
        ArrayList<Arbre> arbreNonRemarquables = new ArrayList<Arbre>();
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
            if(a.getIdBase() == id){
                arbre = a;
            }
        }
        return arbre;
    }


}