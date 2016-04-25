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
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


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
    private static Image octoNaveStill, octoNaveMov, diamondImg1, diamondImg2;
    private static Scene scene;
    private static SpriteManager spriteManager;
    private static StackPane menuStackPane;
    private static Text scoreText;

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
        octoNaveStill = new Image("/octogonaveStill.png", 57, 57, true, false, true);
        octoNaveMov = new Image("/octogonaveMoving.png", 57, 57, true, false, true);
        diamondImg1 = new Image("/diamond.png", 32, 24, true, false, true);
        diamondImg2 = new Image("/diamond2.png", 32, 24, true, false, true);
        
    }
    /**
     * Crea los nodos (Nodes) utilizados en el juego.
     */
    private void createNodes(){
        octogonave = new PlayerSpacecraft(this, "M 23,0 L 23,0 34,0 35,1 35,8 37,9 42,4 44,4 51,12 51,14 46,19 48,21 55,21 56,22 56,33 55,34 48,34 47,35 47,36 46,37 51,42 51,44 44,51 42,51 35,46 36,47 34,47 34,55 33,56 22,56 21,55 21,48 19,46 14,51 12,51 5,44 5,42 10,37 9,36 9,34 1,34 0,33 0,22 1,21 8,21 10,19 5,14 5,12 12,4 14,4 19,10 20,9 22,9 22,1 Z", 0, 0, octoNaveStill, octoNaveMov);
        diamond = new Gem(this, "M 0,6 L 0,6 6,0 25,0 31,6 31,8 16,23 15,23 0,8 Z", 200, 100, diamondImg1, diamondImg2);
        diamond2 = new Gem(this, "M 0,6 L 0,6 6,0 25,0 31,6 31,8 16,23 15,23 0,8 Z", 460, 10, diamondImg1, diamondImg2);
        diamond3 = new Gem(this, "M 0,6 L 0,6 6,0 25,0 31,6 31,8 16,23 15,23 0,8 Z", 219, 12, diamondImg1, diamondImg2);
        diamond4 = new Gem(this, "M 0,6 L 0,6 6,0 25,0 31,6 31,8 16,23 15,23 0,8 Z", 600, 470, diamondImg1, diamondImg2);
        diamond5 = new Gem(this, "M 0,6 L 0,6 6,0 25,0 31,6 31,8 16,23 15,23 0,8 Z", 323, 260, diamondImg1, diamondImg2);
        scoreText = new Text(Long.toString(score));
        scoreText.setTranslateX(550);
        scoreText.setTranslateY(30);
        scoreText.getStyleClass().add("text");
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

        GameLoop gameLoop = new GameLoop(octogonave, diamonds);
        gameLoop.start();
    }
    
    /**
     * Actualiza el objeto Text que aparece en pantalla con la puntuación actual,
     * obtenida de la variable {@link #score}.
     */
    protected static void updateScoreText() {
        scoreText.setText(Long.toString(score));
    }
    
    
}
