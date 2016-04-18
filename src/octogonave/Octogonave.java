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
    private static Button playButton, instructionsButton, configButton, creditsButton, exitButton;
    private static Gem diamond;
    private static StackPane root;
    private static PlayerSpacecraft octogonave;
    private static Image octogonaveImg1, diamondImg1, diamondImg2;
    private static Scene scene;
    private static SpriteManager spriteManager;

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

    public static Button getExitButton() {
        return exitButton;
    }
    
    public static Scene getScene() {
        return scene;
    }


    public static StackPane getRoot() {
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
    
    
    
    

    
    @Override
    public void start(Stage primaryStage) {
        
        Configuration.loadConfig();
        root = new StackPane();
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
        octogonaveImg1 = new Image("/octogonaveStill.png", 57, 57, true, false, true);
        diamondImg1 = new Image("/diamond.png", 32, 24, true, false, true);
        diamondImg2 = new Image("/diamond2.png", 32, 24, true, false, true);
        
    }
    /**
     * Crea los <i>sprites</i> utilizados en el juego.
     */
    private void createSprites(){
        octogonave = new PlayerSpacecraft(this, "M 23,0 L 23,0 34,0 35,1 35,8 37,9 42,4 44,4 51,12 51,14 46,19 48,21 55,21 56,22 56,33 55,34 48,34 47,35 47,36 46,37 51,42 51,44 44,51 42,51 35,46 36,47 34,47 34,55 33,56 22,56 21,55 21,48 19,46 14,51 12,51 5,44 5,42 10,37 9,36 9,34 1,34 0,33 0,22 1,21 8,21 10,19 5,14 5,12 12,4 14,4 19,10 20,9 22,9 22,1 Z", 0, 0, octogonaveImg1);
        diamond = new Gem(this, "M 0,6 L 0,6 6,0 25,0 31,6 31,8 16,23 15,23 0,8 Z", 200, 100, diamondImg1, diamondImg2);
        
    }
    /**
     * Añade los Nodes de los <i>sprites</i> al Group principal.
     */
    private static void addSpriteNodes(){
        root.getChildren().addAll(octogonave.getSpriteFrame(), 
                                  diamond.getSpriteFrame());
    }
    private static void manageSprites(){
        spriteManager = new SpriteManager();
        spriteManager.addToCurrentSprites(diamond);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    

    private static void createMainMenu() {
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
        root.getChildren().add(menuVBox);
        
    }

    private void makeMainMenuInteract() {
        playButton.setOnAction(e -> 
            {
                root.getChildren().clear();
                loadImages();
                createSprites();
                addSpriteNodes();
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

    private static void startGameLoop() {
        ArrayList<Gem> diamonds = new ArrayList<Gem>(){{
            add(diamond);
        }};
        GameLoop gameLoop = new GameLoop(octogonave, diamonds);
        gameLoop.start();
    }
}
