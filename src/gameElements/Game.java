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

import gameMenus.GameOverMenu;
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
    private Score score;
    private GameLoop gameLoop;
    private GameOverMenu gameOverMenu;
    
    public Game(){
        createNodes();
        addNodes();
        spriteManager = new SpriteManager();
        startGameLoop();
    }

    public GameOverMenu getGameOverMenu() {
        return gameOverMenu;
    }

    GameLoop getGameLoop() {
        return gameLoop;
    }
    
    Octogonave getOctogonave() {
        return octogonave;
    }
    
    public Score getScore() {
        return score;
    }
    
    SpriteManager getSpriteManager() {
        return spriteManager;
    }
    
    /**
     * Crea las instancias de <tt>Nodes</tt> utilizados en el juego.
     */
    private void createNodes() {
        octogonave = new Octogonave(320, 240);
        score = new Score(550, 30);
    }
    
    /**
     * Añade las instancias de <tt>Node</tt> al Pane principal.
     */
    private void addNodes() {
        Main.getRoot().getChildren().addAll(score, octogonave.getSpriteFrame());
        Main.getRoot().setBackground(
                new Background(
                        new BackgroundImage(
                                new Image("/nebula.jpg", 640, 480, true, false, true), 
                                BackgroundRepeat.NO_REPEAT, 
                                BackgroundRepeat.NO_REPEAT, 
                                BackgroundPosition.CENTER, 
                                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true))));
    }
    
    /**
     * Arranca el <tt>AnimationTimer</tt>, que ejecutará la lógica de acción y actualización
     * del juego, que se ejecutará en cada fotograma en condiciones idóneas.
     */
    private void startGameLoop() {
        gameLoop = new GameLoop(octogonave, spriteManager);
        gameLoop.start();
    }
    
    /**
     * Finaliza la partida llevando al menú de fin de la partida.
     */
    void endGame(){
        gameLoop.stop();
        gameLoop.getTimeline().stop();
        Main.getRoot().getChildren().clear();
        gameOverMenu = new GameOverMenu();
        Main.getScene().setRoot(gameOverMenu);
        if(score.isRecord()){
            gameOverMenu.showHighScoresAnimatedMessage();
            gameOverMenu.createSavingArea();
        }
    }
    
    
    
}
