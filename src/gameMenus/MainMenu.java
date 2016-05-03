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
    private Button playButton, instructionsButton, configButton, creditsButton, exitButton;
    
    
    public MainMenu(){
        Configuration.setLanguageText();
        
        VBox menuVBox = new VBox();
        menuVBox.setSpacing(PADDING);
        menuVBox.setAlignment(Pos.CENTER);
        
        Text title = new Text(Main.getGAME_TITLE());
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
        getChildren().add(menuVBox);
    }

    public byte getPADDING() {
        return PADDING;
    }

    public Button getPlayButton() {
        return playButton;
    }

    public Button getInstructionsButton() {
        return instructionsButton;
    }

    public Button getConfigButton() {
        return configButton;
    }

    public Button getCreditsButton() {
        return creditsButton;
    }

    public Button getExitButton() {
        return exitButton;
    }
    
    public void makeButtonsInteract(){
        playButton.setOnAction(e -> 
            {
                Main.getScene().setRoot(Main.getRoot());
                Main.createNodes();
                Main.addNodes();
                Main.manageSprites();
                Main.startGameLoop();
            }
        );
        instructionsButton.setOnAction(e -> 
            {
                Main.getRoot().getChildren().clear();
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
    
}
