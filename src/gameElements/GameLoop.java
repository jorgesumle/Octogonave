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

import gameMenus.ConfigMenu;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

/**
 * El bucle del juego. Hereda de la clase AnimationTimer, lo cual
 * permite que se actualice (en condiciones idílicas) cada fotograma siempre que
 * está en marcha.
 * @author Jorge Maldonado Ventura 
 */
class GameLoop extends AnimationTimer{
    private final Octogonave octogonave;
    private final SpriteManager SPRITE_MANAGER;
    GameLoop(Octogonave octogonave, SpriteManager spriteManager){
        this.octogonave = octogonave;
        this.SPRITE_MANAGER = spriteManager;
        playTimeLine();
        if(ConfigMenu.isMusicOn()){
            Sounds.playMusic();
        }
    }

    /**
     * Este código se ejecuta cada fotograma mientras el AnimationTimer este
     * activo. Actualiza todos los <i>sprites</i> del juego.
     * @param now El registro del pulso (fotograma) actual en nanosegundos. 
     * Este valor es el mismo para todos los AnimationTimers llamados en el mismo pulso. 
     */
    @Override
    public void handle(long now) {
        octogonave.update(); 
        
        SPRITE_MANAGER.getCURRENT_NORMAL().stream().forEach((sprite) -> {
            sprite.update();
        });
        SPRITE_MANAGER.removeFromCURRENT_NORMAL(SPRITE_MANAGER.getNORMAL_TO_REMOVE().stream().toArray(Sprite[]::new));
        SPRITE_MANAGER.clearNORMAL_TO_REMOVE();
        SPRITE_MANAGER.addToCURRENT_NORMAL(SPRITE_MANAGER.getNORMAL_TO_ADD().stream().toArray(Sprite[]::new));
        SPRITE_MANAGER.clearNORMAL_TO_ADD();
        
        SPRITE_MANAGER.getCURRENT_BULLETS().stream().forEach((sprite) -> {
            sprite.update();
        });
        SPRITE_MANAGER.removeFromCURRENT_BULLETS(SPRITE_MANAGER.getBULLETS_TO_REMOVE().stream().toArray(Sprite[]::new));
        SPRITE_MANAGER.clearBULLETS_TO_REMOVE();
        SPRITE_MANAGER.addToCURRENT_BULLETS(SPRITE_MANAGER.getBULLETS_TO_ADD().stream().toArray(Sprite[]::new));
        SPRITE_MANAGER.clearBULLETS_TO_ADD();
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
     * Empieza el TimeLine, que añade nuevos sprites al juego cada 5 segundos.
     */
    private void playTimeLine(){
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(5000), (ActionEvent e) -> {
            Sprite sprite;
            byte randomNumber = (byte)(Math.random() * 3);
            switch (randomNumber) {
                case 0:
                    sprite = new Diamond((Math.random() * (640 - 32 + 1) ), (Math.random() * (480 - 24 + 1)));
                    break;
                case 1:
                    sprite = new Ruby((Math.random() * (640 - 32 + 1)), (Math.random() * (480 - 32 + 1)));
                    break;
                default:
                    sprite = new YellowSapphire((Math.random() * (640 - 22 + 1)), (Math.random() * (480 - 21 + 1)));
                    break;
            }
            Main.getRoot().getChildren().add(sprite.getSpriteFrame());
            SPRITE_MANAGER.addToNORMAL_TO_ADD(sprite);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
