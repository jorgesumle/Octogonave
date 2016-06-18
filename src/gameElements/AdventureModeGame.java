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

import gameMenus.Texts;

/**
 *
 * @author Jorge Maldonado Ventura
 */
public class AdventureModeGame extends Game{
    
    public AdventureModeGame(int level) {
        super();
        Levels.initAnyLevelTimeline();
        switch(level){
            case 1:
                Levels.newLevelTransition(Texts.getLevel1Text(), 1);
                Levels.setCurrentLevel((byte)1);
                break;
            case 2:
                Levels.newLevelTransition(Texts.getLevel2Text(), 2);
                Levels.setCurrentLevel((byte)2);
                break;
            case 3:
                Levels.newLevelTransition(Texts.getLevel3Text(), 3);
                Levels.setCurrentLevel((byte)3);
                break;
            case 4:
                Levels.newLevelTransition(Texts.getLevel4Text(), 4);
                Levels.setCurrentLevel((byte)4);
                break;
            case 5:
                Levels.newLevelTransition(Texts.getLevel5Text(), 5);
                Levels.setCurrentLevel((byte)5);
                break;
            default:
                System.out.println("Error, no existe ese nivel.");
                break;
        }
    }
    
    @Override
    protected void pause(){
        super.pause();
        Levels.pauseAdventureModeTimers();
    }
    
    @Override
    protected void resume(){
        super.resume();
        Levels.resumeAdventureModeTimers();
    }
}
