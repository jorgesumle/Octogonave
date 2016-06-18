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

import gameElements.AdventureModeGame;
import gameElements.Main;
import gameElements.Score;
import java.util.ArrayList;
import java.util.Calendar;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author Jorge Maldonado Ventura
 */
public class LoadGameMenu extends GridPane implements Window{
    
    private ArrayList<VBox> savedGames;
    private final byte NUMBER_OF_SAVED_GAMES = 3;
    private Text title;
    private Button backButton;
    
    public LoadGameMenu(){
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
                if(!SavedGamesXML.isEmpty(savedGames.indexOf(savedGame))){
                    ArrayList<String> gameMeta = SavedGamesXML.retrieveSavedGameMeta(savedGames.indexOf(savedGame));
                    int level = Integer.parseInt(gameMeta.get(1));
                    Main.getScene().setRoot(Main.getRoot());
                    Main.getMainMenu().setGame(new AdventureModeGame(level));
                    Score score = Main.getMainMenu().getGame().getScore();
                    score.setScore(Long.parseLong(gameMeta.get(4)));
                    score.updateScoreText();
                    SavedGamesXML.delete(savedGames.indexOf(savedGame));
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
            Main.getScene().setRoot(Main.getMainMenu());
        });
    }

    @Override
    public void setTexts() {
        title.setText(Texts.getChooseAGame());
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
                            ((Text)savedGames.get(i).getChildren().get(j)).setText("Nivel " + savedGameMeta.get(j));
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
    public void addNodes() {
        add(title, 0, 0, 3, 1);
        for(byte i = 0; i < NUMBER_OF_SAVED_GAMES; i++){
            add(savedGames.get(i), i, 1);
        }
        add(backButton, 1, 2);
    }
    
}
