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

import gameElements.ArcadeModeGame;
import gameElements.Main;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Se encarga de guardar y cargar la puntuación en el XML de máximas puntuaciones.
 * Las puntuaciones se cargan y se guardan en dos instancias de <tt>ArrayList</tt>:
 * una para las puntuaciones y otra para los jugadores que han obtenido dichas puntuaciones.
 * @author Jorge Maldonado Ventura
 */
public class ScoreXML {
    
    private static final String HIGHESTS_SCORES_FILE = "highestsScores.xml";
    private static ArrayList<String> adventureModeScores, 
            adventureModeRecordHolders, 
            arcadeModeScores,
            arcadeModeRecordHolders;
    
    
    public static ArrayList<String> getAdventureModeScores() {
        return adventureModeScores;
    }

    public static ArrayList<String> getAdventureModeRecordHolders() {
        return adventureModeRecordHolders;
    }

    public static ArrayList<String> getArcadeModeScores() {
        return arcadeModeScores;
    }
    
    public static ArrayList<String> getArcadeModeRecordHolders() {
        return arcadeModeRecordHolders;
    }
    
    public static void setArcadeModeScores(ArrayList<String> arcadeModeScores) {
        ScoreXML.arcadeModeScores = arcadeModeScores;
    }

    public static void setArcadeModeRecordHolders(ArrayList<String> arcadeModeRecordHolders) {
        ScoreXML.arcadeModeRecordHolders = arcadeModeRecordHolders;
    }

    public static void setAdventureModeRecordHolders(ArrayList<String> recordHolders) {
        ScoreXML.adventureModeRecordHolders = recordHolders;
    }

    public static void setAdventureModeScores(ArrayList<String> scores) {
        ScoreXML.adventureModeScores = scores;
    }
    
    /**
     * Carga las puntuaciones...
     */
    public static void load(){
        if(Main.getMainMenu().getGame() instanceof ArcadeModeGame){
            ScoreXML.loadArcadeModeScores();
        } else{
            ScoreXML.loadAdventureModeScores();
        }
    }
    
    /**
     * Actualiza las puntuaciones y ejecuta un método de guardado acorde al modo
     * de juego: ejecuta {@link #saveAdventureModeScores()} si la partida es del
     * modo aventura y {@link #saveArcadeModeScores()} si es del modo recreátiva.
     */
    public static void save(){
        Main.getMainMenu().getGame().getScore().updateHighestsScoreXMLValues();
        if(Main.getMainMenu().getGame() instanceof ArcadeModeGame){
            ScoreXML.saveArcadeModeScores();
        } else{
            ScoreXML.saveAdventureModeScores();
        }
    }
    
    /**
     * Carga las mejores puntuaciones del modo aventura del archivo XML de mejores puntuaciones.
     */
    public static void loadAdventureModeScores() {
        Document document = XMLUtils.createDocument(HIGHESTS_SCORES_FILE);
        NodeList adventureModeNodeList = document.getElementsByTagName("adventureMode");
        Node adventureModeNode = adventureModeNodeList.item(0);
        NodeList recordHoldersNodeList = ((Element)adventureModeNode).getElementsByTagName("player");
        NodeList scoresNodeList = ((Element)adventureModeNode).getElementsByTagName("points");
        adventureModeScores = new ArrayList<>();
        adventureModeRecordHolders = new ArrayList<>();
        for(byte i = 0; i < scoresNodeList.getLength(); i++){
            adventureModeScores.add(i, scoresNodeList.item(i).getTextContent());
            adventureModeRecordHolders.add(i, recordHoldersNodeList.item(i).getTextContent());
        }
    }
    
    /**
     * Carga las mejores puntuaciones del modo recreativa del archivo XML de mejores puntuaciones.
     */
    public static void loadArcadeModeScores() {
        Document document = XMLUtils.createDocument(HIGHESTS_SCORES_FILE);
        NodeList arcadeModeNodeList = document.getElementsByTagName("arcadeMode");
        Node arcadeModeNode = arcadeModeNodeList.item(0);
        NodeList recordHoldersNodeList = ((Element)arcadeModeNode).getElementsByTagName("player");
        NodeList scoresNodeList = ((Element)arcadeModeNode).getElementsByTagName("points");
        arcadeModeScores = new ArrayList<>();
        arcadeModeRecordHolders = new ArrayList<>();
        for(byte i = 0; i < scoresNodeList.getLength(); i++){
            arcadeModeScores.add(i, scoresNodeList.item(i).getTextContent());
            arcadeModeRecordHolders.add(i, recordHoldersNodeList.item(i).getTextContent());
        }
    }
    
    /**
     * Guarda las mejores puntuaciones del modo aventura en el archivo XML de mejores puntuaciones.
     */
    public static void saveAdventureModeScores() {
        Document document = XMLUtils.createDocument(HIGHESTS_SCORES_FILE);
        NodeList adventureModeNodeList = document.getElementsByTagName("adventureMode");
        Node adventureModeNode = adventureModeNodeList.item(0);
        NodeList recordHoldersNodeList = ((Element)adventureModeNode).getElementsByTagName("player");
        NodeList scoresNodeList = ((Element)adventureModeNode).getElementsByTagName("points");
        for(byte i = 0; i < recordHoldersNodeList.getLength(); i++){
            recordHoldersNodeList.item(i).setTextContent(adventureModeRecordHolders.get(i));
            scoresNodeList.item(i).setTextContent(adventureModeScores.get(i));
        }
        XMLUtils.transform(document, HIGHESTS_SCORES_FILE);
    }
    
    /**
     * Guarda las mejores puntuaciones del modo recreátiva en el archivo XML de mejores puntuaciones.
     */
    public static void saveArcadeModeScores() {
        Document document = XMLUtils.createDocument(HIGHESTS_SCORES_FILE);
        NodeList arcadeModeNodeList = document.getElementsByTagName("arcadeMode");
        Node arcadeModeNode = arcadeModeNodeList.item(0);
        NodeList recordHoldersNodeList = ((Element)arcadeModeNode).getElementsByTagName("player");
        NodeList scoresNodeList = ((Element)arcadeModeNode).getElementsByTagName("points");
        for(byte i = 0; i < recordHoldersNodeList.getLength(); i++){
            recordHoldersNodeList.item(i).setTextContent(arcadeModeRecordHolders.get(i));
            scoresNodeList.item(i).setTextContent(arcadeModeScores.get(i));
        }
        XMLUtils.transform(document, HIGHESTS_SCORES_FILE);
    }
    
}