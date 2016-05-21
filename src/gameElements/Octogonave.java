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

import gameMenus.Config;
import gameMenus.GameOverMenu;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.SVGPath;
import javafx.scene.shape.Shape;

/**
 * El héroe del juego.
 * @author Jorge Maldonado Ventura 
 */
class Octogonave extends Sprite{
    
    private static final String SVG_PATH = "M 53,30 L 53,30 64,30 65,31 65,38 67,39 72,34 74,34 81,42 81,44 76,49 78,51 85,51 86,52 86,63 85,64 78,64 77,65 77,66 76,67 81,72 81,74 74,81 72,81 65,76 66,77 64,77 64,85 63,86 52,86 51,85 51,78 49,76 44,81 42,81 35,74 35,72 40,67 39,66 39,64 31,64 30,63 30,52 31,51 38,51 40,49 35,44 35,42 42,34 44,34 49,40 50,39 52,39 52,31 Z";
    private static final Image octoNaveStill = new Image("/octogonaveStill.png", 117, 117, true, false, true),
            octoNaveMov1 = new Image("/octogonaveMovingFire1.png", 117, 117, true, false, true),
            octoNaveMov2 = new Image("/octogonaveMovingFire2.png", 117, 117, true, false, true),
            octoNaveMov3 = new Image("/octogonaveMovingFire3.png", 117, 117, true, false, true),
            octoNaveHurtStill = new Image("/octogonaveHurtStill.png", 117, 117, true, false, true),
            octoNaveMovHurt1 = new Image("/octogonaveMovingFireHurt1.png", 117, 117, true, false, true),
            octoNaveMovHurt2 = new Image("octogonaveMovingFireHurt2.png", 117, 117, true, false, true),
            octoNaveMovHurt3 = new Image("octogonaveMovingFireHurt3.png", 117, 117, true, false, true);
    private boolean up, right, down, left, fireUp, fireRight, fireLeft, fireDown;
    private final byte RELOAD_RATE = 15; //
    private double velocity;
    private byte currentFrame, reloadCounter;
    private AudioClip shootSound, bonusSound, movingSpacecraft;
    
    /**
     * Esta constante influye en la velocidad en la que se produce un cambio de fotograma de la nave, se le resta
     * posteriormente la velocidad para que a más velocidad mayor sea el cambio.
     */
    private final short frameChangeRate;
    private int frameCounter;
    /**
     * Posición del <i>sprite</i> en el eje X. 
     */
    private double xPos;
    /**
     * Posición del <i>sprite</i> en el eje Y.
     */
    private double yPos;
    private byte lives;
    
    Octogonave(double xLocation, double yLocation) {
        super(SVG_PATH, xLocation, yLocation, octoNaveStill, octoNaveMov1, octoNaveMov2, octoNaveMov3);
        currentFrame = 1;
        reloadCounter = 6;
        velocity = 5;
        frameChangeRate = 10;
        xPos = xLocation;
        yPos = yLocation;
        lives = 2;
        shootSound = new AudioClip(this.getClass().getResource("/shoot.wav").toExternalForm());
        bonusSound = new AudioClip(this.getClass().getResource("/bonusSound.wav").toExternalForm());
        movingSpacecraft = new AudioClip(this.getClass().getResource("/movingSpacecraft.wav").toExternalForm());
        movingSpacecraft.setCycleCount(AudioClip.INDEFINITE);
    }

    public AudioClip getBonusSound() {
        return bonusSound;
    }

    @Override
    void update() {
        determineKeyPressed();
        determineKeyReleased();
        shoot();
        determineFrame();
        setXAndYPosition();
        move();
        checkCollision();
    }
    
