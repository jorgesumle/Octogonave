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

import java.nio.file.Paths;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


/**
 *
 * @author Jorge Maldonado Ventura 
 */
public class Sounds {
    private static final String GAME_MUSIC_URL = "Stealth Groover.aiff";
    private static MediaPlayer gameMusicPlayer; //Si no esta declarado aquí el, recolector de basura de Java lo detiene en diez segundos.
    /**
     * Reproduce la música del juego una y otra vez.
     */
    protected static void playMusic(){
        gameMusicPlayer = new MediaPlayer(new Media(Paths.get("src/"+GAME_MUSIC_URL).toUri().toString()));
        gameMusicPlayer.setOnEndOfMedia(() -> {
            gameMusicPlayer.seek(Duration.ZERO);
            gameMusicPlayer.play();
        });
        gameMusicPlayer.play();
    }
    
}
