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
 *
 * @author Jorge Maldonado Ventura
 */
public class Instructions {
    
    public static void displayInstructions() {
        VBox instructionsMenu = new VBox();
        instructionsMenu.setAlignment(Pos.CENTER);
        
        instructionsMenu.setSpacing(Main.getMainMenu().getPADDING());
    
        Text title = new Text("Instrucciones");
        title.getStyleClass().add("smallTitle");
        
        Text gameTitle = new Text(Main.getGAME_TITLE());
        gameTitle.setStyle("-fx-font-style: italic;");
        Text instructionsText = new Text(Configuration.getInstructionsText());
        
        TextFlow instructions = new TextFlow(gameTitle, instructionsText);
        instructionsMenu.setMargin(instructions, new Insets(6));
        
        Button back = new Button("Atrás");
        back.setOnAction(e ->
            {
                Main.getScene().setRoot(Main.getMainMenu());
            }
        );
        
        instructionsMenu.getChildren().addAll(title, instructions, back);
        Main.getScene().setRoot(instructionsMenu);
    }
}
