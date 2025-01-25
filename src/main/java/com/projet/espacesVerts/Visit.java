package com.projet.espacesVerts;

import com.projet.Arbre;

import java.time.LocalDateTime;

public record Visit(String association, Arbre arbre, LocalDateTime date, String participant, String cr, boolean payee) {
    public Visit withParticipant(String p){
           return this.participant.equals(p) ? this: new Visit(association, arbre, date, p, cr, payee);
    }
    public Visit withCr(String compteR){
        return this.cr.equals(compteR)?this: new Visit(association, arbre, date, participant, compteR, payee);
    }
    public Visit withPayee(boolean payee) {
        return this.payee==payee?this: new Visit(association, arbre, date, participant, cr, payee);
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
