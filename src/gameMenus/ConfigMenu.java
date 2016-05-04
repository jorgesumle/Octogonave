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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
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
 *
 * @author Jorge Maldonado Ventura
 */
public class ConfigMenu {
    private static final File SETTINGS_FILE = new File("settings.xml");
    private static ArrayList<String> text;
    private static String playButtonText, instructionsButtonText, configButtonText, creditsButtonText, 
            exitButtonText, languageLabelText;
    public static String selectedLanguage;
    private static boolean musicOn;
    private static GridPane configMenu;

    public static boolean isMusicOn() {
        return musicOn;
    }

    public static String getPlayButtonText() {
        return playButtonText;
    }

    public static String getInstructionsButtonText() {
        return instructionsButtonText;
    }

    public static String getConfigButtonText() {
        return configButtonText;
    }

    public static String getCreditsButtonText() {
        return creditsButtonText;
    }

    public static String getExitButtonText() {
        return exitButtonText;
    }

    public static String getLanguageLabelText() {
        return languageLabelText;
    }
    
    public static String getInstructionsText(){
        return text.get(11);
    }
    
    public static String getBackButtonText(){
        return text.get(12);
    }
    
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
        if(languageValue.getTextContent().equals("castellano")){
            selectedLanguage = "castellano";
            text = LanguageFileReader.readLanguageFile("lang/castellano.lang");
        } else if(languageValue.getTextContent().equals("english")){
            selectedLanguage = "english";
            text = LanguageFileReader.readLanguageFile("lang/english.lang");
        } else if(languageValue.getTextContent().equals("deutsch")){
            selectedLanguage = "deutsch";
            text = LanguageFileReader.readLanguageFile("lang/deutsch.lang");
        }
        
        NodeList musicTag = configXML.getElementsByTagName("music");
        Node musicValue = musicTag.item(0);
        musicOn = musicValue.getTextContent().equals("on");
    }
    
    public static void configMenu() {
        configMenu = new GridPane();
        configMenu.setVgap(Main.getMainMenu().getPADDING());
        configMenu.setHgap(10);
        configMenu.setAlignment(Pos.CENTER);
        Main.getScene().setRoot(configMenu);
        
        Text title = new Text(configButtonText);
        title.getStyleClass().add("smallTitle");
        configMenu.add(title, 0, 0, 2, 1);
        
        languageConfigNodes();  
        musicConfigNodes();
        
        Button back = new Button(getBackButtonText());
        back.setOnAction(e ->
            {
                Main.getScene().setRoot(Main.getMainMenu());
                applyLanguageChange();
                saveConfig();
            }
        );
        configMenu.add(back, 0, 3, 2, 1);
    }
    private static void saveConfig(){
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
        switch (text.get(0)) {
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
        if(musicOn){
            music.appendChild(configXML.createTextNode("on"));
        } else{
            music.appendChild(configXML.createTextNode("off"));
        }
        
        root.appendChild(language);
        root.appendChild(music);
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

    public static void applyLanguageChange() {
        setLanguageText();
        
        Main.getMainMenu().getPLAYBUTTON().setText(playButtonText);
        Main.getMainMenu().getINSTRUCTIONSBUTTON().setText(instructionsButtonText);
        Main.getMainMenu().getCONFIGBUTTON().setText(configButtonText);
        Main.getMainMenu().getCREDITSBUTTON().setText(creditsButtonText);
        Main.getMainMenu().getEXITBUTTON().setText(exitButtonText);
    }
    public static void setLanguageText(){
        playButtonText = text.get(2);
        instructionsButtonText = text.get(3);
        configButtonText = text.get(4);
        creditsButtonText = text.get(5);
        exitButtonText = text.get(6);
    }

    private static void setSelectedElement(ChoiceBox languages) {
        switch(selectedLanguage){
            case "castellano":
                languages.getSelectionModel().select(0);
                break;
            case "deutsch":
                languages.getSelectionModel().select(1);
                break;
            case "english":
                languages.getSelectionModel().select(2);
                break;
        }
    }
    
    /**
     * Se encarga de la creación del Label y ChoiceBox relacionados con la
     * configuración del idioma dentro del menú de configuración del juego.
     */
    private static void languageConfigNodes(){
        Label language = new Label(text.get(7));
        configMenu.add(language, 0, 1);
        
        ChoiceBox languages = new ChoiceBox<>();
        languages.getItems().add("castellano");
        languages.getItems().add("deutsch");
        languages.getItems().add("english");
        setSelectedElement(languages);
        languages.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                if(languages.getItems().get((Integer) number2) == "castellano"){
                    selectedLanguage = "castellano";
                    text = LanguageFileReader.readLanguageFile("lang/castellano.lang");
                } else if(languages.getItems().get((Integer) number2) == "deutsch"){
                    selectedLanguage = "deutsch";
                    text = LanguageFileReader.readLanguageFile("lang/deutsch.lang");
                } else if(languages.getItems().get((Integer) number2) == "english"){
                    selectedLanguage = "english";
                    text = LanguageFileReader.readLanguageFile("lang/english.lang");
                } 
                saveConfig();
            }
        });
        configMenu.add(languages, 1, 1);
    }
    
    /**
     * Se encarga de la creación del Label y Button relacionados con la
     * configuración de música dentro del menú de configuración del juego.
     */
    private static void musicConfigNodes(){
        Label musicLabel = new Label(text.get(8));
        Button musicButton;
        if(musicOn){
            musicButton = new Button(text.get(9));
        } else{
            musicButton = new Button(text.get(10));
        }
        musicButton.setOnAction(e -> 
            {
                if(musicOn){
                    musicButton.setText(text.get(10));
                    musicOn = false;
                } else{
                    musicButton.setText(text.get(9));
                    musicOn = true;
                }
            }
        );
        configMenu.add(musicLabel, 0, 2);
        configMenu.add(musicButton, 1, 2);
    }
}