    private void determineKeyPressed(){
        scene.setOnKeyPressed((KeyEvent event) -> 
            {
                if(Config.areSoundsOn() && !movingSpacecraft.isPlaying()){
                    movingSpacecraft.play();
                }
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
                    case W:
                        fireUp = true;
                        break;
                    case D:
                        fireRight = true;
                        break;
                    case S:
                        fireDown = true;
                        break;
                    case A:
                        fireLeft = true;
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
                    case W:
                        fireUp = false;
                        break;
                    case D:
                        fireRight = false;
                        break;
                    case S:
                        fireDown = false;
                        break;
                    case A:
                        fireLeft = false;
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
            if(Config.areSoundsOn() && movingSpacecraft.isPlaying()){
                movingSpacecraft.stop();
            }
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
    private void move() {
        spriteFrame.setTranslateX(xPos);
        spriteFrame.setTranslateY(yPos);
        spriteBound.setTranslateX(xPos);
        spriteBound.setTranslateY(yPos);
    }
    
    private void increaseSpeed(double pixelsPerMove){
        if(velocity <= 9){
            velocity += pixelsPerMove;
            movingSpacecraft.setVolume(velocity / 10);
        }
    }
    private void decreaseSpeed(double pixelsPerMove){
        if(velocity > 1){
            velocity -= pixelsPerMove;
            movingSpacecraft.setVolume(velocity / 10);
        }
    }
    
    /**
     * Comprueba si ha colisionado con alguno de los <i>sprites</i> presentes
     * en el ArrayList CURRENT_NORMAL de SpriteManager y realiza las acciones oportunas.
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
        

        for(Sprite sprite: Main.getMainMenu().getGame().getSpriteManager().getCurrentNormal()){            
            if(collide(sprite)){
                if(sprite instanceof Diamond || sprite instanceof Ruby || sprite instanceof YellowSapphire){
                    Main.getMainMenu().getGame().getSpriteManager().addToNormalToRemove(sprite);
                    Main.getRoot().getChildren().remove(sprite.getSpriteFrame());
                    Main.getMainMenu().getGame().getScore().updateScore(sprite);
                } else if(sprite instanceof Asteroid){
                    damage();
                    ((Asteroid) sprite).setDestroy(true);
                }
            }
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
                add(octoNaveHurtStill);
                add(octoNaveMovHurt1);
                add(octoNaveMovHurt2);
                add(octoNaveMovHurt3);
            }};
            this.setSpriteImages(hurtImages);
        } else if(lives == 0){
            Main.getMainMenu().getGame().endGame();
        }
    }
    
    /**
     * Comprueba si la nave ha colisionado con un <i>sprite</i> determinado.
     * @param sprite el <i>sprite</i> con el que desea comprobar si se ha colisionado
     * @return <tt>true</tt> si se ha producido una colisión; <tt>false</tt> en caso contrario.
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
     * Informa de si la nave está tocando el límite de la ventana o ha salido de ella.
     * @return <tt>true</tt> si ha contactado con el límite o está fuera; <tt>false</tt> si se encuentra dentro de
     * la ventana.
     */
    private boolean boundsLimitOrOutX(){
        return xPos <= 0 - 30 || xPos >= Main.getScene().getWidth() - 87;
    }
    private boolean boundsLimitOrOutY(){
        return yPos <= 0 - 30 || yPos >= Main.getScene().getHeight() - 87;
    }

    private void shoot() {
        reloadCounter++;
        if(reloadCounter >= RELOAD_RATE){
            if(fireUp || fireLeft || fireDown || fireRight){
                if(Config.areSoundsOn()){
                    shootSound.play();
                }
                reloadCounter = 0;
                Bullet bullet = null;
                if(fireUp && fireRight){
                    bullet = new Bullet(xPos + 78, yPos + 28);
                    bullet.setHorizontalVelocity(7);
                    bullet.setVerticalVelocity(-7);
                    bullet.getSpriteFrame().setRotate(45);
                    bullet.getSpriteBound().setRotate(45);
                } else if(fireDown && fireRight){
                    bullet = new Bullet(xPos + 78, yPos + 76);
                    bullet.setHorizontalVelocity(7);
                    bullet.setVerticalVelocity(7);
                    bullet.getSpriteFrame().setRotate(135);
                    bullet.getSpriteBound().setRotate(135);
                } else if(fireDown && fireLeft){
                    bullet = new Bullet(xPos + 30, yPos + 76);
                    bullet.setHorizontalVelocity(-7);
                    bullet.setVerticalVelocity(7);
                    bullet.getSpriteFrame().setRotate(225);
                    bullet.getSpriteBound().setRotate(225);
                } else if(fireLeft && fireUp){
                    bullet = new Bullet(xPos + 30, yPos + 27);
                    bullet.setHorizontalVelocity(-7);
                    bullet.setVerticalVelocity(-7);
                    bullet.getSpriteFrame().setRotate(315);
                    bullet.getSpriteBound().setRotate(315);
                } else if(fireUp){
                    bullet = new Bullet(xPos + 54, yPos + 17); //(xPos + la mitad de la anchura de octogonave - la mitad de la anchura de la bala, yPos + la transparencia de la imagen de octogonave - la altura de la bala.
                    bullet.setVerticalVelocity(-7);
                } else if(fireLeft){
                    bullet = new Bullet(xPos + 19, yPos + 52); //(xPos + la transparencia de la imagen de octogonave - la anchura de la bala, yPos + la mitad de la anchura de octogonave - la mitad de la altura de la bala).
                    bullet.setHorizontalVelocity(-7);
                    bullet.getSpriteFrame().setRotate(-90);
                    bullet.getSpriteBound().setRotate(-90);
                } else if(fireDown){
                    bullet = new Bullet(xPos + 54, yPos + 87); //(xPos + la mitad de la anchura de la nave - la mitad de la anchura de la bala, yPos + la anchura de Octogonave - la transparencia de la imagen de octogonave)
                    bullet.setVerticalVelocity(7);
                    bullet.getSpriteFrame().setRotate(180);
                    bullet.getSpriteBound().setRotate(180);
                } else if(fireRight){
                    bullet = new Bullet(xPos + 89, yPos + 52); //(xPos + la anchura de Octogonave - la transparencia de la imagen de octogonave, yPos)
                    bullet.setHorizontalVelocity(7);
                    bullet.getSpriteFrame().setRotate(90);
                    bullet.getSpriteBound().setRotate(90);
                } 
                Main.getMainMenu().getGame().getSpriteManager().addToBulletsToAdd(bullet);
                Main.getRoot().getChildren().add(bullet.getSpriteFrame());
            } else{
                reloadCounter--; //Para que no se pueda desbordar nunca la variable.
            }
        }
    }

}
