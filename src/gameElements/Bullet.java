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
 *
 * @author Jorge Maldonado Ventura
 */
class Bullet extends Sprite{
    private static final Image BULLET_IMG = new Image("/bullet.png", 10, 13, true, false, true);
    private static final String SVG_PATH = "M 4,0 L 4,0 5,0 6,1 6,8 9,11 9,12 0,12 0,11 3,8 3,1 Z";
    private double verticalVelocity;
    private double horizontalVelocity;
    /**
     * Posición del <i>sprite</i> en el eje X. 
     */
    private double xPos;
    /**
     * Posición del <i>sprite</i> en el eje Y.
     */
    private double yPos;
    
    Bullet(double xLocation, double yLocation) {
        super(SVG_PATH, xLocation, yLocation, BULLET_IMG);
        xPos = xLocation;
        yPos = yLocation;
    }

    void setVerticalVelocity(double verticalVelocity) {
        this.verticalVelocity = verticalVelocity;
    }

    void setHorizontalVelocity(double horizontalVelocity) {
        this.horizontalVelocity = horizontalVelocity;
    }

    @Override
    void update() {
        setXAndYPosition();
        move();
        checkCollision();
    }
    
    /**
     * Comprueba si la bala está en contacto con el borde del eje X de la ventana o ha salido de esta.
     * @return <tt>true</tt> si la bala ha salido del eje X de la ventana; <tt>false</tt> en caso contrario.
     */
    private boolean boundsLimitOrOutX(){
        return getSpriteFrame().getTranslateX() <= 0 - getSpriteFrame().getFitWidth() || getSpriteFrame().getTranslateX() >= Main.getScene().getWidth() - getSpriteFrame().getFitWidth();
    }
    
    /**
     * Comprueba si la bala está en contacto con el borde del eje Y de la ventana o ha salido de esta.
     * @return <tt>true</tt> si la bala ha salido del eje Y de la ventana; <tt>false</tt> en caso contrario.
     */
    private boolean boundsLimitOrOutY(){
        return getSpriteFrame().getTranslateY() <= 0 - getSpriteFrame().getFitHeight() || getSpriteFrame().getTranslateY() >= Main.getScene().getHeight() - getSpriteFrame().getFitHeight();
    }
    
    private void setXAndYPosition(){
        xPos += horizontalVelocity;
        yPos += verticalVelocity;
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
        if(boundsLimitOrOutY() || boundsLimitOrOutX()){
            Main.getMainMenu().getGame().getSpriteManager().addToBulletsToRemove(this);
            Main.getRoot().getChildren().remove(getSpriteFrame());
        }
        for(Sprite sprite: Main.getMainMenu().getGame().getSpriteManager().getCurrentNormal()){
            if(collide(sprite)){
                Main.getMainMenu().getGame().getSpriteManager().addToNormalToRemove(sprite);
                Main.getRoot().getChildren().remove(sprite.getSpriteFrame());
                Main.getMainMenu().getGame().getSpriteManager().addToBulletsToRemove(this);
                Main.getRoot().getChildren().remove(getSpriteFrame());
                Main.getMainMenu().getGame().getPlayScore().updateScore(sprite);
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
