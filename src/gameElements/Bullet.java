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

import javafx.scene.image.Image;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

/**
 * Una bala que destruye otros <tt>sprites</tt>, salvo Octogonave.
 * @author Jorge Maldonado Ventura
 */
class Bullet extends MovingSprite{
    
    private static final Image BULLET_IMG = new Image("/bullet.png", 10, 13, true, false, true);
    private static final String SVG_PATH = "M 4,0 L 4,0 5,0 6,1 6,8 9,11 9,12 0,12 0,11 3,8 3,1 Z";
    private double xSpeed, ySpeed;
    
    Bullet(double xLocation, double yLocation, double xSpeed, double ySpeed) {
        super(SVG_PATH, xLocation, yLocation, BULLET_IMG);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        xPos = xLocation;
        yPos = yLocation;
    }

    @Override
    void update() {
        setXAndYPosition();
        move();
        checkCollision();
    }
    
    private void setXAndYPosition(){
        xPos += xSpeed;
        yPos += ySpeed;
    }
    
    /**
     * Mueve la bala, es decir, su <tt>spriteFrame</tt> y <tt>spriteBound</tt>.
     */
    private void move() {
        spriteFrame.setTranslateX(xPos);
        spriteFrame.setTranslateY(yPos);
        spriteBound.setTranslateX(xPos);
        spriteBound.setTranslateY(yPos);
    }
    
    /**
     * Comprueba si ha colisionado con alguno de los <i>sprites</i> presentes
     * en el <tt>ArrayList</tt> <tt>CURRENT_NORMAL</tt> de <tt>SpriteManager</tt> y realiza las acciones oportunas.
     */
    private void checkCollision() {
        if(isOutOfScreen()){
            Main.getMainMenu().getGame().getSpriteManager().addToBulletsToRemove(this);
            Main.getRoot().getChildren().remove(getSpriteFrame());
        }
        for(Sprite sprite: Main.getMainMenu().getGame().getSpriteManager().getCurrentNormal()){
            if(collide(sprite)){
                if(sprite instanceof MovingEnemy){
                    ((MovingEnemy)sprite).setDestroy(true);
                    Main.getMainMenu().getGame().getScore().updateScore(sprite);
                } else if(sprite instanceof Gem){
                    ((Gem)sprite).setDestroy(true);
                    /*Main.getMainMenu().getGame().getSpriteManager().addToNormalToRemove(sprite);
                    Main.getRoot().getChildren().remove(sprite.getSpriteFrame());*/
                } else{
                    Main.getMainMenu().getGame().getSpriteManager().addToNormalToRemove(sprite);
                    Main.getRoot().getChildren().remove(sprite.getSpriteFrame());
                }
                Main.getMainMenu().getGame().getSpriteManager().addToBulletsToRemove(this);
                Main.getRoot().getChildren().remove(getSpriteFrame());
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
}
