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
    private static Timeline arcadeModeTimeline;
    private static Timeline anyLevelTimeline;

    public static Timeline getArcadeModeTimeline() {
        return arcadeModeTimeline;
    }

    public static Timeline getAnyLevelTimeline() {
        return anyLevelTimeline;
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
                    createUfo((byte) 4);
                } else if (Main.getMainMenu().getGame().getScore().getScore() < 800) {
                    createAsteroid((byte) 4);
                    createAsteroid((byte) 3);
                    if (random.nextInt(114) == 0) {
                        createGem();
                        createBonus();
                    }
                } else if (Main.getMainMenu().getGame().getScore().getScore() < 1_200) {
                    createAsteroid((byte) 4);
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
            }));
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
                if (random.nextInt(20) == 0) {
                    createGem();
                } else if (random.nextInt(15) == 0) {
                    createBonus();
                }
            }));
        anyLevelTimeline.setCycleCount(65);
        anyLevelTimeline.setOnFinished(e -> 
            {
                newLevelTransition("Nivel 2. Nebulosa desconocida", 2);
            });
        anyLevelTimeline.play();
    }

    private static void level2() {
        anyLevelTimeline = new Timeline();
        anyLevelTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(400), (ActionEvent e) -> 
            {
                createAsteroid((byte) 4);
                createAsteroid((byte) 2);
                if (random.nextInt(40) == 0) {
                createGem();
                } else if (random.nextInt(30) == 0) {
                    createBonus();
                }
            }));
        anyLevelTimeline.setCycleCount(100);
        anyLevelTimeline.play();
        anyLevelTimeline.setOnFinished(e -> {
            newLevelTransition("Has completado el juego", 3);
        });
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
    private static byte randomDir() {
        if (random.nextBoolean()) {
            return 1;
        } else {
            return -1;
        }
    }

    public static void newLevelTransition(String text, int nextLevel) {
        Rectangle rect = new Rectangle(0, 0, Main.getWINDOW_WIDTH(), Main.getWINDOW_HEIGHT());
        rect.setArcHeight(50);
        rect.setArcWidth(50);
        rect.setFill(Color.BLACK);

        Text textNode = new Text(text);
        textNode.getStyleClass().add("smallTextOnlyWhiteStrong");
        textNode.setTranslateX(20);
        textNode.setTranslateY(20);
        Main.getRoot().getChildren().addAll(rect, textNode);
        final int TRANSITION_DURATION_IN_MILLIS = 10_000;

        FadeTransition ft = new FadeTransition(Duration.millis(TRANSITION_DURATION_IN_MILLIS / 2), rect);
        ft.setFromValue(0);
        ft.setToValue(1.0);
        ft.setCycleCount(2);
        ft.setAutoReverse(true);
        ft.play();
        switch (nextLevel) {
            case 2:
                changeBackground("/nebula.jpg", TRANSITION_DURATION_IN_MILLIS / 2);
                break;
        }
        ft.setOnFinished(e -> 
            {
                Main.getRoot().getChildren().remove(textNode);
                Levels.getAnyLevelTimeline().play();
                switch (nextLevel) {
                    case 1:
                        level1();
                        break;
                    case 2:
                        level2();
                        break;
                    default:
                        Main.getMainMenu().getGame().endGame();
                        break;
            }
        }
        );
    }

    /**
     * Cambia el fondo del nivel por la image cuya URL se ha pasado como
     * parámetro.
     * @param backgroundURL la URL del fondo de pantalla.
     */
    private static void changeBackground(String backgroundURL, int timeBeforeChange) {
        Timeline backgroundChange = new Timeline();
        backgroundChange.getKeyFrames().add(new KeyFrame(Duration.millis(timeBeforeChange), (ActionEvent e) -> 
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
        Main.getMainMenu().getGame().getSpriteManager().getCurrentNormal().clear();
        Main.getMainMenu().getGame().getSpriteManager().getNormalToAdd().clear();
        Main.getMainMenu().getGame().getOctogonave().heal();
    }
}
