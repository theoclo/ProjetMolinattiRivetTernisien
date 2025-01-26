# Projet Arbres - Gautier TERNISIEN, Clothilde MOLINATTI, Mattis RIVET

## Lancement des applications
- Ouvrir le dossier ProjetMolinattiRiverTernisien dans IntelliJ
- Ouvrir la classe Launcher dans le dossier src/main/java/com.projet/launcher
- Lancer avec le bouton vert ou MAJ + F10

Ces étapes lancent une application qui simule un bureau Windows, sur lequel 
figurent les trois applications (membre, association, espaces verts) que l'on peut 
lancer en double-cliquant dessus comme on le ferait pour une application
d'ordinateur habituellement.

## Réinitialisation des membres et des associations
Nous avons fourni une base personnes et d'associations avec le projet, mais
s'il y a besoin de réinitialiser ces données : 

- Aller dans la classe Main dans le dossier src/main/java/com.projet et 
décommenter les lignes
171 et 172 (lectureFichierCSV() et creationServiceEV()) : 

```java
    public static void creationJSON(){
        //lectureFichierCSV(); //A FAIRE SI PAS DE JSON ou si vide
        //creationServiceEV(); //QUE SI PAS JSON ou si vide
        creationPersonnes(20); //A FAIRE SI PAS DE JSON ou si vide
        creationAssociation(2);
    }
```

- Puis décommenter la ligne 20 dans la classe Launcher dans le dossier 
src/main/java/com.projet/launcher (creationJSON()) :

```java
public static void main(String[] args) throws IOException {
    //creationJSON(); // DECOMMENTER CETTE LIGNE POUR RESET LES DONNEES
    MaJFichierJSONPersonnes();
    MaJFichierJSONArbres();
    MaJFichierServiceEV();
    MaJFichierJSONAssociation();
    MaJFichierJSONPersonnes();
    InitialisationAppMembre.arbres.clear();
    InitialisationAppMembre.arbres.addAll(Arbre.listeArbres);
    InitialisationAppMembre.arbresNonRemarquables.clear();
    InitialisationAppMembre.arbresNonRemarquables.addAll(Arbre.obtenirNonRemarquables());
    InitialisationAppMembre.arbresRemarquables.clear();
    InitialisationAppMembre.arbresRemarquables.addAll(Arbre.obtenirArbreRemarquables());


    launch(args);
}
```

- Lancer le programme une fois pour réinitialiser les données, puis 
recommenter les lignes décommentées afin de figer les données qui 
viennent d'être créées.

