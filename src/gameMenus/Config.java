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
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Contiene métodos para cargar y guardar la configuración del juego.
 * @author Jorge Maldonado Ventura
 */
public class Config {

    private static final File SETTINGS_FILE = new File("settings.xml");
    
    public static void loadConfig() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        Document configXML = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            configXML = documentBuilder.parse(SETTINGS_FILE);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        NodeList languageTag = configXML.getElementsByTagName("language");
        Node languageValue = languageTag.item(0);
        if (languageValue.getTextContent().equals("castellano")) {
            ConfigMenu.setSelectedLanguage("castellano");
            Texts.setTexts(LanguageFileReader.readLanguageFile("lang/castellano.lang"));
        } else if (languageValue.getTextContent().equals("english")) {
            ConfigMenu.setSelectedLanguage("english");
            Texts.setTexts(LanguageFileReader.readLanguageFile("lang/english.lang"));
        } else if (languageValue.getTextContent().equals("deutsch")) {
            ConfigMenu.setSelectedLanguage("deutsch");
            Texts.setTexts(LanguageFileReader.readLanguageFile("lang/deutsch.lang"));
        }
        NodeList musicTag = configXML.getElementsByTagName("music");
        Node musicValue = musicTag.item(0);
        ConfigMenu.setMusicOn(musicValue.getTextContent().equals("on"));
        NodeList soundsTag = configXML.getElementsByTagName("sounds");
        Node soundsValue = soundsTag.item(0);
        ConfigMenu.setSoundsOn(soundsValue.getTextContent().equals("on"));
    }

    static void saveConfig() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        Document configXML = documentBuilder.newDocument();
        Element root = (Element) configXML.createElement("settings");
        Element language = (Element) configXML.createElement("language");
        switch (Texts.getLanguage()) {
            case "castellano":
                language.appendChild(configXML.createTextNode("castellano"));
                break;
            case "english":
                language.appendChild(configXML.createTextNode("english"));
                break;
            case "deutsch":
                language.appendChild(configXML.createTextNode("deutsch"));
                break;
        }
        Element music = (Element) configXML.createElement("music");
        if (ConfigMenu.isMusicOn()) {
            music.appendChild(configXML.createTextNode("on"));
        } else {
            music.appendChild(configXML.createTextNode("off"));
        }
        Element sounds = (Element) configXML.createElement("sounds");
        if (ConfigMenu.areSoundsOn()) {
            sounds.appendChild(configXML.createTextNode("on"));
        } else {
            sounds.appendChild(configXML.createTextNode("off"));
        }
        root.appendChild(language);
        root.appendChild(music);
        root.appendChild(sounds);
        configXML.appendChild(root);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        DOMSource source = new DOMSource(configXML);
        StreamResult streamResult = new StreamResult(SETTINGS_FILE);
        try {
            transformer.transform(source, streamResult);
        } catch (TransformerException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
