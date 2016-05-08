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

import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;

/**
 * Representa una partida, lleva asociado el controlador de los <i>sprites</i>, la Octogonave y la
 * puntuación.
 * @author Jorge Maldonado Ventura
 */
public class Game {
    
    private Octogonave octogonave;
    private SpriteManager spriteManager;
    private Score playScore;
    private GameLoop gameLoop;
    
    public Game(){
        createNodes();
        addNodes();
        spriteManager = new SpriteManager();
        startGameLoop();
    }

    GameLoop getGameLoop() {
        return gameLoop;
    }
    
    Octogonave getOctogonave() {
        return octogonave;
    }
    
    Score getPlayScore() {
        return playScore;
    }
    
    SpriteManager getSpriteManager() {
        return spriteManager;
    }
    
    /**
     * Crea los nodos (Nodes) utilizados en el juego.
     */
    private void createNodes() {
        octogonave = new Octogonave(320, 240);
        playScore = new Score(550, 30);
    }
    
    /**
     * Añade los nodos (Nodes) al Pane principal.
     */
    private void addNodes() {
        Main.getRoot().getChildren().addAll(playScore, octogonave.getSpriteFrame());
        Main.getRoot().setBackground(
                new Background(
                        new BackgroundImage(
                                new Image("/spaceBackgroundInvSmall.jpg", 640, 480, true, false, true), 
                                BackgroundRepeat.NO_REPEAT, 
                                BackgroundRepeat.NO_REPEAT, 
                                BackgroundPosition.CENTER, 
                                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true))));
    }
    
    /**
     * Arranca el AnimationTimer, que ejecutará la lógica de acción y actualización
     * del juego, que se ejecutará en cada fotograma en condiciones idóneas.
     */
    private void startGameLoop() {
        gameLoop = new GameLoop(octogonave, spriteManager);
        gameLoop.start();
    }    
    
}
