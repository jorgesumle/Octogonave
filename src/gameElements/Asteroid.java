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
public class Asteroid extends Sprite{
    private static final String SVG_PATH = "M 18,1 L 18,1 29,0 51,7 54,19 56,23 54,28 55,37 47,49 47,52 39,58 36,55 21,55 20,54 11,47 8,47 6,45 6,41 3,32 0,28 10,6 16,4 Z";
    private static final byte BONUS = 5;
    private static Image asteroidImg = new Image("/asteroid.png", 57, 59, true, false, true),
            asteroidDestroyedImg1 = new Image("/asteroidDestroyed1.png", 70, 72, true, false, true),
            asteroidDestroyedImg2 = new Image("/asteroidDestroyed2.png", 70, 72, true, false, true),
            asteroidDestroyedImg3 = new Image("/asteroidDestroyed3.png", 70, 72, true, false, true);
    private final double ROTATION_VELOCITY;
    private double xVelocity, yVelocity, xPos, yPos, rotationStage;
    private byte destructionFrame;
    private boolean destroy;
    private Timeline asteroidTimeline;

    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }
    
    public Asteroid(double xLocation, double yLocation, double horizontalVelocity, double verticalVelocity) {
        super(SVG_PATH, xLocation, yLocation, asteroidImg);
        this.xVelocity = horizontalVelocity;
        this.yVelocity = verticalVelocity;
        xPos = xLocation;
        yPos = yLocation;
        setRandomRotation();
        rotationStage = spriteFrame.getRotate();
        destructionFrame = 0;
        destroy = false;
        ROTATION_VELOCITY = horizontalVelocity + verticalVelocity;
        asteroidTimeline = new Timeline();
        asteroidTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(70), (ActionEvent e) -> {
            rotate();
            checkCollision();
        }));
        asteroidTimeline.setCycleCount(Animation.INDEFINITE);
        asteroidTimeline.play();
    }

    public static byte getBONUS() {
        return BONUS;
    }
    
    @Override
    void update() {
        if(destroy){
            destroy();
        }
        setXAndYPosition();
        move();
        //checkCollision();
    }
    
    private void setRandomRotation(){
        double randomRotation = (Math.random() * 360 + 1);
        spriteFrame.setRotate(randomRotation);
        spriteBound.setRotate(randomRotation);
    }
    
    private void setXAndYPosition(){
        xPos += xVelocity;
        yPos += yVelocity;
    }
    
    private void move(){
        spriteFrame.setTranslateX(xPos);
        spriteFrame.setTranslateY(yPos);
        spriteBound.setTranslateX(xPos);
        spriteBound.setTranslateY(yPos);
    }
    
    private void rotate(){
        rotationStage += ROTATION_VELOCITY;
        if(rotationStage == 360){
            rotationStage = 0;
        }
        spriteFrame.setRotate(rotationStage);
        spriteBound.setRotate(rotationStage);
    }
    
    private void checkCollision(){
        if(boundsOutY() || boundsOutX()){
            Main.getMainMenu().getGame().getSpriteManager().addToNormalToRemove(this);
            Main.getRoot().getChildren().remove(getSpriteFrame());
        }
    }
    
    /**
     * Destruye el asteroide. La destrucción consiste en la eliminación del detector
     * de colisiones, una animación y la eliminación del objeto del bucle del juego y 
     * de la pantalla.
     */
    void destroy(){
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
                asteroidTimeline.stop();
                asteroidTimeline = null;
                break;
            default:
                destructionFrame++;
                break;
        }
    }
    
    /**
     * Comprueba si el asteroide ha salido de la ventana por el eje X.
     * @return <tt>true</tt> si el asteroide ha salido del eje X de la ventana; <tt>false</tt> en caso contrario.
     */
    private boolean boundsOutX(){
        return getSpriteFrame().getTranslateX() <= 0 - 57 || getSpriteFrame().getTranslateX() >= Main.getScene().getWidth();
    }
    
    /**
     * Comprueba si el asteroide ha salido de la ventana por el eje Y.
     * @return <tt>true</tt> si el asteroide ha salido del eje Y de la ventana; <tt>false</tt> en caso contrario.
     */
    private boolean boundsOutY(){
        return getSpriteFrame().getTranslateY() <= 0 - 59 || getSpriteFrame().getTranslateY() >= Main.getScene().getHeight();
    }
}
