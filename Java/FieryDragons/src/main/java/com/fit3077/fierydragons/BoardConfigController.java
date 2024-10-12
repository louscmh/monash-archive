package com.fit3077.fierydragons;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class BoardConfigController implements Initializable {

    @FXML
    private TextField volcanoCardCountTextField;

    @FXML
    private TextField volcanoTilesPerVolcanoCardCountTextField;

    @FXML
    private TextField cave1VolcanoCardIndexTextField;

    @FXML
    private TextField cave2VolcanoCardIndexTextField;

    @FXML
    private TextField cave3VolcanoCardIndexTextField;

    @FXML
    private TextField cave4VolcanoCardIndexTextField;

    @FXML
    private TextField cave1VolcanoTileIndexTextField;

    @FXML
    private TextField cave2VolcanoTileIndexTextField;

    @FXML
    private TextField cave3VolcanoTileIndexTextField;

    @FXML
    private TextField cave4VolcanoTileIndexTextField;

    @FXML
    private TextField playerCountTextField;

    @FXML
    private ChoiceBox<String> cave1AnimalChoiceBox;

    @FXML
    private ChoiceBox<String> cave2AnimalChoiceBox;

    @FXML
    private ChoiceBox<String> cave3AnimalChoiceBox;

    @FXML
    private ChoiceBox<String> cave4AnimalChoiceBox;

    private boolean canSetupBoard = false;
    private boolean validInputs = false;

    private Stage stage;
    private Scene scene;
    private Parent root;

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
        // Start Game will only start the game after an appropriate number of volcano cards
        // and the number of volcano tiles per volcano card has been configured

        // Extract the Volcano Card Count
        int volcanoCardCount = Integer.parseInt(volcanoCardCountTextField.getText());


        // Extract the Volcano Tiles per Volcano Card
        String volcanoTilesPerVolcanoCardString = volcanoTilesPerVolcanoCardCountTextField.getText();
        List<String> volcanoTilesPerVolcanoCardStringList = Arrays.asList(volcanoTilesPerVolcanoCardString.split(","));
        List<Integer> volcanoTilesPerVolcanoCard = new ArrayList<>();
        for (int i = 0; i < volcanoTilesPerVolcanoCardStringList.size(); i++) {
            volcanoTilesPerVolcanoCard.add(Integer.parseInt(volcanoTilesPerVolcanoCardStringList.get(i)));
        }

        // Extract the Player Count
        int playerCount = Integer.parseInt(playerCountTextField.getText());

        // Check to ensure player count is within 2-4 players
        if (playerCount > 4 || playerCount < 2) {
            return;
        }

        // Check to ensure the volcano card count is the same as the number of integers in the number of volcano tiles for each volcano card list
        if (volcanoCardCount != volcanoTilesPerVolcanoCard.size()) {
            return;
        }

        // Check to ensure that the volcano card count is at least 2
        if (volcanoCardCount < 2) {
            return;
        }

        // Check to ensure that the number of volcano cards is greater than or equal to the number of players
        if (playerCount > volcanoCardCount) {
            return;
        }

        // Extract the Cave Volcano Card Index Positions
        int cave1VolcanoCardIndex;
        int cave2VolcanoCardIndex;
        int cave3VolcanoCardIndex;
        int cave4VolcanoCardIndex;
        List<Integer> caveVolcanoCardIndex = new ArrayList<>();
        if (playerCount == 4) {
            cave1VolcanoCardIndex = Integer.parseInt(cave1VolcanoCardIndexTextField.getText());
            cave2VolcanoCardIndex = Integer.parseInt(cave2VolcanoCardIndexTextField.getText());
            cave3VolcanoCardIndex = Integer.parseInt(cave3VolcanoCardIndexTextField.getText());
            cave4VolcanoCardIndex = Integer.parseInt(cave4VolcanoCardIndexTextField.getText());

            Set<Integer> caveVolcanoCardIndexSet = new HashSet<>();
            caveVolcanoCardIndexSet.add(cave1VolcanoCardIndex);
            caveVolcanoCardIndexSet.add(cave2VolcanoCardIndex);
            caveVolcanoCardIndexSet.add(cave3VolcanoCardIndex);
            caveVolcanoCardIndexSet.add(cave4VolcanoCardIndex);

            // Ensure that all cave volcano card index positions that we look at are unique
            if (caveVolcanoCardIndexSet.size() == 4 &&
                cave1VolcanoCardIndex >= 0 && cave1VolcanoCardIndex < volcanoCardCount &&
                cave2VolcanoCardIndex >= 0 && cave2VolcanoCardIndex < volcanoCardCount &&
                cave3VolcanoCardIndex >= 0 && cave3VolcanoCardIndex < volcanoCardCount &&
                cave4VolcanoCardIndex >= 0 && cave4VolcanoCardIndex < volcanoCardCount
            ) {
                caveVolcanoCardIndex.add(cave1VolcanoCardIndex);
                caveVolcanoCardIndex.add(cave2VolcanoCardIndex);
                caveVolcanoCardIndex.add(cave3VolcanoCardIndex);
                caveVolcanoCardIndex.add(cave4VolcanoCardIndex);
            } else {
                return;
            }
        } else if (playerCount == 3) {
            cave1VolcanoCardIndex = Integer.parseInt(cave1VolcanoCardIndexTextField.getText());
            cave2VolcanoCardIndex = Integer.parseInt(cave2VolcanoCardIndexTextField.getText());
            cave3VolcanoCardIndex = Integer.parseInt(cave3VolcanoCardIndexTextField.getText());
            cave4VolcanoCardIndex = 0;

            Set<Integer> caveVolcanoCardIndexSet = new HashSet<>();
            caveVolcanoCardIndexSet.add(cave1VolcanoCardIndex);
            caveVolcanoCardIndexSet.add(cave2VolcanoCardIndex);
            caveVolcanoCardIndexSet.add(cave3VolcanoCardIndex);

            // Ensure that all 3 cave volcano card index positions that we look at are unique
            if (caveVolcanoCardIndexSet.size() == 3 &&
                cave1VolcanoCardIndex >= 0 && cave1VolcanoCardIndex < volcanoCardCount &&
                cave2VolcanoCardIndex >= 0 && cave2VolcanoCardIndex < volcanoCardCount &&
                cave3VolcanoCardIndex >= 0 && cave3VolcanoCardIndex < volcanoCardCount
            ) {
                caveVolcanoCardIndex.add(cave1VolcanoCardIndex);
                caveVolcanoCardIndex.add(cave2VolcanoCardIndex);
                caveVolcanoCardIndex.add(cave3VolcanoCardIndex);
                caveVolcanoCardIndex.add(cave4VolcanoCardIndex);
            } else {
                return;
            }
        } else if (playerCount == 2) {
            cave1VolcanoCardIndex = Integer.parseInt(cave1VolcanoCardIndexTextField.getText());
            cave2VolcanoCardIndex = Integer.parseInt(cave2VolcanoCardIndexTextField.getText());
            cave3VolcanoCardIndex = 0;
            cave4VolcanoCardIndex = 0;

            Set<Integer> caveVolcanoCardIndexSet = new HashSet<>();
            caveVolcanoCardIndexSet.add(cave1VolcanoCardIndex);
            caveVolcanoCardIndexSet.add(cave2VolcanoCardIndex);

            // Ensure that all 2 cave volcano card index positions that we look at are unique
            if (caveVolcanoCardIndexSet.size() == 2 &&
                cave1VolcanoCardIndex >= 0 && cave1VolcanoCardIndex < volcanoCardCount &&
                cave2VolcanoCardIndex >= 0 && cave2VolcanoCardIndex < volcanoCardCount
            ) {
                caveVolcanoCardIndex.add(cave1VolcanoCardIndex);
                caveVolcanoCardIndex.add(cave3VolcanoCardIndex);
                caveVolcanoCardIndex.add(cave2VolcanoCardIndex);
                caveVolcanoCardIndex.add(cave4VolcanoCardIndex);
            } else {
                return;
            }
        }

        // Extract the Cave Locations
        int cave1VolcanoTileIndex;
        int cave2VolcanoTileIndex;
        int cave3VolcanoTileIndex;
        int cave4VolcanoTileIndex;
        List<Integer> caveVolcanoTileIndex = new ArrayList<>();

        if (playerCount == 4) {
            cave1VolcanoTileIndex = Integer.parseInt(cave1VolcanoTileIndexTextField.getText());
            cave2VolcanoTileIndex = Integer.parseInt(cave2VolcanoTileIndexTextField.getText());
            cave3VolcanoTileIndex = Integer.parseInt(cave3VolcanoTileIndexTextField.getText());
            cave4VolcanoTileIndex = Integer.parseInt(cave4VolcanoTileIndexTextField.getText());

            // Ensure that all cave volcano tile index positions are within the accepted range of values
            if (cave1VolcanoTileIndex >= 0 && cave1VolcanoTileIndex < volcanoTilesPerVolcanoCard.get(caveVolcanoCardIndex.get(0)) &&
                cave2VolcanoTileIndex >= 0 && cave2VolcanoTileIndex < volcanoTilesPerVolcanoCard.get(caveVolcanoCardIndex.get(1)) &&
                cave3VolcanoTileIndex >= 0 && cave3VolcanoTileIndex < volcanoTilesPerVolcanoCard.get(caveVolcanoCardIndex.get(2)) &&
                cave4VolcanoTileIndex >= 0 && cave4VolcanoTileIndex < volcanoTilesPerVolcanoCard.get(caveVolcanoCardIndex.get(3))
            ) {
                caveVolcanoTileIndex.add(cave1VolcanoTileIndex);
                caveVolcanoTileIndex.add(cave2VolcanoTileIndex);
                caveVolcanoTileIndex.add(cave3VolcanoTileIndex);
                caveVolcanoTileIndex.add(cave4VolcanoTileIndex);
            } else {
                return;
            }
        } else if (playerCount == 3) {
            cave1VolcanoTileIndex = Integer.parseInt(cave1VolcanoTileIndexTextField.getText());
            cave2VolcanoTileIndex = Integer.parseInt(cave2VolcanoTileIndexTextField.getText());
            cave3VolcanoTileIndex = Integer.parseInt(cave3VolcanoTileIndexTextField.getText());
            cave4VolcanoTileIndex = 0;

            // Ensure that all 3 cave volcano tile index positions we look at are within the accepted range of values
            if (cave1VolcanoTileIndex >= 0 && cave1VolcanoTileIndex < volcanoTilesPerVolcanoCard.get(caveVolcanoCardIndex.get(0)) &&
                    cave2VolcanoTileIndex >= 0 && cave2VolcanoTileIndex < volcanoTilesPerVolcanoCard.get(caveVolcanoCardIndex.get(1)) &&
                    cave3VolcanoTileIndex >= 0 && cave3VolcanoTileIndex < volcanoTilesPerVolcanoCard.get(caveVolcanoCardIndex.get(2))
            ) {
                caveVolcanoTileIndex.add(cave1VolcanoTileIndex);
                caveVolcanoTileIndex.add(cave2VolcanoTileIndex);
                caveVolcanoTileIndex.add(cave3VolcanoTileIndex);
                caveVolcanoTileIndex.add(cave4VolcanoTileIndex);
            } else {
                return;
            }
        } else if (playerCount == 2) {
            cave1VolcanoTileIndex = Integer.parseInt(cave1VolcanoTileIndexTextField.getText());
            cave2VolcanoTileIndex = Integer.parseInt(cave2VolcanoTileIndexTextField.getText());
            cave3VolcanoTileIndex = 0;
            cave4VolcanoTileIndex = 0;

            // Ensure that all 2 cave volcano tile index positions we look at are within the accepted range of values
            if (cave1VolcanoTileIndex >= 0 && cave1VolcanoTileIndex < volcanoTilesPerVolcanoCard.get(caveVolcanoCardIndex.get(0)) &&
                cave2VolcanoTileIndex >= 0 && cave2VolcanoTileIndex < volcanoTilesPerVolcanoCard.get(caveVolcanoCardIndex.get(2))
            ) {
                caveVolcanoTileIndex.add(cave1VolcanoTileIndex);
                caveVolcanoTileIndex.add(cave3VolcanoTileIndex);
                caveVolcanoTileIndex.add(cave2VolcanoTileIndex);
                caveVolcanoTileIndex.add(cave4VolcanoTileIndex);
            } else {
                return;
            }
        }


        // Extract the Cave Animals for each Cave
        String cave1Animal = cave1AnimalChoiceBox.getValue();
        String cave2Animal = cave2AnimalChoiceBox.getValue();
        String cave3Animal = cave3AnimalChoiceBox.getValue();
        String cave4Animal = cave4AnimalChoiceBox.getValue();

        Map<String, Integer> caveAnimalToCaveAnimalNumberMapping = new HashMap<>();
        caveAnimalToCaveAnimalNumberMapping.put("Baby Dragon", 0);
        caveAnimalToCaveAnimalNumberMapping.put("Salamander", 1);
        caveAnimalToCaveAnimalNumberMapping.put("Bat", 2);
        caveAnimalToCaveAnimalNumberMapping.put("Spider", 3);

        int cave1AnimalNumber = caveAnimalToCaveAnimalNumberMapping.get(cave1Animal);
        int cave2AnimalNumber = caveAnimalToCaveAnimalNumberMapping.get(cave2Animal);
        int cave3AnimalNumber = caveAnimalToCaveAnimalNumberMapping.get(cave3Animal);
        int cave4AnimalNumber = caveAnimalToCaveAnimalNumberMapping.get(cave4Animal);

        List<Integer> caveAnimalNumbers = new ArrayList<>();

        List<Integer> possibleAnimalNumbers = new ArrayList<>();
        possibleAnimalNumbers.add(0);
        possibleAnimalNumbers.add(1);
        possibleAnimalNumbers.add(2);
        possibleAnimalNumbers.add(3);

        if (playerCount == 4) {
            // Ensure that all 4 cave animals are unique
            Set<Integer> caveAnimalNumberStringSet = new HashSet<>();
            caveAnimalNumberStringSet.add(cave1AnimalNumber);
            caveAnimalNumberStringSet.add(cave2AnimalNumber);
            caveAnimalNumberStringSet.add(cave3AnimalNumber);
            caveAnimalNumberStringSet.add(cave4AnimalNumber);

            if (caveAnimalNumberStringSet.size() == 4) {
                caveAnimalNumbers.add(cave1AnimalNumber);
                caveAnimalNumbers.add(cave2AnimalNumber);
                caveAnimalNumbers.add(cave3AnimalNumber);
                caveAnimalNumbers.add(cave4AnimalNumber);
            } else {
                return;
            }
        } else if (playerCount == 3) {
            // Ensure that the animals of the 3 caves we look at are all unique
            Set<Integer> caveAnimalNumberStringSet = new HashSet<>();
            caveAnimalNumberStringSet.add(cave1AnimalNumber);
            caveAnimalNumberStringSet.add(cave2AnimalNumber);
            caveAnimalNumberStringSet.add(cave3AnimalNumber);

            if (caveAnimalNumberStringSet.size() == 3) {
                caveAnimalNumbers.add(cave1AnimalNumber);
                caveAnimalNumbers.add(cave2AnimalNumber);
                caveAnimalNumbers.add(cave3AnimalNumber);

                possibleAnimalNumbers.remove(possibleAnimalNumbers.indexOf(cave1AnimalNumber));
                possibleAnimalNumbers.remove(possibleAnimalNumbers.indexOf(cave2AnimalNumber));
                possibleAnimalNumbers.remove(possibleAnimalNumbers.indexOf(cave3AnimalNumber));

                caveAnimalNumbers.add(possibleAnimalNumbers.get(0));
            } else {
                return;
            }
        } else if (playerCount == 2) {
            // Ensure that the animals of the 2 caves we look at are all unique
            Set<Integer> caveAnimalNumberStringSet = new HashSet<>();
            caveAnimalNumberStringSet.add(cave1AnimalNumber);
            caveAnimalNumberStringSet.add(cave2AnimalNumber);

            if (caveAnimalNumberStringSet.size() == 2) {
                caveAnimalNumbers.add(cave1AnimalNumber);

                possibleAnimalNumbers.remove(possibleAnimalNumbers.indexOf(cave1AnimalNumber));
                possibleAnimalNumbers.remove(possibleAnimalNumbers.indexOf(cave2AnimalNumber));

                caveAnimalNumbers.add(possibleAnimalNumbers.get(0));

                caveAnimalNumbers.add(cave2AnimalNumber);

                caveAnimalNumbers.add(possibleAnimalNumbers.get(1));
            } else {
                return;
            }
        }



        // Get the total number of volcano tiles
        int totalVolcanoTilesCount = 0;
        for (int i = 0; i < volcanoTilesPerVolcanoCard.size(); i++) {
            totalVolcanoTilesCount += volcanoTilesPerVolcanoCard.get(i);
        }

        // Map the Volcano Tiles to their Animal numbers

        HashMap<Integer, Integer> volcanoTileIndexToAnimalNumber = new HashMap<>();
        HashMap<Integer, Image> volcanoTileIndexToImage = new HashMap<>();
        int typesOfAnimals = 4;

        if (volcanoCardCount == 8 && volcanoTilesPerVolcanoCardString.equals("3,3,3,3,3,3,3,3")) {

            // For an original game, follow the original sequence of animals on each volcano tile making up the volcano
            // Baby Dragon - Bat - Spider (Cave)
            volcanoTileIndexToAnimalNumber.put(0, 0);
            volcanoTileIndexToAnimalNumber.put(1, 2);
            volcanoTileIndexToAnimalNumber.put(2, 3);

            // Spider - Bat - Salamander (Regular)
            volcanoTileIndexToAnimalNumber.put(3, 3);
            volcanoTileIndexToAnimalNumber.put(4, 2);
            volcanoTileIndexToAnimalNumber.put(5, 1);

            // Salamander - Spider - Bat (Cave)
            volcanoTileIndexToAnimalNumber.put(6, 1);
            volcanoTileIndexToAnimalNumber.put(7, 3);
            volcanoTileIndexToAnimalNumber.put(8, 2);

            // Baby Dragon - Salamander - Bat (Regular)
            volcanoTileIndexToAnimalNumber.put(9, 0);
            volcanoTileIndexToAnimalNumber.put(10, 1);
            volcanoTileIndexToAnimalNumber.put(11, 2);

            // Spider - Salamander - Baby Dragon (Cave)
            volcanoTileIndexToAnimalNumber.put(12, 3);
            volcanoTileIndexToAnimalNumber.put(13, 1);
            volcanoTileIndexToAnimalNumber.put(14, 0);

            // Bat - Baby Dragon - Salamander (Regular)
            volcanoTileIndexToAnimalNumber.put(15, 2);
            volcanoTileIndexToAnimalNumber.put(16, 0);
            volcanoTileIndexToAnimalNumber.put(17, 1);

            // Bat - Spider - Baby Dragon (Cave)
            volcanoTileIndexToAnimalNumber.put(18, 2);
            volcanoTileIndexToAnimalNumber.put(19, 3);
            volcanoTileIndexToAnimalNumber.put(20, 0);

            // Salamander - Baby Dragon - Spider (Regular)
            volcanoTileIndexToAnimalNumber.put(21, 1);
            volcanoTileIndexToAnimalNumber.put(22, 0);
            volcanoTileIndexToAnimalNumber.put(23, 3);

            for (int i = 0; i < totalVolcanoTilesCount; i++) {
                int animalNumber = volcanoTileIndexToAnimalNumber.get(i);

                // Map the Volcano Tiles to their Animal images
                switch (animalNumber) {
                    case 0: // Baby Dragon
                        volcanoTileIndexToImage.put(i,
                                new Image(getClass().getResourceAsStream("BabyDragonVolcanoTile.png")));
                        break;
                    case 1: // Salamander
                        volcanoTileIndexToImage.put(i,
                                new Image(getClass().getResourceAsStream("SalamanderVolcanoTile.png")));
                        break;
                    case 2: // Bat
                        volcanoTileIndexToImage.put(i,
                                new Image(getClass().getResourceAsStream("BatVolcanoTile.png")));
                        break;
                    case 3: // Spider
                        volcanoTileIndexToImage.put(i,
                                new Image(getClass().getResourceAsStream("SpiderVolcanoTile.png")));
                        break;
                    default:
                        break;
                }
            }
        } else {

            // For a new game, randomly generate the sequence of animals on each volcano tile making up the volcano
            for (int i = 0; i < totalVolcanoTilesCount; i++) {
                Random random = new Random();
                int animalNumber = random.nextInt(typesOfAnimals);
                volcanoTileIndexToAnimalNumber.put(i, animalNumber);

                // Map the Volcano Tiles to their Animal images
                switch (animalNumber) {
                    case 0: // Baby Dragon
                        volcanoTileIndexToImage.put(i,
                                new Image(getClass().getResourceAsStream("BabyDragonVolcanoTile.png")));
                        break;
                    case 1: // Salamander
                        volcanoTileIndexToImage.put(i,
                                new Image(getClass().getResourceAsStream("SalamanderVolcanoTile.png")));
                        break;
                    case 2: // Bat
                        volcanoTileIndexToImage.put(i,
                                new Image(getClass().getResourceAsStream("BatVolcanoTile.png")));
                        break;
                    case 3: // Spider
                        volcanoTileIndexToImage.put(i,
                                new Image(getClass().getResourceAsStream("SpiderVolcanoTile.png")));
                        break;
                    default:
                        break;
                }
            }
        }

        this.validInputs = true;




        if (validInputs) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Game.fxml"));
            root = fxmlLoader.load();

            // Recreate the chosen board configuration in the actual game
            GameController gameController = fxmlLoader.getController();
            gameController.buildGame(
                    volcanoCardCount,
                    volcanoTilesPerVolcanoCard,
                    caveVolcanoCardIndex,
                    caveVolcanoTileIndex,
                    caveAnimalNumbers,
                    playerCount,
                    volcanoTileIndexToAnimalNumber,
                    volcanoTileIndexToImage
                    );
            gameController.adjustPlayers(playerCount);

            // Generate the index positions of the event tiles
            List<Integer> eventTileIndexes = new ArrayList<>();
            for (int i = 0; i < totalVolcanoTilesCount; i += 4) {
                eventTileIndexes.add(i);
            }
            gameController.setEventTile(eventTileIndexes);

            // Swap scene to the Game scene
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Method for clicking on the Back button
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param event the event object from clicking on the back button
     * @throws IOException
     */
    public void back(ActionEvent event) throws IOException {
        // Swap scene to the MainMenu scene
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
        root = fxmlLoader.load();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method for initializing the choice boxes for each cave's associated cave animal
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[] animalChoices = {"Baby Dragon", "Salamander", "Bat", "Spider"};
        cave1AnimalChoiceBox.getItems().addAll(animalChoices);
        cave2AnimalChoiceBox.getItems().addAll(animalChoices);
        cave3AnimalChoiceBox.getItems().addAll(animalChoices);
        cave4AnimalChoiceBox.getItems().addAll(animalChoices);

        cave1AnimalChoiceBox.setValue("Baby Dragon");
        cave2AnimalChoiceBox.setValue("Salamander");
        cave3AnimalChoiceBox.setValue("Bat");
        cave4AnimalChoiceBox.setValue("Spider");
    }
}
