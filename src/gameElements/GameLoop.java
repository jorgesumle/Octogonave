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

import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * El bucle del juego. Hereda de la clase AnimationTimer, lo cual
 * permite que se actualice (en condiciones idílicas) cada fotograma siempre que
 * está en marcha.
 * @author Jorge Maldonado Ventura 
 */
class GameLoop extends AnimationTimer{
    
    private final Octogonave octogonave;
    private final SpriteManager spriteManager;
    private final String GAME_MUSIC_PATH = "/Stealth Groover.aiff";
    private MediaPlayer gameMusicPlayer; //Si no esta declarado aquí el, recolector de basura de Java lo detiene en diez segundos.
    Timeline mediaPlayerTimeline;
    private Random random;
    private Timeline timeline;
    
    GameLoop(Octogonave octogonave, SpriteManager spriteManager){
        this.octogonave = octogonave;
        this.spriteManager = spriteManager;
        random = new Random();
        playTimeLine();
        if(gameMenus.Config.isMusicOn()){
            playMusic();
        }
    }
    
    public MediaPlayer getGameMusicPlayer() {
        return gameMusicPlayer;
    }

    public Timeline getMediaPlayerTimeline() {
        return mediaPlayerTimeline;
    }

    public Timeline getTimeline() {
        return timeline;
    }
    
    /**
     * Este código se ejecuta cada fotograma mientras el <tt>AnimationTimer</tt> este
     * activo. Actualiza todos los <i>sprites</i> del juego.
     * @param now El registro del pulso (fotograma) actual en nanosegundos. 
     * Este valor es el mismo para todos las instancias de <tt>AnimationTimer</tt> 
     * llamadas en el mismo pulso. 
     */
    @Override
    public void handle(long now) {
        octogonave.update();
        if(!Main.getMainMenu().getGame().isPaused()){
            spriteManager.getCurrentNormal().stream().forEach((sprite) -> {
                sprite.update();
            });
            spriteManager.removeFromCurrentNormal(spriteManager.getNormalToRemove().stream().toArray(Sprite[]::new));
            spriteManager.clearNormalToRemove();
            spriteManager.addToCurrentNormal(spriteManager.getNormalToAdd().stream().toArray(Sprite[]::new));
            spriteManager.clearNormalToAdd();
            spriteManager.getCurrentBullets().stream().forEach((sprite) -> {
                sprite.update();
            });
            spriteManager.removeFromCurrentBullets(spriteManager.getBulletsToRemove().stream().toArray(Sprite[]::new));
            spriteManager.clearBulletsToRemove();
            spriteManager.addToCurrentBullets(spriteManager.getBulletsToAdd().stream().toArray(Sprite[]::new));
            spriteManager.clearBulletsToAdd();
        }
    }
    
    /**
     * Empieza el AnimationTimer. Una vez empezado, el método handle(long) de 
     * este AnimationTimer seré llamado en cada fotograma. El AnimationTimer 
     * puede ser parado llamando al método {@link #stop()}.
     */
    @Override
    public void start() {
        super.start();
    }
    
    /**
     * Para el AnimationTimer. Puede ser activado de nuevo llamando a {@link #start()}.
     */
    @Override
    public void stop(){
        super.stop();
    }
    
