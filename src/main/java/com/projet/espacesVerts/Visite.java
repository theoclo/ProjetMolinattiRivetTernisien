package com.projet.espacesVerts;

import com.projet.Arbre;

import java.time.LocalDateTime;

public record Visite(String association, Arbre arbre, LocalDateTime date, String participant, String cr, boolean payee) {
    public Visite withParticipant(String p){
           return this.participant.equals(p) ? this: new Visite(association, arbre, date, p, cr, payee);
    }
    public Visite withCr(String compteR){
        return this.cr.equals(compteR)?this: new Visite(association, arbre, date, participant, compteR, payee);
    }
    public Visite withPayee(boolean payee) {
        return this.payee==payee?this: new Visite(association, arbre, date, participant, cr, payee);
    }

    @Override
    public String toString() {
        String s= "Arbre n°" + arbre.getIdBase() + ", Nom : " + arbre.getNom() + " | Date : " + date.getDayOfMonth() + "/" + date.getMonthValue() + "/" + date.getYear();
        if(!participant.equals("")){
            s+=" Membre : " + participant;
        }
        return s;
    }

}
