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
package gameElements;

import javafx.scene.image.Image;

/**
 * Bonificación que reduce el tiempo de recarga.
 * @author Jorge Maldonado Ventura
 */
public class ReloadBonus extends Sprite{
    private static final Image reloadBonusImg = new Image("/reloadBonus.png", 32, 24, true, false, true);
    private static final String SVG_PATH = "M 11,0 L 11,0 26,0 37,11 37,26 27,36 11,36 0,26 0,11 Z";
    public ReloadBonus(double xLocation, double yLocation) {
        super(SVG_PATH, xLocation, yLocation, reloadBonusImg);
    }

    @Override
    void update() {}
    
}
