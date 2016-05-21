/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameMenus;

import gameElements.Game;
import gameElements.Main;
import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    TextField playerNameTextField;
     
    public GameOverMenu() {
        applyLayoutStyle();
        createTitleText();
        createScoreText();
        createPlayButton();
        createToMainButton();
        setTexts();
        addNodes();
    }

    public TextField getPlayerNameTextField() {
        return playerNameTextField;
    }
    
    private void applyLayoutStyle(){
        setVgap(Main.getMainMenu().getPADDING());
        setHgap(Main.getMainMenu().getPADDING());
        setAlignment(Pos.CENTER);
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
                Main.getMainMenu().getGame().getScore().updateHighestsScoreXMLValues();
                ScoreXML.save();
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
                Main.getMainMenu().getGame().getScore().updateHighestsScoreXMLValues();
                ScoreXML.save();
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
    
    
    
    public void showHighScoresAnimatedMessage(){
        HBox hBox = new HBox();
        String recordMessage = Texts.getRecordText();            
        for(int i = 0; i < recordMessage.length(); i++){
            char letter = recordMessage.charAt(i);
            
            Text text = new Text(String.valueOf(letter));
            text.setFont(Font.font(48));
            text.setOpacity(0);

            hBox.getChildren().add(text);
            
            FadeTransition ft = new FadeTransition(Duration.seconds(0.5), text);
            ft.setToValue(1);
            ft.setDelay(Duration.seconds(i * 0.15));
            ft.play();
        }
        add(hBox, 0, 1);
    }
    
    /**
     * Crea los nodos para guardar el nombre del usuario que ha conseguido batir un récord.
     */
    public void createSavingArea(){
        HBox hBox = new HBox();
        playerNameTextField = new TextField(System.getProperty("user.name"));
        playerNameTextField.textProperty().addListener((observable, oldValue, newValue) -> 
            {
                if(newValue.length() > 30){
                    playerNameTextField.setText(playerNameTextField.getText().substring(0, 30));    
                    new Alert(AlertType.INFORMATION, "No puedes guardar un nombre con más de 30 letras").showAndWait();  
                }
            }
        );
        hBox.getChildren().addAll(new Text(Texts.getSavingText()), playerNameTextField);
        add(hBox, 0, 3);
    }
}
