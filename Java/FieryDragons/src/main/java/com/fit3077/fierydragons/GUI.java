package com.fit3077.fierydragons;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * A class that launches the GUI for the Fiery Dragons Game
 *
 * Created by:
 * @author Kerk Han Chin
 */
public class GUI extends Application {
    /**
     * Method that starts the Fiery Dragons Game and displays, first and foremost, the Main Menu
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param stage the stage object for the Fiery Dragons Game
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        // Set the scene to the Main Menu scene
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Fiery Dragons");
        stage.setScene(scene);
        stage.show();
    }
}
