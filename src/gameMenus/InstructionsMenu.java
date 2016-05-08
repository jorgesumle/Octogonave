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
import javafx.scene.text.TextFlow;

/**
 * La pantalla que muestra las instrucciones del juego.
 * @author Jorge Maldonado Ventura
 */
public class InstructionsMenu extends VBox{
    
    public InstructionsMenu(){
        setAlignment(Pos.CENTER);
        setSpacing(Main.getMainMenu().getPADDING());
        
        Text title = new Text(Texts.getInstructionsButton());
        title.getStyleClass().add("smallTitle");
        
        Text gameTitle = new Text(Texts.getProgramTitle());
        gameTitle.setStyle("-fx-font-style: italic;");
        Text instructionsText = new Text(Texts.getInstructionsText());
        
        TextFlow instructions = new TextFlow(gameTitle, instructionsText);
        setMargin(instructions, new Insets(6));
        
        Button back = new Button(Texts.getBackButton());
        back.setOnAction(e ->
            {
                Main.getScene().setRoot(Main.getMainMenu());
            }
        );
        getChildren().addAll(title, instructions, back);
    }
    
}
