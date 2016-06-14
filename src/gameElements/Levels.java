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

import gameMenus.Texts;
import java.util.ArrayList;
import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author Jorge Maldonado Ventura
 */
public class Levels {

    private static Random random = new Random();
    private static Timeline arcadeModeTimeline, 
            anyLevelTimeline, 
            backgroundChange;
    private static FadeTransition ft;
    private static final int TRANSITION_DURATION_IN_MILLIS = 10_000;
    private static boolean changingLevel;

    static Timeline getArcadeModeTimeline() {
        return arcadeModeTimeline;
    }
    
    static void removeTimers(){
        if(Main.getMainMenu().getGame() instanceof ArcadeModeGame){
            arcadeModeTimeline.stop();
        } else{
            anyLevelTimeline.stop();
        }
        arcadeModeTimeline = null;
        anyLevelTimeline = null;
        backgroundChange = null;
        ft = null;
    }
    
    static void pauseAdventureModeTimers(){
        if(changingLevel){
            ft.pause();
            if(backgroundChange != null){
                backgroundChange.pause();
            }
        } else{
            anyLevelTimeline.pause();
        }
    }
    
    static void resumeAdventureModeTimers(){
        if(changingLevel){
            ft.play();
            if(backgroundChange != null){
                backgroundChange.play();
            }
        } else{
            anyLevelTimeline.play();
        }
    }
    
