package com.projet.entite;

import com.projet.Arbre;
import com.projet.Main;
import com.projet.espacesVerts.ServiceEV;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static com.projet.Main.MaJFichierJSONAssociation;
import static com.projet.Main.MaJFichierJSONPersonnes;

public class Personne implements Abonne, Entite {

    /*  CHAMPS  */

    private final String pseudo;
    private final String nom;
    private final String prenom;
    private final String adresse;
    private Optional<String> abonnement; //se refere à un serviceEV
    private Optional<String> association;
    private ArrayList<String> listeNotif;
    private int solde;
    private boolean aCotise;
    private int nbVisites;
    private ArrayList<LocalDate> listeCotisation; //toutes les années où on a cotisé et le jour correspondant

    public static ArrayList<Personne> listePersonnes = new ArrayList<>();

    public Personne(String nom, String prenom, String adresse, Optional<String> abonnement, Optional<String> association, ArrayList<String> listeNotif, int solde, ArrayList<LocalDate> listeCotisation) {
        this.pseudo = nom.toUpperCase()+prenom;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.abonnement = abonnement;
        this.association = association;
        this.listeNotif = listeNotif;
        this.solde = solde;
        this.listeCotisation = listeCotisation;
        this.aCotise = false;
        this.nbVisites = 0;
    }

    public Personne(){
        this.pseudo = "";
        this.nom = "";
        this.prenom = "";
        this.adresse = "";
        this.abonnement = Optional.empty();
        this.association = Optional.empty();
        this.solde = 0;
        this.listeNotif = new ArrayList<>();
        this.listeCotisation = new ArrayList<>();
        this.aCotise = false;
        this.nbVisites = 0;
    }

    /*  GETTERS  */

