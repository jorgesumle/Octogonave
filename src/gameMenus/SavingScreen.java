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
import java.util.Calendar;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 *
 * @author Jorge Maldonado Ventura
 */
public class SavingScreen extends GridPane implements Window{
    private Text title, nameText, gameNameText;
    private TextField nameTextField, gameNameTextField;
    private Button saveButton, backButton;
    
    public SavingScreen(){
        applyStyle();
        createTitleText();
        createPlayerNameText();
        createPlayerNameTextField();
        createGameNameText();
        createGameNameTextField();
        createSaveButton();
        createBackButton();
        setTexts();
        addNodes();
    }
    
    public void applyStyle(){
        setHgap(Main.getMainMenu().getPADDING());
        setVgap(Main.getMainMenu().getPADDING());
        setAlignment(Pos.CENTER);
    }
    
    private void createTitleText(){
        title = new Text();
        title.getStyleClass().add("smallTitle");
    }
    
    private void createPlayerNameText(){
        nameText = new Text();
        nameText.getStyleClass().add("text");
    }
    
    private void createPlayerNameTextField(){
        nameTextField = new TextField(System.getProperty("user.name"));
        nameTextField.textProperty().addListener((observable, oldValue, newValue) -> 
            {
                if(newValue.length() > 20){
                    nameTextField.setText(nameTextField.getText().substring(0, 20));    
                    new Alert(Alert.AlertType.INFORMATION, "No puedes guardar un nombre con más de 20 letras").showAndWait();  
                }
            }
        );
    }
    
    private void createGameNameText(){
        gameNameText = new Text();
        gameNameText.getStyleClass().add("text");
    }
    
    private void createGameNameTextField(){
        gameNameTextField = new TextField();
        gameNameTextField.textProperty().addListener((observable, oldValue, newValue) -> 
            {
                if(newValue.length() > 20){
                    gameNameTextField.setText(gameNameTextField.getText().substring(0, 20));    
                    new Alert(Alert.AlertType.INFORMATION, "No puedes guardar un nombre de partida con más de 20 letras").showAndWait();  
                }
            }
        );
    }
    
    private void createSaveButton(){
        saveButton = new Button();
        saveButton.setOnAction(e -> {
            Main.getScene().setRoot(new SavedGamesScreen(Calendar.getInstance(), nameTextField.getText(), gameNameTextField.getText()));
        });
    }
    
    private void createBackButton(){
        backButton = new Button();
        backButton.setOnAction(e -> {
            Main.getScene().setRoot(Main.getRoot());
            Main.getMainMenu().getGame().setSaving(false);
        });
    }
    
    public void addNodes(){
        add(title, 0, 0, 2, 1);
        add(nameText, 0, 1);
        add(nameTextField, 1, 1);
        add(gameNameText, 0, 2);
        add(gameNameTextField, 1, 2);
        add(saveButton, 0, 3);
        add(backButton, 1, 3);
    }

    @Override
    public void setTexts() {
        title.setText(Texts.getSavingScreen());
        nameText.setText(Texts.getOptionalEnterYourName());
        gameNameText.setText(Texts.getOptionalEnterGameName());
        saveButton.setText(Texts.getSave());
        backButton.setText(Texts.getBackButton());
    }
    
}
