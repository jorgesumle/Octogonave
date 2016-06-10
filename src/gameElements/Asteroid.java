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
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;
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
    private boolean destroy;
    private Timeline asteroidTimeline;

    public Timeline getAsteroidTimeline() {
        return asteroidTimeline;
    }
    
    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }
    
    public Asteroid(double xLocation, double yLocation, double xVelocity, double yVelocity) {
        super(SVG_PATH, xLocation, yLocation, asteroidImg);
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
        xPos = xLocation;
        yPos = yLocation;
        setRandomRotation();
        rotationStage = spriteFrame.getRotate();
        destructionFrame = 0;
        destroy = false;
        ROTATION_VELOCITY = xVelocity + yVelocity;
        asteroidTimeline = new Timeline();
        boolean randomDirection = Math.random() < 0.5;
        asteroidTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(70), (ActionEvent e) -> {
            if(randomDirection) rotateRight();
            else rotateLeft();
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
    
    private void checkCollision(){
        if(boundsOutY() || boundsOutX()){
            Main.getMainMenu().getGame().getSpriteManager().addToNormalToRemove(this);
            Main.getRoot().getChildren().remove(getSpriteFrame());
        } else{
            for(Sprite sprite: Main.getMainMenu().getGame().getSpriteManager().getCurrentNormal()){            
                if(collide(sprite) && this != sprite){
                    destroy = true;
                    if(sprite instanceof Asteroid){
                        ((Asteroid) sprite).setDestroy(true);
                    } else if(sprite instanceof Gem){
                        ((Gem) sprite).setDestroy(true);
                    }
                    else{
                        Main.getMainMenu().getGame().getSpriteManager().addToNormalToRemove(sprite);
                        Main.getRoot().getChildren().remove(sprite.getSpriteFrame());
                    }
                }
            }
        }
    }
    
    private boolean collide(Sprite sprite){
        if(spriteFrame.getBoundsInParent().intersects(sprite.spriteFrame.getBoundsInParent())){
            Shape intersection = SVGPath.intersect(spriteBound, sprite.spriteBound);
            if(!intersection.getBoundsInLocal().isEmpty()){
                return true;
            }
        }
        return false;
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
