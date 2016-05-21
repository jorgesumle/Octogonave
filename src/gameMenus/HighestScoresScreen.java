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
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author Jorge Maldonado Ventura
 */
class HighestScoresScreen extends GridPane{
    
    Text title, score1, score2, score3, score4, score5;
    Button backButton;
    
    HighestScoresScreen(){
        applyLayoutStyle();
        createNodes();
        addNodes();
    }
    
    void setTexts(){
        title.setText(Texts.getHighestScoresButton());
        System.out.println(Texts.getHighestScoresButton());
        backButton.setText(Texts.getBackButton());
    }
    
    private void applyLayoutStyle(){
        setVgap(Main.getMainMenu().getPADDING());
        setHgap(Main.getMainMenu().getPADDING());
        setAlignment(Pos.CENTER);
    }
    
    private void createNodes(){
        ScoreXML.loadScores();
        ArrayList<String> scores = ScoreXML.getScores();
        
        title = new Text();
        title.getStyleClass().add("smallTitle");
        
        score1 = new Text(scores.get(0));
        score2 = new Text(scores.get(1));
        score3 = new Text(scores.get(2));
        score4 = new Text(scores.get(3));
        score5 = new Text(scores.get(4));
        
        backButton = new Button();
        backButton.setOnAction(e ->
            {
                Main.getScene().setRoot(Main.getMainMenu());
            }
        );
    }
    
    private void addNodes(){
        add(title, 0, 0, 2, 1);
        add(score1, 0, 1, 2, 1);
        add(score2, 0, 2, 2, 1);
        add(score3, 0, 3, 2, 1);
        add(score4, 0, 4, 2, 1);
        add(score5, 0, 5, 2, 1);
        
        add(backButton, 0, 6, 2, 1);
    }
}
