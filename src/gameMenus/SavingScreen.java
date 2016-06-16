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
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author Jorge Maldonado Ventura
 */
class SavingScreen extends GridPane{
    private Text title;
    
    SavingScreen(){
        applyStyle();
        createTitleText();
    }
    
    private void applyStyle(){
        setHgap(Main.getMainMenu().getPADDING());
        setVgap(Main.getMainMenu().getPADDING());
        setAlignment(Pos.CENTER);
    }
    
    private void createTitleText(){
        title = new Text("Área de guardado");
        title.getStyleClass().add("smallTitleWhite");
    }
}
