package com.fit3077.fierydragons;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * A class that represents the Controller for the Main Menu of the Fiery Dragons game
 *
 * Created by:
 * @author Kerk Han Chin
 */
public class MainMenuController {

    @FXML
    private Button startGameButton;

    @FXML
    private Button exitGameButton;

    @FXML
    private AnchorPane mainMenuPane;

    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * exitGame is a method that closes the game when the exit game button is pressed
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param event the ActionEvent generated upon clicking the exitGameButton
     */
    public void exitGame(ActionEvent event) {
        stage = (Stage) mainMenuPane.getScene().getWindow();
        stage.close();
    }

    /**
     * Method for clicking on the Start Game button
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param event the event object from clicking on the start game button
     * @throws IOException
     */
    public void startGame(ActionEvent event) throws IOException {
        // Swap scene to the Board Config scene
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BoardConfig.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method for clicking on the Load Game button that loads a selected save file
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param event the event object from clicking on the load game button
     * @throws IOException
     */
    public void loadGame(ActionEvent event) throws IOException {
        // Swap scene to the Game scene
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Game.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);

        // Get the game controller and load a game
        GameController gameController = fxmlLoader.getController();
        gameController.loadGame();
    }

}
