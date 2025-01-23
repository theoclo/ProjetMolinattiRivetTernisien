package com.projet.entite;

import com.projet.Arbre;
import com.projet.Main;
import com.projet.appMembres.InitialisationAppMembre;
import com.projet.espacesVerts.ServiceEV;
import com.projet.espacesVerts.Visite;
import javafx.util.Pair;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.projet.Main.MaJFichierJSONAssociation;
import static com.projet.Main.MaJFichierJSONPersonnes;

public class Association implements Abonne{

    /*  CHAMPS  */

    private String nom;
    private Optional<String> abonnement;
    private Personne president;
    private ArrayList<Personne> listeMembre;
    private ArrayList<String> listeNotif;
    private int budget;
    private int prixCotisation;
    private int montantDefraiement;
    private ArrayList<String> listeDonateurs;
    private ArrayList<String> listeFactures; //String de type : String f = "50 : facture";
    //int i = f.indexOf(' ');
    //String word = f.substring(0, i);
    //String rest = f.substring(i);
    //    System.out.println(word);
    private Map<String, ArrayList<Arbre>> listeReco;
    private ArrayList<Visite> listeVisite;

    public static ArrayList<Association> listeAssociations=new ArrayList<Association>();

    /*  CONSTRUCTEUR  */

    public Association(String n,
                       Optional<String> a,
                       Personne p,
                       ArrayList<Personne> lM,
                       ArrayList<String> lN,
                       int b,
                       int pC,
                       int mDF,
                       ArrayList<String> lD,
                       ArrayList<String> lF,
                       HashMap<String, ArrayList<Arbre>> lR) {
        nom = n;
        abonnement = a;
        president = p;
        listeMembre = lM;
        listeNotif = lN;
        budget = b;
        prixCotisation = pC;
        montantDefraiement = mDF;
        listeDonateurs = lD;
        listeFactures = lF;
        listeReco = lR;
        listeVisite = new ArrayList<>();
    }

    public Association(String n,
                       Personne p,
                       int b,
                       int c,
                       int m){
        nom = n;
        abonnement = Optional.empty();
        president = p;
        listeMembre = new ArrayList<>();
        listeMembre.add(p);
        listeNotif = new ArrayList<>();
        budget = b;
        prixCotisation = c;
        montantDefraiement = m;
        listeDonateurs = new ArrayList<>();
        listeFactures = new ArrayList<>();
        listeReco = new HashMap<>();
        listeVisite = new ArrayList<>();
    }

    public Association(){
        this.nom = "";
        this.abonnement = Optional.empty();
        this.president = null;
        this.listeMembre = new ArrayList<>();
        this.listeNotif = new ArrayList<>();
        this.budget = 0;
        this.prixCotisation = 0;
        this.montantDefraiement = 0;
        this.listeDonateurs = new ArrayList<>();
        this.listeFactures = new ArrayList<>();
        this.listeReco = new HashMap<>();
        listeVisite = new ArrayList<>();
    }

    /*  GETTERS  */

    public String getNom() {
        return nom;
    }

    public Optional<String> getAbonnement(){
        return abonnement;
    }

    public Personne getPresident(){
        return president;
    }

    public ArrayList<Personne> getListeMembre(){
        return listeMembre;
    }

    public int getBudget() {
        return budget;
    }

    public int getPrixCotisation(){
        return prixCotisation;
    }

    public int getMontantDefraiement(){
        return montantDefraiement;
    }

    public ArrayList<String> getListeDonateurs(){
        return listeDonateurs;
    }

    public ArrayList<String> getListeFactures(){
        return listeFactures;
    }

    public Map<String, ArrayList<Arbre>> getListeReco() {
        return listeReco;
    }

    public ArrayList<Visite> getListeVisite() {
        return listeVisite;
    }

    @Override
    public ArrayList<String> getListeNotif() {
        return listeNotif;
    }

    @Override
    public void setListeNotif(ArrayList<String> listeNotif) {
        this.listeNotif = listeNotif;
    }

