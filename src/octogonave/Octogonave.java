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

import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * Clase principal.
 * @author Jorge Maldonado Ventura
 */
public class Octogonave extends Application {
    private static final short HEIGHT = 480, WIDTH = 640;
    private static final String GAME_TITLE = "Octogonave";
    private static final byte PADDING = 10;
    private static long score = 0;
    private static Button playButton, instructionsButton, configButton, creditsButton, exitButton;
    private static Gem diamond, diamond2, diamond3, diamond4, diamond5;
    private static Pane root;
    private static PlayerSpacecraft octogonave;
    private static Image octoNaveStill, octoNaveMov1, octoNaveMov2, octoNaveMov3, diamondImg1, diamondImg2, spaceBackground;
    private static Scene scene;
    private static SpriteManager spriteManager;
    private static StackPane menuStackPane;
    private static Text scoreText;

    public static Image getDiamondImg1() {
        return diamondImg1;
    }

    public static Image getDiamondImg2() {
        return diamondImg2;
    }

    
    public static Button getPlayButton() {
        return playButton;
    }

    public static Button getInstructionsButton() {
        return instructionsButton;
    }

    public static Button getConfigButton() {
        return configButton;
    }

    public static Button getCreditsButton() {
        return creditsButton;
    }
    
    public static StackPane getMenuStackPane(){
        return menuStackPane;
    }
    
    public static Button getExitButton() {
        return exitButton;
    }
    
    public static Scene getScene() {
        return scene;
    }


    public static Pane getRoot() {
        return root;
    }

    public static SpriteManager getSpriteManager() {
        return spriteManager;
    }

    public static PlayerSpacecraft getOctogonave() {
        return octogonave;
    }

    public static byte getPADDING() {
        return PADDING;
    }

    public static long getScore() {
        return score;
    }

