/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gameMenus;

import gameElements.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * La pantalla que muestra las instrucciones del juego.
 * @author Jorge Maldonado Ventura
 */
public class InstructionsMenu extends VBox{
    
    public InstructionsMenu(){
        setAlignment(Pos.CENTER);
        setSpacing(Main.getMainMenu().getPADDING());
        
        Text title = new Text(ConfigMenu.getInstructionsButtonText());
        title.getStyleClass().add("smallTitle");
        
        Text gameTitle = new Text(Main.getGAME_TITLE());
        gameTitle.setStyle("-fx-font-style: italic;");
        Text instructionsText = new Text(ConfigMenu.getInstructionsText());
        
        TextFlow instructions = new TextFlow(gameTitle, instructionsText);
        setMargin(instructions, new Insets(6));
        
        Button back = new Button(ConfigMenu.getBackButtonText());
        back.setOnAction(e ->
            {
                Main.getScene().setRoot(Main.getMainMenu());
            }
        );
        getChildren().addAll(title, instructions, back);
    }
    
}
