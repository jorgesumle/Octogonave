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
    private final Button playButton, instructionsButton, configButton, creditsButton, exitButton;
    private Game game;
    private ConfigMenu configMenu;
    private InstructionsMenu instructionsMenu;
    private Text title;
    
    public MainMenu(){
        VBox menuVBox = new VBox();
        PADDING = 10;
        menuVBox.setSpacing(PADDING);
        menuVBox.setAlignment(Pos.CENTER);
        title = new Text();
        title.setId("title");
        playButton = new Button();
        playButton.getStyleClass().add("button");    
        instructionsButton = new Button();
        configButton = new Button();
        creditsButton = new Button();
        exitButton = new Button();
        setTexts();
        menuVBox.getChildren().addAll(title, playButton, instructionsButton, configButton, creditsButton, exitButton);
        getChildren().add(menuVBox);
    }

    public Game getGame() {
        return game;
    }
    
    byte getPADDING() {
        return PADDING;
    }

    public InstructionsMenu getInstructionsMenu() {
        return instructionsMenu;
    }

    Button getPlayButton() {
        return playButton;
    }

    Button getInstructionsButton() {
        return instructionsButton;
    }

    Button getConfigButton() {
        return configButton;
    }

    Button getCreditsButton() {
        return creditsButton;
    }

    Button getExitButton() {
        return exitButton;
    }
    
    /**
     * Aporta funcionalidad a los botones del menú principal.
     */
    public void makeButtonsInteract(){
        playButton.setOnAction(e -> 
            {
                Main.getScene().setRoot(Main.getRoot());
                game = new Game();
            }
        );
        instructionsButton.setOnAction(e -> 
            {
                if(instructionsMenu == null){
                    instructionsMenu = new InstructionsMenu();
                }
                Main.getRoot().getChildren().clear();
                Main.getScene().setRoot(instructionsMenu);
            }
        );
        configButton.setOnAction(e -> 
            {
                if(configMenu == null){
                    configMenu = new ConfigMenu();
                }
                Main.getRoot().getChildren().clear();
                Main.getScene().setRoot(configMenu);
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
     * Asigna el texto de las instancias de <tt>Node</tt> que contienen texto.
     */
    void setTexts(){
        title.setText(Texts.getProgramTitle());
        playButton.setText(Texts.getPlayButton());
        instructionsButton.setText(Texts.getInstructionsButton());
        configButton.setText(Texts.getConfigButton());
        creditsButton.setText(Texts.getCreditsButton());
        exitButton.setText(Texts.getExitButton());
    }
    
}
