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
 * Un diamante que muestra una animación.
 * @author Jorge Maldonado Ventura 
 */
class Diamond extends Sprite{
    private static final Image diamondImg1 = new Image("/diamond.png", 32, 24, true, false, true),
            diamondImg2 = new Image("/diamond2.png", 32, 24, true, false, true);
    private static final String SVG_PATH = "M 0,6 L 0,6 6,0 25,0 31,6 31,8 16,23 15,23 0,8 Z";
    private static final byte FRAME_CHANGE_TIME = 20;
    private static final byte BONUS = 15;
    private byte frameCounter, selectedFrame;
    private boolean changeFrame;
    
    Diamond(double xLocation, double yLocation) {
        super(SVG_PATH, xLocation, yLocation, diamondImg1, diamondImg2);
        selectedFrame = 0;
        changeFrame = false;
    }

    public static byte getBONUS() {
        return BONUS;
    }
    
    @Override
    void update() {
        setCurrentFrame();
    }
    
    private void setCurrentFrame(){
        frameCounter++;
        if(changeFrame){
            frameCounter = 0;
            if(selectedFrame == 0){
                spriteFrame.setImage(spriteImages.get(1));
                selectedFrame = 1;
            } else if(selectedFrame == 1){
                spriteFrame.setImage(spriteImages.get(0));
                selectedFrame = 0;
            }
            changeFrame = false;
        } else{
            if(frameCounter >= FRAME_CHANGE_TIME){
                changeFrame = true;
            }
        }
    }
}
