/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package octogonave;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
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
 * @author jorge
 */
public class Configuration {
    private static final File settingsFile = new File("settings.xml");
    private static ArrayList<String> text;
    private static String playText, instructionsText, configText, creditsText, exitText, languageLabelText;

    public static String getPlayText() {
        return playText;
    }

    public static String getInstructionsText() {
        return instructionsText;
    }

    public static String getConfigText() {
        return configText;
    }

    public static String getCreditsText() {
        return creditsText;
    }

    public static String getExitText() {
        return exitText;
    }

    public static String getLanguageLabelText() {
        return languageLabelText;
    }
    
    protected static void loadConfig() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        Document configXML = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();   
            configXML = documentBuilder.parse(settingsFile);
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(Octogonave.class.getName()).log(Level.SEVERE, null, ex);
        }
        NodeList languageTag = configXML.getElementsByTagName("language");
        Node languageValue = languageTag.item(0);
        if(languageValue.getTextContent().equals("castellano")){
            text = LanguageFileReader.readLanguageFile("lang/castellano.lang");
        } else if(languageValue.getTextContent().equals("english")){
            text = LanguageFileReader.readLanguageFile("lang/english.lang");
        } else if(languageValue.getTextContent().equals("deutsch")){
            text = LanguageFileReader.readLanguageFile("lang/deutsch.lang");
        }
    }
    protected static void configMenu(Scene scene, Group root) {
        languageLabelText = text.get(7);
        GridPane configMenu = new GridPane();
        configMenu.setAlignment(Pos.CENTER);
        scene.setRoot(configMenu);
        
        Text title = new Text(configText);
        title.getStyleClass().add("smallTitle");
        configMenu.add(title, 0, 0, 2, 1);
        configMenu.setHalignment(title, HPos.CENTER);
        
        Label language = new Label(languageLabelText);
        configMenu.add(language, 0, 1);
        
        ChoiceBox languages = new ChoiceBox<>();
        languages.getItems().add("castellano");
        languages.getItems().add("english");
        languages.getItems().add("deutsch");
        languages.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                if(languages.getItems().get((Integer) number2) == "castellano"){
                    text = LanguageFileReader.readLanguageFile("lang/castellano.lang");
                } else if(languages.getItems().get((Integer) number2) == "english"){
                    text = LanguageFileReader.readLanguageFile("lang/english.lang");
                } else if(languages.getItems().get((Integer) number2) == "deutsch"){
                    text = LanguageFileReader.readLanguageFile("lang/deutsch.lang");
                } 
                saveConfig();
            }
        });
        configMenu.add(languages, 1, 1);
        
        Button back = new Button("Atrás");
        back.setOnAction(e ->
            {
                scene.setRoot(root);
                applyLanguageChange();
            }
        );
        configMenu.add(back, 0, 2, 2, 1);
    }
    private static void saveConfig(){
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();     
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Octogonave.class.getName()).log(Level.SEVERE, null, ex);
        }
        Document configXML = documentBuilder.newDocument();
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
        configXML.appendChild(language);
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = null;
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(Octogonave.class.getName()).log(Level.SEVERE, null, ex);
        }
        DOMSource source = new DOMSource(configXML);
        
        StreamResult streamResult = new StreamResult(settingsFile);
        try {
            transformer.transform(source, streamResult);
        } catch (TransformerException ex) {
            Logger.getLogger(Octogonave.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected static void applyLanguageChange() {
        setLanguageText();
        
        Octogonave.playButton.setText(playText);
        Octogonave.instructionsButton.setText(instructionsText);
        Octogonave.configButton.setText(configText);
        Octogonave.creditsButton.setText(creditsText);
        Octogonave.exitButton.setText(exitText);
    }
    protected static void setLanguageText(){
        playText = text.get(2);
        instructionsText = text.get(3);
        configText = text.get(4);
        creditsText = text.get(5);
        exitText = text.get(6);
    }
}
