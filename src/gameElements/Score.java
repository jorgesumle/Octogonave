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

package gameElements;

import gameMenus.Config;
import gameMenus.ScoreXML;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * Contiene los métodos y atributos necesarios para representar la puntuación del
 * juego.
 * @author Jorge Maldonado Ventura
 */
public class Score extends Text{
    
    private long score;
    private static ArrayList<String> adventureModeRecordHolders,
            adventureModeHighestsScores,
            arcadeModeRecordHolders,
            arcadeModeHighestsScores;
    private boolean record;
    
    Score(double xLocation, double yLocation){
        score = 0;
        record = false;
        setTranslateX(xLocation);
        setTranslateY(yLocation);
        getStyleClass().add("text");
        setFill(Color.WHITE);
    }
    
    public boolean isRecord(){
        return record;
    }
    
    public void setRecord(boolean record){
        this.record = record;
    }
    
    public ArrayList<String> getAdventureModeHighestsScores() {
        return adventureModeHighestsScores;
    }

    public static ArrayList<String> getAdventureModeRecordHolders() {
        return adventureModeRecordHolders;
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
        if(sprite instanceof Asteroid){
            increaseScore(Asteroid.getBONUS());
        } else if(sprite instanceof UFO){
            increaseScore(UFO.getBONUS());
        } else if(sprite instanceof ReloadBonus){
            increaseScore(ReloadBonus.getBONUS());
            if(Config.areSoundsOn()) {
                Main.getMainMenu().getGame().getOctogonave().getReloadSound().play();
            }
        } else{
            if(Config.areSoundsOn()) {
                Main.getMainMenu().getGame().getOctogonave().getBonusSound().play();
            } 
            if(sprite instanceof Diamond) {
                increaseScore(Diamond.getBONUS());
            } else if(sprite instanceof Ruby) {
                increaseScore(Ruby.getBONUS());
            } else if(sprite instanceof YellowSapphire) {
                increaseScore(YellowSapphire.getBONUS());
            }
        }
        updateScoreText();
    }
    
    /**
     * Comprueba si la puntuación obtenida es una de las cinco mejores.
     * @return <tt>true</tt> si la puntuación obtenida es una de las cinco mejores
     * ; <tt>false</tt> en caso contrario.
     */
    public boolean checkRecord(){
        ScoreXML.load();
        if(Main.getMainMenu().getGame().isArcadeMode()){
            adventureModeHighestsScores = ScoreXML.getArcadeModeScores();
        } else{
            adventureModeHighestsScores = ScoreXML.getAdventureModeScores();
        }
        
        for(int i = 0; i < adventureModeHighestsScores.size(); i++){
            if(score > Long.parseLong(adventureModeHighestsScores.get(i))){
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
        for(byte i = 0; i < adventureModeHighestsScores.size(); i++){
            if(score > Long.parseLong(adventureModeHighestsScores.get(i))){
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
        if(Main.getMainMenu().getGame().isArcadeMode()){
            for(int j = arcadeModeHighestsScores.size() - 1; j > scorePos - 1; j--){
                arcadeModeHighestsScores.set(j, arcadeModeHighestsScores.get(j - 1));                        
            }
            arcadeModeHighestsScores.set(scorePos - 1, Long.toString(score));
        } else{
            for(int j = adventureModeHighestsScores.size() - 1; j > scorePos - 1; j--){
                adventureModeHighestsScores.set(j, adventureModeHighestsScores.get(j - 1));                        
            }
            adventureModeHighestsScores.set(scorePos - 1, Long.toString(score));
        }
    }
    
    /**
     * Actualiza la lista de los mejores jugadores, pero no la guarda en el XML.
     * @param playerPos el número de jugadores de la lista de récords que se han superado.
     */
    private void addRecordHolder(int playerPos){
        if(Main.getMainMenu().getGame().isArcadeMode()){
            adventureModeRecordHolders = ScoreXML.getArcadeModeRecordHolders();
            for(int j = adventureModeRecordHolders.size() - 1; j > playerPos - 1; j--){
                adventureModeRecordHolders.set(j, adventureModeRecordHolders.get(j - 1));                        
            }
            adventureModeRecordHolders.set(playerPos - 1, Main.getMainMenu().getGame().getGameOverMenu().getPlayerNameTextField().getText());
        } else{
            arcadeModeRecordHolders = ScoreXML.getAdventureModeRecordHolders();
            for(int j = arcadeModeRecordHolders.size() - 1; j > playerPos - 1; j--){
                arcadeModeRecordHolders.set(j, arcadeModeRecordHolders.get(j - 1));                        
            }
            arcadeModeRecordHolders.set(playerPos - 1, Main.getMainMenu().getGame().getGameOverMenu().getPlayerNameTextField().getText());
        }
        
    }
}
