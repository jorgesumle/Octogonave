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
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import gameElements.Main;

/**
 * El menú que aparece al arrancar el programa.
 * @author Jorge Maldonado Ventura
 */
public class MainMenu extends StackPane{
    
    private final byte PADDING;
    private final Button PLAY_BUTTON, INSTRUCTIONS_BUTTON, CONFIG_BUTTON, CREDITS_BUTTON, EXIT_BUTTON;
    private Game game;
    private ConfigMenu configMenu;
    private InstructionsMenu instructionsMenu;
    
    public MainMenu(){
        
        VBox menuVBox = new VBox();
        PADDING = 10;
        menuVBox.setSpacing(PADDING);
        menuVBox.setAlignment(Pos.CENTER);
        
        Text title = new Text(Texts.getProgramTitle());
        title.setId("title");
        
        PLAY_BUTTON = new Button(Texts.getPlayButton());
        PLAY_BUTTON.getStyleClass().add("button");    
        INSTRUCTIONS_BUTTON = new Button(Texts.getInstructionsButton());
        INSTRUCTIONS_BUTTON.getStyleClass().add("instructions");   
        CONFIG_BUTTON = new Button(Texts.getConfigButton());
        CONFIG_BUTTON.getStyleClass().add("config");
        CREDITS_BUTTON = new Button(Texts.getCreditsButton());
        CREDITS_BUTTON.getStyleClass().add("credits");
        EXIT_BUTTON = new Button(Texts.getExitButton());
        EXIT_BUTTON.getStyleClass().add("exit");
        
        menuVBox.getChildren().addAll(title, PLAY_BUTTON, INSTRUCTIONS_BUTTON, CONFIG_BUTTON, CREDITS_BUTTON, EXIT_BUTTON);
        getChildren().add(menuVBox);
    }

    public Game getGame() {
        return game;
    }
    
    public byte getPADDING() {
        return PADDING;
    }

    public Button getPLAY_BUTTON() {
        return PLAY_BUTTON;
    }

    public Button getINSTRUCTIONS_BUTTON() {
        return INSTRUCTIONS_BUTTON;
    }

    public Button getCONFIG_BUTTON() {
        return CONFIG_BUTTON;
    }

    public Button getCREDITS_BUTTON() {
        return CREDITS_BUTTON;
    }

    public Button getEXIT_BUTTON() {
        return EXIT_BUTTON;
    }
    
    public void makeButtonsInteract(){
        PLAY_BUTTON.setOnAction(e -> 
            {
                Main.getScene().setRoot(Main.getRoot());
                game = new Game();
            }
        );
        INSTRUCTIONS_BUTTON.setOnAction(e -> 
            {
                if(instructionsMenu == null){
                    instructionsMenu = new InstructionsMenu();
                }
                Main.getRoot().getChildren().clear();
                Main.getScene().setRoot(instructionsMenu);
            }
        );
        CONFIG_BUTTON.setOnAction(e -> 
            {
                if(configMenu == null){
                    configMenu = new ConfigMenu();
                }
                Main.getRoot().getChildren().clear();
                Main.getScene().setRoot(configMenu);
            }
        );
        CREDITS_BUTTON.setOnAction(e -> 
            {
                
            }
        );
        EXIT_BUTTON.setOnAction(e -> 
            {
                System.exit(0);
            }
        );
    }
    
}
