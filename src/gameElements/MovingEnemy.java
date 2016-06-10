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

import javafx.animation.Timeline;
import javafx.scene.image.Image;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

/**
 *
 * @author Jorge Maldonado Ventura
 */
public abstract class MovingEnemy extends Sprite {
    protected double xPos, yPos, xSpeed, ySpeed, rotationStage;
    protected boolean destroy;
    protected Timeline timeline;
    protected byte destructionFrame;
    
    public MovingEnemy(String SVGData, double xLocation, double yLocation, Image... spriteImages) {
        super(SVGData, xLocation, yLocation, spriteImages);
    }

    public Timeline getTimeline() {
        return timeline;
    }
    
    public void setDestroy(boolean destroy) {
        this.destroy = destroy;
    }

    @Override
    void update() {
        if(destroy){
            destroy();
        }
        setXAndYPosition();
        move();
    }
    
    /**
     * Destruye el enemigo. La destrucción consiste en la eliminación del detector
     * de colisiones, una animación y la eliminación del objeto del bucle del juego y 
     * de la pantalla.
     */
    protected abstract void destroy();
    
    protected void checkCollision(){
        if(isOutOfScreen()){
            Main.getMainMenu().getGame().getSpriteManager().addToNormalToRemove(this);
            Main.getRoot().getChildren().remove(getSpriteFrame());
        } else{
            for(Sprite sprite: Main.getMainMenu().getGame().getSpriteManager().getCurrentNormal()){            
                if(collide(sprite) && this != sprite){
                    destroy = true;
                    if(sprite instanceof MovingEnemy){
                        ((MovingEnemy) sprite).setDestroy(true);
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
    
    protected boolean isOutOfScreen(){
        return xPos <= 0 - spriteFrame.getImage().getWidth() || 
                xPos >= Main.getScene().getWidth() ||
                yPos <= 0 - spriteFrame.getImage().getHeight() ||
                yPos >= Main.getScene().getHeight();
    }
    
    protected boolean collide(Sprite sprite){
        if(spriteFrame.getBoundsInParent().intersects(sprite.spriteFrame.getBoundsInParent())){
            Shape intersection = SVGPath.intersect(spriteBound, sprite.spriteBound);
            if(!intersection.getBoundsInLocal().isEmpty()){
                return true;
            }
        }
        return false;
    }
    
    protected void setXAndYPosition(){
        xPos += xSpeed;
        yPos += ySpeed;
    }
    
    protected void move(){
        spriteFrame.setTranslateX(xPos);
        spriteFrame.setTranslateY(yPos);
        spriteBound.setTranslateX(xPos);
        spriteBound.setTranslateY(yPos);
    }
    
    protected void setRandomRotation(){
        rotationStage = (Math.random() * 360 + 1);
        spriteFrame.setRotate(rotationStage);
        spriteBound.setRotate(rotationStage);
    }
}
