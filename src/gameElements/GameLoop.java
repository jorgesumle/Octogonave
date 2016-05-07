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
import java.util.ArrayList;
import java.util.Arrays;
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
public class GameLoop extends AnimationTimer{
    private final Octogonave octogonave;
    private final SpriteManager SPRITE_MANAGER;
    private ArrayList<Sprite> spritesToRemove;
    private ArrayList<Sprite> spritesToAdd;
    public GameLoop(Octogonave octogonave, SpriteManager spriteManager){
        this.octogonave = octogonave;
        this.SPRITE_MANAGER = spriteManager;
        spritesToRemove = new ArrayList();
        spritesToAdd = new ArrayList();
        playTimeLine();
        if(ConfigMenu.isMusicOn()){
            Sounds.playMusic();
        }
    }
    
    protected void addToSpritesToRemove(Sprite sprite){
        spritesToRemove.add(sprite);
    }
    
    protected void addToSpritesToAdd(Sprite sprite){
        spritesToAdd.add(sprite);
    }
    
    /**
     * Este código se ejecuta cada fotograma mientras el AnimationTimer este
     * activo.
     * @param now El registro del pulso (fotograma) actual en nanosegundos. 
     * Este valor es el mismo para todos losl AnimationTimers llamados en el mismo pulso. 
     */
    @Override
    public void handle(long now) {
        octogonave.update(); 
        SPRITE_MANAGER.getCURRENT_SPRITES().stream().forEach((sprite) -> {
            sprite.update();
        });
        SPRITE_MANAGER.removeFromCurrentSprites(spritesToRemove.stream().toArray(Sprite[]::new));
        SPRITE_MANAGER.addToCurrentSprites(spritesToAdd.stream().toArray(Sprite[]::new));
        spritesToRemove.clear();
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
    public void playTimeLine(){
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
            SPRITE_MANAGER.addToCurrentSprites(sprite);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
