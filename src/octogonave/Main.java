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

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * Clase principal.
 * @author Jorge Maldonado Ventura
 */
public class Main extends Application {
    private static final short HEIGHT = 480, WIDTH = 640;
    private static final String GAME_TITLE = "Octogonave";
    private static final byte PADDING = 10;
    private static Button playButton, instructionsButton, configButton, creditsButton, exitButton;
    private static Bullet bullet, bullet2;
    private static Pane root;
    private static Octogonave octogonave;
    private static Scene scene;
    private static SpriteManager spriteManager;
    private static StackPane menuStackPane;
    private static Score playScore;

    public static String getGAME_TITLE() {
        return GAME_TITLE;
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

    public static Octogonave getOctogonave() {
        return octogonave;
    }

    public static byte getPADDING() {
        return PADDING;
    }

    public static Score getPlayScore() {
        return playScore;
    }
    
    public static void main(String[] args) {
        launch(args);
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
        primaryStage.setResizable(false);
        primaryStage.show();
        
        createMainMenu();
        makeMainMenuInteract();
    }

    /**
     * Crea los nodos (Nodes) utilizados en el juego.
     */
    private void createNodes(){
        octogonave = new Octogonave(320, 240);
        bullet = new Bullet(WIDTH-9, HEIGHT-12);
        bullet2 = new Bullet(WIDTH-10, HEIGHT-13);
        playScore = new Score(550, 30);
        
    }
    /**
     * Añade los nodos (Nodes) al StackPane principal.
     */
    private static void addNodes(){
         
        root.getChildren().addAll(playScore,
                                  octogonave.getSpriteFrame(),
                                  bullet.getSpriteFrame(),
                                  bullet2.getSpriteFrame());
        root.setBackground(
            new javafx.scene.layout.Background(
                    new BackgroundImage(                             
                            new Image("/spaceBackgroundInvSmall.jpg", 640, 480, true, false, true), 
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
        spriteManager.addToCurrentSprites(bullet, bullet2);
    }

    private static void createMainMenu() {
        menuStackPane = new StackPane();
       
        Configuration.setLanguageText();
        
        VBox menuVBox = new VBox();
        menuVBox.setSpacing(PADDING);
        menuVBox.setAlignment(Pos.CENTER);
        
        Text title = new Text(GAME_TITLE);
        title.setId("title");
        
        playButton = new Button(Configuration.getPlayButtonText());
        playButton.getStyleClass().add("button");    
        instructionsButton = new Button(Configuration.getInstructionsButtonText());
        instructionsButton.getStyleClass().add("instructions");   
        configButton = new Button(Configuration.getConfigButtonText());
        configButton.getStyleClass().add("config");
        creditsButton = new Button(Configuration.getCreditsButtonText());
        creditsButton.getStyleClass().add("credits");
        exitButton = new Button(Configuration.getExitButtonText());
        exitButton.getStyleClass().add("exit");
        
        menuVBox.getChildren().addAll(title, playButton, instructionsButton, configButton, creditsButton, exitButton);
        
        scene.setRoot(menuStackPane);
        menuStackPane.getChildren().add(menuVBox);
        
    }

    private void makeMainMenuInteract() {
        playButton.setOnAction(e -> 
            {
                scene.setRoot(root);
                createNodes();
                addNodes();
                manageSprites();
                startGameLoop();
            }
        );
        instructionsButton.setOnAction(e -> 
            {
                root.getChildren().clear();
                Instructions.displayInstructions();
            }
        );
        configButton.setOnAction(e -> 
            {
                Configuration.configMenu();
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
        GameLoop gameLoop = new GameLoop(octogonave, spriteManager);
        gameLoop.start();
        
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(5000), (ActionEvent e) -> {
            Sprite sprite;
            byte randomNumber = (byte)(Math.random() * 3);
            switch (randomNumber) {
                case 0:
                    sprite = new Diamond((Math.random() * (640 - 32 + 1) ), (Math.random() * (480 - 24 + 1)));
                    break;
                case 1:
                    sprite = new Ruby((Math.random() * (640 - 32 + 1)), (Math.random() * (480 - 32 + 1)));
                    break;
                default:
                    sprite = new YellowSapphire((Math.random() * (640 - 22 + 1)), (Math.random() * (480 - 21 + 1)));
                    break;
            }
            Main.getRoot().getChildren().add(sprite.getSpriteFrame());
            spriteManager.addToCurrentSprites(sprite);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        
        if(Configuration.isMusicOn()){
            Sounds.playMusic();
        }
    }
    
}
