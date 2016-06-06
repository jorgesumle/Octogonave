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
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * El menú que permite modificar las configuración del juego.
 * @author Jorge Maldonado Ventura
 */
class ConfigMenu extends GridPane{

    private Label languageLabel, musicLabel, soundsLabel;
    private Button musicButton, soundsButton, backButton;
    private Text title;
    private ChoiceBox languageChoiceBox;
    
    ConfigMenu(){
        applyStyle();
        createTitleText();
        createLanguageConfigNodes();
        createMusicConfigNodes();
        createSoundConfigNodes();
        createBackButton();
        setTexts();
        addNodes();
    }
    
    /**
     * Aplica estilos al menú de configuración, como el fondo de pantalla, el <i>padding</i>.
     */
    private void applyStyle(){
        setVgap(Main.getMainMenu().getPADDING());
        setHgap(Main.getMainMenu().getPADDING());
        setAlignment(Pos.CENTER);
        setBackground(new Background(
                        new BackgroundImage(
                                new Image("configMenuBackground.jpg", 640, 480, true, false, true),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true))));
    }
    
    /**
     * Crea un <tt>Text</tt> para el título.
     */
    private void createTitleText(){
        title = new Text();
        title.getStyleClass().add("smallTitle");
    }
    
    /**
     * Se encarga de la creación del Label y ChoiceBox relacionados con la
     * configuración del idioma dentro del menú de configuración del juego.
     */
    private void createLanguageConfigNodes(){
        languageLabel = new Label();
        
        languageChoiceBox = new ChoiceBox<>();
        languageChoiceBox.getItems().add("castellano");
        languageChoiceBox.getItems().add("deutsch");
        languageChoiceBox.getItems().add("english");
        setLanguageChoiceBoxText();
        languageChoiceBox.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> observableValue, Number previousOption, Number selectedOption) -> {
            String selectedlanguage = languageChoiceBox.getItems().get((Integer) selectedOption).toString();
            if(selectedlanguage.equals("castellano")){
                Config.setSelectedLanguage("castellano");
                Texts.setTexts(LanguageFileReader.readLanguageFile("lang/castellano.lang"));
            } else if(selectedlanguage.equals("deutsch")){
                Config.setSelectedLanguage("deutsch");
                Texts.setTexts(LanguageFileReader.readLanguageFile("lang/deutsch.lang"));
            } else if(selectedlanguage.equals("english")){
                Config.setSelectedLanguage("english");
                Texts.setTexts(LanguageFileReader.readLanguageFile("lang/english.lang"));
            }
            Config.saveConfig();
            setTexts();
        });
    }
    
    /**
     * Elige la opción activa del <tt>ChoiceBox</tt>, según la configuración.
     */
    private void setLanguageChoiceBoxText() {
        switch(Config.getSelectedLanguage()){
            case "castellano":
                languageChoiceBox.getSelectionModel().select(0);
                break;
            case "deutsch":
                languageChoiceBox.getSelectionModel().select(1);
                break;
            case "english":
                languageChoiceBox.getSelectionModel().select(2);
                break;
        }
    }
    
    /**
     * Se encarga de la creación del Label y Button relacionados con la
     * configuración de música dentro del menú de configuración del juego.
     */
    private void createMusicConfigNodes(){
        musicLabel = new Label();

        if(Config.isMusicOn()){
            musicButton = new Button();
        } else{
            musicButton = new Button();
        }
        musicButton.setOnAction(e -> 
            {
                if(Config.isMusicOn()){
                    musicButton.setText(Texts.getOffMusicButton());
                    Config.setMusicOn(false);
                } else{
                    musicButton.setText(Texts.getOnMusicButton());
                    Config.setMusicOn(true);
                }
            }
        );
        
    }
    
    /**
     * Se encarga de la creación del Label y Button relacionados con la
     * configuración del sonido dentro del menú de configuración del juego.
     */
    private void createSoundConfigNodes(){
        soundsLabel = new Label();

        if(Config.areSoundsOn()){
            soundsButton = new Button();
        } else{
            soundsButton = new Button();
        }
        soundsButton.setOnAction(e -> 
            {
                if(Config.areSoundsOn()){
                    soundsButton.setText(Texts.getOffSoundsButton());
                    Config.setSoundsOn(false);
                } else{
                    soundsButton.setText(Texts.getOnSoundsButton());
                    Config.setSoundsOn(true);
                }
            }
        );   
    }
    
    /**
     * Crea el botón para volver al menú principal.
     */
    private void createBackButton(){
        backButton = new Button();
        backButton.setOnAction(e ->
            {
                Main.getMainMenu().getStarAnimTimer().resume();
                Main.getScene().setRoot(Main.getMainMenu());
                Main.getMainMenu().setTexts();
                Config.saveConfig(); 
            }
        );
    }
    
    /**
     * Asigna el texto de las instancias de <tt>Node</tt> que contienen texto.
     */
    private void setTexts() {
        title.setText(Texts.getConfigButton());
        languageLabel.setText(Texts.getLanguageLabel());
        musicLabel.setText(Texts.getMusicLabel());
        if(Config.isMusicOn()){
            musicButton.setText(Texts.getOnMusicButton());
        } else{
            musicButton.setText(Texts.getOffMusicButton());
        }
        soundsLabel.setText(Texts.getSoundsLabel());
        if(Config.areSoundsOn()){
            soundsButton.setText(Texts.getOnSoundsButton());
        } else{
            soundsButton.setText(Texts.getOffSoundsButton());
        }
        backButton.setText(Texts.getBackButton());
    }
    
    /**
     * Añade los nodos creados con {@code: add()} para hacerlos visibles.
     */
    private void addNodes(){
        add(title, 0, 0, 2, 1);
        add(languageLabel, 0, 1);
        add(languageChoiceBox, 1, 1);
        add(musicLabel, 0, 2);
        add(musicButton, 1, 2);
        add(soundsLabel, 0, 3);
        add(soundsButton, 1, 3);
        add(backButton, 0, 4, 2, 1);
    }

}
