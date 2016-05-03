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
public class Bullet extends Sprite{
    private static final Image BULLET_IMG = new Image("/bullet.png", 10, 13, true, false, true);
    private static final String SVG_PATH = "M 4,0 L 4,0 5,0 6,1 6,8 9,11 9,12 0,12 0,11 3,8 3,1 Z";
    private static double velocity;
    public Bullet(double xLocation, double yLocation) {
        super(SVG_PATH, xLocation, yLocation, BULLET_IMG);
        velocity = 9;
    }

    @Override
    public void update() {
        if(boundsLimitOrOutY() || boundsLimitOrOutX()){
            Main.getSpriteManager().removeFromCurrentSprites(this);
            Main.getRoot().getChildren().remove(getSpriteFrame());
        }
    }
    
    private boolean boundsLimitOrOutX(){
        return getSpriteFrame().getTranslateX() <= 0 - getSpriteFrame().getFitWidth() || getSpriteFrame().getTranslateX() >= Main.getScene().getWidth() - getSpriteFrame().getFitWidth();
    }
    
    private boolean boundsLimitOrOutY(){
        return getSpriteFrame().getTranslateY() <= 0 - getSpriteFrame().getFitHeight() || getSpriteFrame().getTranslateY() >= Main.getScene().getHeight() - getSpriteFrame().getFitHeight();
    }
    
}