    /**
     * Empieza el TimeLine, que añade nuevos sprites al juego cada cierto tiempo.
     */
    private void playTimeLine(){
        timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(333), (ActionEvent e) -> {
            if(Main.getMainMenu().getGame().getScore().getScore() < 400){
                if(random.nextInt(10) == 0){
                    createGem();
                    createBonus();
                }
                createAsteroid((byte)4);
            } else if(Main.getMainMenu().getGame().getScore().getScore() < 800){
                createAsteroid((byte)4);
                createAsteroid((byte)3);
                if(random.nextInt(114) == 0){
                    createGem();
                    createBonus();
                }
            } else if(Main.getMainMenu().getGame().getScore().getScore() < 1_200){
                createAsteroid((byte)4);
                createAsteroid((byte)4);
                if(random.nextInt(60) == 0){
                    createGem();
                    createBonus();
                }
            } else if(Main.getMainMenu().getGame().getScore().getScore() < 2_000){
                createAsteroid((byte)4);
                createAsteroid((byte)4);
                createAsteroid((byte)4);
                if(random.nextInt(48) == 0){
                    createGem();
                    createBonus();
                }
            } else if(Main.getMainMenu().getGame().getScore().getScore() < 2_100){
                createGem();
                createBonus();
            }
            else{
                createAsteroid((byte)5);
                createAsteroid((byte)5);
                createAsteroid((byte)5);
                if(random.nextInt(43) == 0){
                    createGem();
                    createBonus();
                }
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    /**
     * Reproduce la música del juego una y otra vez.
     */
    private void playMusic(){
        gameMusicPlayer = new MediaPlayer(new Media(this.getClass().getResource(GAME_MUSIC_PATH).toExternalForm()));
        gameMusicPlayer.play();
        mediaPlayerTimeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(217_731), (ActionEvent e) -> {
            gameMusicPlayer = new MediaPlayer(new Media(this.getClass().getResource(GAME_MUSIC_PATH).toExternalForm()));
            gameMusicPlayer.play();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    /**
     * Crea una gema en una posición aleatoria.
     */
    private void createGem(){
        Sprite sprite;
        switch (random.nextInt(3)) {
            case 0:                                //1+640-32(diamond width) //1+480-24
                sprite = new Diamond(random.nextInt(609), random.nextInt(455));
                break;
            case 1:
                sprite = new Ruby(random.nextInt(609), random.nextInt(447));
                break;
            default:
                sprite = new YellowSapphire(random.nextInt(609), random.nextInt(458));
                break;
        }
        Main.getRoot().getChildren().add(sprite.getSpriteFrame());
        spriteManager.addToNormalToAdd(sprite);
    }
    
    private void createBonus(){
        Sprite sprite;
        sprite = new ReloadBonus(random.nextInt(609), random.nextInt(458));
        Main.getRoot().getChildren().add(sprite.getSpriteFrame());
        spriteManager.addToNormalToAdd(sprite);
    }
    
    /**
     * Crea un asteroide con una velocidad aleatoria limitada por el número que se
     * pasa como parámetro. El se crea fuera de la visión del juego y la dirección
     * que sigue es hacia alguna posición aleatoria en la ventana.
     * @param maxSpeed la velocidad máxima que puede tener el asteroide.
     */
    private void createAsteroid(byte maxSpeed){
        Asteroid asteroid = null;
        switch(random.nextInt(maxSpeed)){
            case 0: //arriba-abajo
                asteroid = new Asteroid(random.nextDouble() * (56 + Main.getWINDOW_WIDTH()) - 56, 0 - 58, 
                        (random.nextDouble() * maxSpeed + 1) * randomDir(), random.nextDouble() * maxSpeed + 1);
                break;
            case 1: //derecha-izquierda
                asteroid = new Asteroid(Main.getWINDOW_WIDTH() - 1, random.nextDouble() * (58 + Main.getWINDOW_HEIGHT()) - 58, 
                        (random.nextDouble() * maxSpeed + 1) * -1, (random.nextDouble() * maxSpeed + 1) * randomDir());
                break;
            case 2: //abajo-arriba
                asteroid = new Asteroid(random.nextDouble() * (56 + Main.getWINDOW_WIDTH()) - 56, Main.getWINDOW_HEIGHT() - 1, 
                        (random.nextDouble() * maxSpeed + 1) * randomDir(), (random.nextDouble() * maxSpeed + 1) * -1);
                break;
            case 3: //izquierda-derecha
                asteroid = new Asteroid(0 - 56, random.nextDouble() * (58 + Main.getWINDOW_HEIGHT()) - 58, 
                        random.nextDouble() * maxSpeed + 1, (random.nextDouble() * maxSpeed + 1) * randomDir());
                break;
        }
		
        Main.getRoot().getChildren().add(asteroid.getSpriteFrame());
        spriteManager.addToNormalToAdd(asteroid);
    }
    
    /**
     * 
     * @return 1 o -1.
     */
    private byte randomDir(){
        if(random.nextBoolean()){
                return 1;
        } else{
                return -1;
        }
    }
	
}
