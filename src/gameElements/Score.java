/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameElements;

import gameMenus.Config;
import gameMenus.ScoreXML;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 *
 * @author Jorge Maldonado Ventura
 */
public class Score extends Text{
    
    private long score;
    private static ArrayList<String> highestsScores;
    private static ArrayList<String> recordHolders;
    
    Score(double xLocation, double yLocation){
        score = 0;
        setTranslateX(xLocation);
        setTranslateY(yLocation);
        getStyleClass().add("text");
        setFill(Color.WHITE);
    }

    public ArrayList<String> getHighestsScores() {
        return highestsScores;
    }

    public static ArrayList<String> getRecordHolders() {
        return recordHolders;
    }

    public long getScore() {
        return score;
    }
    
    /**
     * Aumenta la puntuación de la partida.
     * @param addedScore la cantidad en que se quiere aumentar la puntuación.
     */
    void increaseScore(int addedScore){
        score += addedScore;
    }
    
    /**
     * Actualiza el texto que aparece en pantalla con la puntuación actual,
     * obtenida de la variable {@link #score}.
     */
    void updateScoreText() {
        setText(Long.toString(score));
    }

    /**
     * Actualiza la puntuación del juego. Cada objeto que recoge la nave tiene
     * una puntuación diferente.
     * @param sprite el objeto que recoge la nave.
     */
    protected void updateScore(Sprite sprite) {
        if (Config.areSoundsOn()) {
            Main.getMainMenu().getGame().getOctogonave().getBonusSound().play();
        }
        if (sprite instanceof Diamond) {
            increaseScore(Diamond.getBONUS());
        } else if (sprite instanceof Ruby) {
            increaseScore(Ruby.getBONUS());
        } else if (sprite instanceof YellowSapphire) {
            increaseScore(YellowSapphire.getBONUS());
        } else if (sprite instanceof Asteroid) {
            increaseScore(Asteroid.getBONUS());
        }
        updateScoreText();
    }
    
    /**
     * Comprueba si la puntuación obtenida es una de las cinco mejores.
     */
    boolean isRecord(){
        ScoreXML.load();
        highestsScores = ScoreXML.getScores();
        for(int i = 0; i < highestsScores.size(); i++){
            if(score > Long.parseLong(highestsScores.get(i))){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Devuelve la puntuación del jugador en la lista de récords. Devuelve 0 si no
     * ha superado ningún récord.
     * @return 
     */
    private byte playerPosition(){
        for(byte i = 0; i < highestsScores.size(); i++){
            if(score > Long.parseLong(highestsScores.get(i))){
                return (byte)(i + 1);
            }
        }
        return 0;
    }
    
    /**
     * Actualiza la lista de las mejores puntuaciones y de los mejores jugadores, pero no la guarda en el XML.
     */
    public void updateHighestsScoreXMLValues(){
        byte recordPos = playerPosition();
        if(recordPos != 0){
            addHighestsScore(recordPos);
            addRecordHolder(recordPos);
        }
    }
    
    /**
     * Actualiza la lista de las mejores puntuaciones, pero no la guarda en el XML.
     * @param scorePos el número de puntuaciones de la lista de récords que se han superado.
     */
    private void addHighestsScore(int scorePos){
        for(int j = highestsScores.size() - 1; j > scorePos - 1; j--){
            highestsScores.set(j, highestsScores.get(j - 1));                        
        }
        highestsScores.set(scorePos - 1, Long.toString(score));
    }
    
    /**
     * Actualiza la lista de los mejores jugadores, pero no la guarda en el XML.
     * @param playerPos el número de jugadores de la lista de récords que se han superado.
     */
    private void addRecordHolder(int playerPos){
        recordHolders = ScoreXML.getRecordHolders();
        for(int j = recordHolders.size() - 1; j > playerPos - 1; j--){
            recordHolders.set(j, recordHolders.get(j - 1));                        
        }
        recordHolders.set(playerPos - 1, Main.getMainMenu().getGame().getGameOverMenu().getPlayerNameTextField().getText());
    }
}
