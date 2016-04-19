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
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

/**
 * El héroe del juego.
 * @author Jorge Maldonado Ventura 
 */
public class PlayerSpacecraft extends Sprite{
    private boolean up;
    private boolean right;
    private boolean down;
    private boolean left;
    private double velocity = 5;
    
    public PlayerSpacecraft(Octogonave octogonave, String SVGData, double xLocation, double yLocation, Image... spriteImages) {
        super(octogonave, SVGData, xLocation, yLocation, spriteImages);
    }

    @Override
    public void update() {
        determineKeyPressed();
        determineKeyReleased();
        setXAndYPosition();
        moveSpaceCraft();
        checkCollision();
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
                    case Z:
                        decreaseSpeed(1);
                        break;
                    case X:
                        increaseSpeed(1);
                        break;
                }
            }
        );
    }
    private void determineKeyReleased(){
        scene.setOnKeyReleased((KeyEvent event) -> 
            {
                switch(event.getCode()){
                    case UP:
                        up = false;
                        break;
                    case RIGHT:
                        right = false;
                        break;
                    case DOWN:
                        down = false;
                        break;
                    case LEFT:
                        left = false;
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
    
    /**
     * Mueve la nave y las coordenadas de su SVGPath.
     */
    private void moveSpaceCraft() {
        spriteFrame.setTranslateX(xPos);
        spriteFrame.setTranslateY(yPos);
        Octogonave.getOctogonave().spriteBound.setTranslateX(xPos);
        Octogonave.getOctogonave().spriteBound.setTranslateY(yPos);
    }
    
    private void increaseSpeed(double pixelsPerMove){
        velocity += pixelsPerMove;
    }
    private void decreaseSpeed(double pixelsPerMove){
        if(!(velocity <= 0)){
            velocity -= pixelsPerMove;
        }
    }
    
    /**
     * Comprueba si la nave ha colisiona con alguno de los <i>sprites</i> presentes
     * en el ArrayList CURRENT_SPRITES de SpriteManager.
     */
    private void checkCollision() {
        
        for(short i = 0; i < Octogonave.getSpriteManager().getCURRENT_SPRITES().size(); i++){
            Sprite sprite = Octogonave.getSpriteManager().getCURRENT_SPRITES().get(i);
            if(collide(sprite)){
                Octogonave.getSpriteManager().removeFromCurrentSprites(sprite);
                updateScore(sprite);
                Octogonave.getRoot().getChildren().remove(sprite.getSpriteFrame());
                Octogonave.getSpriteManager().resetCurrentSprites();
            }
        }
    }
    
    /**
     * Comprueba si la nave ha colisionado con un <i>sprite</i> determinado.
     * @param sprite el <i>sprite</i> con el que desea comprobar si se ha colisionado
     * @return true si se ha producido una colisión y false en caso contrario.
     */
    private boolean collide(Sprite sprite){
        if(Octogonave.getOctogonave().spriteFrame.getBoundsInParent().intersects(sprite.spriteFrame.getBoundsInParent())){
            Shape intersection = SVGPath.intersect(Octogonave.getOctogonave().spriteBound, sprite.spriteBound);
            if(!intersection.getBoundsInLocal().isEmpty()){
                return true;
            }
        }
        return false;
    }

    private void updateScore(Sprite sprite) {
        if(sprite instanceof Gem){
            Octogonave.setScore(Octogonave.getScore() + 1);
            Octogonave.updateScoreText();
        }
    }

}