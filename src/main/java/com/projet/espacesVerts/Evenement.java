package com.projet.espacesVerts;


import com.projet.Arbre;

import java.time.LocalDate;

public record Evenement(TypeEvent typeEvent, Arbre arbreEvent, LocalDate dateEvent, ServiceEV organisateur, String descEvent) {

    public enum TypeEvent {
        Plantation,
        Abattage,
        Classification
    }

    @Override
    public String toString() {
        return typeEvent + " | Arbre N°" + arbreEvent.getIdBase() + " | Date : " + dateEvent + " | Organisateur : " + organisateur + " | Description : " + descEvent;
    }

}
