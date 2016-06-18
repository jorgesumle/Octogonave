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
import java.util.ArrayList;
import java.util.Calendar;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author Jorge Maldonado Ventura
 */
class SavedGamesScreen extends GridPane implements Window{
    
    private ArrayList<VBox> savedGames;
    private final byte NUMBER_OF_SAVED_GAMES = 3;
    private Calendar date;
    private String playerName, gameName;
    private Text title;
    private Button backButton;
    
    SavedGamesScreen(Calendar date, String playerName, String gameName){
        this.date = date;
        this.playerName = playerName;
        this.gameName = gameName;
        applyStyle();
        createTitle();
        createSavedGamesBoxes();
        createSavedGamesBoxesTexts();
        createBackButton();
        setTexts();
        addNodes();
    }
    
    @Override
    public void applyStyle() {
        setHgap(Main.getMainMenu().getPADDING() * 4);
        setVgap(Main.getMainMenu().getPADDING() * 4);
        setAlignment(Pos.CENTER);
    }
    
    private void createTitle(){
        title = new Text();
        title.getStyleClass().add("smallTitle");
    }
    
    private void createSavedGamesBoxes(){
        savedGames = new ArrayList<>();
        for(byte i = 0; i < NUMBER_OF_SAVED_GAMES; i++){
            savedGames.add(new VBox());
            savedGames.get(i).setAlignment(Pos.CENTER);
            savedGames.get(i).setSpacing(Main.getMainMenu().getPADDING());
            savedGames.get(i).getStyleClass().add("button");
        }
        savedGames.stream().forEach((savedGame) -> {
            savedGame.setOnMouseClicked(e ->{
                if(SavedGamesXML.isEmpty(savedGames.indexOf(savedGame))){
                    SavedGamesXML.save(savedGames.indexOf(savedGame), date, playerName, gameName);
                    setTexts();
                } else{
                    new Alert(AlertType.CONFIRMATION, "¿Seguro que quieres sobreescribir esta partida?").showAndWait().ifPresent(response -> {
                         if (response == ButtonType.OK) {
                            SavedGamesXML.save(savedGames.indexOf(savedGame), date, playerName, gameName);
                            setTexts();
                         }
                     });
                }
            });
        });
    }
    
    private void createSavedGamesBoxesTexts(){
        for(byte i = 0; i < NUMBER_OF_SAVED_GAMES; i++){
            for(byte j = 0; j < 5; j++){
                savedGames.get(i).getChildren().add(new Text());
                savedGames.get(i).getChildren().get(j).getStyleClass().add("text");
            }
        }
    }
    
    private void createBackButton(){
        backButton = new Button();
        backButton.setOnAction(e -> {
            Main.getScene().setRoot(Main.getMainMenu().getGame().getSaveMenu());
        });
    }
    
    @Override
    public void setTexts(){
        title.setText(Texts.getSavedGames());
        setSavedGamesBoxesTexts();
        backButton.setText(Texts.getBackButton());
    }
    
    private void setSavedGamesBoxesTexts(){
        for(byte i = 0; i < NUMBER_OF_SAVED_GAMES; i++){
            if(SavedGamesXML.isEmpty(i)){
                ((Text)savedGames.get(i).getChildren().get(0)).setText(Texts.getFree());
            } else{
                ArrayList<String> savedGameMeta = SavedGamesXML.retrieveSavedGameMeta(i);
                for(byte j = 0; j < savedGameMeta.size(); j++){
                    switch (j) {
                        case 0:
                            Calendar date = Calendar.getInstance();
                            date.setTimeInMillis(Long.parseLong(savedGameMeta.get(j)));
                            ((Text)savedGames.get(i).getChildren().get(j)).setText(String.format("%d/%d/%d", date.get(Calendar.DAY_OF_MONTH), date.get(Calendar.MONTH) + 1, date.get(Calendar.YEAR)));
                            break;
                        case 1:
                            ((Text)savedGames.get(i).getChildren().get(j)).setText(Texts.getLevel() + " " + savedGameMeta.get(j));
                            break;
                        case 4:
                            ((Text)savedGames.get(i).getChildren().get(j)).setText(savedGameMeta.get(j) + " " + Texts.getPoints());
                            break;
                        default:
                            ((Text)savedGames.get(i).getChildren().get(j)).setText(savedGameMeta.get(j));
                            break;
                    }
                    savedGames.get(i).getChildren().get(j).getStyleClass().add("text");
                }
            }
        }
    }
    
    @Override
    public void addNodes(){
        add(title, 0, 0, 3, 1);
        for(byte i = 0; i < NUMBER_OF_SAVED_GAMES; i++){
            add(savedGames.get(i), i, 1);
        }
        add(backButton, 1, 2);
    }
    
}
