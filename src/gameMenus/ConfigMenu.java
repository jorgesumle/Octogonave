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
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * El menú que permite modificar las configuración del juego.
 * @author Jorge Maldonado Ventura
 */
public class ConfigMenu extends GridPane{

    private static String selectedLanguage;
    private static boolean musicOn, soundsOn;
    private Label language, musicLabel, soundsLabel;
    private Button musicButton, soundsButton, backButton;
    private Text title;
    
    ConfigMenu(){
  
        setVgap(Main.getMainMenu().getPADDING());
        setHgap(10);
        setAlignment(Pos.CENTER);
        
        title = new Text(Texts.getConfigButton());
        title.getStyleClass().add("smallTitle");
        add(title, 0, 0, 2, 1);
        
        languageConfigNodes();  
        musicConfigNodes();
        soundConfigNodes();
        
        backButton = new Button(Texts.getBackButton());
        backButton.setOnAction(e ->
            {
                Main.getScene().setRoot(Main.getMainMenu());
                applyLanguageChange();
                Config.saveConfig();
            }
        );
        add(backButton, 0, 4, 2, 1);
    }

    public static boolean areSoundsOn() {
        return soundsOn;
    }

    public static boolean isMusicOn() {
        return musicOn;
    }

    public static void setMusicOn(boolean musicOn) {
        ConfigMenu.musicOn = musicOn;
    }

    public static void setSoundsOn(boolean soundsOn) {
        ConfigMenu.soundsOn = soundsOn;
    }

    public static void setSelectedLanguage(String selectedLanguage) {
        ConfigMenu.selectedLanguage = selectedLanguage;
    }
    
    

    private void applyLanguageChange() {
        Main.getMainMenu().getPLAY_BUTTON().setText(Texts.getPlayButton());
        Main.getMainMenu().getINSTRUCTIONS_BUTTON().setText(Texts.getInstructionsButton());
        Main.getMainMenu().getCONFIG_BUTTON().setText(Texts.getConfigButton());
        Main.getMainMenu().getCREDITS_BUTTON().setText(Texts.getCreditsButton());
        Main.getMainMenu().getEXIT_BUTTON().setText(Texts.getExitButton());
        language.setText(Texts.getLanguage());
        musicLabel.setText(Texts.getMusicLabel());
        soundsLabel.setText(Texts.getSoundsLabel());
        title.setText(Texts.getConfigButton());
        if(musicOn){
            musicButton.setText(Texts.getOnMusicButton());
        } else{
            musicButton.setText(Texts.getOffMusicButton());
        }
        if(soundsOn){
            soundsButton.setText(Texts.getOnSoundsButton());
        } else{
            soundsButton.setText(Texts.getOffSoundsButton());
        }
        backButton.setText(Texts.getBackButton());
    }

    private void setSelectedElement(ChoiceBox languages) {
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
    private void languageConfigNodes(){
        language = new Label(Texts.getLanguageLabel());
        add(language, 0, 1);
        
        ChoiceBox languages = new ChoiceBox<>();
        languages.getItems().add("castellano");
        languages.getItems().add("deutsch");
        languages.getItems().add("english");
        setSelectedElement(languages);
        languages.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> observableValue, Number number, Number number2) -> {
            if(languages.getItems().get((Integer) number2) == "castellano"){
                selectedLanguage = "castellano";
                Texts.setTexts(LanguageFileReader.readLanguageFile("lang/castellano.lang"));
            } else if(languages.getItems().get((Integer) number2) == "deutsch"){
                selectedLanguage = "deutsch";
                Texts.setTexts(LanguageFileReader.readLanguageFile("lang/deutsch.lang"));
            } else if(languages.getItems().get((Integer) number2) == "english"){
                selectedLanguage = "english";
                Texts.setTexts(LanguageFileReader.readLanguageFile("lang/english.lang"));
            }
            Config.saveConfig();
            applyLanguageChange();
        });
        add(languages, 1, 1);
    }
    
    /**
     * Se encarga de la creación del Label y Button relacionados con la
     * configuración de música dentro del menú de configuración del juego.
     */
    private void musicConfigNodes(){
        musicLabel = new Label(Texts.getMusicLabel());

        if(musicOn){
            musicButton = new Button(Texts.getOnMusicButton());
        } else{
            musicButton = new Button(Texts.getOffMusicButton());
        }
        musicButton.setOnAction(e -> 
            {
                if(musicOn){
                    musicButton.setText(Texts.getOffMusicButton());
                    musicOn = false;
                } else{
                    musicButton.setText(Texts.getOnMusicButton());
                    musicOn = true;
                }
            }
        );
        add(musicLabel, 0, 2);
        add(musicButton, 1, 2);
    }
    
    /**
     * Se encarga de la creación del Label y Button relacionados con la
     * configuración del sonido dentro del menú de configuración del juego.
     */
    private void soundConfigNodes(){
        soundsLabel = new Label(Texts.getSoundsLabel());

        if(soundsOn){
            soundsButton = new Button(Texts.getOnSoundsButton());
        } else{
            soundsButton = new Button(Texts.getOffSoundsButton());
        }
        soundsButton.setOnAction(e -> 
            {
                if(soundsOn){
                    soundsButton.setText(Texts.getOffSoundsButton());
                    soundsOn = false;
                } else{
                    soundsButton.setText(Texts.getOnSoundsButton());
                    soundsOn = true;
                }
            }
        );
        add(soundsLabel, 0, 3);
        add(soundsButton, 1, 3);
    }

}
