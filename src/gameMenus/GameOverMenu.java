/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameMenus;

import gameElements.Game;
import gameElements.Main;
import java.util.ArrayList;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

/**
 *
 * @author Jorge Maldonado Ventura
 */
public class GameOverMenu extends GridPane{
    private Text gameOverText, scoreText;
    private Button playButton, toMainMenuButton;
    private TextFlow scoreTexts;
     
    public GameOverMenu() {
        setVgap(Main.getMainMenu().getPADDING());
        setHgap(Main.getMainMenu().getPADDING());
        setAlignment(Pos.CENTER);
        createTitleText();
        createScoreText();
        createPlayButton();
        createToMainButton();
        setTexts();
        addNodes();
        //checkHighestsScores();
    }
    
    private void createTitleText(){
        gameOverText = new Text();
        gameOverText.getStyleClass().add("smallTitle");
    }
    
    private void createScoreText(){
        scoreText = new Text();
        Text scoreNumber = new Text(Long.toString(Main.getMainMenu().getGame().getScore().getScore()));
        scoreTexts = new TextFlow(scoreText, new Text(" "), scoreNumber);
    }
    
    private void createPlayButton(){
        playButton = new Button();
        playButton.setOnAction(e ->
            {
                Main.getScene().setRoot(Main.getRoot());
                Main.getMainMenu().setGame(new Game());
            }
        );
    }
    
    private void createToMainButton(){
        toMainMenuButton = new Button();
        toMainMenuButton.setOnAction(e ->
            {
                Main.getScene().setRoot(Main.getMainMenu());
            }
        );
    }
    
    public void setTexts(){
        gameOverText.setText(Texts.getGameOverText());
        playButton.setText(Texts.getPlayButton());
        toMainMenuButton.setText(Texts.getToMainMenuButton());
        scoreText.setText(Texts.getScoreText());
    }
    
    private void addNodes(){
        add(gameOverText, 0, 0, 2, 1);
        add(scoreTexts, 0, 2);
        add(playButton, 1, 3);
        add(toMainMenuButton, 1, 4);
    }
    
    
    /**
     * Comprueba si la puntuación obtenida es una de las cinco mejores.
     */
    /*
    private void checkHighestsScores(){
        ScoreXML.loadScores();
        ArrayList<String> highestsScores = ScoreXML.getScores();
        Long gameScore = Main.getMainMenu().getGame().getScore().getScore();
        boolean writtenHighScore = false;
        for(int i = 0; i < highestsScores.size(); i++){
            if(gameScore > Long.parseLong(highestsScores.get(i))){
                if(i + 1 < highestsScores.size()){
                    //showHighScoresAnimatedMessage();
                    highestsScores.set(i+1, highestsScores.get(i));
                    ScoreXML.saveScores(highestsScores);
                } else{
                    break;
                }
                if(!writtenHighScore){
                    highestsScores.set(i, Long.toString(gameScore));
                    writtenHighScore = true;
                }
            }
        }
    }
    
    private void showHighScoresAnimatedMessage(){
        String recordMessage = Texts.getRecordText();            
        for(int i = 0; i < recordMessage.length(); i++){
            char letter = recordMessage.charAt(i);
            
            Text text = new Text(String.valueOf(letter));
            text.setFont(Font.font(48));
            text.setOpacity(0);

            add(text, 0, 1);
            
            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), text);
            ft.setToValue(1);
            ft.setDelay(Duration.seconds(i * 0.15));
            ft.play();
        }   
    }

    */
    
}
