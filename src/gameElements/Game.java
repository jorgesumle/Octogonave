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
import java.io.File;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * Representa una partida, lleva asociado el controlador de los <i>sprites</i>, la Octogonave y la
 * puntuación.
 * @author Jorge Maldonado Ventura
 */
public class Game {
    private boolean paused, gameOver;
    private Octogonave octogonave;
    private SpriteManager spriteManager;
    private Score score;
    private GameLoop gameLoop;
    private GameOverMenu gameOverMenu;
    private Text pauseText;
    MediaPlayer musicPlayer;
    private final String ARCADE_MODE_MUSIC_PATH = "V3S - DarkNess.wav";
    
    public Game(){
        if(gameMenus.Config.isMusicOn()){
            playMusic();
        }
        paused = false;
        createNodes();
        addNodes();
        spriteManager = new SpriteManager();
        startGameLoop();
        pauseText = new Text(14, 45, Texts.getPausedText());
        pauseText.setId("pauseText");
        gameOver = false;
    }

    protected boolean isPaused() {
        return paused;
    }

    protected boolean isGameOver() {
        return gameOver;
    }

    public GameOverMenu getGameOverMenu() {
        return gameOverMenu;
    }

    protected GameLoop getGameLoop() {
        return gameLoop;
    }
    
    protected Octogonave getOctogonave() {
        return octogonave;
    }
    
    public Score getScore() {
        return score;
    }
    
    protected SpriteManager getSpriteManager() {
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
                                new Image("/Jupiter.jpg", 640, 480, true, false, true), 
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
        gameOver = true;
        gameLoop.stop();
        Levels.removeTimers();
        if(gameMenus.Config.isMusicOn()){
            musicPlayer.stop();
        }
        Main.getRoot().getChildren().clear();
        displayGameOverMenu();
    }
    
    /**
     * Muestra el menú de fin de juego. Si se ha conseguido un récord, 
     * crea una zona para escribir el nombre del jugador y guardar la puntuación.
     */
    private void displayGameOverMenu(){
        gameOverMenu = new GameOverMenu();
        Main.getScene().setRoot(gameOverMenu);
        if(score.checkRecord()){
            score.setRecord(true);
            gameOverMenu.showHighScoresAnimatedMessage();
            gameOverMenu.createSavingArea();
        }
    }
    
    /**
     * Pausa el juego. Cuando el juego está pausado todos los <i>sprites</i> se detienen.
     * Solo permanece activo el método de <i>Octogonave</i> que comprueba las teclas 
     * pulsadas para ejecutar el método {@link #resume()} cuando se pulse la tecla de
     * pausa para reanudar el juego.
     */
    protected void pause(){
        spriteManager.pauseCurrentSpritesTimeline();
        paused = true;
        if(octogonave.getReloadBonusTimer() != null){
            octogonave.getReloadBonusTimer().pause();
        }
        if(gameMenus.Config.isMusicOn()){
            musicPlayer.pause();
        }
        Main.getRoot().getChildren().add(pauseText);
    }
    
    /**
     * Reanuda el juego si está pausado.
     */
    protected void resume(){
        spriteManager.resumeCurrentSpritesTimeline();
        paused = false;
        Main.getRoot().getChildren().remove(pauseText);
        if(octogonave.getReloadBonusTimer() != null){
            octogonave.getReloadBonusTimer().play();
        }
        if(gameMenus.Config.isMusicOn()){
            musicPlayer.play();
        }
    }

    /**
     * Reproduce la música del juego una y otra vez.
     */
    void playMusic() {
        musicPlayer = new MediaPlayer(new Media(new File(ARCADE_MODE_MUSIC_PATH).toURI().toString()));
        musicPlayer.setOnReady(() -> {
            musicPlayer.play();
            musicPlayer.setOnEndOfMedia(() -> {
                musicPlayer.seek(Duration.ZERO);
                musicPlayer.play();
            });
        });
    }
    
}
