package com.projet.espacesVerts;

import com.projet.Arbre;
import com.projet.entite.Association;
import com.projet.entite.Entite;
import com.projet.entite.Personne;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServiceEV implements Entite {

    /*  CHAMPS  */

    private final String commune;
    private ArrayList<Arbre> listeArbre;
    private ArrayList <String> listeAbonne;
    private ArrayList <Map<Integer, Integer>> listeVotesNonRemarquables;

    public static ArrayList<ServiceEV> listeServiceEV = new ArrayList<>();



    /*  CONSTRUCTEURS  */

    public ServiceEV(String commune) {
        this.commune = commune;
        this.listeArbre = new ArrayList<>();
        this.listeAbonne = new ArrayList<>();
        this.listeVotesNonRemarquables = new ArrayList<>();
    }

    public ServiceEV() {
        this.commune = "";
        this.listeArbre = new ArrayList<>();
        this.listeAbonne = new ArrayList<>();
        this.listeVotesNonRemarquables = new ArrayList<>();
    }



    /*  GETTERS  */

    public String getCommune() {
        return commune;
    }
    public ArrayList < Arbre > getListeArbre() {
        return listeArbre;
    }
    public ArrayList < String > getListeAbonne() {
        return listeAbonne;
    }
    public ArrayList < Map<Integer, Integer>> getListeVotesNonRemarquables() {return listeVotesNonRemarquables;}



    /*  SETTERS  */

    public void setListeArbre(ArrayList < Arbre > listeArbre) {
        this.listeArbre = listeArbre;
    }

    /*  AFFICHAGE  */

    @Override
    public String toString() {
        return commune;
    }

    /*  METHODES  */

    public void addArbre(Arbre arbre) {
        listeArbre.add(arbre);
    }

    public void addAbonne(String abonne) {
        if(!listeAbonne.contains(abonne)) {
            listeAbonne.add(abonne);
        }
    }

    public static ServiceEV getServiceEV(String nom){
        ServiceEV serviceEV=null;
        for(ServiceEV service : ServiceEV.listeServiceEV){
            if(service.getCommune().equals(nom)){
                serviceEV = service;
            }
        }
        return serviceEV;
    }

    public static ArrayList<Personne> obtenirParticuliersAbonne() {
        ArrayList<Personne> personnes = new ArrayList<>();
        for (String p : listeServiceEV.getFirst().getListeAbonne()) {
            Personne personne = Personne.obtenirPersonne(p);
            if(personne != null){
                personnes.add(personne);
            }
        }
        return personnes;
    }

    public static ArrayList<Association> obtenirAssosAbonne() {
        ArrayList<Association> associations = new ArrayList<>();
        for (String a : listeServiceEV.getFirst().getListeAbonne()) {
            Association asso = Association.getAssociation(a);
            if(asso != null){
                associations.add(asso);
            }
        }
        return associations;
    }

    public void ajouterVotesNonRemarquables(Map<Integer, Integer> votes) {
        listeVotesNonRemarquables.add(votes);
    }

    public Map<Arbre, Integer> obtenirVotesNonRemarquables() {
        Map<Arbre, Integer> votes = new HashMap<>();
        for(Arbre a : Arbre.obtenirNonRemarquables()){
            votes.put(a, 0);
        }
        for(Map<Integer, Integer> vote : listeVotesNonRemarquables) {
            for(Map.Entry<Integer, Integer> arbre : vote.entrySet()) {
                Arbre arbreVote = Arbre.obtenirArbre(arbre.getKey());
                if(!arbreVote.classifie()){
                    if(votes.containsKey(arbreVote)){
                        votes.put(arbreVote, votes.get(arbreVote)+arbre.getValue());
                    }
                    else{
                        votes.put(arbreVote, arbre.getValue());
                    }
                }
            }
        }
        return votes;
    }


    @Override
    public void transfererMontant(int montant, Association asso) {
        asso.setBudget(asso.getBudget() +montant);
    }
}