    /**
     * Empieza el TimeLine, que añade nuevos sprites al juego cada cierto
     * tiempo.
     */
    public static void arcadeMode() {
        arcadeModeTimeline = new Timeline();
        arcadeModeTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(333), (ActionEvent e) -> 
            {
                if (Main.getMainMenu().getGame().getScore().getScore() < 400) {
                    if (random.nextInt(10) == 0) {
                        createGem();
                        createBonus();
                    }
                    createAsteroid((byte) 4);
                } else if (Main.getMainMenu().getGame().getScore().getScore() < 800) {
                    createAsteroid((byte) 4);
                    createAsteroid((byte) 3);
                    if (random.nextInt(114) == 0) {
                        createGem();
                        createBonus();
                    }
                } else if (Main.getMainMenu().getGame().getScore().getScore() < 1_200) {
                    createUfo((byte) 4);
                    if (random.nextInt(60) == 0) {
                        createGem();
                        createBonus();
                    }
                } else if (Main.getMainMenu().getGame().getScore().getScore() < 2_000) {
                    createAsteroid((byte) 4);
                    createAsteroid((byte) 4);
                    createAsteroid((byte) 4);
                    if (random.nextInt(48) == 0) {
                        createGem();
                        createBonus();
                    }
                } else if (Main.getMainMenu().getGame().getScore().getScore() < 2_100) {
                    createGem();
                    createBonus();
                } else {
                    createAsteroid((byte) 5);
                    createAsteroid((byte) 5);
                    createAsteroid((byte) 5);
                    if (random.nextInt(43) == 0) {
                        createGem();
                        createBonus();
                    }
                }
            })
        );
        arcadeModeTimeline.setCycleCount(Animation.INDEFINITE);
        arcadeModeTimeline.play();
    }

    public static void initAnyLevelTimeline() {
        anyLevelTimeline = new Timeline();
    }

    private static void level1() {
        anyLevelTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(444), (ActionEvent e) -> 
            {
                createAsteroid((byte) 3);
                if (random.nextInt(21) == 0) {
                    createGem();
                } else if (random.nextInt(16) == 0) {
                    createBonus();
                }
            }
        ));
        anyLevelTimeline.setCycleCount(65);
        anyLevelTimeline.play();
        anyLevelTimeline.setOnFinished(e -> 
            {
                newLevelTransition(Texts.getLevel2Text(), 2);
            }
        );
    }

    private static void level2() {
        anyLevelTimeline = new Timeline();
        anyLevelTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(400), (ActionEvent e) -> 
            {
                createAsteroid((byte) 4);
                if (random.nextInt(40) == 0) {
                    createGem();
                }
                if (random.nextInt(31) == 0) {
                    createBonus();
                }
            })
        );
        anyLevelTimeline.setCycleCount(100);
        anyLevelTimeline.play();
        anyLevelTimeline.setOnFinished(e -> 
            {
                newLevelTransition(Texts.getLevel3Text(), 3);
            }
        );
    }
    
    private static void level3(){
        anyLevelTimeline = new Timeline();
        ArrayList<Sprite> bonusSprites = new ArrayList<>();
        bonusSprites.add(new Diamond(0, 0));
        bonusSprites.add(new Diamond(Main.getWINDOW_WIDTH() - 32, 0));
        bonusSprites.add(new Diamond(0, Main.getWINDOW_HEIGHT() - 24));
        bonusSprites.add(new Diamond(Main.getWINDOW_WIDTH() - 32, Main.getWINDOW_HEIGHT() - 32));
        for(byte i = 0;  i < 20; i++){
            createGem();
        }
        bonusSprites.stream().forEach((sprite) -> {
            Main.getRoot().getChildren().add(sprite.getSpriteFrame());
        });
        Main.getMainMenu().getGame().getSpriteManager().addToNormalToAdd(bonusSprites.toArray(new Sprite[bonusSprites.size()]));
        anyLevelTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(5000), (ActionEvent e) -> {
            newLevelTransition(Texts.getLevel4Text(), 4);
        }));
        anyLevelTimeline.setCycleCount(1);
        anyLevelTimeline.play();
    }
    
    private static void level4(){
        anyLevelTimeline = new Timeline();
        anyLevelTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(430), (ActionEvent e) -> 
            {
                createUfo((byte) 3);
                if (random.nextInt(60) == 0) {
                    createGem();
                }
                if (random.nextInt(50) == 0) {
                    createBonus();
                }
            })
        );
        anyLevelTimeline.setCycleCount(50);
        anyLevelTimeline.play();
        anyLevelTimeline.setOnFinished(e -> 
            {
                newLevelTransition(Texts.getLevel5Text(), 5);
            }
        );
    }
    
    private static void level5(){
        anyLevelTimeline = new Timeline();
        anyLevelTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(400), (ActionEvent e) -> 
            {
                createUfo((byte) 4);
                createAsteroid((byte)2);
                if (random.nextInt(60) == 0) {
                    createGem();
                }
                if (random.nextInt(50) == 0) {
                    createBonus();
                }
            })
        );
        anyLevelTimeline.setCycleCount(65);
        anyLevelTimeline.play();
        anyLevelTimeline.setOnFinished(e -> 
            {
                newLevelTransition(Texts.getGameCompletedText(), 6);
            }
        );
    }
    
    /**
     * Crea una gema en una posición aleatoria.
     */
    private static void createGem() {
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

    private static void createBonus() {
        Sprite sprite;
        sprite = new ReloadBonus(random.nextInt(609), random.nextInt(458));
        Main.getRoot().getChildren().add(sprite.getSpriteFrame());
        Main.getMainMenu().getGame().getSpriteManager().addToNormalToAdd(sprite);
    }

    /**
     * Crea un asteroide con una velocidad aleatoria limitada por el número que
     * se pasa como parámetro. El se crea fuera de la visión del juego y la
     * dirección que sigue es hacia alguna posición aleatoria en la ventana.
     *
     * @param maxSpeed la velocidad máxima que puede tener el asteroide.
     */
    private static void createAsteroid(byte maxSpeed) {
        Asteroid asteroid = null;
        switch (random.nextInt(4)) {
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

    private static void createUfo(byte maxSpeed) {
        UFO ufo = null;
        switch (random.nextInt(4)) {
            case 0: //arriba-abajo
                ufo = new UFO(random.nextDouble() * (65 + Main.getWINDOW_WIDTH()) - 65, 0 - 33,
                        (random.nextDouble() * maxSpeed + 1));
                break;
            case 1: //derecha-izquierda
                ufo = new UFO(Main.getWINDOW_WIDTH() - 1, random.nextDouble() * (33 + Main.getWINDOW_HEIGHT()) - 33,
                        (random.nextDouble() * maxSpeed + 1));
                break;
            case 2: //abajo-arriba
                ufo = new UFO(random.nextDouble() * (65 + Main.getWINDOW_WIDTH()) - 65, Main.getWINDOW_HEIGHT() - 1,
                        (random.nextDouble() * maxSpeed + 1));
                break;
            case 3: //izquierda-derecha
                ufo = new UFO(0 - 65, random.nextDouble() * (33 + Main.getWINDOW_HEIGHT()) - 33,
                        random.nextDouble() * maxSpeed + 1);
                break;
        }

        Main.getRoot().getChildren().add(ufo.getSpriteFrame());
        Main.getMainMenu().getGame().getSpriteManager().addToNormalToAdd(ufo);
    }

    /**
     *
     * @return 1 o -1.
     */
    private static byte randomDir() {
        if (random.nextBoolean()) {
            return 1;
        } else {
            return -1;
        }
    }

    public static void newLevelTransition(String text, int nextLevel) {
        changingLevel = true;
        Rectangle rect = new Rectangle(0, 0, Main.getWINDOW_WIDTH(), Main.getWINDOW_HEIGHT());
        rect.setFill(Color.BLACK);
        Text textNode = new Text(text);
        textNode.getStyleClass().add("smallTextOnlyWhiteStrong");
        textNode.setTranslateX(20);
        textNode.setTranslateY(20);
        Main.getRoot().getChildren().addAll(rect, textNode);
        ft = new FadeTransition(Duration.millis(TRANSITION_DURATION_IN_MILLIS / 2), rect);
        ft.setFromValue(0);
        ft.setToValue(1.0);
        ft.setCycleCount(2);
        ft.setAutoReverse(true);
        ft.play();
        chooseBackground(nextLevel);
        ft.setOnFinished(e -> 
            {
                changingLevel = false;
                Main.getRoot().getChildren().remove(textNode);
                startLevel(nextLevel);
            }
        );
    }

    /**
     * Cambia el fondo del nivel por la image cuya URL se ha pasado como
     * parámetro.
     * @param backgroundURL la URL del fondo de pantalla.
     */
    private static void changeBackground(String backgroundURL) {
        backgroundChange = new Timeline();
        backgroundChange.getKeyFrames().add(new KeyFrame(Duration.millis(TRANSITION_DURATION_IN_MILLIS / 2), (ActionEvent e) -> 
            {
                resetSprites();
                Main.getRoot().setBackground(
                    new Background(
                            new BackgroundImage(
                                    new Image(backgroundURL, 640, 480, true, false, true),
                                    BackgroundRepeat.NO_REPEAT,
                                    BackgroundRepeat.NO_REPEAT,
                                    BackgroundPosition.CENTER,
                                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true))));
            }));
        backgroundChange.setCycleCount(1);
        backgroundChange.play();
    }

    /**
     * Deja vacíos los <ii>ArrayList</ii>s de <i>sprites</i> y cura la nave en
     * preparación para el próximo nivel.
     */
    private static void resetSprites() {
        for(Sprite sprite: Main.getMainMenu().getGame().getSpriteManager().getCurrentNormal()){
            Main.getRoot().getChildren().remove(sprite.spriteFrame);
        }
        Main.getMainMenu().getGame().getSpriteManager().getCurrentNormal().clear();
        Main.getMainMenu().getGame().getSpriteManager().getNormalToAdd().clear();
        Main.getMainMenu().getGame().getOctogonave().heal();
    }
    
    private static void startLevel(int level){
        switch (level) {
            case 1:
                level1();
                break;
            case 2:
                level2();
                break;
            case 3:
                level3();
                break;
            case 4:
                level4();
                break;
            case 5:
                level5();
                break;
            case 6:
                Main.getMainMenu().getGame().endGame();
                break;
        }
    }
    
    private static void chooseBackground(int level){
        switch (level) {
            case 2:
                changeBackground("/images/backgrounds/Saturn.jpg");
                break;
            case 3:
                changeBackground("/images/backgrounds/Neptune.jpg");
                break;
            case 4:
                changeBackground("/images/backgrounds/KuiperBelt.jpg");
                break;
            case 5:
                changeBackground("/images/backgrounds/nebula.jpg");
                break;
            case 6:
                changeBackground("/images/backgrounds/alignedPlanets.jpg");
                break;
        }
    }
}
