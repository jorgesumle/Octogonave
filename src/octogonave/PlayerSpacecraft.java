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

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

/**
 * El héroe del juego.
 * @author Jorge Maldonado Ventura 
 */
public class PlayerSpacecraft extends Sprite{
    private boolean up;
    private boolean right;
    private boolean down;
    private boolean left;
    private double velocity = 4;
    
    public PlayerSpacecraft(Scene scene, String SVGData, double xLocation, double yLocation, Image... spriteImages) {
        super(scene, SVGData, xLocation, yLocation, spriteImages);
    }

    @Override
    public void update() {
        determineKeyPressed();
        setXAndYPosition();
        moveSpaceCraft();
    }
    private void determineKeyPressed(){
        scene.setOnKeyPressed((KeyEvent event) -> 
            {
                switch(event.getCode()){
                    case UP:
                        up = true;
                        break;
                    case RIGHT:
                        right = true;
                        break;
                    case DOWN:
                        down = true;
                        break;
                    case LEFT:
                        left = true;
                        break;
                }
            }
        );
    }
    private void setXAndYPosition(){
        if(up){
            yPos -= velocity;
        } if(right){
            xPos += velocity;
        } if(left){
            xPos -= velocity;
        } if(down){
            yPos += velocity;
        }
    }

    private void moveSpaceCraft() {
        spriteFrame.setTranslateX(xPos);
        spriteFrame.setTranslateY(yPos);
    }

}