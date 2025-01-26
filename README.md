# Langages de la JVM II - Projet Arbres - Gautier TERNISIEN, Clothilde MOLINATTI, Mattis RIVET

## Présentation du projet
L'objectif de ce projet est de réaliser trois applications
en langage Java permettant de gérer :
- Les arbres de la ville de Paris (via l'application espaces verts)
- Une association (via l'application association)
- L'adhésion d'une personne d'une association (via l'application membre)


## Lancement des applications
1. Ouvrir le projet 
   - Dans IntelliJ, charger le dossier ProjetMolinattiRivetTernisien.
2. Ouvrir la classe Launcher dans le dossier src/main/java/com.projet/launcher
3. Lancer avec le bouton vert ou MAJ + F10

Ces étapes lancent une application qui simule un bureau Windows, sur lequel 
figurent les trois applications (membre, association, espaces verts) que l'on peut 
lancer en double-cliquant dessus comme on le ferait pour une application
d'ordinateur habituellement.

## Explications de certains choix de conception vis-à-vis du sujet fourni
### Base de données simplifiée 
- Nous avons choisi de ne pas avoir la possibilité dans l'application de créer des membres ni des associations, 
il y a donc une liste prédéfinie de 20 membres (pour alléger les interfaces) et 2 associations 
(ce qui permet de tester les fonctionnalités comme l'exclusion de membre et l'ajout de ce membre dans l'autre association par exemple)
- Nous avons réalisé le code en ayant une base de 150 000 arbres, mais pour des raisons de performances, de taille de dossier et de facilité d'utilisation
nous avons décidé de ne prendre en compte que les 5000 premiers arbres du CSV fourni par les espaces verts de la ville de Paris.
### Simplification de fonctionnalités
- Lors de la fin d'un exercice budgétaire d'une association, l'année réelle ne change pas mais toutes les fonctionnalités
sont maintenues (possibilité de cotiser de nouveau, les visites suivent les jours classiques du calendrier, etc.)
- Pour les visites, aucune vérification n'est effectuée à part le fait qu'elle doit être planifiée à une date future.
- Un membre qui s'inscrit pour réaliser une visite doit effectuer le compte-rendu de cette visite afin de recevoir sa rémunération, 
et si l'exercice budgétaire de l'association est clos avant qu'il fasse son compte-rendu, il ne pourra pas être rémunéré et nous considérons que sa visite n'a pas été effectuée (bien qu'elle ait été planifiée).
- Lorsqu'on plante un arbre, il est considéré comme "Jeune" par souci de logique et simplicité, et il est impossible de changer le stade de développement d'un arbre, toujours par souci de simplicité d'utilisation des applications.

## Réinitialisation des données 
Nous avons fourni une base de données pré-enregistrées avec le projet. Cette base prend
en compte les 5000 premiers arbres du CSV des arbres de la ville de Paris, 
et 20 personnes réparties dans les 2 associations, 
mais s'il y a besoin de réinitialiser ces données : 

1. Aller dans la classe Main dans le dossier 'src/main/java/com.projet' et 
décommenter les lignes
**171 et 172** dans la méthode 'creationJSON') : 

```java
/**
 * Chaque ligne de cette méthode permet de réinitialiser les données de l'application.
 */
public static void creationJSON(){
    //lectureFichierCSV();
    //creationServiceEV();
    creationPersonnes(20);
    creationAssociation(2);
}
```
2. Décommenter la ligne 20 dans la classe Launcher dans le dossier 
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

3. Lancer le programme une fois pour réinitialiser les données 

⚠️ Important : Une fois les données réinitialisées, recommenter les lignes 
décommentées dans les étapes précédentes pour éviter une réinitialisation 
automatique à chaque exécution du programme.

## Liste des dépendances :
- JavaFX-controls
- JavaFX-fxml
- Junit-jupiter-api
- Junit-jupiter-engine
- Jackson-datatype-jdk8
- Datafaker
- Jackson-databind
- Jackson-datatype-jsr310 (Utile pour gérer les dates)
- Jackson-datatype-jdk8 (Utile pour gérer les Optional)
````xml
<dependencies>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>17.0.6</version>
        </dependency>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-fxml</artifactId>
            <version>17.0.6</version>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.datatype</groupId>
            <artifactId>jackson-datatype-jdk8</artifactId>
            <version>2.17.2</version>
        </dependency>
        <dependency>
            <groupId>net.datafaker</groupId>
            <artifactId>datafaker</artifactId>
            <version>2.4.2</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.core</groupId>
            <artifactId>jackson-databind</artifactId>
            <version>2.17.2</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.datatype</groupId>
            <artifactId>jackson-datatype-jsr310</artifactId>
            <version>2.17.2</version>
        </dependency>
        <dependency>
            <groupId>com.fasterxml.jackson.datatype</groupId>
            <artifactId>jackson-datatype-jdk8</artifactId>
            <version>2.17.2</version>
        </dependency>
    </dependencies>
```


