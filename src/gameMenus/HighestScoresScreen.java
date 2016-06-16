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
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * 
 * @author Jorge Maldonado Ventura
 */
class HighestScoresScreen extends GridPane{
    ArrayList<Text> adventureModeRecordHoldersTexts,
            adventureModeScoresTexts,
            arcadeModeRecordHoldersTexts,
            arcadeModeScoresTexts;
    ArrayList<String> adventureModeRecordHolders,
            adventureModeScores,
            arcadeModeRecordHolders,
            arcadeModeScores; 
    Text title;
    Button adventureMode, arcadeMode, backButton;
    boolean showingAdventureModeData;
    
    
    HighestScoresScreen(){
        showingAdventureModeData = false;
        applyStyle();
        createTitleText();
        ScoreXML.loadAdventureModeScores();
        ScoreXML.loadArcadeModeScores();
        createPlayerNamesTexts();
        createPlayerScoresTexts();
        createModesButtons();
        createBackButton();
        addNodes();
    }
    
    void setTexts(){
        title.setText(Texts.getHighestScoresButton());
        adventureMode.setText(Texts.getAdventureModeButton());
        arcadeMode.setText(Texts.getArcadeModeButton());
        backButton.setText(Texts.getBackButton());
        adventureModeRecordHolders = ScoreXML.getAdventureModeRecordHolders();
        adventureModeScores = ScoreXML.getAdventureModeScores();
        arcadeModeRecordHolders = ScoreXML.getArcadeModeRecordHolders();
        arcadeModeScores = ScoreXML.getArcadeModeScores();
        for(int i = 0; i < adventureModeRecordHolders.size(); i++){
            adventureModeRecordHoldersTexts.get(i).setText(adventureModeRecordHolders.get(i));
            adventureModeScoresTexts.get(i).setText(adventureModeScores.get(i));
            arcadeModeRecordHoldersTexts.get(i).setText(arcadeModeRecordHolders.get(i));
            arcadeModeScoresTexts.get(i).setText(arcadeModeScores.get(i));
        }
    }
    
    private void applyStyle(){
        setHgap(Main.getMainMenu().getPADDING());
        setVgap(Main.getMainMenu().getPADDING());
        setAlignment(Pos.CENTER);
        setBackground(new Background(
                        new BackgroundImage(
                                new Image("/images/backgrounds/planets.jpg", 640, 480, true, false, true),
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundRepeat.NO_REPEAT,
                                BackgroundPosition.CENTER,
                                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true))));
    }
    
    private void createTitleText(){
        title = new Text();
        title.getStyleClass().add("smallTitleWhite");
    }
    
    private void createPlayerNamesTexts(){
        adventureModeRecordHoldersTexts = new ArrayList<>();
        arcadeModeRecordHoldersTexts = new ArrayList<>();
        for(int i = 0; i < 5; i++){    
            adventureModeRecordHoldersTexts.add(new Text());
            adventureModeRecordHoldersTexts.get(i).getStyleClass().add("smallTextOnlyWhiteStrong");
            setHalignment(adventureModeRecordHoldersTexts.get(i), HPos.CENTER);
            arcadeModeRecordHoldersTexts.add(new Text());
            arcadeModeRecordHoldersTexts.get(i).getStyleClass().add("smallTextOnlyWhiteStrong");
            setHalignment(arcadeModeRecordHoldersTexts.get(i), HPos.CENTER);
        }
    }
    
    private void createPlayerScoresTexts(){
        adventureModeScoresTexts = new ArrayList<>();
        arcadeModeScoresTexts = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            adventureModeScoresTexts.add(new Text());
            adventureModeScoresTexts.get(i).getStyleClass().add("smallTextOnlyWhiteStrong");
            setHalignment(adventureModeScoresTexts.get(i), HPos.CENTER);
            arcadeModeScoresTexts.add(new Text());
            arcadeModeScoresTexts.get(i).getStyleClass().add("smallTextOnlyWhiteStrong");
            setHalignment(arcadeModeScoresTexts.get(i), HPos.CENTER);
        }
    }
    
    private void createModesButtons(){
        adventureMode = new Button();
        adventureMode.setOnAction(e -> 
            {
                if(!showingAdventureModeData){
                    showDataOfOtherMode();
                    arcadeMode.getStyleClass().remove("buttonSelected");
                    adventureMode.getStyleClass().add("buttonSelected");
                }
            }
        );
        arcadeMode = new Button();
        arcadeMode.setOnAction(e -> 
            {
                if(showingAdventureModeData){
                    showDataOfOtherMode();
                    adventureMode.getStyleClass().remove("buttonSelected");
                    arcadeMode.getStyleClass().add("buttonSelected");
                }
            }
        );
        if(showingAdventureModeData){
            adventureMode.getStyleClass().add("buttonSelected");
        } else{
            arcadeMode.getStyleClass().add("buttonSelected");
        }
    }
    
    private void createBackButton(){
        backButton = new Button();
        backButton.setOnAction(e ->
            {
                Main.getMainMenu().getStarAnimTimer().resume();
                Main.getScene().setRoot(Main.getMainMenu());
            }
        );
    }
    
    private void addNodes(){
        add(title, 0, 0, 2, 1);
        add(adventureMode, 0, 7);
        add(arcadeMode, 1, 7);
        if(showingAdventureModeData){
            for(int i = 0; i < adventureModeRecordHoldersTexts.size(); i++){
                add(adventureModeRecordHoldersTexts.get(i), 0, i + 1);
                add(adventureModeScoresTexts.get(i), 1, i + 1);
            }
        } else{
            for(int i = 0; i < arcadeModeRecordHoldersTexts.size(); i++){
                add(arcadeModeRecordHoldersTexts.get(i), 0, i + 1);
                add(arcadeModeScoresTexts.get(i), 1, i + 1);
            }
        }
        add(backButton, 0, 8, 2, 1);
    }
    
    private void showDataOfOtherMode(){
        if(showingAdventureModeData){
            showingAdventureModeData = false;
            for(int i = 0; i < adventureModeRecordHoldersTexts.size(); i++){
                add(arcadeModeRecordHoldersTexts.get(i), 0, i + 1);
                add(arcadeModeScoresTexts.get(i), 1, i + 1);
                getChildren().remove(adventureModeRecordHoldersTexts.get(i));
                getChildren().remove(adventureModeScoresTexts.get(i));
            }
        } else{
            showingAdventureModeData = true;
            for(int i = 0; i < adventureModeRecordHoldersTexts.size(); i++){
                add(adventureModeRecordHoldersTexts.get(i), 0, i + 1);
                add(adventureModeScoresTexts.get(i), 1, i + 1);
                getChildren().remove(arcadeModeRecordHoldersTexts.get(i));
                getChildren().remove(arcadeModeScoresTexts.get(i));
            }
        }
    }
}
