package com.projet.entite;

import com.projet.Arbre;
import com.projet.Main;
import com.projet.espacesVerts.ServiceEV;
import com.projet.espacesVerts.Visite;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class Personne implements Abonne, Entite {

    /*  CHAMPS  */

    private String pseudo;
    private String nom;
    private String prenom;
    private String adresse;
    private Optional<String> abonnement; //se refere à un serviceEV
    private Optional<String> association;
    private ArrayList<String> listeNotif;
    private int solde;
    private Map<Integer, LocalDate> listeCotisation; //toutes les années où on a cotisé et le jour correspondant

    public static ArrayList<Personne> listePersonnes = new ArrayList<>();

    public Personne(String nom, String prenom, String adresse, Optional<String> abonnement, Optional<String> association, ArrayList<String> listeNotif, int solde, Map<Integer, LocalDate> listeCotisation) {
        this.pseudo = nom.toUpperCase()+prenom;
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.abonnement = abonnement;
        this.association = association;
        this.listeNotif = listeNotif;
        this.solde = solde;
        this.listeCotisation = listeCotisation;
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
        this.listeCotisation = new HashMap<>();
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
        if(association.isPresent()){
            return Optional.of(Association.getAssociation(association.get()));
        }
        return Optional.empty();
    }

    public int getSolde() {
        return solde;
    }

    public Map<Integer, LocalDate> getListeCotisation() {
        return listeCotisation;
    }

    @Override
    public ArrayList<String> getListeNotif() {
        return this.listeNotif;
    }

    @Override
    public void setListeNotif(ArrayList<String> listeNotif) {
        this.listeNotif = listeNotif;
    }
    /*  SETTERS  */

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
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

    public void setListeCotisation(Map<Integer, LocalDate> listeCotisation) {
        this.listeCotisation = listeCotisation;
    }

    /*  AFFICHAGE  */

    public void lireNotif(){
        System.out.println("Liste des notifications :");
        int i=1;
        for(String notif : this.listeNotif){
            System.out.println(i+". "+notif);
            i++;
        }
    }

    public void lireCotisation(){
        System.out.println("Liste des cotisations :");
        for(Map.Entry<Integer, LocalDate> entry : this.listeCotisation.entrySet()){
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }
    }

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
            sEV.addAbonne(this);
        }
    }

    @Override
    public void resilierAbo() {
        if(abonnement.isEmpty()){
            System.out.println("Vous n'êtes pas abonné au Service EV d'une commune");
        }
        else{
            for(Abonne abonne : ServiceEV.getServiceEV(abonnement.get()).getListeAbonne()){
                if(abonne == this){
                    ServiceEV.getServiceEV(abonnement.get()).getListeAbonne().remove(abonne);
                }
            }
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
            else if(listeCotisation.containsKey(LocalDate.now().getYear())){
                System.out.println("Vous avez déjà cotisé cette année.");
            }
            else{
                solde-=prixAPayer;
                listeCotisation.put(LocalDate.now().getYear(), LocalDate.now());
                Personne.listePersonnes.remove(this);
                Personne.listePersonnes.add(this);
                Main.MaJFichierJSONPersonnes();
                Main.MaJFichierJSONAssociation();
            }
        }
    }
    public void sInscrireVisite(Arbre a, LocalDate d) //mettre en bool pour traiter le cas ou pas possible de s’inscrire ?
    {

    }
    public String faireCompteRendu(){
        return "Compte Rendu : ";
    }
    public void quitterAsso() //ne pas oublier de supp les infos côté asso
    {
        if(association.isPresent()) {
            Association a= Association.getAssociation(association.get());
            a.exclureMembre(this);
        }
        else{
            System.out.println("Vous n'êtes dans aucune association");
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
            if(arbre.getIdBase()==(a.getIdBase())){
                dejaPresent = true;
            }
        }
        System.out.println(dejaPresent);

        if(listeReco.size() == 5){
            if(dejaPresent){
                return true;
            }
            else{
                listeReco.remove(0);
                listeReco.add(a);
                asso.getListeReco().put(this.pseudo, listeReco);
                Main.MaJFichierJSONAssociation();
                return true;
            }

        }
        else{
            if(dejaPresent){
                return true;
            }
            else {
                listeReco.add(a);
                asso.getListeReco().put(this.pseudo, listeReco);
                System.out.println(listeReco);
                Main.MaJFichierJSONAssociation();
                return true;
            }
        }

    }

    public void participerVisite(Visite v){
        if(v.getParticipant() == "" && v.getDate().isBefore(LocalDateTime.now())){
            v.affecterParticipant(this.pseudo);
        }
    }

    public void ecrireCRVisite(Visite v, String CR){
        if(v.getDate().isAfter(LocalDateTime.now()) && this.pseudo == v.getParticipant()){
            v.modifCR(CR);
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


}