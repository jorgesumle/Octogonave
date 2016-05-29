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

import gameElements.Main;
import java.util.ArrayList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * 
 * @author Jorge Maldonado Ventura
 */
class HighestScoresScreen extends GridPane{
    
    Text title, score1, score2, score3, score4, score5, player1, player2, player3,
            player4, player5;
    Button backButton;
    ArrayList<String> bestPlayers, scores; 
    
    HighestScoresScreen(){
        applyStyle();
        createTitleText();
        ScoreXML.load();
        createPlayerNamesTexts();
        createPlayerScoresTexts();
        createBackButton();
        addNodes();
    }
    
    void setTexts(){
        title.setText(Texts.getHighestScoresButton());
        backButton.setText(Texts.getBackButton());
        bestPlayers = ScoreXML.getRecordHolders();
        player1.setText(bestPlayers.get(0));
        player2.setText(bestPlayers.get(1));
        player3.setText(bestPlayers.get(2));
        player4.setText(bestPlayers.get(3));
        player5.setText(bestPlayers.get(4));
        scores = ScoreXML.getScores();
        score1.setText(scores.get(0));
        score2.setText(scores.get(1));
        score3.setText(scores.get(2));
        score4.setText(scores.get(3));
        score5.setText(scores.get(4));
    }
    
    private void applyStyle(){
        setVgap(Main.getMainMenu().getPADDING());
        setHgap(Main.getMainMenu().getPADDING());
        setAlignment(Pos.CENTER);
        setBackground(new Background(
                        new BackgroundImage(
                                new Image("planets.jpg", 640, 480, true, false, true),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true))));
    }
    
    private void createTitleText(){
        title = new Text();
        title.getStyleClass().add("smallTitleWhite");
    }
    
    private void createPlayerNamesTexts(){
        player1 = new Text();
        player1.getStyleClass().add("smallTextOnlyWhiteStrong");
        setHalignment(player1, HPos.CENTER);
        player2 = new Text();
        player2.getStyleClass().add("smallTextOnlyWhiteStrong");
        setHalignment(player2, HPos.CENTER);
        player3 = new Text();
        player3.getStyleClass().add("smallTextOnlyWhiteStrong");
        setHalignment(player3, HPos.CENTER);
        player4 = new Text();
        player4.getStyleClass().add("smallTextOnlyWhiteStrong");
        setHalignment(player4, HPos.CENTER);
        player5 = new Text();
        player5.getStyleClass().add("smallTextOnlyWhiteStrong");
        setHalignment(player5, HPos.CENTER);
    }
    
    private void createPlayerScoresTexts(){
        score1 = new Text();
        score1.getStyleClass().add("smallTextOnlyWhiteStrong");
        setHalignment(score1, HPos.CENTER);
        score2 = new Text();
        score2.getStyleClass().add("smallTextOnlyWhiteStrong");
        setHalignment(score2, HPos.CENTER);
        score3 = new Text();
        score3.getStyleClass().add("smallTextOnlyWhiteStrong");
        setHalignment(score3, HPos.CENTER);
        score4 = new Text();
        score4.getStyleClass().add("smallTextOnlyWhiteStrong");
        setHalignment(score4, HPos.CENTER);
        score5 = new Text();
        score5.getStyleClass().add("smallTextOnlyWhiteStrong");
        setHalignment(score5, HPos.CENTER);
    }
    
    private void createBackButton(){
        backButton = new Button();
        backButton.setOnAction(e ->
            {
                Main.getMainMenu().getStarTimeline().play();
                Main.getScene().setRoot(Main.getMainMenu());
            }
        );
    }
    
    private void addNodes(){
        add(title, 0, 0, 2, 1);
        add(player1, 0, 1);
        add(score1, 1, 1);
        add(player2, 0, 2);
        add(score2, 1, 2);
        add(player3, 0, 3);
        add(score3, 1, 3);
        add(player4, 0, 4);
        add(score4, 1, 4);
        add(player5, 0, 5);
        add(score5, 1, 5);
        add(backButton, 0, 6, 2, 1);
    }
}
