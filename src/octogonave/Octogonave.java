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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
 * Clase principal.
 * @author Jorge Maldonado Ventura
 */
public class Octogonave extends Application {
    private static final short WIDTH = 640;
    private static final short HEIGHT = 480;
    private static final String gameTitle = "Octogonave";
    private ArrayList<String> text;
    private static final File settingsFile = new File("settings.xml");
    
    private Button playButton, instructionsButton, configButton, creditsButton, exitButton;
    private Group root;
    private PlayerSpacecraft octogonave;
    
    private Image octnave1;
    private Scene scene;
    private SpriteManager spriteManager;
    private String playText;
    private String instructionsText;
    private String configText;
    private String creditsText;
    private String exitText;
    private String languageLabelText;

    @Override
    public void start(Stage primaryStage) {
        
        loadConfig();
        root = new Group();
        scene = new Scene(root, WIDTH, HEIGHT);   
        primaryStage.setTitle(gameTitle);
        primaryStage.setScene(scene);
        primaryStage.show();
        //primaryStage.setFullScreen(true);
        
        createMainMenu();
        makeMainMenuInteract();
    }
    /**
     * Carga todas las imágenes utilizadas en el juego.
     */
    private void loadImages(){
        octnave1 = new Image("/octogonaveEstatica.png", 270, 268, true, false, true);
    }
    /**
     * Crea los <i>sprites</i> utilizados en el juego.
     */
    private void createActors(){
        octogonave = new PlayerSpacecraft(scene, "", 45, 45, octnave1);
    }
    /**
     * Añade los Nodes de los <i>sprites</i> al Group principal.
     */
    private void addActorNodes(){
        root.getChildren().add(octogonave.getSpriteFrame());
    }
    private void manageSprites(){
        spriteManager = new SpriteManager();
        spriteManager.addToCurrentActors(octogonave);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void loadConfig() {
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
            text = ToRead.readLanguageFile("lang/castellano.lang");
        } else if(languageValue.getTextContent().equals("english")){
            text = ToRead.readLanguageFile("lang/english.lang");
        } else if(languageValue.getTextContent().equals("deutsch")){
            text = ToRead.readLanguageFile("lang/deutsch.lang");
        }
    }

    private void createMainMenu() {
        playText = text.get(2);
        instructionsText = text.get(3);
        configText = text.get(4);
        creditsText = text.get(5);
        exitText = text.get(6);
        
        VBox menuVBox = new VBox();
        
        Text title = new Text(gameTitle);
        title.setId("title");
        
        playButton = new Button(playText);
        playButton.getStyleClass().add("button");    
        instructionsButton = new Button(instructionsText);
        instructionsButton.getStyleClass().add("instructions");   
        configButton = new Button(configText);
        configButton.getStyleClass().add("config");
        creditsButton = new Button(creditsText);
        creditsButton.getStyleClass().add("credits");
        exitButton = new Button(exitText);
        exitButton.getStyleClass().add("exit");
        
        menuVBox.getChildren().addAll(title, playButton, instructionsButton, configButton, creditsButton, exitButton);
        root.getChildren().add(menuVBox);
    }

    private void makeMainMenuInteract() {
        playButton.setOnAction(e -> 
            {
                loadImages();
                createActors();
                addActorNodes();
                manageSprites();
                startGameLoop();
            }
        );
        instructionsButton.setOnAction(e -> 
            {
                
            }
        );
        configButton.setOnAction(e -> 
            {
                configMenu();
                applyLanguageChange();
            }
        );
        creditsButton.setOnAction(e -> 
            {
                
            }
        );
        exitButton.setOnAction(e -> 
            {
                System.exit(0);
            }
        );
    }

    private void configMenu() {
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
                    text = ToRead.readLanguageFile("lang/castellano.lang");
                } else if(languages.getItems().get((Integer) number2) == "english"){
                    text = ToRead.readLanguageFile("lang/english.lang");
                } else if(languages.getItems().get((Integer) number2) == "deutsch"){
                    text = ToRead.readLanguageFile("lang/deutsch.lang");
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
    private void saveConfig(){
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

    private void applyLanguageChange() {
        playText = text.get(2);
        instructionsText = text.get(3);
        configText = text.get(4);
        creditsText = text.get(5);
        exitText = text.get(6);
        
        playButton.setText(playText);
        instructionsButton.setText(instructionsText);
        configButton.setText(configText);
        creditsButton.setText(creditsText);
        exitButton.setText(exitText);
    }

    private void startGameLoop() {
        GameLoop gameLoop = new GameLoop(octogonave);
        gameLoop.start();
    }
    
}
