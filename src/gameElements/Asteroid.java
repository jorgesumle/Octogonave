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

/**
 *
 * @author Jorge Maldonado Ventura
 */
public class Asteroid extends Sprite{
    private static final String SVG_PATH = "M 18,1 L 18,1 29,0 51,7 54,19 56,23 54,28 55,37 47,49 47,52 39,58 36,55 21,55 20,54 11,47 8,47 6,45 6,41 3,32 0,28 10,6 16,4 Z";
    private static final byte BONUS = 6;
    private static Image asteroidImg = new Image("/asteroid.png", 57, 59, true, false, true);
    private double xVelocity;
    private double yVelocity;
    double xPos;
    double yPos;
    public Asteroid(double xLocation, double yLocation, double horizontalVelocity, double verticalVelocity) {
        super(SVG_PATH, xLocation, yLocation, asteroidImg);
        this.xVelocity = horizontalVelocity;
        this.yVelocity = verticalVelocity;
        xPos = xLocation;
        yPos = yLocation;
        setRandomRotation();
    }

    public static byte getBONUS() {
        return BONUS;
    }
    
    @Override
    void update() {
        setXAndYPosition();
        move();
        checkCollision();
    }
    
    private void setRandomRotation(){
        spriteFrame.setRotate(Math.random() * 360);
        spriteBound.setRotate(Math.random() * 360);
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
    
    private void checkCollision(){
        if(boundsOutY() || boundsOutX()){
            Main.getMainMenu().getGame().getSpriteManager().addToNormalToRemove(this);
            Main.getRoot().getChildren().remove(getSpriteFrame());
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
