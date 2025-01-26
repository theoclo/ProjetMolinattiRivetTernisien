package com.projet.entite;

import com.projet.Arbre;
import com.projet.espacesVerts.ServiceEV;
import com.projet.espacesVerts.Visite;
import javafx.util.Pair;
import net.datafaker.Faker;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.projet.Main.MaJFichierJSONAssociation;
import static com.projet.Main.MaJFichierJSONPersonnes;

public class Association implements Abonne, Entite{

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
    private ArrayList<String> listeFacturesNonPayees; //String de type : String f = "50 : facture";
    private ArrayList<String> listeFacturesPayees;
    private Map<String, ArrayList<Arbre>> listeReco;
    private ArrayList<Visite> listeVisite;
    private int anneeBudgetaire;
    private ArrayList<String> listeDemandeDons;
    private ArrayList<ArrayList<String>> listeExercicesBudgetaires;

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
        listeFacturesNonPayees = new ArrayList<>();
        listeFacturesPayees = new ArrayList<>();
        listeReco = lR;
        listeVisite = new ArrayList<>();
        anneeBudgetaire = 0;
        listeDemandeDons = new ArrayList<>();
        listeExercicesBudgetaires = new ArrayList<>();
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
        listeFacturesNonPayees = new ArrayList<>();
        listeFacturesPayees = new ArrayList<>();
        listeReco = new HashMap<>();
        listeVisite = new ArrayList<>();
        anneeBudgetaire = 0;
        listeDemandeDons = new ArrayList<>();
        listeExercicesBudgetaires = new ArrayList<>();
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
        this.listeFacturesNonPayees = new ArrayList<>();
        this.listeFacturesPayees = new ArrayList<>();
        this.listeReco = new HashMap<>();
        listeVisite = new ArrayList<>();
        anneeBudgetaire = 0;
        listeDemandeDons = new ArrayList<>();
        listeExercicesBudgetaires = new ArrayList<>();
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

    public ArrayList<String> getListeFacturesNonPayees(){
        return listeFacturesNonPayees;
    }

    public ArrayList<String> getListeFacturesPayees(){
        return listeFacturesPayees;
    }

    public Map<String, ArrayList<Arbre>> getListeReco() {
        return listeReco;
    }

    public ArrayList<Visite> getListeVisite() {
        return listeVisite;
    }

    public ArrayList<String> getListeDemandeDons() {
        return listeDemandeDons;
    }

    public ArrayList<ArrayList<String>> getListeExercicesBudgetaires() {
        return listeExercicesBudgetaires;
    }

    @Override
    public ArrayList<String> getListeNotif() {
        return listeNotif;
    }

    public int getAnneeBudgetaire() {
        return anneeBudgetaire;
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

    public void setListeMembre(ArrayList<Personne> listeMembre) {
        this.listeMembre = listeMembre;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public void setAnneeBudgetaire(int anneeBudgetaire) {
        this.anneeBudgetaire = anneeBudgetaire;
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
        str+= "\n listeFactures = "+ listeFacturesNonPayees.toString();
        return str;
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
            p.setListeCotisation(new ArrayList<LocalDate>());
            p.setaCotise(false);
            p.setNbVisites(0);
            MaJFichierJSONAssociation();
            MaJFichierJSONPersonnes();
            MaJFichierJSONAssociation();


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
        Association asso = null;
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
            if(p.getaCotise()){
                liste.add(new Pair<>(p, true));
            }
            else{
                liste.add(new Pair<>(p, false));
            }
        }
        return liste;
    }

    public LinkedHashMap<Arbre, Integer> selectTop5Nomination(){
        Map<Arbre,Integer> votes = new HashMap<>();
        for(ArrayList<Arbre> a : listeReco.values()){
            for(Arbre arbre : a){
                Arbre arbreVote = Arbre.obtenirArbre(arbre.idBase());
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
                        cmp = Integer.compare(e2.getKey().circonference(), e1.getKey().circonference());
                        if (cmp == 0) {
                            // Si égalité de circonférence, on trie par hauteur décroissante
                            cmp = Integer.compare(e2.getKey().hauteur(), e1.getKey().hauteur());
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
                Arbre arbreVote = Arbre.obtenirArbre(arbre.idBase());
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
                        cmp = Integer.compare(e2.getKey().circonference(), e1.getKey().circonference());
                        if (cmp == 0) {
                            // Si égalité de circonférence, on trie par hauteur décroissante
                            cmp = Integer.compare(e2.getKey().hauteur(), e1.getKey().hauteur());
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
        ArrayList<Visite> visites = new ArrayList<>();
        Association a = Association.getAssociation(asso);
        for(Visite v : a.listeVisite){
            if(v.association().equals(asso)){
                if(v.participant().equals("")){
                    visites.add(v);
                }
            }
        }

        return visites;
    }

    public static ArrayList<Visite> obtenirVisitesParticipant(String asso, String participant){
        ArrayList<Visite> visites = new ArrayList<>();
        Association a = Association.getAssociation(asso);
        for(Visite v : a.listeVisite){
            if(v.association().equals(asso)){
                if(v.participant().equals(participant) && v.cr().equals("")){
                    visites.add(v);
                }
            }
        }

        return visites;
    }


    @Override
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

    public void ajouterFacturesBase(){
        Faker faker = new Faker(new Locale("fr_FR", "FR"));
        this.listeFacturesNonPayees.add(faker.number().numberBetween(200,300)+"€ : Electricite");
        this.listeFacturesNonPayees.add(faker.number().numberBetween(100,200)+"€ : Eau");
        this.listeFacturesNonPayees.add(faker.number().numberBetween(500,700)+"€ : Loyer");
        this.listeFacturesNonPayees.add(faker.number().numberBetween(50,150)+"€ : Autre");
    }

    @Override
    public void transfererMontant(int montant, Association asso) {
        Association.listeAssociations.remove(this);
        this.setBudget(this.getBudget() - montant);
        Association.listeAssociations.add(this);
        asso.setBudget(asso.getBudget() +montant);
    }
}

