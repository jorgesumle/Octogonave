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
package octogonave;

import javafx.scene.image.Image;

/**
 *
 * @author Jorge Maldonado Ventura
 */
public class Bullet extends Sprite{
    private static double velocity;
    public Bullet(String SVGData, double xLocation, double yLocation, Image... spriteImages) {
        super(SVGData, xLocation, yLocation, spriteImages);
        velocity = 9;
    }

    @Override
    public void update() {
        if(boundsLimitOrOutY() || boundsLimitOrOutX()){
            Octogonave.getSpriteManager().removeFromCurrentSprites(this);
            Octogonave.getRoot().getChildren().remove(getSpriteFrame());
        }
    }
    
    private boolean boundsLimitOrOutX(){
        return getSpriteFrame().getTranslateX() <= 0 - getSpriteFrame().getFitWidth() || getSpriteFrame().getTranslateX() >= Octogonave.getScene().getWidth() - getSpriteFrame().getFitWidth();
    }
    
    private boolean boundsLimitOrOutY(){
        return getSpriteFrame().getTranslateY() <= 0 - getSpriteFrame().getFitHeight() || getSpriteFrame().getTranslateY() >= Octogonave.getScene().getHeight() - getSpriteFrame().getFitHeight();
    }
    
}
