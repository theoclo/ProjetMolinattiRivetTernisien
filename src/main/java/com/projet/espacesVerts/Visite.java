package com.projet.espacesVerts;

import com.projet.Arbre;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Visite {

    private final String association;
    private final Arbre arbre;
    private LocalDateTime date;
    private String participant;
    private String CR;
    private boolean payee;

    public static ArrayList<Visite> listeVisites=new ArrayList<Visite>();


    public Visite(String asso, Arbre arbre, LocalDateTime date){
        this.association = asso;
        this.arbre = arbre;
        this.date = date;
        this.participant = "";
        this.CR = "";
        this.payee = false;
    }

    public String getAssociation(){
        return association;
    }

    public Arbre getArbre(){
        return arbre;
    }

    public LocalDateTime getDate(){
        return date;
    }

    public String getParticipant(){
        return participant;
    }

    public String getCR(){
        return CR;
    }

    public boolean getPayee() {
        return payee;
    }




    public void modifDate(LocalDateTime new_date){
        this.date = new_date;
    }

    public void modifCR(String new_CR){
        this.CR = new_CR;
    }

    public void affecterParticipant(String pseudo_membre){
        this.participant = pseudo_membre;
    }

    public void payer(){
        this.payee = true;
    }


    @Override
    public String toString(){
        return "Arbre : " + arbre.getIdBase() + " | Date et Heure : " + date;
    }
}
