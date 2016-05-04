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

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import gameMenus.ConfigMenu;
import gameMenus.MainMenu;


/**
 * Clase principal.
 * @author Jorge Maldonado Ventura
 */
public class Main extends Application {
    private static final short HEIGHT = 480, WIDTH = 640;
    private static Pane root;
    private static Scene scene;
    private static MainMenu mainMenu;
    private static final String GAME_TITLE = "Octogonave";

    public static String getGAME_TITLE() {
        return GAME_TITLE;
    }
    
    public static MainMenu getMainMenu(){
        return mainMenu;
    }
    
    public static Scene getScene() {
        return scene;
    }

    public static Pane getRoot() {
        return root;
    }

    public static short getHEIGHT() {
        return HEIGHT;
    }

    public static short getWIDTH() {
        return WIDTH;
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        ConfigMenu.loadConfig();
        root = new Pane();
        root.prefHeight(HEIGHT);
        root.prefWidth(WIDTH);
        
        scene = new Scene(root, WIDTH, HEIGHT); 
        scene.getStylesheets().add(this.getClass().getResource("menus.css").toExternalForm());
        primaryStage.getIcons().add(new Image("/octogonaveStillOriginal.png"));
        primaryStage.setTitle("Octogonave");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        mainMenu = new gameMenus.MainMenu();   
        scene.setRoot(mainMenu);
        mainMenu.makeButtonsInteract();
    }
    
}
