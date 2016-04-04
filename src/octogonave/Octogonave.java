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
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
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
    private static final String gameTitle = "Octogonave";
    protected static Button playButton, instructionsButton, configButton, creditsButton, exitButton;
    
    private Group root;
    private PlayerSpacecraft octogonave;
    private Image octnave1;
    private Scene scene;
    private SpriteManager spriteManager;


    @Override
    public void start(Stage primaryStage) {
        
        Configuration.loadConfig();
        root = new Group();
        scene = new Scene(root, WIDTH, HEIGHT);   
        primaryStage.setTitle(gameTitle);
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
        octnave1 = new Image("/octogonaveEstatica.png", 135, 134, true, false, true);
    }
    /**
     * Crea los <i>sprites</i> utilizados en el juego.
     */
    private void createActors(){
        octogonave = new PlayerSpacecraft(scene, "M 187,7\n" +
"           C 187,7 261,84 261,84\n" +
"             261,84 260,186 260,186\n" +
"             260,186 188,257 188,257\n" +
"             188,257 82,258 82,258\n" +
"             82,258 10,186 10,186\n" +
"             10,186 9,83 9,83\n" +
"             9,83 82,8 82,8 Z", 45, 45, octnave1);
    }
    /**
     * Añade los Nodes de los <i>sprites</i> al Group principal.
     */
    private void addActorNodes(){
        root.getChildren().add(octogonave.getSpriteFrame());
    }
    private void manageSprites(){
        spriteManager = new SpriteManager();
        spriteManager.addToCurrentActors(octogonave);
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
        menuVBox.setAlignment(Pos.CENTER);
        
        Text title = new Text(gameTitle);
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
                createActors();
                addActorNodes();
                manageSprites();
                startGameLoop();
            }
        );
        instructionsButton.setOnAction(e -> 
            {
                
            }
        );
        configButton.setOnAction(e -> 
            {
                Configuration.configMenu(scene, root);
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
        GameLoop gameLoop = new GameLoop(octogonave);
        gameLoop.start();
    }
}