    public String getPseudo() {
        return pseudo;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getAdresse() {
        return adresse;
    }

    public Optional<String> getAbonnement() {
        return abonnement;
    }

    public Optional<String> getAssociation() {
        return association;
    }
    public Optional<Association> obtenirAssociationObjet() {
        return association.map(Association::getAssociation);
    }

    public int getSolde() {
        return solde;
    }

    public ArrayList<LocalDate> getListeCotisation() {
        return listeCotisation;
    }

    @Override
    public ArrayList<String> getListeNotif() {
        return this.listeNotif;
    }

    public boolean getaCotise(){
        return this.aCotise;
    }

    public int getNbVisites() {
        return nbVisites;
    }

    /*  SETTERS  */

    @Override
    public void setListeNotif(ArrayList<String> listeNotif) {

        this.listeNotif = listeNotif;
    }

    public void setAbonnement(Optional<String> abonnement) {
        this.abonnement = abonnement;
    }

    public void setAssociation(Optional<String > mMembre) {
        this.association = mMembre;
    }

    public void setSolde(int solde) {
        this.solde = solde;
    }

    public void setListeCotisation(ArrayList<LocalDate> listeCotisation) {
        this.listeCotisation = listeCotisation;
    }

    public void setaCotise(boolean aCotise) {this.aCotise = aCotise;}

    public void setNbVisites(int nbVisites) {this.nbVisites = nbVisites;}

    /*  AFFICHAGE  */

    @Override
    public String toString() {
        return nom.toUpperCase() + " " + prenom;
    }

    // Affichage complet
    public String affiche(){
        String str= "Personne [nom= " + nom + ", prenom= " + prenom + ", adresse= " + adresse +" solde= "+solde+"]\n";
        if(abonnement.isPresent()) {
            str+="Abonnement= " + abonnement.get() + "\n";
        }
        if(association.isPresent()) {
            str+="Membre de l'association= " + association.get() + "\n";
        }
        if(listeNotif != null) {
            str+="ListeNotif= " + listeNotif + "\n";
        }
        if(listeCotisation != null) {
            str+="listeCotisation= "+listeCotisation+"\n";
        }
        return str;
    }



    /*  METHODES  */

    public void rejoindreAsso(Association asso){
        if(association.isPresent()) {
            System.out.println("Vous êtes déjà membre d'une association : "+association.get());
        }
        else{
            asso.ajouterMembre(this);
            setAssociation(Optional.of(asso.getNom()));

        }
    }

    @Override
    public void sAbonner(ServiceEV sEV) {
        if(abonnement.isPresent()) {
            System.out.println("Vous avez déjà un abonnement : "+abonnement.get());
        }
        else{
            setAbonnement(Optional.of(sEV.getCommune()));
            sEV.addAbonne(this.getPseudo());
        }
    }


    @Override
    public boolean transfererMontant(int montant, Association asso) {
        if(solde >= montant) {
            solde -= montant;
            asso.ajouterBudget(montant);
            return true;
        }
        return false;
    }

    public void payerAsso() throws IOException {
        if(association.isPresent()) {
            Association a= Association.getAssociation(association.get());
            int prixAPayer = a.getPrixCotisation();
            if(prixAPayer>solde){
                System.out.println("Vous n'avez pas un solde nécessaire ("+solde+") pour payer : "+prixAPayer);
            }
            else if(aCotise){
                System.out.println("Vous avez déjà cotisé cette année.");
            }
            else{
                Personne p = Personne.obtenirPersonne(this.getPseudo());
                for(Personne pers : a.getListeMembre()){
                    if(pers.getPseudo().equals(p.getPseudo())){
                        a.getListeMembre().remove(pers);
                        break;
                    }
                }
                Personne.listePersonnes.remove(p);
                solde-=prixAPayer;
                aCotise = true;
                listeCotisation.add(LocalDate.now());
                a.setBudget(a.getBudget()+prixAPayer);

                a.getListeMembre().add(this);
                Personne.listePersonnes.add(this);
                Main.MaJFichierJSONPersonnes();
                Main.MaJFichierJSONAssociation();
            }
        }
    }
    public boolean quitterAsso() throws IOException //ne pas oublier de supp les infos côté asso
    {
        if(association.isPresent()) {
            Association a= Association.getAssociation(association.get());
            return a.exclureMembre(this);
        }
        else{
            System.out.println("Vous n'êtes dans aucune association");
            return false;
        }
    }
    public boolean nominerArbre(Arbre a) throws IOException //5 max sinon le premier saute
    {
        Association asso = Association.getAssociation(association.get());
        ArrayList<Arbre> listeReco=new ArrayList<>();
        if(asso.getListeReco().containsKey(this.pseudo)){
            listeReco = asso.getListeReco().get(this.pseudo);
        }
        boolean dejaPresent = false;
        for(Arbre arbre:listeReco){
            if (arbre.idBase() == (a.idBase())) {
                dejaPresent = true;
                break;
            }
        }

        if(listeReco.size() == 5){
            if(dejaPresent){
                return false;
            }
            else{
                listeReco.removeFirst();
                listeReco.add(a);
                asso.getListeReco().put(this.pseudo, listeReco);
                Main.MaJFichierJSONAssociation();
                return true;
            }

        }
        else{
            if(dejaPresent){
                return false;
            }
            else {
                listeReco.add(a);
                asso.getListeReco().put(this.pseudo, listeReco);
                Main.MaJFichierJSONAssociation();
                return true;
            }
        }

    }

    public void retirerArbre(Arbre a) throws IOException {
        if(association.isPresent()) {
            Association asso = Association.getAssociation(association.get());
            ArrayList<Arbre> listeReco=new ArrayList<>();
            if(asso.getListeReco().containsKey(this.pseudo)){
                listeReco = asso.getListeReco().get(this.pseudo);
            }
            for(Arbre arbre:listeReco){
                if(arbre.idBase()==(a.idBase())){
                    listeReco.remove(arbre);
                    System.out.println(listeReco);
                    asso.getListeReco().put(this.pseudo, listeReco);
                    Main.MaJFichierJSONPersonnes();
                    Main.MaJFichierJSONAssociation();
                    return;
                }
            }
        }
    }

    public static Personne obtenirPersonne(String pseudo){
        for(Personne p : Personne.listePersonnes){
            if(p.getPseudo().equals(pseudo)){
                return p;
            }
        }
        return null;
    }

    @Override
    public void ajouterNotification(String notification) throws IOException {
        Personne p = Personne.obtenirPersonne(this.pseudo);

        ArrayList<String> notif = new ArrayList<>();
        if(p.getListeNotif()!= null){
            notif = p.getListeNotif();
        }
        notif.add(notification);
        p.setListeNotif(notif);
        MaJFichierJSONPersonnes();
        MaJFichierJSONAssociation();

    }

    public ArrayList<String> cotisations(){
        ArrayList<String> list = new ArrayList<>();
        for(LocalDate date : listeCotisation){
            list.add(" Annee : " + date.getYear() + " cotisé le : " + date);
        }
        list.add("Total : " + listeCotisation.size()* Association.getAssociation(association.get()).getPrixCotisation());

        return list;
    }

    public static ArrayList<String> personnesSansAsso(){
        ArrayList<String> list = new ArrayList<>();
        for(Personne p : Personne.listePersonnes){
            if(p.getAssociation().isEmpty()){
                list.add(p.getPseudo());
            }
        }
        return list;
    }

}