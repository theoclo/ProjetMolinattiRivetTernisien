package com.projet.entite;

import com.projet.espacesVerts.ServiceEV;

import java.io.IOException;
import java.util.ArrayList;

public interface Abonne {
    public void sAbonner(ServiceEV sEV);
    public void ajouterNotification(String notification) throws IOException;
    public ArrayList<String> getListeNotif();
    public void setListeNotif(ArrayList<String> listeNotif);
}
