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
    private static final short WIDTH = 640;
    private static final short HEIGHT = 480;
    private static final String GAME_TITLE = "Octogonave";
    protected static Button playButton, instructionsButton, configButton, creditsButton, exitButton;
    private Gem diamond;
    private StackPane root;
    private PlayerSpacecraft octogonave;
    private Image octnave1, diamond1, diamond2;
    private Scene scene;
    private SpriteManager spriteManager;
    protected static final byte PADDING = 10;

    public Scene getScene() {
        return scene;
    }


    public StackPane getRoot() {
        return root;
    }

    public SpriteManager getSpriteManager() {
        return spriteManager;
    }

    public PlayerSpacecraft getOctogonave() {
        return octogonave;
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
    private void loadImages(){
        octnave1 = new Image("/octogonaveStill.png", 57, 57, true, false, true);
        diamond1 = new Image("/diamond.png", 32, 32, true, false, true);
        diamond2 = new Image("/diamond2.png", 32, 32, true, false, true);
        
    }
    /**
     * Crea los <i>sprites</i> utilizados en el juego.
     */
    private void createSprites(){
        octogonave = new PlayerSpacecraft(this, "M 0,22 L 0,22 1,21 8,21 10,19 5,14 5,12 12,4 14,4 19,10 20,9 22,9 22,1 23,0 34,0 35,1 35,8 37,10 42,4 44,4 51,12 51,14 46,19 48,21 55,21 56,22 56,33 55,34 48,34 46,37 51,42 51,44 44,51 42,51 37,46 36,47 34,47 34,55 33,56 22,56 21,55 21,47 19,46 14,51 12,51 5,44 5,42 10,37 9,36 9,34 1,34 0,33 Z", 270, 268, octnave1);
        diamond = new Gem(this, "M0,14 L0,14 6,8 25,8 31,14 31,16 16,31 15,31 0,16 Z", 250, 100, diamond1, diamond2);
    }
    /**
     * Añade los Nodes de los <i>sprites</i> al Group principal.
     */
    private void addSpriteNodes(){
        root.getChildren().add(octogonave.getSpriteFrame());
        root.getChildren().add(diamond.getSpriteFrame());
    }
    private void manageSprites(){
        spriteManager = new SpriteManager();
        spriteManager.addToCurrentSprites(diamond);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    

    private void createMainMenu() {
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

    private void startGameLoop() {
        GameLoop gameLoop = new GameLoop(octogonave, diamond);
        gameLoop.start();
    }
}
