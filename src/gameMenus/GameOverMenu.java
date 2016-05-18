/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameMenus;

import gameElements.Game;
import gameElements.Main;
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
        gameOverText = new Text();
        add(gameOverText, 0, 0, 2, 1);
        playButton = new Button();
        playButton.setOnAction(e ->
            {
                Main.getMainMenu().setGame(new Game());
            }
        );
        add(playButton, 1, 1);
        toMainMenuButton = new Button();
        toMainMenuButton.setOnAction(e ->
            {
                Main.getScene().setRoot(Main.getMainMenu());
            }
        );
        add(toMainMenuButton, 1, 2);
    }
    
    public void setTexts(){
        gameOverText.setText(Texts.getGameOverText());
        playButton.setText(Texts.getPlayButton());
        toMainMenuButton.setText(Texts.getToMainMenuButton());
    }
    
}