    /*  SETTERS  */

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setAbonnement(Optional<String> abonnement) {
        this.abonnement = abonnement;
    }

    public void setPresident(Personne p) {
        president = p;
    }

    public void setListeMembre(ArrayList<Personne> listeMembre) {
        this.listeMembre = listeMembre;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public void setPrixCotisation(int prixCotisation) {
        this.prixCotisation = prixCotisation;
    }

    public void setMontantDefraiement(int montantDefraie) {
        this.montantDefraiement = montantDefraie;
    }

    public void setListeDonateurs(ArrayList<String> listeDonateurs) {
        this.listeDonateurs = listeDonateurs;
    }

    public void setListeFactures(ArrayList<String> listeFactures) {
        this.listeFactures = listeFactures;
    }

    public void setListeReco(Map<String, ArrayList<Arbre>> listeReco) {
        this.listeReco = listeReco;
    }

    public void setListeVisite(ArrayList<Visite> listeVisite) {
        this.listeVisite = listeVisite;
    }
    /*  AFFICHAGE  */

    @Override
    public String toString() {
        return nom;
    }

    public String affiche() { //affiche toutes les informations
        String str="Association [nom = "+nom;
        if(abonnement.isPresent()){
            str+= ", abonnement = "+abonnement.get().toString();
        }
        str+= ", president = "+president.getNom();
        str+= "\n listeMembre = "+listeMembre.toString();
        if(listeNotif != null) {
            str+="ListeNotif= " + listeNotif + "\n";
        }

        str+= "\n budget = "+budget+", prixCotisation = "+prixCotisation+", defraiement = "+montantDefraiement;
        str+="\n listeDonateurs = "+listeDonateurs.toString();
        str+= "\n listeFactures = "+listeFactures.toString();
        return str;
    }

    public void lireNotif(){
        System.out.println("Liste des notifications :");
        int i=1;
        for(String notif : this.listeNotif){
            System.out.println(i+". "+notif);
            i++;
        }
    }

    public void lireFacture(){
        System.out.println("Liste des factures :");
        int i=1;
        for(String facture : this.listeFactures){
            System.out.println(i+". montant : "+facture +"\n");
            i++;
        }
    }

    public void lireDonateur(){
        System.out.println("Liste des donateurs :");
        int i=1;
        for(String donateur : this.listeDonateurs){
            System.out.println(i+". "+donateur);
        }
    }

    public void lireMembre(){
        System.out.println("Liste des membres :");
        int i=1;
        for(Personne membre : this.listeMembre){
            System.out.println(i+". "+membre);
        }
    }

    public void lireReco(){
        System.out.println("Liste des recommandations :");
        int i=1;
        for(Map.Entry<String, ArrayList<Arbre>> reco : this.listeReco.entrySet()){
            System.out.println(i+". "+reco.getKey()+"\n");
            int j=1;
            for(Arbre arbre : reco.getValue()){
                System.out.println(j+". "+arbre);
                j++;
            }
        }
    }

    public void afficheVisitesDispo(){
        for (Visite v : listeVisite){
            if(v.getDate().isBefore(LocalDateTime.now()) && v.getParticipant() == ""){
                System.out.println(v);
            }
        }
    }

    @Override
    public void sAbonner(ServiceEV sEV) {
        if(abonnement.isPresent()) {
            System.out.println("Vous avez déjà un abonnement : "+abonnement.get());
        }
        else{
            setAbonnement(Optional.of(sEV.getCommune()));
            sEV.addAbonne(this.getNom());
        }
    }

    @Override
    public void resilierAbo() {
        if(abonnement.isEmpty()){
            System.out.println("Vous n'êtes pas abonné au Service EV d'une commune");
        }
        else{
            for(String abonne : ServiceEV.getServiceEV(abonnement.get()).getListeAbonne()){
                if(abonne.equals(this.getNom())){
                    ServiceEV.getServiceEV(abonnement.get()).getListeAbonne().remove(abonne);
                }
            }
        }
    }

    public void ajouterMembre(Personne p) {
        if(listeMembre.contains(p)){
            System.out.println("Cette personne fait deja partie de l'association");
        }
        else{
            if(p.getAssociation().isEmpty()){
                listeMembre.add(p);
            }
            else{
                System.out.println("Cette personne a deja une association");
            }
        }
    }

    public boolean exclureMembre(Personne p) throws IOException {

        Personne membre=null;
        for(Personne m : listeMembre){
            if(m.getPseudo().equals(p.getPseudo())){
                membre = m;
            }
            if(this.getPresident().getPseudo().equals(p.getPseudo())){
                return false;
            }
        }
        if(membre!= null){

            //ajouter l'effacement de ses données
            listeMembre.remove(membre);
            p.setAssociation(Optional.empty());
            Main.MaJFichierJSONAssociation();
            Main.MaJFichierJSONPersonnes();
            Main.MaJFichierJSONAssociation();
            InitialisationAppMembre.associations.clear();
            InitialisationAppMembre.associations.addAll(Association.listeAssociations);


            return true;
        }
        else{
            System.out.println("Cette personne n'est pas membre de l'association.");
            return false;
        }

    }

    public void ajouterBudget(int montant){
        budget += montant;
    }

    public static Association getAssociation(String nom) {
        Association asso = new Association();
        for(Association a : listeAssociations){
            if(a.getNom().equals(nom)){
                asso = a;
            }
        }
        return asso;
    }



    public void ajouterDonateur(String p){
        if(listeDonateurs.contains(p)){
            System.out.println("Cette personne fait déjà partie des donateurs.");
        }
        else{
            listeDonateurs.add(p);
        }
    }

    public void supprimerDonateur(String p){
        if(listeDonateurs.contains(p)){
            listeDonateurs.remove(p);
        }
        else{
            System.out.println("Cette personne ne fait pas partie des donateurs.");
        }
    }

    public ArrayList<Pair<Personne, Boolean>> verifierCotisations(){//renvoie true si cotisé false sinon
        ArrayList<Pair<Personne, Boolean>> liste = new ArrayList<>();
        for(Personne p : listeMembre){
            if(p.getListeCotisation().containsKey(LocalDate.now().getYear())){
                liste.add(new Pair<>(p, true));
            }
            else{
                liste.add(new Pair<>(p, false));
            }
        }
        return liste;
    }

    public int recettesCotisations(Personne p){
        if(listeMembre.contains(p)){
            return p.getListeCotisation().size()*prixCotisation;
        }
        else{
            return 0;
        }
    }

    public void transmettreListeReco(ServiceEV sEV){
        //envoyer listeReco à sEV
    }

    public void organiserVisite(Arbre arbre_visite, LocalDateTime date_visite){
        if (!arbre_visite.getClassifie()){
            System.out.println("Arbre non remarquable !");
        }
        else{
            Visite visite = new Visite(this.nom, arbre_visite, date_visite);
            this.listeVisite.add(visite);
        }
    }

    public boolean payerVisite(Visite v) throws IOException {
        if(v.getPayee()){
            System.out.println("Visite déjà payée");
            return false;
        }
        else{
            Personne p = Personne.obtenirPersonne(v.getParticipant());
            if(listeMembre.contains(p)){
                if(budget>= montantDefraiement){
                    budget -= montantDefraiement;
                    v.payer();
                    p.setSolde(p.getSolde()+montantDefraiement);
                    Main.MaJFichierJSONPersonnes();
                    Main.MaJFichierJSONAssociation();
                    return true;
                }
                else{
                    System.out.println("Vous n'avez pas le budget pour défrayer ce membre de sa visite.");
                    return false;
                }
            }
            else{
                System.out.println("Cette personne n'est pas membre de l'association.");
                return false;
            }
        }
    }


    public LinkedHashMap<Arbre, Integer> selectTop5Nomination(){
        Map<Arbre,Integer> votes = new HashMap<>();
        for(ArrayList<Arbre> a : listeReco.values()){
            for(Arbre arbre : a){
                Arbre arbreVote = Arbre.obtenirArbre(arbre.getIdBase());
                if(votes.containsKey(arbreVote)){
                    votes.put(arbreVote, votes.get(arbreVote)+1);
                }
                else{
                    votes.put(arbreVote, 1);
                }
            }
        }
        LinkedHashMap<Arbre, Integer> sortedMap = votes.entrySet().stream()
                .sorted((e1, e2) -> {
                    // Tri par nombre de votes décroissant
                    int cmp = Integer.compare(e2.getValue(), e1.getValue());
                    if (cmp == 0) {
                        // Si égalité de votes, on trie par circonférence décroissante
                        cmp = Integer.compare(e2.getKey().getCirconference(), e1.getKey().getCirconference());
                        if (cmp == 0) {
                            // Si égalité de circonférence, on trie par hauteur décroissante
                            cmp = Integer.compare(e2.getKey().getHauteur(), e1.getKey().getHauteur());
                        }
                    }
                    return cmp;
                })
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        System.out.println(sortedMap.keySet());
        System.out.println(sortedMap.values());
        System.out.println(sortedMap.size());
        return sortedMap;
    }

    public LinkedHashMap<Arbre, Integer> selectAllNomination(){
        Map<Arbre,Integer> votes = new HashMap<>();
        for(ArrayList<Arbre> a : listeReco.values()){
            for(Arbre arbre : a){
                Arbre arbreVote = Arbre.obtenirArbre(arbre.getIdBase());
                if(votes.containsKey(arbreVote)){
                    votes.put(arbreVote, votes.get(arbreVote)+1);
                }
                else{
                    votes.put(arbreVote, 1);
                }
            }
        }
        LinkedHashMap<Arbre, Integer> sortedMap = votes.entrySet().stream()
                .sorted((e1, e2) -> {
                    // Tri par nombre de votes décroissant
                    int cmp = Integer.compare(e2.getValue(), e1.getValue());
                    if (cmp == 0) {
                        // Si égalité de votes, on trie par circonférence décroissante
                        cmp = Integer.compare(e2.getKey().getCirconference(), e1.getKey().getCirconference());
                        if (cmp == 0) {
                            // Si égalité de circonférence, on trie par hauteur décroissante
                            cmp = Integer.compare(e2.getKey().getHauteur(), e1.getKey().getHauteur());
                        }
                    }
                    return cmp;
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        System.out.println(sortedMap.keySet());
        System.out.println(sortedMap.values());
        System.out.println(sortedMap.size());
        return sortedMap;
    }

    public static ArrayList<Visite> obtenirVisitesSansParticipant(String asso){
        ArrayList<Visite> visites = new ArrayList<Visite>();
        Association a = Association.getAssociation(asso);
        for(Visite v : a.listeVisite){
            if(v.getAssociation().equals(asso)){
                if(v.getParticipant().equals("")){
                    visites.add(v);
                }
            }
        }

        return visites;
    }

    public static ArrayList<Visite> obtenirVisitesParticipant(String asso, String participant){
        ArrayList<Visite> visites = new ArrayList<Visite>();
        Association a = Association.getAssociation(asso);
        for(Visite v : a.listeVisite){
            if(v.getAssociation().equals(asso)){
                if(v.getParticipant().equals(participant) && v.getCr().equals("")){
                    visites.add(v);
                }
            }
        }

        return visites;
    }

    public void ajouterNotification(String notification) throws IOException {
        Association a = Association.getAssociation(this.nom);

        ArrayList<String> notif = new ArrayList<>();
        if(a.getListeNotif()!= null){
            notif = a.getListeNotif();
        }
        notif.add(notification);
        a.setListeNotif(notif);
        MaJFichierJSONPersonnes();
        MaJFichierJSONAssociation();

    }

}

