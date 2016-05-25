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

import gameElements.Main;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

/**
 * La pantalla que muestra las instrucciones del juego.
 * @author Jorge Maldonado Ventura
 */
class InstructionsScreen extends VBox{
    
    private Text titleText, gameTitleText, instructionsText;
    private Button backButton;
    private TextFlow instructions;
    
    InstructionsScreen(){
        applyLayoutStyle();
        createTitleText();
        createInstructionsTextFlow();
        createBackButton();
        setTexts();
        getChildren().addAll(titleText, instructions, backButton);
    }
    
    private void applyLayoutStyle(){
        setAlignment(Pos.CENTER);
        setSpacing(Main.getMainMenu().getPADDING());
    }
    
    private void createTitleText(){
        titleText = new Text();
        titleText.getStyleClass().add("smallTitle");
    }
    
    private void createInstructionsTextFlow(){
        gameTitleText = new Text();
        gameTitleText.setStyle("-fx-font-style: italic;");
        instructionsText = new Text();
        instructions = new TextFlow(gameTitleText, instructionsText);
        instructions.getStyleClass().add("smallText");
        instructions.setTextAlignment(TextAlignment.JUSTIFY);
        setMargin(instructions, new Insets(7));
    }
    
    private void createBackButton(){
        backButton = new Button();
        backButton.setOnAction(e ->
            {
                Main.getMainMenu().getStarTimeline().play();
                Main.getScene().setRoot(Main.getMainMenu());
            }
        );
    }
    
    /**
     * Asigna el texto de las instancias de <tt>Node</tt> que contienen texto.
     */
    void setTexts() {
        titleText.setText(Texts.getInstructionsButton());
        gameTitleText.setText(Texts.getProgramTitle());
        instructionsText.setText(Texts.getInstructionsText());
        backButton.setText(Texts.getBackButton());
    }
    
}
