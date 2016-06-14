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

package gameMenus;

import gameElements.Game;
import gameElements.ArcadeModeGame;
import gameElements.AdventureModeGame;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import gameElements.Main;
import java.util.ArrayList;
import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * El menú que aparece al arrancar el programa.
 * @author Jorge Maldonado Ventura
 */
public class MainMenu extends StackPane{
    private Timeline starTimeline;
    private byte PADDING;
    private Button playAdventureModeButton, playArcadeModeButton, instructionsButton, scoreButton, configButton, exitButton;
    private Game game;
    private ConfigMenu configMenu;
    private InstructionsScreen instructionsScreen;
    private HighestScoresScreen highestScoresScreen;
    private Text title;
    private VBox menuVBox;
    private StarAnimTimer starAnimTimer;
    
    public MainMenu(){
        applyLayoutStyle();
        animateBackground();
        createTitleText();
        createButtons();
        makeButtonsInteract();
        setTexts();
        menuVBox.getChildren().addAll(title, playAdventureModeButton, playArcadeModeButton, instructionsButton, scoreButton, configButton, exitButton);
        getChildren().add(menuVBox);
    }

    public Game getGame() {
        return game;
    }
    
    byte getPADDING() {
        return PADDING;
    }

    public InstructionsScreen getInstructionsScreen() {
        return instructionsScreen;
    }

    Button getPlayArcadeModeButton() {
        return playArcadeModeButton;
    }

    Button getInstructionsButton() {
        return instructionsButton;
    }

    public Timeline getStarTimeline() {
        return starTimeline;
    }

    Button getConfigButton() {
        return configButton;
    }

    Button getExitButton() {
        return exitButton;
    }

    public StarAnimTimer getStarAnimTimer() {
        return starAnimTimer;
    }

    public void setGame(Game game) {
        this.game = game;
    }
    
    private void applyLayoutStyle(){
        setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        menuVBox = new VBox();
        menuVBox.getStyleClass().add("transparent");
        PADDING = 10;
        menuVBox.setSpacing(PADDING);
        menuVBox.setAlignment(Pos.CENTER);
    }
    
    private void animateBackground(){
        ArrayList<Star> stars = new ArrayList<>();
        starAnimTimer = new StarAnimTimer(stars);
        Random random = new Random();
        starTimeline = new Timeline();
        starTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(9), (ActionEvent e) -> {
            Star star = new Star(0, 0, random.nextDouble() * 9 - 5, random.nextDouble() * 9 - 5);
            getChildren().add(star);
            stars.add(star);
        }));
        starTimeline.setCycleCount(Animation.INDEFINITE);
        starTimeline.play();
        starAnimTimer.start();
    }
    
    private void createTitleText(){
        title = new Text();
        title.setId("title");
    }
    
    private void createButtons(){
        playAdventureModeButton = new Button();
        playArcadeModeButton = new Button(); 
        instructionsButton = new Button();
        scoreButton = new Button();
        configButton = new Button();
        exitButton = new Button();
    }
    
    /**
     * Aporta funcionalidad a los botones del menú principal.
     */
    public void makeButtonsInteract(){
        playAdventureModeButton.setOnAction(e ->
            {
                Main.getScene().setRoot(Main.getRoot());
                starAnimTimer.pause();
                game = new AdventureModeGame();
            }
        );
        playArcadeModeButton.setOnAction(e -> 
            {
                Main.getScene().setRoot(Main.getRoot());
                starAnimTimer.pause();
                game = new ArcadeModeGame();
            }
        );
        instructionsButton.setOnAction(e -> 
            {
                if(instructionsScreen == null){
                    instructionsScreen = new InstructionsScreen();
                }
                starAnimTimer.pause();
                Main.getRoot().getChildren().clear();
                instructionsScreen.setTexts();
                Main.getScene().setRoot(instructionsScreen);
            }
        );
        scoreButton.setOnAction(e ->
            {
                if(highestScoresScreen == null){
                    highestScoresScreen = new HighestScoresScreen();
                }
                starAnimTimer.pause();
                Main.getRoot().getChildren().clear();
                highestScoresScreen.setTexts();
                Main.getScene().setRoot(highestScoresScreen);
            }
        );
        configButton.setOnAction(e -> 
            {
                if(configMenu == null){
                    configMenu = new ConfigMenu();
                }
                starAnimTimer.pause();
                Main.getRoot().getChildren().clear();
                Main.getScene().setRoot(configMenu);
            }
        );
        exitButton.setOnAction(e -> 
            {
                System.exit(0);
            }
        );
    }
    
    /**
     * Asigna el texto de las instancias de <tt>Node</tt> que contienen texto.
     */
    void setTexts(){
        title.setText(Texts.getProgramTitle());
        playAdventureModeButton.setText(Texts.getAdventureModeButton());
        playArcadeModeButton.setText(Texts.getArcadeModeButton());
        instructionsButton.setText(Texts.getInstructionsButton());
        scoreButton.setText(Texts.getHighestScoresButton());
        configButton.setText(Texts.getConfigButton());
        exitButton.setText(Texts.getExitButton());
    }
    
}
