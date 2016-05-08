/*
 * Copyright (C) 2016 Jorge Maldonado Ventura
 *
 * Este programa es software libre: usted puede redistruirlo y/o modificarlo
 * bajo los términos de la Licencia Pública General GNU, tal y como está publicada por
 * la Free Software Foundation; ya sea la versión 3 de la Licencia, o
 * (a su elección) cualquier versión posterior.
 *
 * Este programa se distribuye con la intención de ser útil,
 * pero SIN NINGUNA GARANTÍA; incluso sin la garantía implícita de
 * USABILIDAD O UTILIDAD PARA UN FIN PARTICULAR. Vea la
 * Licencia Pública General GNU para más detalles.
 *
 * Usted debería haber recibido una copia de la Licencia Pública General GNU
 * junto a este programa.  Si no es así, vea <http://www.gnu.org/licenses/>.
 */
package gameMenus;

import java.util.ArrayList;

/**
 *
 * @author Jorge Maldonado Ventura
 */
public class Texts{
    private static ArrayList<String> texts;
    
    public static String getLanguage(){
        return texts.get(0);
    }
    
    public static String getProgramTitle(){
        return texts.get(1);
    }
    
    public static String getPlayButton() {
        return texts.get(2);
    }

    public static String getInstructionsButton() {
        return texts.get(3);
    }

    public static String getConfigButton() {
        return texts.get(4);
    }

    public static String getCreditsButton() {
        return texts.get(5);
    }

    public static String getExitButton() {
        return texts.get(6);
    }

    public static String getLanguageLabel() {
        return texts.get(7);
    }

    public static String getMusicLabel() {
        return texts.get(8);
    }

    public static String getOnMusicButton() {
        return texts.get(9);
    }

    public static String getOffMusicButton() {
        return texts.get(10);
    }

    public static String getInstructionsText() {
        return texts.get(11);
    }

    public static String getBackButton() {
        return texts.get(12);
    }

    public static String getSoundsLabel() {
        return texts.get(13);
    }

    public static String getOnSoundsButton() {
        return texts.get(14);
    }

    public static String getOffSoundsButton() {
        return texts.get(15);
    }
    
    
    public ArrayList<String> getTexts() {
        return texts;
    }

    public static void setTexts(ArrayList<String> texts) {
        Texts.texts = texts;
    }
    
    
}
