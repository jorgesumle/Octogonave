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

import javafx.animation.AnimationTimer;

/**
 * El bucle del juego. Hereda de la clase AnimationTimer, lo cual
 * permite que se actualice (en condiciones idílicas) cada fotograma siempre que
 * está en marcha.
 * @author Jorge Maldonado Ventura 
 */
public class GameLoop extends AnimationTimer{
    private final PlayerSpacecraft octogonave;
    private SpriteManager spriteManager;
    public GameLoop(PlayerSpacecraft octogonave, SpriteManager spriteManager){
        this.octogonave = octogonave;
        this.spriteManager = spriteManager;
    }
    /**
     * Este código se ejecuta cada fotograma mientras el AnimationTimer este
     * activo.
     * @param now El registro del pulso (fotograma) actual en nanosegundos. 
     * Este valor es el mismo para todos losl AnimationTimers llamados en el mismo pulso. 
     */
    @Override
    public void handle(long now) {
        octogonave.update();
        spriteManager.getCURRENT_SPRITES().stream().forEach((sprite) -> {
            sprite.update();
        });
    }
    /**
     * Empieza el AnimationTimer. Una vez empezado, el método handle(long) de 
     * este AnimationTimer seré llamado en cada fotograma. El AnimationTimer 
     * puede ser parado llamando al método {@link #stop()}.
     */
    @Override
    public void start() {
        super.start();
    }
    /**
     * Para el AnimationTimer. Puede ser activado de nuevo llamando a {@link #start()}.
     */
    @Override
    public void stop(){
        super.stop();
    }
}
