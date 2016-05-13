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
    private static Image asteroidImage = new Image("/asteroid.png", 199, 205, true, false, true);
    private double horizontalVelocity;
    private double verticalVelocity;
    double xPos;
    double yPos;
    public Asteroid(String SVGData, double xLocation, double yLocation, double horizontalVelocity, double verticalVelocity) {
        super(SVGData, xLocation, yLocation, asteroidImage);
        this.horizontalVelocity = horizontalVelocity;
        this.verticalVelocity = verticalVelocity;
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
        xPos += verticalVelocity;
        yPos += horizontalVelocity;
    }
    
    private void move(){
        spriteFrame.setTranslateX(xPos);
        spriteFrame.setTranslateY(yPos);
    }
    
    private void checkCollision(){
        if(boundsOutY() || boundsOutX()){
            Main.getMainMenu().getGame().getSpriteManager().addToNORMAL_TO_REMOVE(this);
            Main.getRoot().getChildren().remove(getSpriteFrame());
            System.out.println("Destruido");
            System.out.println(getSpriteFrame().getTranslateX());
            System.out.println(getSpriteFrame().getTranslateY());
        }
    }
    
    /**
     * Comprueba si el asteroide ha salido de la ventana por el eje X.
     * @return <tt>true</tt> si el asteroide ha salido del eje X de la ventana; <tt>false</tt> en caso contrario.
     */
    private boolean boundsOutX(){
        return getSpriteFrame().getTranslateX() <= 0 - getSpriteFrame().getFitWidth() || getSpriteFrame().getTranslateX() >= Main.getScene().getWidth();
    }
    
    /**
     * Comprueba si el asteroide ha salido de la ventana por el eje Y.
     * @return <tt>true</tt> si el asteroide ha salido del eje Y de la ventana; <tt>false</tt> en caso contrario.
     */
    private boolean boundsOutY(){
        return getSpriteFrame().getTranslateY() <= 0 - getSpriteFrame().getFitHeight() || getSpriteFrame().getTranslateY() >= Main.getScene().getHeight();
    }
}
