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
 *
 * @author Jorge Maldonado Ventura
 */
class Asteroid extends MovingEnemy{
    
    private static final String SVG_PATH = "M 18,1 L 18,1 29,0 51,7 54,19 56,23 54,28 55,37 47,49 47,52 39,58 36,55 21,55 20,54 11,47 8,47 6,45 6,41 3,32 0,28 10,6 16,4 Z";
    private static final byte BONUS = 5;
    private static Image asteroidImg = new Image("/asteroid.png", 57, 59, true, false, true),
            asteroidDestroyedImg1 = new Image("/asteroidDestroyed1.png", 70, 72, true, false, true),
            asteroidDestroyedImg2 = new Image("/asteroidDestroyed2.png", 70, 72, true, false, true),
            asteroidDestroyedImg3 = new Image("/asteroidDestroyed3.png", 70, 72, true, false, true);
    private final double ROTATION_VELOCITY;
    private double rotationStage;
    private byte destructionFrame;
    
    public Asteroid(double xLocation, double yLocation, double xVelocity, double yVelocity) {
        super(SVG_PATH, xLocation, yLocation, asteroidImg);
        this.xSpeed = xVelocity;
        this.ySpeed = yVelocity;
        xPos = xLocation;
        yPos = yLocation;
        setRandomRotation();
        rotationStage = spriteFrame.getRotate();
        destructionFrame = 0;
        destroy = false;
        ROTATION_VELOCITY = xVelocity + yVelocity;
        timeline = new Timeline();
        boolean randomDirection = Math.random() < 0.5;
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(70), (ActionEvent e) -> {
            if(randomDirection) rotateRight();
            else rotateLeft();
            checkCollision();  
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public static byte getBONUS() {
        return BONUS;
    }
    
    private void setRandomRotation(){
        double randomRotation = (Math.random() * 360 + 1);
        spriteFrame.setRotate(randomRotation);
        spriteBound.setRotate(randomRotation);
    }
    
    private void rotateRight(){
        rotationStage += ROTATION_VELOCITY;
        if(rotationStage == 360){
            rotationStage = 0;
        }
        spriteFrame.setRotate(rotationStage);
        spriteBound.setRotate(rotationStage);
    }
    
    private void rotateLeft(){
        rotationStage -= ROTATION_VELOCITY;
        if(rotationStage == -360){
            rotationStage = 0;
        }
        spriteFrame.setRotate(rotationStage);
        spriteBound.setRotate(rotationStage);
    }
    
    @Override
    protected void destroy(){
        spriteBound.setContent("");
        switch(destructionFrame){
            case 0:
                spriteFrame.setImage(asteroidDestroyedImg1);
                destructionFrame++;
                break;
            case 3:
                spriteFrame.setImage(asteroidDestroyedImg2);
                destructionFrame++;
                break;
            case 6: 
                spriteFrame.setImage(asteroidDestroyedImg3);
                destructionFrame++;
                break;
            case 9:
                Main.getMainMenu().getGame().getSpriteManager().addToNormalToRemove(this);
                Main.getRoot().getChildren().remove(this.getSpriteFrame());
                timeline.stop();
                timeline = null;
                break;
            default:
                destructionFrame++;
                break;
        }
    }

}
