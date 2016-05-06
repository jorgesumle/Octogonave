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
 * Un <i>sprite</i> que no tiene animación, es decir, siempre muestra el mismo
 * fotograma.
 * @author Jorge Maldonado Ventura
 */
public class StaticSprite extends Sprite{
    
    public StaticSprite(String SVGData, double xLocation, double yLocation, Image... spriteImages) {
        super(SVGData, xLocation, yLocation, spriteImages);
    }

    @Override
    public void update() {}
    
}
