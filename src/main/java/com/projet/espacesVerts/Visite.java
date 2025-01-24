package com.projet.espacesVerts;

import com.projet.Arbre;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Visite {

    private final String association;
    private final Arbre arbre;
    private LocalDateTime date;
    private String participant;
    private String cr;
    private boolean payee;



    public Visite(String asso, Arbre arbre, LocalDateTime date){
        this.association = asso;
        this.arbre = arbre;
        this.date = date;
        this.participant = "";
        this.cr = "";
        this.payee = false;
    }

    public Visite(){
        this.association = "";
        this.arbre = null;
        this.date = null;
        this.participant = "";
        this.cr = "";
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

    public String getCr(){
        return cr;
    }

    public boolean getPayee() {
        return payee;
    }




    public void modifDate(LocalDateTime new_date){
        this.date = new_date;
    }

    public void modifCR(String new_CR){
        this.cr = new_CR;
    }

    public void affecterParticipant(String pseudo_membre){
        this.participant = pseudo_membre;
    }

    public void payer(){
        this.payee = true;
    }


    @Override
    public String toString(){
        String s= "Arbre n°" + arbre.getIdBase() + ", Nom : " + arbre.getNom() + " | Date : " + date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear();
        if(!getParticipant().equals("")){
            s+=" Membre : " + getParticipant();
        }
        return s;
    }


    @Override
    public boolean equals(Object o){
        if(o instanceof Visite){
            Visite v = (Visite)o;
            return v.getAssociation().equals(this.getAssociation()) && v.getDate().equals(this.getDate()) && v.arbre.equals(this.getArbre());
        }
        return false;
    }
}
