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

import gameElements.Main;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Jorge Maldonado Ventura
 */
public class ScoreXML {
    
    private static final String HIGHESTS_SCORES_FILE = "highestsScores.xml";
    public static ArrayList<String> scores;
    public static ArrayList<String> recordHolders;

    public static ArrayList<String> getScores() {
        return scores;
    }

    public static ArrayList<String> getRecordHolders() {
        return recordHolders;
    }

    public static void setRecordHolders(ArrayList<String> recordHolders) {
        ScoreXML.recordHolders = recordHolders;
    }

    public static void setScores(ArrayList<String> scores) {
        ScoreXML.scores = scores;
    }
    
    /**
     * Carga las mejores puntuaciones del archivo XML de mejores puntuaciones.
     */
    public static void load() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        Document configXML = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            configXML = documentBuilder.parse(HIGHESTS_SCORES_FILE);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        NodeList highestsScores = configXML.getElementsByTagName("points");
        NodeList recordHoldersNodeList = configXML.getElementsByTagName("player");
        
        scores = new ArrayList<>();
        recordHolders = new ArrayList<>();
        for(byte i = 0; i < highestsScores.getLength(); i++){
            scores.add(i, highestsScores.item(i).getTextContent());
            recordHolders.add(i, recordHoldersNodeList.item(i).getTextContent());
        }
    }
    
    /**
     * Guarda las mejores puntuaciones en el archivo XML de mejores puntuaciones.
     */
    public static void save() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(HIGHESTS_SCORES_FILE);
            for(byte i = 0; i < scores.size(); i++){
                document.getElementsByTagName("points").item(i).setTextContent(scores.get(i));
                document.getElementsByTagName("player").item(i).setTextContent(recordHolders.get(i));
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(HIGHESTS_SCORES_FILE));
            transformer.transform(source, result);
        } catch (SAXException | ParserConfigurationException | IOException | TransformerConfigurationException ex) {
            Logger.getLogger(ScoreXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(ScoreXML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
