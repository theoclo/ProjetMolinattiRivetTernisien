package com.projet;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.projet.entite.Association;
import com.projet.entite.Personne;
import com.projet.espacesVerts.ServiceEV;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Main {
    public static void lectureFichierCSV() {
        try {
            java.io.BufferedReader CSVreader = new java.io.BufferedReader(
                    new java.io.FileReader("les-arbres.csv"));
            String line = "";
            CSVreader.readLine(); // Passer la première ligne
            Arbre arbre;
            while ((line = CSVreader.readLine()) != null) {

                List<String> info= Arrays.asList(line.split(";"));

                if(Integer.valueOf(info.get(0)).equals(2030898)){break;}

                Arbre.ArbreDEV dev;
                if(info.get(14).equals("Adulte")){
                    dev = Arbre.ArbreDEV.Adulte;
                }
                else if(info.get(14).equals("Jeune (arbre)Adulte\n")){
                    dev = Arbre.ArbreDEV.JeuneAdulte;
                }
                else if(info.get(14).equals("Jeune (arbre)\n")){
                    dev = Arbre.ArbreDEV.Jeune;
                }
                else if(info.get(14).equals("Mature")){
                    dev = Arbre.ArbreDEV.Mature;
                }
                else{
                    dev = Arbre.ArbreDEV.NonRenseigne;
                }

                HashMap<String,Double> coordonnee = new HashMap<>();

                List<String> coord= Arrays.asList(info.get(16).split(","));
                coordonnee.put("latitude", Double.valueOf(coord.get(0)));
                coordonnee.put("longitude", Double.valueOf(coord.get(1)));
                arbre = new Arbre(Integer.valueOf(info.get(0)), info.get(8),
                        info.get(9), info.get(10),
                        Integer.valueOf(info.get(12)),Integer.valueOf(info.get(13)),
                        dev,
                        info.get(3)+", "+info.get(4)+" "+info.get(5)+" "+info.get(6),
                        coordonnee,false,Optional.empty()

                );
                if(info.get(15).equals("OUI")){
                    arbre = arbre.withClassifie(true);
                }
                Arbre.listeArbres.add(arbre);
            }

            CSVreader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void creationPersonnes(int nombrePersonne) {
        net.datafaker.Faker faker = new net.datafaker.Faker(new java.util.Locale("fr_FR", "FR"));
        for(int i = 0; i < nombrePersonne; i++){
            String prenom = faker.name().firstName();
            String nom = faker.name().lastName();
            String adresse = faker.address().fullAddress();
            int solde = faker.number().numberBetween(1, 10000);
            Personne personne = new Personne(nom, prenom, adresse, Optional.empty(),Optional.empty(),new ArrayList<>(),solde,new ArrayList<>());
            Personne.listePersonnes.add(personne);
        }
    }
    public static void creationServiceEV(){
        ServiceEV.listeServiceEV.add(new ServiceEV("Paris"));
        ServiceEV.listeServiceEV.get(0).setListeArbre(Arbre.listeArbres);
    }
    public static void creationAssociation(int nombreAsso){
        net.datafaker.Faker faker = new net.datafaker.Faker(new java.util.Locale("fr_FR", "FR"));
        for(int i = 0; i < nombreAsso; i++){
            int budget = faker.number().numberBetween(1, 100000);
            int prixCotisation = faker.number().numberBetween(10, 25);
            int montantDefraiement = faker.number().numberBetween(15,50);
            Personne president=Personne.listePersonnes.get(faker.number().numberBetween(0, Personne.listePersonnes.size()));
            while(president.getAssociation().isPresent()){
                president = Personne.listePersonnes.get(faker.number().numberBetween(0, Personne.listePersonnes.size()));
            }
            Association asso = new Association("Association"+i, president,budget,prixCotisation,montantDefraiement);
            asso.ajouterFacturesBase();
            president.rejoindreAsso(asso);
            System.out.println(asso);
            Association.listeAssociations.add(asso);
        }
        int k=0;
        for(Personne membre : Personne.listePersonnes){
            if(membre == Association.listeAssociations.get(0).getPresident() || membre == Association.listeAssociations.get(1).getPresident()){
                k++;
                continue;
            }
            else if(k%2==0){
                membre.rejoindreAsso(Association.listeAssociations.get(0));
            }
            else if(k%2==1){
                membre.rejoindreAsso(Association.listeAssociations.get(1));
            }
            k++;
        }
    }

    public static void MaJFichierServiceEV() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        if(!ServiceEV.listeServiceEV.isEmpty()){
            objectMapper.writeValue(new File("target/serviceEV.json"),ServiceEV.listeServiceEV);
        }
        ServiceEV.listeServiceEV = objectMapper.readValue(new File("target/serviceEV.json"), new TypeReference<ArrayList<ServiceEV>>() {});
        objectMapper.writeValue(new File("target/serviceEV.json"), ServiceEV.listeServiceEV);
    }
    public static void MaJFichierJSONArbres() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        if(!Arbre.listeArbres.isEmpty()){
            objectMapper.writeValue(new File("target/arbres.json"), Arbre.listeArbres);
        }
        Arbre.listeArbres = objectMapper.readValue(new File("target/arbres.json"), new TypeReference<ArrayList<Arbre>>() {});
        objectMapper.writeValue(new File("target/arbres.json"), Arbre.listeArbres);
    }
    public static void MaJFichierJSONPersonnes() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        if(!Personne.listePersonnes.isEmpty()){
            objectMapper.writeValue(new File("target/personnes.json"), Personne.listePersonnes);
        }
        Personne.listePersonnes = objectMapper.readValue(new File("target/personnes.json"), new TypeReference<ArrayList<Personne>>() {});
        objectMapper.writeValue(new File("target/personnes.json"), Personne.listePersonnes);
    }
    public static void MaJFichierJSONAssociation() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(new JavaTimeModule());
        if(!Association.listeAssociations.isEmpty()){
            objectMapper.writeValue(new File("target/associations.json"),Association.listeAssociations);
        }
        Association.listeAssociations = objectMapper.readValue(new File("target/associations.json"), new TypeReference<ArrayList<Association>>() {});
        objectMapper.writeValue(new File("target/associations.json"), Association.listeAssociations);
    }


    public static void creationJSON(){
        lectureFichierCSV(); //A FAIRE SI PAS DE JSON ou si vide
        creationServiceEV(); //QUE SI PAS JSON ou si vide
        //creationPersonnes(10); //A FAIRE SI PAS DE JSON ou si vide
        //creationAssociation(2);
    }
/**
 public static void main(String[] args) throws IOException {
 //creationJSON();
 MaJFichierJSONArbres();
 MaJFichierJSONPersonnes(); //met à jour le fichier json si modification (si ajout d'une valeur dans Personne.listePersonnes
 MaJFichierServiceEV();
 MaJFichierJSONAssociation();
 MaJFichierJSONPersonnes();
 MaJFichierJSONVisite();
 Personne.listePersonnes.get(3).rejoindreAsso(Association.listeAssociations.get(0));
 MaJFichierJSONAssociation();
 MaJFichierJSONPersonnes();
 System.out.println(Association.listeAssociations.get(0).affiche());
 System.out.println(Personne.listePersonnes.get(3).affiche());

 }
 **/
}
