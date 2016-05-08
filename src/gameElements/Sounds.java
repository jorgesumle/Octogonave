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

import java.io.File;
import java.net.MalformedURLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


/**
 *
 * @author Jorge Maldonado Ventura 
 */
public class Sounds {
    private static final String GAME_MUSIC_PATH = "/Stealth Groover.aiff";
    private static MediaPlayer gameMusicPlayer; //Si no esta declarado aquí el, recolector de basura de Java lo detiene en diez segundos.
    private static final AudioClip BONUS_SOUND = new AudioClip(Sounds.class.getResource("/bonusSound.wav").toExternalForm());
    /**
     * Reproduce la música del juego una y otra vez.
     */
    protected static void playMusic(){
        gameMusicPlayer = new MediaPlayer(new Media(Sounds.class.getResource(GAME_MUSIC_PATH).toExternalForm()));
        gameMusicPlayer.setOnEndOfMedia(() -> {
            gameMusicPlayer.seek(Duration.ZERO);
            gameMusicPlayer.play();
        });
        gameMusicPlayer.play();
    }
    
    protected static void playBonusSound(){
        BONUS_SOUND.play();
    }
}