    public static void setScore(long score) {
        Octogonave.score = score;
    }
    
     
    @Override
    public void start(Stage primaryStage) {
        
        Configuration.loadConfig();
        root = new Pane();
        root.prefHeight(HEIGHT);
        root.prefWidth(WIDTH);
        
        scene = new Scene(root, WIDTH, HEIGHT); 
        scene.getStylesheets().add(this.getClass().getResource("menus.css").toExternalForm());
        primaryStage.getIcons().add(new Image("/octogonaveStillOriginal.png"));
        primaryStage.setTitle(GAME_TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
        //primaryStage.setFullScreen(true);
        
        createMainMenu();
        makeMainMenuInteract();
    }
    /**
     * Carga todas las imágenes utilizadas en el juego.
     */
    private static void loadImages(){
        octoNaveStill = new Image("/octogonaveStill.png", 117, 117, true, false, true);
        octoNaveMov1 = new Image("/octogonaveMovingFire1.png", 117, 117, true, false, true);
        octoNaveMov2 = new Image("/octogonaveMovingFire2.png", 117, 117, true, false, true);
        octoNaveMov3 = new Image("/octogonaveMovingFire3.png", 117, 117, true, false, true);
        diamondImg1 = new Image("/diamond.png", 32, 24, true, false, true);
        diamondImg2 = new Image("/diamond2.png", 32, 24, true, false, true);
        spaceBackground = new Image("/spaceBackgroundInv.jpg", 640, 480, true, false, true);
    }
    /**
     * Crea los nodos (Nodes) utilizados en el juego.
     */
    private void createNodes(){
        octogonave = new PlayerSpacecraft("M 53,30 L 53,30 64,30 65,31 65,38 67,39 72,34 74,34 81,42 81,44 76,49 78,51 85,51 86,52 86,63 85,64 78,64 77,65 77,66 76,67 81,72 81,74 74,81 72,81 65,76 66,77 64,77 64,85 63,86 52,86 51,85 51,78 49,76 44,81 42,81 35,74 35,72 40,67 39,66 39,64 31,64 30,63 30,52 31,51 38,51 40,49 35,44 35,42 42,34 44,34 49,40 50,39 52,39 52,31 Z", 0, 0, octoNaveStill, octoNaveMov1, octoNaveMov2, octoNaveMov3);
        diamond = new Gem("M 0,6 L 0,6 6,0 25,0 31,6 31,8 16,23 15,23 0,8 Z", 200, 100, diamondImg1, diamondImg2);
        diamond2 = new Gem("M 0,6 L 0,6 6,0 25,0 31,6 31,8 16,23 15,23 0,8 Z", 460, 10, diamondImg1, diamondImg2);
        diamond3 = new Gem("M 0,6 L 0,6 6,0 25,0 31,6 31,8 16,23 15,23 0,8 Z", 219, 12, diamondImg1, diamondImg2);
        diamond4 = new Gem("M 0,6 L 0,6 6,0 25,0 31,6 31,8 16,23 15,23 0,8 Z", 600, 470, diamondImg1, diamondImg2);
        diamond5 = new Gem("M 0,6 L 0,6 6,0 25,0 31,6 31,8 16,23 15,23 0,8 Z", 323, 260, diamondImg1, diamondImg2);
        scoreText = new Text(Long.toString(score));
        scoreText.setTranslateX(550);
        scoreText.setTranslateY(30);
        scoreText.getStyleClass().add("text");
        scoreText.setFill(Color.BLACK);
    }
    /**
     * Añade los nodos (Nodes) al StackPane principal.
     */
    private static void addNodes(){
         
        root.getChildren().addAll(scoreText,
                                  octogonave.getSpriteFrame(), 
                                  diamond.getSpriteFrame(),
                                  diamond2.getSpriteFrame(),
                                  diamond3.getSpriteFrame(),
                                  diamond4.getSpriteFrame(),
                                  diamond5.getSpriteFrame());
        root.setBackground(
            new javafx.scene.layout.Background(
                    new BackgroundImage(                             
                            spaceBackground, 
                            BackgroundRepeat.NO_REPEAT, 
                            BackgroundRepeat.NO_REPEAT, 
                            BackgroundPosition.CENTER, 
                            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true)
                    )
            )
        );
    }
    private static void manageSprites(){
        spriteManager = new SpriteManager();
        spriteManager.addToCurrentSprites(diamond, diamond2, diamond3, diamond4, diamond5);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    

    private static void createMainMenu() {
        menuStackPane = new StackPane();
       
        Configuration.setLanguageText();
        
        VBox menuVBox = new VBox();
        menuVBox.setSpacing(PADDING);
        menuVBox.setAlignment(Pos.CENTER);
        
        Text title = new Text(GAME_TITLE);
        title.setId("title");
        
        playButton = new Button(Configuration.getPlayText());
        playButton.getStyleClass().add("button");    
        instructionsButton = new Button(Configuration.getInstructionsText());
        instructionsButton.getStyleClass().add("instructions");   
        configButton = new Button(Configuration.getConfigText());
        configButton.getStyleClass().add("config");
        creditsButton = new Button(Configuration.getCreditsText());
        creditsButton.getStyleClass().add("credits");
        exitButton = new Button(Configuration.getExitText());
        exitButton.getStyleClass().add("exit");
        
        menuVBox.getChildren().addAll(title, playButton, instructionsButton, configButton, creditsButton, exitButton);
        
        scene.setRoot(menuStackPane);
        menuStackPane.getChildren().add(menuVBox);
        
    }

    private void makeMainMenuInteract() {
        playButton.setOnAction(e -> 
            {
                scene.setRoot(root);
                loadImages();
                createNodes();
                addNodes();
                manageSprites();
                startGameLoop();
            }
        );
        instructionsButton.setOnAction(e -> 
            {
                root.getChildren().clear();
                Instructions.displayInstructions(this);
            }
        );
        configButton.setOnAction(e -> 
            {
                Configuration.configMenu(this);
                Configuration.applyLanguageChange();
            }
        );
        creditsButton.setOnAction(e -> 
            {
                
            }
        );
        exitButton.setOnAction(e -> 
            {
                System.exit(0);
            }
        );
    }
    
    /**
     * Arranca el AnimationTimer, que ejecutará la lógica de acción y actualización
     * del juego, que se ejecutará en cada fotograma en condiciones idóneas.
     */
    private static void startGameLoop() {
        ArrayList<Gem> diamonds = new ArrayList<>();
        diamonds.add(diamond);
        diamonds.add(diamond2);
        diamonds.add(diamond3);
        diamonds.add(diamond4);
        diamonds.add(diamond5);

        GameLoop gameLoop = new GameLoop(octogonave, spriteManager);
        gameLoop.start();
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(5000), (ActionEvent e) -> {
            Gem diamond1 = new Gem("M 0,6 L 0,6 6,0 25,0 31,6 31,8 16,23 15,23 0,8 Z", (Math.random() * 640 + 1), (Math.random() * 480 + 1), Octogonave.getDiamondImg1(), Octogonave.getDiamondImg2());
            Octogonave.getRoot().getChildren().add(diamond1.getSpriteFrame());
            spriteManager.addToCurrentSprites(diamond1);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    /**
     * Actualiza el objeto Text que aparece en pantalla con la puntuación actual,
     * obtenida de la variable {@link #score}.
     */
    protected static void updateScoreText() {
        scoreText.setText(Long.toString(score));
    }
    
    
}
