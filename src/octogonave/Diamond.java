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
public class Diamond extends Gem{
    private final byte FRAME_CHANGE_TIME;
    private byte frameCounter;
    private boolean changeFrame;
    private byte selectedFrame;
    
    public Diamond(String SVGData, double xLocation, double yLocation, Image... spriteImages) {
        super(SVGData, xLocation, yLocation, spriteImages);
        FRAME_CHANGE_TIME = 20;
        selectedFrame = 0;
        changeFrame = false;
    }

    @Override
    public void update() {
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
