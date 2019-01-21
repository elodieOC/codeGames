package com.elodie.jeux.jeu_Recherche;

import com.elodie.jeux.Methodes.Methodes_MecaniqueJeu;
import java.util.ArrayList;
import java.util.Scanner;
import static com.elodie.jeux.Methodes.Methodes_Generales.*;
import static com.elodie.jeux.Methodes.Methodes_MecaniqueJeu.*;
import static java.lang.Character.getNumericValue;

/**
 * <b>Recherche +/- Mode duel où l'ordinateur et vous jouez tour à tour, le premier à trouver la combinaison gagne.</b>
 * <p>Le but : découvrir la combinaison à x chiffres de l'adversaire (le défenseur) en premier.
 * <p>Pour ce faire, l'attaquant fait une proposition. Le défenseur indique pour chaque
 * chiffre de la combinaison proposée si le chiffre de sa combinaison est plus grand (+),
 * plus petit (-) ou si c'est le bon chiffre (=). Puis les rôles s'inversent.
 *<p>Le joueur et l'ordinateur doivent deviner la combinaison secrète en un nombre limité d'essais.
 *<p><i>(Combinaison secrète : 1234)</i></p>
 *<p><i>(Proposition : 4278 -&#155; Réponse : -=--</i></p>
 *<p><i>(Proposition : 2214 -&#155; Réponse : -=+=</i></p>
 * @author elojito
 * @version 1.0
 */

public class SearchGame_Duel {
    /**
     * <b>Variables globales:</b>
     * <ul>
     * <li>tableau de chiffres de 0 à 9</li>
     * <li>une chaine de caractère vide pour les entrées utilisateur à venir</li>
     * <li>une chaine de caractère vide pour les entrées AI à venir</li>
     * <li>une chaine de caractère vide représentant les indices "+-=+" à venir pour l'utilisateur</li>
     * <li>une chaine de caractère vide représentant les indices "+-=+" à venir pour l'AI</li>
     * <li>une chaine de caractère "====" représentant l'affichage sortie si la combinaison est trouvée</li>
     * <li>un booléen pour les exceptions</li>
     * <li>un compteur d'essais pouyr l'utilisateur</li>
     * <li>un compteur d'essais pour l'ordinateur</li>
     * </ul>
     */
    static final String[] nbr = {"0","1","2","3","4","5","6","7","8","9"};
    static String userInput = "";
    static String AIinput = "";
    static String verifReponseUser = "";
    static String verifReponseAI = "";
    static final String winwin = "====";
    boolean catched = false;

    /**
     * Méthode comprend la mécanique du jeu pour le Mode Duel (ustilisateur et AI à chaque tour).
     * <p>On demande à l'utilisateur la longueur du code avec laquelle il souhaite jouer.</p>
     * @see Methodes_MecaniqueJeu#chooseCodeLenght()
     * <p>On créée une combinaison secrète.</p>
     * @see Methodes_MecaniqueJeu#computedSecretCode()
     * <p>On demande à l'utilisateur de créer une combinaison secrète.</p>
     * @see Methodes_MecaniqueJeu#inputSecretCode()
     * <p>On vérifie si on est en mode développeur ou non, si c'est le cas on affiche le code secret à trouver.</p>
     * @see Methodes_MecaniqueJeu#modeDevOrNot(int[])
     * <p>Tour de l'utilisateur:</p>
     * @see Methodes_MecaniqueJeu#playerTurnSearchGame(String, ArrayList, int[])
     * <p>Puis c'est au tour de l'ordinateur de jouer:
     * <p>On demande à l'AI d'entrer une combinaison de chiffres, on compare à la combinaison
     * secrète puis affiche les indices "+", "-", ou "="</p>
     * @see Methodes_MecaniqueJeu#tryOutCheckSearchGame(ArrayList, int[], String)
     * <p>Si l'utilisateur ou l'ordinateur trouve la bonne combinaison alors apparait "====", la partie s'arrête.
     * @see Methodes_MecaniqueJeu#stopOuEncore()
     */

    public SearchGame_Duel() {

        int counterUser = 0;
        int counterAI = 0;
        System.out.println( "-----------------------------------------" );
        System.out.println( "Bienvenue dans Recherche +/- Mode DUEL." );
        System.out.println( "-----------------------------------------" );
        //choix du nombre de cases à deviner
        chooseCodeLenght();
        //génération du code secret
        int[] secretCodeForUser = computedSecretCode();
        int[] secretCodeForAI = inputSecretCode();
        //affichage du code secret pour mode développeur
        modeDevOrNot( secretCodeForUser );
        //choix du nombre d'essais max
        chooseMaxTries();
        int max = maxTries();

        Scanner sc = new Scanner( System.in );
        boolean catched;
        ArrayList userInputToArray = new ArrayList();

        do{
            //Tour de l'utilisateur
            verifReponseUser = playerTurnSearchGame( userInput, userInputToArray, secretCodeForUser );
            counterUser++;
            if(!(verifReponseUser.equals( winwin ))) {

                //Tour de l'ordinateur
                ArrayList AIinputToArray = new ArrayList();
                System.out.println( "\nProposition de l'ordinateur:" );
                //Si des essais ont déjà été faits par l'AI:
                if (counterAI!=0) {
                    AIinputToArray.clear(); // on vide la liste de l'essai AI
                    for (int i = 0; i < verifReponseAI.length(); i++) {
                        //On garde le chiffre donné s'il est bon
                        if (verifReponseAI.charAt( i ) == '=') {
                            String ok = "" + getNumericValue( AIinput.charAt( i ) );
                            AIinputToArray.add( ok );
                        }
                        //TODO écarter les propositions déjà faites
                        //S'il est supérieur à celui du code secret, on lance un random avec en entier max ce chiffre essai
                        else if (verifReponseAI.charAt( i ) == '-') {
                            int minus = getNumericValue( AIinput.charAt( i ) );
                            minus = randomInRange( -1, minus );
                            AIinputToArray.add( minus );
                        }
                        //S'il est inférieur à celui du code secret, on lance un random avec en entier min ce chiffre essai
                        else if (verifReponseAI.charAt( i ) == '+') {
                            int plus = getNumericValue( AIinput.charAt( i ) );
                            plus = randomInRange( plus - 1, nbr.length - 1 );
                            AIinputToArray.add( plus );
                        }
                    }
                    AIinput = myTrimString( AIinputToArray.toString() );
                }

                //Si c'est le premier essai, on lance des randoms
                else {
                    for(int i = 0; i<secretCodeForAI.length;i++) {
                        AIinputToArray.add( (int) (Math.random() * 10));
                    }
                    AIinput = myTrimString( AIinputToArray.toString() );
                }
                try {
                    Thread.sleep( 2000 );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //vérification réponse/code
                verifReponseAI = tryOutCheckSearchGame( AIinputToArray, secretCodeForAI, AIinput );
                counterAI++;
            }
        }while(!verifReponseUser.equals( winwin ) && !verifReponseAI.equals( winwin )&& counterAI <max && counterUser <max);
        if(verifReponseUser.equals( winwin )) {
            System.out.println( "\nBravo vous avez trouvé la combinaison: !" );
        }
        else if(verifReponseAI.equals( winwin )) {
            System.out.println( "\nL'ordinateur a trouvé votre combinaison: !" );
            System.out.println( "La combinaison de l'ordinateur était: "+ secretCodeForUser );
        }
        else if(!verifReponseUser.equals( winwin ) && !verifReponseAI.equals( winwin )){
            System.out.println( "\nAucune combinaison trouvée. Le code de l'ordinateur était : " + showSecretCode( secretCodeForUser ));
        }
    }
}
