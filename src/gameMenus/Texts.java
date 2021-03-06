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
 * Contiene el <tt>ArrayList</tt> con los textos del idioma seleccionado y métodos
 * para acceder a las distintas posiciones. Cada método tiene un nombre que indica
 * a que texto se refiere.
 * @author Jorge Maldonado Ventura
 */
public class Texts{
    
    private static ArrayList<String> texts;
    
    static String getLanguage(){
        return texts.get(0);
    }
    
    public static String getProgramTitle(){
        return texts.get(1);
    }
    
    static String getAdventureModeButton() {
        return texts.get(2);
    }

    static String getInstructionsButton() {
        return texts.get(3);
    }

    static String getConfigButton() {
        return texts.get(4);
    }

    static String getCreditsButton() {
        return texts.get(5);
    }

    static String getExitButton() {
        return texts.get(6);
    }

    static String getLanguageLabel() {
        return texts.get(7);
    }

    static String getMusicLabel() {
        return texts.get(8);
    }

    static String getOnMusicButton() {
        return texts.get(9);
    }

    static String getOffMusicButton() {
        return texts.get(10);
    }

    static String getInstructionsText() {
        return texts.get(11);
    }

    static String getBackButton() {
        return texts.get(12);
    }

    static String getSoundsLabel() {
        return texts.get(13);
    }

    static String getOnSoundsButton() {
        return texts.get(14);
    }

    static String getOffSoundsButton() {
        return texts.get(15);
    }
    
    static String getHighestScoresButton() {
        return texts.get(16);
    }
    
    static String getGameOverText(){
        return texts.get(17);
    }
    
    static String getToMainMenuButton(){
        return texts.get(18);
    }
    
    static String getScoreText(){
        return texts.get(19);
    }
    
    static String getRecordText(){
        return texts.get(20);
    }
    
    static String getSavingText(){
        return texts.get(21);
    }
    
    public static String getPausedText(){
        return texts.get(22);
    }
    
    static String getArcadeModeButton(){
        return texts.get(23);
    }
    
    public static String getLevel1Text(){
        return texts.get(24);
    }
    
    public static String getLevel2Text(){
        return texts.get(25);
    }
    
    public static String getLevel3Text(){
        return texts.get(26);
    }
    
    public static String getLevel4Text(){
        return texts.get(27);
    }
    
    public static String getLevel5Text(){
        return texts.get(28);
    }
    
    public static String getGameCompletedText(){
        return texts.get(29);
    }
    
    static String getSavingScreen(){
        return texts.get(30);
    }
    
    static String getOptionalEnterYourName(){
        return texts.get(31);
    }
    
    static String getOptionalEnterGameName(){
        return texts.get(32);
    }
    
    static String getSave(){
        return texts.get(33);
    }
    
    static String getSavedGames(){
        return texts.get(34);
    }
    
    static String getFree(){
        return texts.get(35);
    }
    
    static String getChooseAGame(){
        return texts.get(36);
    }
    
    static String getLevel(){
        return texts.get(37);
    }
    
    static String getPoints(){
        return texts.get(38);
    }
    
    static String getWantToLoadSavedGame(){
        return texts.get(39);
    }
    
    static String getSureOverrideMessage(){
        return texts.get(40);
    }
    
    static String get20CharactersNameLimit(){
        return texts.get(41);
    }
    
    public ArrayList<String> getTexts() {
        return texts;
    }

    public static void setTexts(ArrayList<String> texts) {
        Texts.texts = texts;
    }
    
}
