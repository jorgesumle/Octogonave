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

package octogonave;

import javafx.scene.Scene;
import javafx.scene.image.Image;

/**
 * Una gema. El héroe del juego las recoge para aumentar su puntuación.
 * @author Jorge Maldonado Ventura 
 */
public class Gem extends Sprite{
    /**
     * Indica si es si el <i>sprite</i> es una bonificación
     * que otorga puntos en la partida. Es {@code true} si lo es y, {@code false} 
     * en caso contrario.
     */
    protected boolean bonus;
    
    public Gem(Scene scene, String SVGData, double xLocation, double yLocation, Image... spriteImages) {
        super(scene, SVGData, xLocation, yLocation, spriteImages);
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * 
     * @return devuelve {@code true} si el <i>sprite</i> es una bonificación
     * que otorga puntos en la partida y {@code false} en caso contrario.
     */
    public boolean isBonus() {
        return bonus;
    }
    /**
     * Asigna un valor para indicar si el <i>sprite</i> es una modificación.
     * @param bonus 
     */
    public void setBonus(boolean bonus) {
        this.bonus = bonus;
    }
}
