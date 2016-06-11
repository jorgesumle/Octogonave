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
import gameMenus.Texts;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.text.Text;

/**
 * Representa una partida, lleva asociado el controlador de los <i>sprites</i>, la Octogonave y la
 * puntuación.
 * @author Jorge Maldonado Ventura
 */
public class Game {
    private boolean isArcadeMode;
    private boolean paused;
    private Octogonave octogonave;
    private SpriteManager spriteManager;
    private Score score;
    private GameLoop gameLoop;
    private GameOverMenu gameOverMenu;
    private Text pauseText;
    
    public Game(boolean isArcadeMode){
        this.isArcadeMode = isArcadeMode;
        if(isArcadeMode) Levels.arcadeMode();
        else{
            Levels.initAnyLevelTimeline();
            Levels.newLevelTransition(Texts.getLevel1Text(), 1);
        }
        paused = false;
        createNodes();
        addNodes();
        spriteManager = new SpriteManager();
        startGameLoop();
        pauseText = new Text(Texts.getPausedText());
    }

    boolean isPaused() {
        return paused;
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
        if(isArcadeMode){
            Levels.getArcadeModeTimeline().stop();
        } else{
            Levels.getAnyLevelTimeline().stop();
        }
        if(gameMenus.Config.isMusicOn()){
            stopGameMusic();
        }
        Main.getRoot().getChildren().clear();
        gameOverMenu = new GameOverMenu();
        Main.getScene().setRoot(gameOverMenu);
        if(score.isRecord()){
            gameOverMenu.showHighScoresAnimatedMessage();
            gameOverMenu.createSavingArea();
        }
    }
    
    /**
     * Detiene la música del juego.
     */
    private void stopGameMusic(){
        gameLoop.getGameMusicPlayer().stop();
        gameLoop.getMediaPlayerTimeline().stop();
    }
    
    /**
     * Pausa el juego. Cuando el juego está pausado todos los <i>sprites</i> se detienen.
     * Solo permanece activo el método de <i>Octogonave</i> que comprueba las teclas 
     * pulsadas para ejecutar el método {@link #resume()} cuando se pulse la tecla de
     * pausa para reanudar el juego.
     */
    void pause(){
        spriteManager.getCurrentNormal().stream().forEach((sprite) -> {
            if(sprite instanceof MovingEnemy){
                ((MovingEnemy)sprite).getTimeline().pause();
            }
        });
        paused = true;
        if(isArcadeMode){
            Levels.getArcadeModeTimeline().pause();
        } else{
            Levels.getAnyLevelTimeline().pause();
        }
        if(octogonave.getReloadBonusTimer() != null){
            octogonave.getReloadBonusTimer().pause();
        }
        
        pauseText.setX(14);
        pauseText.setY(45);
        pauseText.setId("pauseText");
        Main.getRoot().getChildren().add(pauseText);
    }
    
    /**
     * Reanuda el juego si está pausado.
     */
    void resume(){
        spriteManager.getCurrentNormal().stream().forEach((sprite) -> {
            if(sprite instanceof MovingEnemy){
                ((MovingEnemy)sprite).getTimeline().play();
            }
        });
        paused = false;
        Main.getRoot().getChildren().remove(pauseText);
        if(isArcadeMode){
            Levels.getArcadeModeTimeline().play();
        } else{
            Levels.getAnyLevelTimeline().play();
        }
        if(octogonave.getReloadBonusTimer() != null){
            octogonave.getReloadBonusTimer().play();
        }
    }
    
}
