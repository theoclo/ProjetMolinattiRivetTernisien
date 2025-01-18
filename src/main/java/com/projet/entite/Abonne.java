package com.projet.entite;

import com.projet.espacesVerts.ServiceEV;
import java.util.ArrayList;

public interface Abonne {
    public void sAbonner(ServiceEV sEV);
    public void resilierAbo();
    public ArrayList<String> getListeNotif();
    public void setListeNotif(ArrayList<String> listeNotif);
}
