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

import gameElements.Levels;
import gameElements.Main;
import java.util.ArrayList;
import java.util.Calendar;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Contiene los métodos necesarios para cargar y guardar partidas.
 * @author Jorge Maldonado Ventura 
 */
class SavedGamesXML {
    private final static String SAVED_GAMES_XML_PATH = "savedGames.xml";
    
    static void save(int gameNumber, Calendar date, String playerName, String gameName){
        Document document = XMLUtils.createDocument(SAVED_GAMES_XML_PATH);
        Node savedGame = document.getElementsByTagName("savedGame").item(gameNumber);
        ((Element)savedGame).getElementsByTagName("date").item(0).setTextContent(String.valueOf(date.getTimeInMillis()));
        ((Element)savedGame).getElementsByTagName("level").item(0).setTextContent(String.valueOf(Levels.getCurrentLevel() + 1));
        ((Element)savedGame).getElementsByTagName("player").item(0).setTextContent(playerName);
        ((Element)savedGame).getElementsByTagName("gameName").item(0).setTextContent(gameName);
        ((Element)savedGame).getElementsByTagName("score").item(0).setTextContent(String.valueOf(Main.getMainMenu().getGame().getScore().getScore()));
        XMLUtils.transform(document, SAVED_GAMES_XML_PATH);
    }
    
    /**
     * Borra la partida indicada.
     * @param gameNumber el número de la partida que se quiere borrar.
     */
    static void delete(int gameNumber){
        Document document = XMLUtils.createDocument(SAVED_GAMES_XML_PATH);
        Node savedGame = document.getElementsByTagName("savedGame").item(gameNumber);
        ((Element)savedGame).getElementsByTagName("date").item(0).setTextContent("");
        ((Element)savedGame).getElementsByTagName("level").item(0).setTextContent("");
        ((Element)savedGame).getElementsByTagName("player").item(0).setTextContent("");
        ((Element)savedGame).getElementsByTagName("gameName").item(0).setTextContent("");
        ((Element)savedGame).getElementsByTagName("score").item(0).setTextContent("");
        XMLUtils.transform(document, SAVED_GAMES_XML_PATH);
    }
    
    /**
     * Devuelve <tt>true</tt> si no hay ninguna partida en el elemento seleccionado
     * del XML de partidas guardadas; <tt>false</tt> en caso contrario.
     * @param game la partida guardada seleccionada.
     * @return <tt>true</tt> si no hay ninguna partida en el elemento seleccionado
     * del XML de partidas guardadas; <tt>false</tt> en caso contrario.
     */
    static boolean isEmpty(int game){
        Document document = XMLUtils.createDocument(SAVED_GAMES_XML_PATH);
        Node savedGame = document.getElementsByTagName("savedGame").item(game);
        String date = ((Element)savedGame).getElementsByTagName("date").item(0).getTextContent();
        String level = ((Element)savedGame).getElementsByTagName("level").item(0).getTextContent();
        String player = ((Element)savedGame).getElementsByTagName("player").item(0).getTextContent();
        String gameName = ((Element)savedGame).getElementsByTagName("gameName").item(0).getTextContent();
        String score = ((Element)savedGame).getElementsByTagName("score").item(0).getTextContent();
        return date.equals("") && level.equals("") && player.equals("") && gameName.equals("") && score.equals("");
    }
    
    /**
     * Devuelve los metadatos de la partida del XML de partidas guardadas seleccionada.
     * @param game la partida del XML seleccionada.
     * @return una lista de los metadatos de la partida seleccionada.
     */
    static ArrayList<String> retrieveSavedGameMeta(int game){
        Document document = XMLUtils.createDocument(SAVED_GAMES_XML_PATH);
        Node savedGame = document.getElementsByTagName("savedGame").item(game);
        String date = ((Element)savedGame).getElementsByTagName("date").item(0).getTextContent();
        String level = ((Element)savedGame).getElementsByTagName("level").item(0).getTextContent();
        String player = ((Element)savedGame).getElementsByTagName("player").item(0).getTextContent();
        String gameName = ((Element)savedGame).getElementsByTagName("gameName").item(0).getTextContent();
        String score = ((Element)savedGame).getElementsByTagName("score").item(0).getTextContent();
        ArrayList<String> meta = new ArrayList<>();
        meta.add(date);
        meta.add(level);
        meta.add(player);
        meta.add(gameName);
        meta.add(score);
        return meta;
    }
    
}
