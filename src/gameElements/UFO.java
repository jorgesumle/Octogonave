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

import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.util.Duration;

/**
 * OVNI.
 * @author Jorge Maldonado Ventura
 */
public class UFO extends MovingEnemy{
    
    private static final String SVG_PATH = "M 32,0 L 32,0 21,8 2,19 31,33 64,20 Z";
    private static final byte BONUS = 7;
    private static Image UFOImg = new Image("/images/sprites/UFO/UFO.png", 65, 34, true, false, true),
            UFODestroyedImg1 = new Image("/images/sprites/UFO/UFODestroyed1.png", 69, 36, true, false, true),
            UFODestroyedImg2 = new Image("/images/sprites/UFO/UFODestroyed2.png", 73, 38, true, false, true);
    
    public UFO(double xLocation, double yLocation, double speed) {
        super(SVG_PATH, xLocation, yLocation, UFOImg);
        xPos = xLocation;
        yPos = yLocation;
        this.xSpeed = speed;
        this.ySpeed = speed;
        setRandomRotation();
        destroy = false;
        destructionFrame = 0;
        createUFOTimeline();
    }

    public static byte getBONUS() {
        return BONUS;
    }

    public Timeline getUFOTimeline() {
        return timeline;
    }
    
    private double getxCenter(){
        return xPos + spriteFrame.getImage().getWidth() / 2;
    }
    
    private double getyCenter(){
        return yPos + spriteFrame.getImage().getHeight() / 2;
    }
            
    @Override
    protected void destroy() {
        spriteBound.setContent("");
        switch(destructionFrame){
            case 0:
                spriteFrame.setImage(UFODestroyedImg1);
                destructionFrame++;
                break;
            case 3:
                spriteFrame.setImage(UFODestroyedImg2);
                destructionFrame++;
                break;
            case 6:
                Main.getMainMenu().getGame().getSpriteManager().addToNormalToRemove(this);
                Main.getRoot().getChildren().remove(this.getSpriteFrame());
                timeline.stop();
                timeline = null;
                break;
            default:
                destructionFrame++;
                break;
        }
    }
    
    private void createUFOTimeline(){
        timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(70), (ActionEvent e) -> {
            rotateRandomly();
            checkCollision();  
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    private void rotateRandomly(){
        Random random = new Random();
        if(Math.random() > 5){
            rotationStage += random.nextDouble() * xSpeed * 2;
        } else{
            rotationStage -= random.nextDouble() * xSpeed * 2;
        }
        spriteFrame.setRotate(rotationStage);
        spriteBound.setRotate(rotationStage);
    }
    
    @Override
    protected void setXAndYPosition(){
        Octogonave octogonave = Main.getMainMenu().getGame().getOctogonave();
        if(!(octogonave.getxCenter() == getxCenter())){
            if(octogonave.getxCenter() > getxCenter()){
                if(getxCenter() + xSpeed > octogonave.getxCenter()){
                    xPos = octogonave.getxCenter() - spriteFrame.getImage().getWidth() / 2;
                }
                else{
                    xPos += xSpeed;
                }
            } else{
                if(getxCenter() - xSpeed < octogonave.getxCenter()){
                    xPos = octogonave.getxCenter() - spriteFrame.getImage().getWidth() / 2;
                } else{
                    xPos -= xSpeed;
                }
            }
        } 
        if(!(octogonave.getyCenter() == getyCenter())){
            if(octogonave.getyCenter() > getyCenter()){
                if(getyCenter() + ySpeed > octogonave.getyCenter()){
                    yPos = octogonave.getyCenter() - spriteFrame.getImage().getHeight() / 2;
                }
                else{
                    yPos += ySpeed;
                }
            } else{
                if(getyCenter() - ySpeed < octogonave.getyCenter()){
                    yPos = octogonave.getyCenter() - spriteFrame.getImage().getHeight() / 2;
                } else{
                    yPos -= ySpeed;
                }
            }
        }
        
    

    }
    
}
