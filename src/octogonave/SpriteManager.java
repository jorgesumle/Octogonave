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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Se encarga de la administración de todos los elementos interactivos del juego.
 * @author Jorge Maldonado Ventura 
 */
public class SpriteManager {
    private final List<Sprite> CURRENT_SPRITES;
    private final List<Sprite> REMOVED_SPRITES;
    public SpriteManager(){
        CURRENT_SPRITES = new ArrayList<>();
        REMOVED_SPRITES = new ArrayList<>();
    }

    public List<Sprite> getCURRENT_SPRITES() {
        return CURRENT_SPRITES;
    }
    /**
     * Añade un <i>sprite</i> a la lista de <i>sprites</i>: {@code(List<Actor>}.
     * @param sprites el <i>sprite</i> que se desea añadir.
     */
    public void addToCurrentSprites(Sprite... sprites){
        CURRENT_SPRITES.addAll(Arrays.asList(sprites));
    }
    /**
     * Borra un <i>sprite</i>  de la lista de <i>sprites</i>: {@code(List<Actor>}.
     * @param sprites el <i>sprite</i> que se desea eliminar.
     */
    public void removeFromCurrentSprites(Sprite... sprites){
        CURRENT_SPRITES.removeAll(Arrays.asList(sprites));
        REMOVED_SPRITES.addAll(Arrays.asList(sprites));
    }
    /**
     * Borra todos los <i>sprites</i> de la lista de <i>sprites</i>: {@code(List<Actor>}.
     */
    public void resetCurrentSprites(){
        CURRENT_SPRITES.clear();
    }
}
