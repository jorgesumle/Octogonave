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
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 *
 * @author Jorge Maldonado Ventura
 */
class CreditsScreen extends VBox{
    Text title, programmingTag, jorge;
    Button backButton;
    TextFlow programming;
    CreditsScreen(){
        setAlignment(Pos.CENTER);
        setSpacing(10);
        title = new Text();
        title.setId("smallTitle");
        
        jorge = new Text("Jorge Maldonado Ventura");
        programmingTag = new Text();
        programming = new TextFlow(programmingTag, jorge);
        
        
        Text license = new Text();
        
        backButton = new Button();
        backButton.setOnAction(e ->
            {
                Main.getScene().setRoot(Main.getMainMenu());
            }
        );
        setTexts();
        getChildren().addAll(title, programming, backButton);
    }
    
    private void setTexts(){
        title.setText(Texts.getCreditsButton());
        backButton.setText(Texts.getBackButton());
    }
}
