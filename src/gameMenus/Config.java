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

    private static File settingsFile = new File("settings.xml");
    private static boolean soundsOn;
    private static String selectedLanguage;
    private static boolean musicOn;
    
    public static boolean areSoundsOn() {
        return soundsOn;
    }
    
    static String getSelectedLanguage() {
        return selectedLanguage;
    }
    
    public static boolean isMusicOn() {
        return musicOn;
    }
    
    static void setMusicOn(boolean musicOn) {
        Config.musicOn = musicOn;
    }
    
    static void setSelectedLanguage(String selectedLanguage) {
        Config.selectedLanguage = selectedLanguage;
    }
    static void setSoundsOn(boolean soundsOn) {
        Config.soundsOn = soundsOn;
    }
    
    /**
     * Carga la configuración del XML de configuración. Le asigna el valor de las
     * opciones de configuración a los atributos de la clase. Si el documento XML está
     * dañado crea otro con valores predeterminados.
     */
    public static void loadConfig() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        Document configXML = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            configXML = documentBuilder.parse(settingsFile);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            saveDefaultConfig();
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("El archivo XML de configuración se ha dañado por causas desconocidas."
                    + "Se ha creado un nuevo archivo de configuración.");
        }
        NodeList languageTag = configXML.getElementsByTagName("language");
        Node languageValue = languageTag.item(0);
        if (languageValue.getTextContent().equals("castellano")) {
            selectedLanguage = "castellano";
            Texts.setTexts(LanguageFileReader.readLanguageFile("lang/castellano.lang"));
        } else if (languageValue.getTextContent().equals("english")) {
            selectedLanguage = "english";
            Texts.setTexts(LanguageFileReader.readLanguageFile("lang/english.lang"));
        } else if (languageValue.getTextContent().equals("deutsch")) {
            selectedLanguage = "deutsch";
            Texts.setTexts(LanguageFileReader.readLanguageFile("lang/deutsch.lang"));
        }
        NodeList musicTag = configXML.getElementsByTagName("music");
        Node musicValue = musicTag.item(0);
        musicOn = musicValue.getTextContent().equals("on");
        NodeList soundsTag = configXML.getElementsByTagName("sounds");
        Node soundsValue = soundsTag.item(0);
        soundsOn = soundsValue.getTextContent().equals("on");
    }
    
    /**
     * Guarda las opciones de configuración en el XML de configuración.
     */
    static void saveConfig() {
        Document document = XMLUtils.createDocument(settingsFile.toString());
        document.getElementsByTagName("language").item(0).setTextContent(Texts.getLanguage());
        if(musicOn){
            document.getElementsByTagName("music").item(0).setTextContent("on");
        } else{
            document.getElementsByTagName("music").item(0).setTextContent("off");
        }
        if(soundsOn){
            document.getElementsByTagName("sounds").item(0).setTextContent("on");
        } else{
            document.getElementsByTagName("sounds").item(0).setTextContent("off");
        }
        XMLUtils.transform(document, settingsFile.toString());
    }
    
     /**
     * Guarda las opciones de configuración por defecto en el XML de configuración para
     * reponerse del posible error que supone un archivo de configuración dañado.
     */
    static void saveDefaultConfig() {
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
        language.appendChild(configXML.createTextNode("castellano"));
        Element music = (Element) configXML.createElement("music");
        music.appendChild(configXML.createTextNode("on"));
        Element sounds = (Element) configXML.createElement("sounds");
        sounds.appendChild(configXML.createTextNode("on"));
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
        StreamResult streamResult = new StreamResult(settingsFile);
        try {
            transformer.transform(source, streamResult);
        } catch (TransformerException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        loadConfig();
    }

}
