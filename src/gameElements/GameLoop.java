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
     * Reproduce la música del juego una y otra vez.
     */
    private void playMusic(){
        gameMusicPlayer = new MediaPlayer(new Media(this.getClass().getResource(GAME_MUSIC_PATH).toExternalForm()));
        gameMusicPlayer.play();
        mediaPlayerTimeline = new Timeline();
        mediaPlayerTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(217_731), (ActionEvent e) -> {
            gameMusicPlayer = new MediaPlayer(new Media(this.getClass().getResource(GAME_MUSIC_PATH).toExternalForm()));
            gameMusicPlayer.play();
        }));
        mediaPlayerTimeline.setCycleCount(Animation.INDEFINITE);
        mediaPlayerTimeline.play();
    }
    
}
