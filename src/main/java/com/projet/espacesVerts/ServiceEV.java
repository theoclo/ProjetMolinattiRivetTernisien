package com.projet.espacesVerts;

import com.projet.Arbre;
import com.projet.entite.Abonne;

import java.time.LocalDate;
import java.util.ArrayList;

public class ServiceEV {

    /*  CHAMPS  */

    private String commune;
    private ArrayList<Arbre> listeArbre;
    private ArrayList <Abonne> listeAbonne;
    private ArrayList < Evenement > listeEvent;

    public static ArrayList<ServiceEV> listeServiceEV = new ArrayList<>();



    /*  CONSTRUCTEURS  */

    public ServiceEV(String commune) {
        this.commune = commune;
        this.listeArbre = new ArrayList<>();
        this.listeAbonne = new ArrayList<>();
        this.listeEvent = new ArrayList<>();
    }

    public ServiceEV() {
        this.commune = "";
        this.listeArbre = new ArrayList<>();
        this.listeAbonne = new ArrayList<>();
        this.listeEvent = new ArrayList<>();
    }



    /*  GETTERS  */

    public String getCommune() {
        return commune;
    }
    public ArrayList < Arbre > getListeArbre() {
        return listeArbre;
    }
    public ArrayList < Abonne > getListeAbonne() {
        return listeAbonne;
    }
    public ArrayList < Evenement > getListeEvent() {
        return listeEvent;
    }



    /*  SETTERS  */

    public void setCommune(String commune) {
        this.commune = commune;
    }
    public void setListeArbre(ArrayList < Arbre > listeArbre) {
        this.listeArbre = listeArbre;
    }
    public void setListeAbonne(ArrayList<Abonne> listeAbonne) {
        this.listeAbonne = listeAbonne;
    }
    public void setListeEvent(ArrayList<Evenement> listeEvent) {
        this.listeEvent = listeEvent;
    }



    /*  AFFICHAGE  */

    @Override
    public String toString() {
        return "Service des Espaces Verts de la commune de : " + commune;
    }

    public String showListeArbre() {
        String str = "";
        for (int i = 0; i < listeArbre.size(); i++) {
            str += listeArbre.get(i).toString() + "\n" ;
        }
        return str;
    }

    public String showListeAbonne() {
        String str = "";
        for (int i = 0; i < listeAbonne.size(); i++) {
            str += listeAbonne.get(i).toString() + "\n" ;
        }
        return str;
    }

    public String showListeEvent() {
        String str = "";
        for (int i = 0; i < listeEvent.size(); i++) {
            str += listeEvent.get(i).toString() + "\n" ;
        }
        return str;
    }



    /*  METHODES  */

    public void addArbre(Arbre arbre) {
        listeArbre.add(arbre);
    }

    public void addAbonne(Abonne abonne) {
        listeAbonne.add(abonne);
    }

    public void addEvent(Evenement event) {
        listeEvent.add(event);
    }

    // Evènement aujourd'hui
    public void organiserEvent(Evenement.TypeEvent typeEvent, Arbre arbreEvent, String descEvent) {
        Evenement new_event = new Evenement(typeEvent, arbreEvent, LocalDate.now(), this, descEvent);
        listeEvent.add(new_event);
        for (Abonne abonne : listeAbonne) {
            notifier(new_event);
        }
    }

    // Evènement à une date futur
    public void organiserEvent(Evenement.TypeEvent typeEvent, Arbre arbreEvent, LocalDate dateEvent, String descEvent) {
        if (dateEvent.isBefore(LocalDate.now())) {
            System.out.println("Date invalide !");
        }
        else{
            Evenement new_event = new Evenement(typeEvent, arbreEvent, dateEvent, this, descEvent);
            listeEvent.add(new_event);
            notifier(new_event);
        }
    }

    public void notifier(Evenement event) {
        for (Abonne abonne : listeAbonne) {
            abonne.getListeNotif().add("Un évènement a été organisé : " + event.toString());
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
}

