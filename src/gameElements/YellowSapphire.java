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
 * Un zafiro amarillo.
 * @author Jorge Maldonado Ventura
 */
class YellowSapphire extends Gem{
    
    private static final Image yellowSapphireImg = new Image("/images/sprites/yellowSapphire/yellowSapphire.png", 22, 21, true, false, true),
            yellowSapphireDestroyedImg1 = new Image("/images/sprites/yellowSapphire/yellowSapphireDestroyed1.png", 26, 35, true, false, true),
            yellowSapphireDestroyedImg2 = new Image("/images/sprites/yellowSapphire/yellowSapphireDestroyed2.png", 30, 29, true, false, true);
    private static final String SVG_PATH = "M 0,4 L 0,4 4,0 18,0 22,4 22,17 18,21 4,21 0,17 Z";
    private static final byte BONUS = 40;
    YellowSapphire(double xLocation, double yLocation){
        super(SVG_PATH, xLocation, yLocation, yellowSapphireImg, yellowSapphireDestroyedImg1, yellowSapphireDestroyedImg2);
    }

    public static byte getBONUS() {
        return BONUS;
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
