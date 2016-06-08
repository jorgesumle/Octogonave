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
 * OVNI.
 * @author Jorge Maldonado Ventura
 */
public class UFO extends Sprite{
    
    private static final String SVG_PATH = "";
    private static final byte BONUS = 7;
    private static Image UFOImage = new Image("/UFO.png", 65, 40, true, false, true);
    private double velocity, xPos, yPos;
    
    public UFO(double xLocation, double yLocation, double velocity) {
        super(SVG_PATH, xLocation, yLocation, UFOImage);
        xPos = xLocation;
        yPos = yLocation;
        this.velocity = velocity;
        
    }

    @Override
    void update() {
        setXAndYPosition();
        move();
    }
    
    private void setXAndYPosition(){
        xPos += velocity;
        yPos += velocity;
    }
    
    private void move(){
        spriteFrame.setTranslateX(xPos);
        spriteFrame.setTranslateY(yPos);
        spriteBound.setTranslateX(xPos);
        spriteBound.setTranslateY(yPos);
    }
    
}
