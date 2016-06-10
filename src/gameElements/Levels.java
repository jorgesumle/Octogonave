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
import javafx.util.Duration;

/**
 *
 * @author Jorge Maldonado Ventura
 */
public class Levels {
    
    private static Random random = new Random();
    private static Timeline arcadeModeTimeline;
    private static Timeline anyLevelTimeline;

    public static Timeline getArcadeModeTimeline() {
        return arcadeModeTimeline;
    }

    public static Timeline getAnyLevelTimeline() {
        return anyLevelTimeline;
    }
    
    /**
     * Empieza el TimeLine, que añade nuevos sprites al juego cada cierto tiempo.
     */
    public static void arcadeMode(){
        arcadeModeTimeline = new Timeline();
        arcadeModeTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(333), (ActionEvent e) -> {
            if(Main.getMainMenu().getGame().getScore().getScore() < 400){
                if(random.nextInt(10) == 0){
                    createGem();
                    createBonus();
                }
                createUfo((byte)4);
            } else if(Main.getMainMenu().getGame().getScore().getScore() < 800){
                createAsteroid((byte)4);
                createAsteroid((byte)3);
                if(random.nextInt(114) == 0){
                    createGem();
                    createBonus();
                }
            } else if(Main.getMainMenu().getGame().getScore().getScore() < 1_200){
                createAsteroid((byte)4);
                createUfo((byte)4);
                if(random.nextInt(60) == 0){
                    createGem();
                    createBonus();
                }
            } else if(Main.getMainMenu().getGame().getScore().getScore() < 2_000){
                createAsteroid((byte)4);
                createAsteroid((byte)4);
                createAsteroid((byte)4);
                if(random.nextInt(48) == 0){
                    createGem();
                    createBonus();
                }
            } else if(Main.getMainMenu().getGame().getScore().getScore() < 2_100){
                createGem();
                createBonus();
            }
            else{
                createAsteroid((byte)5);
                createAsteroid((byte)5);
                createAsteroid((byte)5);
                if(random.nextInt(43) == 0){
                    createGem();
                    createBonus();
                }
            }
        }));
        arcadeModeTimeline.setCycleCount(Animation.INDEFINITE);
        arcadeModeTimeline.play();
    }
    
    public static void level1(){
        anyLevelTimeline = new Timeline();
        anyLevelTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(444), (ActionEvent e) -> {
            createAsteroid((byte)3);
            if(random.nextInt(20) == 0){
                createGem();
            } else if(random.nextInt(15) == 0){
                createBonus();
            }
        }));
        anyLevelTimeline.setCycleCount(65);
        anyLevelTimeline.setOnFinished(e -> 
            {
                System.out.println("Nivel 1 completado.");
                level2();
            });
        anyLevelTimeline.play();
    }
    
    public static void level2(){
        anyLevelTimeline = new Timeline();
        anyLevelTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(400), (ActionEvent e) -> {
            createAsteroid((byte)4);
            createAsteroid((byte)2);
            if(random.nextInt(40) == 0){
                createGem();
            } else if(random.nextInt(30) == 0){
                createBonus();
            }
        }));
        anyLevelTimeline.setCycleCount(100);
        anyLevelTimeline.setOnFinished(e -> 
            {
                System.out.println("Nivel 2 completado.");
            });
        anyLevelTimeline.play();
    }
    
    /**
     * Crea una gema en una posición aleatoria.
     */
    private static void createGem(){
        Sprite sprite;
        switch (random.nextInt(3)) {
            case 0:                                //1+640-32(diamond width) //1+480-24
                sprite = new Diamond(random.nextInt(609), random.nextInt(455));
                break;
            case 1:
                sprite = new Ruby(random.nextInt(609), random.nextInt(447));
                break;
            default:
                sprite = new YellowSapphire(random.nextInt(609), random.nextInt(458));
                break;
        }
        Main.getRoot().getChildren().add(sprite.getSpriteFrame());
        Main.getMainMenu().getGame().getSpriteManager().addToNormalToAdd(sprite);
    }
    
    private static void createBonus(){
        Sprite sprite;
        sprite = new ReloadBonus(random.nextInt(609), random.nextInt(458));
        Main.getRoot().getChildren().add(sprite.getSpriteFrame());
        Main.getMainMenu().getGame().getSpriteManager().addToNormalToAdd(sprite);
    }
    
    /**
     * Crea un asteroide con una velocidad aleatoria limitada por el número que se
     * pasa como parámetro. El se crea fuera de la visión del juego y la dirección
     * que sigue es hacia alguna posición aleatoria en la ventana.
     * @param maxSpeed la velocidad máxima que puede tener el asteroide.
     */
    private static void createAsteroid(byte maxSpeed){
        Asteroid asteroid = null;
        switch(random.nextInt(4)){
            case 0: //arriba-abajo
                asteroid = new Asteroid(random.nextDouble() * (56 + Main.getWINDOW_WIDTH()) - 56, 0 - 58, 
                        (random.nextDouble() * maxSpeed + 1) * randomDir(), random.nextDouble() * maxSpeed + 1);
                break;
            case 1: //derecha-izquierda
                asteroid = new Asteroid(Main.getWINDOW_WIDTH() - 1, random.nextDouble() * (58 + Main.getWINDOW_HEIGHT()) - 58, 
                        (random.nextDouble() * maxSpeed + 1) * -1, (random.nextDouble() * maxSpeed + 1) * randomDir());
                break;
            case 2: //abajo-arriba
                asteroid = new Asteroid(random.nextDouble() * (56 + Main.getWINDOW_WIDTH()) - 56, Main.getWINDOW_HEIGHT() - 1, 
                        (random.nextDouble() * maxSpeed + 1) * randomDir(), (random.nextDouble() * maxSpeed + 1) * -1);
                break;
            case 3: //izquierda-derecha
                asteroid = new Asteroid(0 - 56, random.nextDouble() * (58 + Main.getWINDOW_HEIGHT()) - 58, 
                        random.nextDouble() * maxSpeed + 1, (random.nextDouble() * maxSpeed + 1) * randomDir());
                break;
        }
		
        Main.getRoot().getChildren().add(asteroid.getSpriteFrame());
        Main.getMainMenu().getGame().getSpriteManager().addToNormalToAdd(asteroid);
    }
    
    private static void createUfo(byte maxSpeed){
        UFO ufo = null;
        switch(random.nextInt(4)){
            case 0: //arriba-abajo
                ufo = new UFO(random.nextDouble() * (65 + Main.getWINDOW_WIDTH()) - 65, 0 - 33, 
                        (random.nextDouble() * maxSpeed + 1) * randomDir(), random.nextDouble() * maxSpeed + 1);
                break;
            case 1: //derecha-izquierda
                ufo = new UFO(Main.getWINDOW_WIDTH() - 1, random.nextDouble() * (33 + Main.getWINDOW_HEIGHT()) - 33, 
                        (random.nextDouble() * maxSpeed + 1) * -1, (random.nextDouble() * maxSpeed + 1) * randomDir());
                break;
            case 2: //abajo-arriba
                ufo = new UFO(random.nextDouble() * (65 + Main.getWINDOW_WIDTH()) - 65, Main.getWINDOW_HEIGHT() - 1, 
                        (random.nextDouble() * maxSpeed + 1) * randomDir(), (random.nextDouble() * maxSpeed + 1) * -1);
                break;
            case 3: //izquierda-derecha
                ufo = new UFO(0 - 65, random.nextDouble() * (33 + Main.getWINDOW_HEIGHT()) - 33, 
                        random.nextDouble() * maxSpeed + 1, (random.nextDouble() * maxSpeed + 1) * randomDir());
                break;
        }
		
        Main.getRoot().getChildren().add(ufo.getSpriteFrame());
        Main.getMainMenu().getGame().getSpriteManager().addToNormalToAdd(ufo);
    }
    /**
     * 
     * @return 1 o -1.
     */
    private static byte randomDir(){
        if(random.nextBoolean()){
                return 1;
        } else{
                return -1;
        }
    }
}
