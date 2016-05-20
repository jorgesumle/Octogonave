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
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author Jorge Maldonado Ventura
 */
class HighestScoresScreen extends GridPane{
    HighestScoresScreen(){
        ScoreXML.loadScores();
        ArrayList<String> scores = ScoreXML.getScores();
        
        Text title = new Text();
        
        Text score1 = new Text(scores.get(0));
        Text score2 = new Text(scores.get(1));
        Text score3 = new Text(scores.get(2));
        Text score4 = new Text(scores.get(3));
        Text score5 = new Text(scores.get(4));
        
        setTexts();
        
        add(title, 0, 0, 2, 1);
        add(score1, 0, 1, 2, 1);
        add(score2, 0, 2, 2, 1);
        add(score3, 0, 3, 2, 1);
        add(score4, 0, 4, 2, 1);
        add(score5, 0, 5, 2, 1);
    }
    
    private void setTexts(){
        Texts.getHighestScoresButton();
    }
}
