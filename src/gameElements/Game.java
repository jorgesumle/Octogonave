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
 *
 * @author Jorge Maldonado Ventura
 */
public class Game {
    
    private Bullet bullet, bullet2;
    private Octogonave octogonave;
    private SpriteManager spriteManager;
    private Score playScore;
    
    public Game(){
        createNodes();
        addNodes();
        manageSprites();
        startGameLoop();
    }
    
    public Octogonave getOctogonave() {
        return octogonave;
    }
    
    public Score getPlayScore() {
        return playScore;
    }
    
    public SpriteManager getSpriteManager() {
        return spriteManager;
    }
    
    /**
     * Crea los nodos (Nodes) utilizados en el juego.
     */
    private void createNodes() {
        octogonave = new Octogonave(320, 240);
        bullet = new Bullet(Main.getWIDTH() - 9, Main.getHEIGHT()  - 12);
        bullet2 = new Bullet(Main.getWIDTH() - 10, Main.getHEIGHT() - 13);
        playScore = new Score(550, 30);
    }
    
    /**
     * Añade los nodos (Nodes) al Pane principal.
     */
    public void addNodes() {
        Main.getRoot().getChildren().addAll(playScore, octogonave.getSpriteFrame(), bullet.getSpriteFrame(), bullet2.getSpriteFrame());
        Main.getRoot().setBackground(
                new Background(
                        new BackgroundImage(
                                new Image("/spaceBackgroundInvSmall.jpg", 640, 480, true, false, true), 
                                BackgroundRepeat.NO_REPEAT, 
                                BackgroundRepeat.NO_REPEAT, 
                                BackgroundPosition.CENTER, 
                                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true))));
    }
    
    private void manageSprites() {
        spriteManager = new SpriteManager();
        spriteManager.addToCurrentSprites(bullet, bullet2);
    }
    
    /**
     * Arranca el AnimationTimer, que ejecutará la lógica de acción y actualización
     * del juego, que se ejecutará en cada fotograma en condiciones idóneas.
     */
    private void startGameLoop() {
        GameLoop gameLoop = new GameLoop(octogonave, spriteManager);
        gameLoop.start();
    }    
    
}
