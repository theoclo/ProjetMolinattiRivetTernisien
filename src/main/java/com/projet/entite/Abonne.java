package com.projet.entite;

import java.io.IOException;
import java.util.ArrayList;

public interface Abonne {
    void ajouterNotification(String notification) throws IOException;
    ArrayList<String> getListeNotif();
    void setListeNotif(ArrayList<String> listeNotif);
}
