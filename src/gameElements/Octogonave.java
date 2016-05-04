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

import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

/**
 * El héroe del juego.
 * @author Jorge Maldonado Ventura 
 */
public class Octogonave extends Sprite{
    private static final String SVG_PATH = "M 53,30 L 53,30 64,30 65,31 65,38 67,39 72,34 74,34 81,42 81,44 76,49 78,51 85,51 86,52 86,63 85,64 78,64 77,65 77,66 76,67 81,72 81,74 74,81 72,81 65,76 66,77 64,77 64,85 63,86 52,86 51,85 51,78 49,76 44,81 42,81 35,74 35,72 40,67 39,66 39,64 31,64 30,63 30,52 31,51 38,51 40,49 35,44 35,42 42,34 44,34 49,40 50,39 52,39 52,31 Z";
    private static final Image OCTO_NAVE_STILL = new Image("/octogonaveStill.png", 117, 117, true, false, true),
            OCTO_NAVE_MOV_1 = new Image("/octogonaveMovingFire1.png", 117, 117, true, false, true),
            OCTO_NAVE_MOV_2 = new Image("/octogonaveMovingFire2.png", 117, 117, true, false, true),
            OCTO_NAVE_MOV_3 = new Image("/octogonaveMovingFire3.png", 117, 117, true, false, true),
            OCTO_NAVE_HURT_STILL = new Image("/octogonaveHurtStill.png", 117, 117, true, false, true),
            OCTO_NAVE_MOV_HURT_1 = new Image("/octogonaveMovingFireHurt1.png", 117, 117, true, false, true),
            OCTO_NAVE_MOV_HURT_2 = new Image("octogonaveMovingFireHurt2.png", 117, 117, true, false, true),
            OCTO_NAVE_MOV_HURT_3 = new Image("octogonaveMovingFireHurt3.png", 117, 117, true, false, true);
    private boolean up;
    private boolean right;
    private boolean down;
    private boolean left;
    private double velocity;
    private byte currentFrame;
    /**
     * Esta constante influye en la velocidad en la que se produce un cambio de fotograma de la nave, se le resta
     * posteriormente la velocidad para que a más velocidad mayor sea el cambio.
     */
    private final short frameChangeRate;
    private int frameCounter;
    /**
     * Posición del <i>sprite</i> en el eje X. 
     */
    protected double xPos;
    /**
     * Posición del <i>sprite</i> en el eje Y.
     */
    protected double yPos;
    private byte lives;
    
    public Octogonave(double xLocation, double yLocation) {
        super(SVG_PATH, xLocation, yLocation, OCTO_NAVE_STILL, OCTO_NAVE_MOV_1, OCTO_NAVE_MOV_2, OCTO_NAVE_MOV_3);
        currentFrame = 1;
        velocity = 5;
        frameChangeRate = 10;
        xPos = xLocation;
        yPos = yLocation;
        lives = 2;
    }

