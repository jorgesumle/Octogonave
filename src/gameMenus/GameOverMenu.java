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

import gameElements.Game;
import gameElements.Main;
import javafx.animation.FadeTransition;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
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
        setBackground(new Background(
                        new BackgroundImage(
                                new Image("explosion.jpg", 640, 480, true, false, true),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true))));
    }
    
    private void createTitleText(){
        gameOverText = new Text();
        gameOverText.getStyleClass().add("smallTitleWhite");
        setHalignment(gameOverText, HPos.CENTER);
    }
    
    private void createScoreText(){
        scoreText = new Text();
        scoreText.getStyleClass().add("smallTextWhiteStrong");
        Text scoreNumber = new Text(Long.toString(Main.getMainMenu().getGame().getScore().getScore()));
        scoreNumber.getStyleClass().add("smallTextWhiteStrong");
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
                Main.getMainMenu().getStarTimeline().play();
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

    public void showHighScoresAnimatedMessage(){
        HBox hBox = new HBox();
        String recordMessage = Texts.getRecordText();            
        for(int i = 0; i < recordMessage.length(); i++){
            char letter = recordMessage.charAt(i);
            
            Text text = new Text(String.valueOf(letter));
            text.setFont(Font.font(48));
            text.setFill(Color.AQUAMARINE);
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
        Text savingText = new Text(Texts.getSavingText());
        savingText.getStyleClass().add("smallTextWhiteStrong");
        playerNameTextField = new TextField(System.getProperty("user.name"));
        playerNameTextField.textProperty().addListener((observable, oldValue, newValue) -> 
            {
                if(newValue.length() > 30){
                    playerNameTextField.setText(playerNameTextField.getText().substring(0, 30));    
                    new Alert(AlertType.INFORMATION, "No puedes guardar un nombre con más de 30 letras").showAndWait();  
                }
            }
        );
        hBox.getChildren().addAll(savingText, playerNameTextField);
        add(hBox, 0, 3);
    }
}
