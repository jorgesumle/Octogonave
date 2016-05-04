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

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import gameElements.Main;

/**
 *
 * @author Jorge Maldonado Ventura
 */
public class MainMenu extends StackPane{
    
    private final byte PADDING = 10;
    private final Button PLAY_BUTTON, INSTRUCTIONS_BUTTON, CONFIG_BUTTON, CREDITS_BUTTON, EXIT_BUTTON;
    
    
    public MainMenu(){
        ConfigMenu.setLanguageText();
        
        VBox menuVBox = new VBox();
        menuVBox.setSpacing(PADDING);
        menuVBox.setAlignment(Pos.CENTER);
        
        Text title = new Text(Main.getGAME_TITLE());
        title.setId("title");
        
        PLAY_BUTTON = new Button(ConfigMenu.getPlayButtonText());
        PLAY_BUTTON.getStyleClass().add("button");    
        INSTRUCTIONS_BUTTON = new Button(ConfigMenu.getInstructionsButtonText());
        INSTRUCTIONS_BUTTON.getStyleClass().add("instructions");   
        CONFIG_BUTTON = new Button(ConfigMenu.getConfigButtonText());
        CONFIG_BUTTON.getStyleClass().add("config");
        CREDITS_BUTTON = new Button(ConfigMenu.getCreditsButtonText());
        CREDITS_BUTTON.getStyleClass().add("credits");
        EXIT_BUTTON = new Button(ConfigMenu.getExitButtonText());
        EXIT_BUTTON.getStyleClass().add("exit");
        
        menuVBox.getChildren().addAll(title, PLAY_BUTTON, INSTRUCTIONS_BUTTON, CONFIG_BUTTON, CREDITS_BUTTON, EXIT_BUTTON);
        getChildren().add(menuVBox);
    }

    public byte getPADDING() {
        return PADDING;
    }

    public Button getPLAYBUTTON() {
        return PLAY_BUTTON;
    }

    public Button getINSTRUCTIONSBUTTON() {
        return INSTRUCTIONS_BUTTON;
    }

    public Button getCONFIGBUTTON() {
        return CONFIG_BUTTON;
    }

    public Button getCREDITSBUTTON() {
        return CREDITS_BUTTON;
    }

    public Button getEXITBUTTON() {
        return EXIT_BUTTON;
    }
    
    public void makeButtonsInteract(){
        PLAY_BUTTON.setOnAction(e -> 
            {
                Main.getScene().setRoot(Main.getRoot());
                Main.createNodes();
                Main.addNodes();
                Main.manageSprites();
                Main.startGameLoop();
            }
        );
        INSTRUCTIONS_BUTTON.setOnAction(e -> 
            {
                InstructionsMenu instructionsMenu = new InstructionsMenu();
                Main.getRoot().getChildren().clear();
                Main.getScene().setRoot(instructionsMenu);
            }
        );
        CONFIG_BUTTON.setOnAction(e -> 
            {
                ConfigMenu.configMenu();
                ConfigMenu.applyLanguageChange();
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