    @Override
    public void update() {
        determineKeyPressed();
        determineKeyReleased();
        determineFrame();
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
    
    /**
     * Determina el fotograma que se mostrará según las teclas pulsadas. Por ejemplo, si se pulsa la tecla
     * de arriba, se mostrará el fotograma de la nave moviéndose en esa dirección.
     */
    private void determineFrame(){
        frameCounter++;
        if(frameCounter >= (frameChangeRate - velocity)){
            frameCounter = 0;
            currentFrame++;
            if(currentFrame > 3){
                currentFrame = 1;
            }
        }
        
        if(left && up){
            spriteFrame.setImage(spriteImages.get(currentFrame));
            spriteFrame.setRotate(-45);
        } else if(up && right){
            spriteFrame.setImage(spriteImages.get(currentFrame));
            spriteFrame.setRotate(45);
        } else if(right && down){
            spriteFrame.setImage(spriteImages.get(currentFrame));
            spriteFrame.setRotate(135);
        } else if(down && left){
            spriteFrame.setImage(spriteImages.get(currentFrame));
            spriteFrame.setRotate(-135);
        } else if(left){
            spriteFrame.setImage(spriteImages.get(currentFrame));
            spriteFrame.setRotate(-90);
        } else if(up){
            spriteFrame.setImage(spriteImages.get(currentFrame));
            spriteFrame.setRotate(0);
        } else if(right){
            spriteFrame.setImage(spriteImages.get(currentFrame));
            spriteFrame.setRotate(90);
        } else if(down){
            spriteFrame.setImage(spriteImages.get(currentFrame));
            spriteFrame.setRotate(180);
        } else{
            spriteFrame.setImage(spriteImages.get(0));
        }
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
     * Mueve la nave, es decir, su spriteFrame y spriteBound.
     */
    private void moveSpaceCraft() {
        spriteFrame.setTranslateX(xPos);
        spriteFrame.setTranslateY(yPos);
        spriteBound.setTranslateX(xPos);
        spriteBound.setTranslateY(yPos);
    }
    
    private void increaseSpeed(double pixelsPerMove){
        velocity += pixelsPerMove;
    }
    private void decreaseSpeed(double pixelsPerMove){
        if(!(velocity <= 1)){
            velocity -= pixelsPerMove;
        }
    }
    
    /**
     * Comprueba si la nave ha colisiona con alguno de los <i>sprites</i> presentes
     * en el ArrayList CURRENT_SPRITES de SpriteManager.
     */
    private void checkCollision() {
        if(boundsLimitOrOutY()){
            damage();
            if(yPos <= 0 - 30){
                yPos = -28;
            } else{
                yPos = Main.getScene().getHeight() - 89; 
            }
            velocity = 0.01;
        } else if(boundsLimitOrOutX()){
           damage();
            if(xPos <= 0 - 30){
                xPos = -28;
            } else{
                xPos = Main.getScene().getWidth() - 89; 
            }
            velocity = 0.01;
        }
        
        try{
            for(Sprite sprite: Main.getSpriteManager().getCURRENT_SPRITES()){            
                if(collide(sprite)){
                    Main.getSpriteManager().removeFromCurrentSprites(sprite);
                    updateScore(sprite);
                    Main.getRoot().getChildren().remove(sprite.getSpriteFrame());
                }
            }
        } catch(Exception e){
            //Este es un fallo no controlado correctamente, arréglalo luego.
        }
    }
    
    /**
     * Le quita una vida a la nave. A medida que la nave pierde vidas, su aspecto
     * cambia. Si la nave se queda sin vidas acaba la partida.
     */
    private void damage(){
        lives--;
        if(lives == 1){
            List<Image> hurtImages = new ArrayList<Image>(){{
                add(OCTO_NAVE_HURT_STILL);
                add(OCTO_NAVE_MOV_HURT_1);
                add(OCTO_NAVE_MOV_HURT_2);
                add(OCTO_NAVE_MOV_HURT_3);
            }};
            this.setSpriteImages(hurtImages);
        } else if(lives == 0){
            System.exit(0);
        }
    }
    
    /**
     * Comprueba si la nave ha colisionado con un <i>sprite</i> determinado.
     * @param sprite el <i>sprite</i> con el que desea comprobar si se ha colisionado
     * @return true si se ha producido una colisión y false en caso contrario.
     */
    private boolean collide(Sprite sprite){
        if(spriteFrame.getBoundsInParent().intersects(sprite.spriteFrame.getBoundsInParent())){
            Shape intersection = SVGPath.intersect(spriteBound, sprite.spriteBound);
            if(!intersection.getBoundsInLocal().isEmpty()){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Actualiza la puntuación del juego. Cada objeto que recoge la nave tiene
     * una puntuación diferente.
     * @param sprite el objeto que recoge la nave.
     */
    private void updateScore(Sprite sprite) {
        Score playScore = Main.getPlayScore();
        if(sprite instanceof Diamond){
            playScore.increaseScore(3);
        } else if(sprite instanceof Ruby){
            playScore.increaseScore(2);
        } else if(sprite instanceof YellowSapphire){
            playScore.increaseScore(1);
        }
        Sounds.playBonusSound();
        playScore.updateScoreText();
    }
    
    /**
     * Informa de si la nave está tocando el límite de la ventana o ha salido de ella.
     * @return true si ha contactado con el límite o está fuera y false si se encuentra dentro de
     * la ventana.
     */
    private boolean boundsLimitOrOutX(){
        return xPos <= 0 - 30 || xPos >= Main.getScene().getWidth() - 87;
    }
    private boolean boundsLimitOrOutY(){
        return yPos <= 0 - 30 || yPos >= Main.getScene().getHeight() - 87;
    }

}