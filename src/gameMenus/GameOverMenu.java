/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameMenus;

import gameElements.Game;
import gameElements.Main;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author Jorge Maldonado Ventura
 */
public class GameOverMenu extends GridPane{
    Text gameOverText;
    Button playButton, toMainMenuButton;
    public GameOverMenu() {
        setVgap(Main.getMainMenu().getPADDING());
        setHgap(Main.getMainMenu().getPADDING());
        setAlignment(Pos.CENTER);
        gameOverText = new Text();
        gameOverText.getStyleClass().add("smallTitle");
        add(gameOverText, 0, 0, 2, 1);
        Text score = new Text(Long.toString(Main.getMainMenu().getGame().getScore().getScore()));
        add(score, 0, 1);
        
        
        
        playButton = new Button();
        playButton.setOnAction(e ->
            {
                Main.getScene().setRoot(Main.getRoot());
                Main.getMainMenu().setGame(new Game());
            }
        );
        add(playButton, 1, 2);
        toMainMenuButton = new Button();
        toMainMenuButton.setOnAction(e ->
            {
                Main.getScene().setRoot(Main.getMainMenu());
            }
        );
        add(toMainMenuButton, 1, 3);
    }
    
    public void setTexts(){
        gameOverText.setText(Texts.getGameOverText());
        playButton.setText(Texts.getPlayButton());
        toMainMenuButton.setText(Texts.getToMainMenuButton());
    }
    
    /**
     * Comprueba si la puntuación obtenida es una de las cinco mejores.
     */
    private void checkHighestsScores(){
        ArrayList<String> highestsScores = ScoreXML.getScores();
        
        for(int i = highestsScores.size(); i > 0; i--){
            if(Main.getMainMenu().getGame().getScore().getScore() > Long.parseLong(highestsScores.get(i))){
                //ScoreXML.update();
            }
        }
    }
    
}
