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

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.util.Duration;

/**
 * OVNI.
 * @author Jorge Maldonado Ventura
 */
public class UFO extends MovingEnemy{
    
    private static final String SVG_PATH = "M 32,0 L 32,0 21,8 2,19 31,33 64,20 Z";
    private static final byte BONUS = 7;
    private static Image UFOImage = new Image("/UFO.png", 65, 34, true, false, true);
    
    public UFO(double xLocation, double yLocation, double xSpeed, double ySpeed) {
        super(SVG_PATH, xLocation, yLocation, UFOImage);
        xPos = xLocation;
        yPos = yLocation;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        destroy = false;
        timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(70), (ActionEvent e) -> {
            checkCollision();  
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public static byte getBONUS() {
        return BONUS;
    }

    public Timeline getUFOTimeline() {
        return timeline;
    }

    @Override
    protected void destroy() {
        spriteBound.setContent("");
        Main.getMainMenu().getGame().getSpriteManager().addToNormalToRemove(this);
        Main.getRoot().getChildren().remove(this.getSpriteFrame());
    }
    
}
