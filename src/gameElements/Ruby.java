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
 * Un rubí.
 * @author Jorge Maldonado Ventura 
 */
class Ruby extends Gem{
    
    private static final Image rubyImg = new Image("/ruby.png", 32, 32, true, false, true),
            rubyDestroyedImg1 = new Image("/rubyDestroyed1.png", 36, 36, true, false, true),
            rubyDestroyedImg2 = new Image("/rubyDestroyed2.png", 40, 40, true, false, true);
    private static final String SVG_PATH = "M 14,0 L 14,0 17,0 31,14 31,16 16,31 15,31 0,16 0,14 Z";
    
    Ruby(double xLocation, double yLocation) {
        super(SVG_PATH, xLocation, yLocation, rubyImg, rubyDestroyedImg1, rubyDestroyedImg2);
        bonus = 45;
    }

    @Override
    protected void update() {
        super.update();
    }

    @Override
    void destroy() {
        spriteBound.setContent("");
        switch(destructionFrame){
            case 0:
                spriteFrame.setImage(spriteImages.get(1));
                destructionFrame++;
                break;
            case 3:
                spriteFrame.setImage(spriteImages.get(2));
                destructionFrame++;
                break;
            case 6:
                Main.getMainMenu().getGame().getSpriteManager().addToNormalToRemove(this);
                Main.getRoot().getChildren().remove(this.getSpriteFrame());
                break;
            default:
                destructionFrame++;
                break;
        }
    }
    
}
