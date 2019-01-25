package com.elodie.jeux.search;

import com.elodie.jeux.Main;
import com.elodie.jeux.utilities.utilsGameMecanics;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import static com.elodie.jeux.utilities.utilsGameMecanics.*;

/**
 * <b>Recherche +/- - Mode challenger</b>
 * <p>Le but : découvrir la combinaison à x chiffres de l'adversaire (le défenseur, soit l'ordinateur).
 * <p>Pour ce faire, l'attaquant fait une proposition. Le défenseur indique pour chaque
 * chiffre de la combinaison proposée si le chiffre de sa combinaison est plus grand (+),
 * plus petit (-) ou si c'est le bon chiffre (=).
 *<p>L'attaquant doit deviner la combinaison secrète en un nombre limité d'essais.
 *<p>(Combinaison secrète : 1234))</p>
 *<p>(Proposition : 4278 -&#155; Réponse : -=--)</p>
 *<p>(Proposition : 2214 -&#155; Réponse : -=+=)</p>
 * @author elojito
 * @version 1.0
 */

public class searchChallenger {
    /**
     * <b>Variables globales:</b>
     * <ul>
     * <li>tableau de chiffres de 0 à 9</li>
     * <li>une chaine de caractère vide pour les entrées utilisateur à venir</li>
     * <li>une chaine de caractère vide représentant les indices "+-=+" à venir</li>
     * <li>une chaine de caractère "====" représentant l'affichage sortie si la combinaison est trouvée</li>
     * <li>un booléen pour les exceptions</li>
     * <li>un compteur d'essais</li>
     * <li>un appel pour le logger/li>
     * </ul>
     */
    static final String[] nbr = {"0","1","2","3","4","5","6","7","8","9"};
    static String userInput = "";
    static String verifReponse = "";
    static final String winwin = "====";
    private static final Logger logger = LogManager.getLogger( Main.class);

    /**
     * Méthode comprend la mécanique du jeu pour le Mode Challenger (utilisateur VS AI).
     * <p>On demande à l'utilisateur la longueur du code avec laquelle il souhaite jouer.</p>
     * @see utilsGameMecanics#chooseCodeLenght()
     * <p>On créée une combinaison secrète.</p>
     * @see utilsGameMecanics#computedSecretCode()
     * <p>On vérifie si on est en mode développeur ou non, si c'est le cas on affiche le code secret à trouver.</p>
     * @see utilsGameMecanics#modeDevOrNot()
     * <p>On demande à l'utilisateur le nombre d'essais maximal à ne pas dépasser.</p>
     * @see utilsGameMecanics#maxTries()
     * <p>Tour de jeu de l'utilisateur.</p>
     * @see utilsGameMecanics#playerTurnSearchGame(String, ArrayList, int[])
     * <p>Si l'utilisateur trouve alors apparait "====", la partie s'arrête.</p>
     * <p>Si l'utilisateur ne trouve pas en moins de 6 essais, la partie s'arrête et on affiche la solution.</p>
     * @see utilsGameMecanics#showSecretCode(int[])
     */

    public searchChallenger() {
        int counter = 0;
        System.out.println( "-----------------------------------------" );
        System.out.println( "Bienvenue dans Recherche +/- Mode Challenger." );
        System.out.println( "-----------------------------------------" );

        //Si le mode développeur est activé, on l'affiche
        showModeDevOn();
        //choix du nombre de cases à deviner
        chooseCodeLenght();
        //génération du code secret
        int[] secretCode = computedSecretCode();
        //affichage du code secret pour mode développeur
        if(modeDevOrNot()==true) {
            System.out.println( secretCode );
        }
        //choix du nombre d'essais max
        chooseMaxTries();
        int max = maxTries();

        ArrayList inputToArray = new ArrayList();

        do{
            verifReponse = playerTurnSearchGame( userInput, inputToArray, secretCode );
            counter++;
        }while(!(verifReponse.equals( winwin )) && counter < max);
        if(verifReponse.equals( winwin )){
            System.out.println( "\nBravo vous avez trouvé la combinaison !" );
            logger.info( "combinaison trouvée." );
        }
        else{
            System.out.println( "\nVous n'avez pas trouvé la combinaison. La réponse était : " + showSecretCode( secretCode ));
            logger.info( "combinaison non trouvée." );
        }
    }
}
