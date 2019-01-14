package com.elodie.jeux.jeu_Mastermind;
//TODO MasterMind Challenger de base
import com.elodie.jeux.GeneralMethodes.Methodes_MecaniqueJeu;

import java.util.ArrayList;

import static com.elodie.jeux.GeneralMethodes.Methodes_Generales.*;
import static java.lang.Character.getNumericValue;

/**
 *  <b>Mastermind // Mode défenseur où c'est à l'ordinateur de trouver votre combinaison secrète</b>
 * <p>Le but : découvrir la combinaison à x chiffres de l'adversaire (le défenseur).
 * <p>Pour ce faire, l'attaquant fait une proposition. Le défenseur indique pour
 * chaque proposition le nombre de chiffres de la proposition qui apparaissent
 * à la bonne place et à la mauvaise place dans la combinaison secrète.
 *<p>L'attaquant doit deviner la combinaison secrète en un nombre limité d'essais.
 *<section>
 *<p><i>(Combinaison secrète : 1234)</i></p>
 *<p><i>(Proposition : 4278 -> Réponse : 1 présent, 1 bien placé</i></p>
 *<p><i>(Proposition : 2214 -> Réponse : 2 bien placés</i></p>
 *</section>
 * @author elojito
 * @version 1.0
 */

public class MastermindGame_Defenseur {
    /**
     * <b>Pose les bases et paramètres de base du jeu</b>
     * <ul>Possède les attributs de bases:
     * <li>tableau de chiffres de 0 à 9</li>
     * <li>tableau de 4 chiffres comportant la combinaison secrète</li>
     * @see Methodes_MecaniqueJeu#computedSecretCode()
     * <li>un tableau formant une combinaison secrète avec ces 4 chiffres</li>
     * <li>une chaine de caractère vide pour les entrées utilisateur à venir</li>
     * <li>une chaine de caractère "====" représentant l'affichage sortie si la combinaison est trouvée</li>
     * </ul>
     * <p>On lance le jeu</p>
     * @see MastermindGame_Defenseur#startDefenseurMastermindGame()
     * <p>Si l'utilisateur trouve alors apparait "4 bien placés", le jeu s'arrête</p>
     */
    static final String[] nbr = {"0","1","2","3","4","5","6","7","8","9"};
    public static final int[] secretCode = Methodes_MecaniqueJeu.inputSecretCode();
    static String AIinput = "";
    static ArrayList output = new ArrayList();
    static ArrayList reponse = new ArrayList();
    static String reponseToString = "";
    final String winwin = "4 bien placés";

    public MastermindGame_Defenseur(){
        //affichage du code secret pour mode développeur
        System.out.print( "(Code Secret: " );
        for(int i =0;i<secretCode.length;i++){
            System.out.print( secretCode[i] );
        }
        System.out.print( ")" );
        //On lance le jeu
        do {
            startDefenseurMastermindGame();
        }while(!(reponseToString.equals(winwin)));
        System.out.println( "\nBravo vous avez trouvé la combinaison!" );
    }

    /**
     * Méthode affiche le résultat de l'essai pour trouver la combinaison.
     * On récupère une liste composée des essais, qu'on compare avec lse entrées d'un tableau de chiffres.
     * Selon si les entrées de la liste sont trouvées et à la bonne place, ou présentes dans la combinaison
     * mais mal placées, on affichera en sortie "x bien placé" ou "x présent".
     * Ces résultats seront stocker dans une liste, puis transformés en chaine de caractères pour l'affichage sortie.
     * L'attribut "found" s'incrémente si un chiffre est bien placé.
     * L'attribut "somewhere" s'incrémente si un chiffre est trouvé mais mal placé.
     * @param liste une liste composée de chaque chiffre de la chaine de caractères input
     * @param secret un tableau composé de chiffres définis
     * @param input entrée utilisateur composée de chiffres
     * @param output liste composée des chaines de caractères définissant les chiffres de "liste" comme étant
     *               présents ou bien placés par rapport à "secret" à l'aide des attributs entiers
     *               found et somewhere.
     * @return une chaine de caractères composées des entrées de la liste "output"
     */

    public static String tryOutCheck(ArrayList liste, int[] secret, String input, ArrayList output){
        int found = 0;
        int somewhere = 0;
        System.out.print( "Votre proposition: " + input + " -> Réponse: " );
        int i=0;
        //On parcoure la liste des entrées utilisateurs en parsant chaque entrée en chiffre entier
        for (Object o:liste) {
            int oInt = Integer.parseInt(o.toString());
            //Si le chiffre de la liste entrées se trouve dans le code et est au bon endroit
            if (oInt == secret[i]) {
                found += 1;
            }
            //Autrement: Si le chiffre de la liste entrées se trouve au moins dans le code
            else {
                for (int j = 0; j < secret.length; j++) {
                    if (secret[j] == oInt) {
                        somewhere+=1;
                    }
                }
            }
            i++;
        }
        //Une fois la liste entièrement parcourue, on regarde les valeurs de found et somewhere
        //On ajoute les chaines de caractères correspondantes à la liste output pour l'affichage sortie
        if(found == 1){
            output.add(found+" bien placé");
        }
        // Pour la gestion du pluriel
        else if(found >1){
            output.add( found+" bien placés" );
        }
        if(somewhere == 1){
            output.add( somewhere+" présent" );
        }
        // Pour la gestion du pluriel
        else if(somewhere >1){
            output.add( somewhere+" présents" );
        }
        String outputToString = myTrimStringWithSpaces(output.toString());
        System.out.println(outputToString);
        return outputToString;
    }
    /**
     * Méthode comprend la mécanique du jeu pour le Mode Defenseur (AI VS utilisateur).
     * <p>On demande à l'AI d'entrer une combinaison de chiffres</p>
     * <p>On compare à la combinaison secrète puis affiche les indices bien placés ou présents</p>
     * @see MastermindGame_Defenseur#tryOutCheck(ArrayList, int[], String, ArrayList)
     */
    public static String startDefenseurMastermindGame(){
        ArrayList inputToArray = new ArrayList();
        int first = (int) (Math.random() * 10);
        int second = (int) (Math.random() * 10);
        int third = (int) (Math.random() * 10);
        int fourth = (int) (Math.random() * 10);

        System.out.println( "\nProposition de l'ordinateur." );
        if(!reponseToString.isEmpty()){
            inputToArray.clear();
            for (int i = 0; i < reponseToString.length(); i++) {
                if (AIinput.charAt(i) == secretCode[i]) {
                    String ok = ""+ getNumericValue(AIinput.charAt(i));
                    inputToArray.add( ok );
                }
                else if(AIinput.charAt(i) != secretCode[i]){
                    int minus = getNumericValue(AIinput.charAt(i));
                    minus = randomInRange( -1, getNumericValue( AIinput.charAt(i)));
                    inputToArray.add( minus );
                }
            }
            AIinput = myTrimString(inputToArray.toString());
        }

        else{
            inputToArray.add( first );
            inputToArray.add( second );
            inputToArray.add( third );
            inputToArray.add( fourth );
            AIinput = myTrimString( inputToArray.toString() );
        }
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //vérification réponse/code
        reponseToString = tryOutCheck(inputToArray, secretCode, AIinput, output);
        output.clear();
        return reponseToString;
    }
}
