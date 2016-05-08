/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameElements;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 *
 * @author Jorge Maldonado Ventura
 */
class Score extends Text{
    private long score;
    
    Score(double xLocation, double yLocation){
        score = 0;
        setTranslateX(xLocation);
        setTranslateY(yLocation);
        getStyleClass().add("text");
        setFill(Color.BLACK);
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
}
