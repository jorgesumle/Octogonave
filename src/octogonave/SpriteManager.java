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
    private final List<Sprite> CURRENT_ACTORS;
    private final List<Sprite> REMOVED_ACTORS;
    public SpriteManager(){
        CURRENT_ACTORS = new ArrayList<>();
        REMOVED_ACTORS = new ArrayList<>();
    }
    /**
     * Añade un <i>sprite</i> a la lista de <i>sprites</i>: {@code(List<Actor>}.
     * @param actors el <i>sprite</i> que se desea añadir.
     */
    public void addToCurrentActors(Sprite... actors){
        CURRENT_ACTORS.addAll(Arrays.asList(actors));
    }
    /**
     * Borra un <i>sprite</i>  de la lista de <i>sprites</i>: {@code(List<Actor>}.
     * @param actors el <i>sprite</i> que se desea eliminar.
     */
    public void removeFromCurrentActors(Sprite... actors){
        CURRENT_ACTORS.removeAll(Arrays.asList(actors));
    }
    /**
     * Borra todos los <i>sprites</i> de la lista de <i>sprites</i>: {@code(List<Actor>}.
     */
    public void resetCurrentActors(){
        CURRENT_ACTORS.clear();
    }
}
