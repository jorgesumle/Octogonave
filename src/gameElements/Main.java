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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import gameMenus.MainMenu;
import gameMenus.Texts;


/**
 * Contiene las propiedades y métodos básicos y necesarios para una aplicación
 * JavaFX. Entre ellas se encuentran las propiedades de la ventana, la <tt>Scene</tt>
 * principal, el <tt>Node</tt> raíz y el menú principal, que se crea en el método
 * <tt>start()</tt>
 * @author Jorge Maldonado Ventura
 */
public class Main extends Application {
    
    private static final short WINDOW_HEIGHT = 480, WINDOW_WIDTH = 640;
    private static Pane root;
    private static Scene scene;
    private static MainMenu mainMenu;
    
    /**
     * Devuelve el menú principal.
     * @return el menú principal.
     */
    public static MainMenu getMainMenu(){
        return mainMenu;
    }
    
    /**
     * Devuelve el <tt>Scene</tt> principal.
     * @return el <tt>Scene</tt> principal.
     */
    public static Scene getScene() {
        return scene;
    }
   
    /**
     * Devuelve el <tt>Pane</tt> principal.
     * @return el <tt>Pane</tt> principal.
     */
    public static Pane getRoot() {
        return root;
    }

    public static short getWINDOW_HEIGHT() {
        return WINDOW_HEIGHT;
    }

    public static short getWINDOW_WIDTH() {
        return WINDOW_WIDTH;
    }
    
    public static void main(String[] args) {
        if(args.length > 0){
            String parameter = args[0].toLowerCase();
            if(parameter.contains("version") || parameter.equals("-v")){
                System.out.println("2.3.0.1");
            } else if(parameter.contains("license") || parameter.equals("-l")){
                System.out.println("GNU General Public License (http://www.gnu.org/licenses/)");
            } else if(parameter.contains("help") || parameter.equals("-h")){
                System.out.print("-h, help\n"
                        + "    Muestra este mensaje de ayuda.\n"
                        + "-l, license\n"
                        + "    Muestra la licencia del programa.\n"
                        + "-v, version\n"
                        + "    Muestra la versión del programa.\n");
            } else{
                System.out.println("Parámetro desconocido. Para ver los parámetros"
                        + "admitidos utilice el parámetro -h.");
            }
            System.exit(0);
        } else{
            launch(args);
        }
    }
    
    /**
     * Crea y vincula los recursos necesarios para el correcto funcionamiento
     * del programa: el <tt>.css</tt> de los menús, la <tt>Scene</tt>, el <tt>Stage</tt>,
     * el icono de la ventana y el menú principal (<tt>MainMenu</tt>).
     * @param primaryStage el <tt>Stage</tt> principal.
     */
    @Override
    public void start(Stage primaryStage) {
        gameMenus.Config.loadConfig();
        root = new Pane();
        root.prefHeight(WINDOW_HEIGHT);
        root.prefWidth(WINDOW_WIDTH);
        
        scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT); 
        scene.getStylesheets().add(MainMenu.class.getResource("menus.css").toExternalForm());
        primaryStage.getIcons().add(new Image("octogonaveIcon.png"));
        primaryStage.setTitle(Texts.getProgramTitle());
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        
        mainMenu = new gameMenus.MainMenu();   
        scene.setRoot(mainMenu);
    }
    
}
