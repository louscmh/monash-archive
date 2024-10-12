package com.fit3077.fierydragons;

import com.fit3077.fierydragons.DragonCards.DragonCard;
import com.fit3077.fierydragons.Factories.*;
import com.fit3077.fierydragons.Game.Game;
import com.fit3077.fierydragons.Game.Player;
import com.fit3077.fierydragons.Game.Volcano;
import com.fit3077.fierydragons.Visitors.FlipVisitorImpl;
import com.fit3077.fierydragons.VolcanoCards.CaveVolcanoCard;
import com.fit3077.fierydragons.VolcanoCards.RegularVolcanoCard;
import com.fit3077.fierydragons.VolcanoCards.VolcanoCard;
import com.fit3077.fierydragons.VolcanoTiles.Caves.*;
import com.fit3077.fierydragons.VolcanoTiles.VolcanoTile;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

/**
 * A class that represents the Controller for the Game of the Fiery Dragons game
 *
 * Created by:
 * @author Kerk Han Chin
 * Modified by:
 * @author David Lee
 * @author Louis Chow Meng Hoe
 */
public class GameController implements Initializable {

    @FXML
    private AnchorPane gamePane;

    @FXML
    private ScrollPane volcanoScrollPane;

    @FXML
    private Group volcanoAndCavesGroup;

    @FXML
    private Group topSideVolcanoCardGroup;

    @FXML
    private Group rightSideVolcanoCardGroup;

    @FXML
    private Group bottomSideVolcanoCardGroup;

    @FXML
    private Group leftSideVolcanoCardGroup;

    @FXML
    private ImageView cave1ImageView;

    @FXML
    private ImageView cave2ImageView;

    @FXML
    private ImageView cave3ImageView;

    @FXML
    private ImageView cave4ImageView;

    @FXML
    private Circle player1Circle;

    @FXML
    private Circle player2Circle;

    @FXML
    private Circle player3Circle;

    @FXML
    private Circle player4Circle;

    @FXML
    private ImageView animal1Display;

    @FXML
    private ImageView animal2Display;

    @FXML
    private ImageView animal3Display;

    @FXML
    private ImageView animal4Display;

    @FXML
    private Label tilesToWin1Display;

    @FXML
    private Label tilesToWin2Display;

    @FXML
    private Label tilesToWin3Display;

    @FXML
    private Label tilesToWin4Display;

    @FXML
    private Label playerLabel1;

    @FXML
    private Label playerLabel2;

    @FXML
    private Label playerLabel3;

    @FXML
    private Label playerLabel4;

    @FXML
    private Rectangle rectangle2;

    @FXML
    private Rectangle rectangle3;

    @FXML
    private Rectangle rectangle4;

    @FXML
    private Circle circle1;

    @FXML
    private Circle circle2;

    @FXML
    private Circle circle3;

    @FXML
    private Circle circle4;

    @FXML
    private Group dragonCardGroup;

    @FXML
    private Rectangle curtainRectangle;



    private int volcanoCardCount;
    private List<Integer> volcanoTilesPerVolcanoCardList = new ArrayList<>();
    private List<Integer> caveVolcanoCardIndex = new ArrayList<>();
    private List<Integer> caveVolcanoTileIndex = new ArrayList<>();
    private List<Integer> caveAnimalNumbers = new ArrayList<>();
    private int playerCount;
    private HashMap<Integer, Integer> volcanoTileIndexToAnimalNumber = new HashMap<>();
    private HashMap<Integer, Image> volcanoTileIndexToImage = new HashMap<>();

    private int totalVolcanoTilesCount = 0;

    private HashMap<Integer, ImageView> volcanoTileIndexToVolcanoTileImageView = new HashMap<>();
    private HashMap<Integer, VolcanoTile> volcanoTileIndexToVolcanoTile = new HashMap<>();
    private HashMap<Integer, Integer> volcanoTileIndexToSideNumber = new HashMap<>();
    private HashMap<Integer, List<Integer>> groupNumberToVolcanoTileIndexList = new HashMap<>();
    private HashMap<Integer, Integer> volcanoTileIndexToVolcanoCardIndex = new HashMap<>();
    private HashMap<Integer, List<Integer>> volcanoCardIndexToVolcanoTilesIndex = new HashMap<>();
    private HashMap<Integer, Integer> caveIndexToCaveVolcanoCardStartingVolcanoTileIndex = new HashMap<>();
    private HashMap<Integer, Integer> caveIndexToCaveExitVolcanoTileIndex = new HashMap<>();

    private List<ImageView> caveImageViews = new ArrayList<>();
    private List<Circle> playerCircles = new ArrayList<>();
    private List<ImageView> animalDisplays = new ArrayList<>();
    private List<Label> tilesToWinDisplays = new ArrayList<>();
    private List<Label> playerLabels = new ArrayList<>();
    private List<Circle> leaderboardCircles = new ArrayList<>();

    private List<Cave> caveList = new ArrayList<>();
    private List<Player> playerList = new ArrayList<>();

    private List<Integer> eventTileIndexes = new ArrayList<>();


    private HashMap<Integer, Integer> dragonCardOrder = new HashMap<>();
    private HashMap<Integer, Integer> playerOrder = new HashMap<>();

    private Game fieryDragonsGame;


    private boolean canClickOnDragonCard = false;
    private boolean canFlipDragonCard = true;
    private boolean skip = false;

    private FileChooser fileChooser = new FileChooser();

    private Stage stage;
    private Scene scene;
    private Parent root;


    /**
     * Method for building a game, setting up the board, in the back-end and front-end, based on the player-defined configuration settings
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param volcanoCardCount - the number of volcano cards
     * @param volcanoTilesPerVolcanoCardList - a list containing the number of volcano tiles for each volcano card
     * @param caveVolcanoCardIndex - a list containing the volcano card index for each cave
     * @param caveVolcanoTileIndex - a list containing the volcano tile index of the volcano card associated with each cave
     * @param caveAnimalNumbers - a list containing the animal numbers associated with each cave
     * @param playerCount - the number of players in the game
     * @param volcanoTileIndexToAnimalNumber - a mapping of the volcano tile index to the animal number of that volcano tile
     * @param volcanoTileIndexToImage - a mapping of the volcano tile index to the image of that volcano tile
     */
    public void buildGame(int volcanoCardCount, List<Integer> volcanoTilesPerVolcanoCardList,
                          List<Integer> caveVolcanoCardIndex,
                          List<Integer> caveVolcanoTileIndex, List<Integer> caveAnimalNumbers,
                          int playerCount,
                          HashMap<Integer, Integer> volcanoTileIndexToAnimalNumber,
                          HashMap<Integer, Image> volcanoTileIndexToImage) {
        // Remove the curtain rectangle
        this.gamePane.getChildren().remove(this.curtainRectangle);
        // Store the Volcano Card Count
        this.volcanoCardCount = volcanoCardCount;
        // Store the volcano tiles for each volcano card index
        this.volcanoTilesPerVolcanoCardList = volcanoTilesPerVolcanoCardList;
        // Store the cave volcano card index for each cave
        this.caveVolcanoCardIndex = caveVolcanoCardIndex;
        // Store the cave volcano tile index for each cave
        this.caveVolcanoTileIndex = caveVolcanoTileIndex;
        // Store the animal number for each cave
        this.caveAnimalNumbers = caveAnimalNumbers;
        // Store the player count
        this.playerCount = playerCount;
        // Store the mapping of volcano tile index to animal number
        this.volcanoTileIndexToAnimalNumber = volcanoTileIndexToAnimalNumber;
        // Store the mapping of volcano tile index to image
        this.volcanoTileIndexToImage = volcanoTileIndexToImage;

        // Configure the game board Scroll Pane
        this.volcanoScrollPane.setPrefHeight(632);
        this.volcanoScrollPane.setPrefWidth(632);
        volcanoScrollPane.setContent(volcanoAndCavesGroup);

        // Build the Volcano Board
        // Obtain the total number of volcano tiles
        totalVolcanoTilesCount = 0;
        for (int i = 0; i < volcanoTilesPerVolcanoCardList.size(); i++) {
            totalVolcanoTilesCount += volcanoTilesPerVolcanoCardList.get(i);
        }

        int volcanoTilesForTopBottomAndRightSides;
        int volcanoTilesForLeftSide;

        if (totalVolcanoTilesCount % 4 == 0) {
            volcanoTilesForTopBottomAndRightSides = totalVolcanoTilesCount / 4;
            volcanoTilesForLeftSide = totalVolcanoTilesCount / 4;
        } else {
            volcanoTilesForTopBottomAndRightSides = (totalVolcanoTilesCount / 4) + 1;
            volcanoTilesForLeftSide = totalVolcanoTilesCount - (volcanoTilesForTopBottomAndRightSides * 3);
        }



        // Populate the board with volcano tiles



        int currentVolcanoTileIndex = 0;
        // Build the Top Side
        HBox topSideVolcanoCardHBox = new HBox();
        List<Integer> topSideVolcanoTileIndexList = new ArrayList<>();
        for (int i = 0; i < volcanoTilesForTopBottomAndRightSides; i++) {
            ImageView volcanoTileImageView = new ImageView();
            volcanoTileImageView.setImage(volcanoTileIndexToImage.get(currentVolcanoTileIndex));
            volcanoTileImageView.setFitHeight(70);
            volcanoTileImageView.setFitWidth(70);
            topSideVolcanoCardHBox.getChildren().add(volcanoTileImageView);

            volcanoTileIndexToVolcanoTileImageView.put(currentVolcanoTileIndex, volcanoTileImageView);
            topSideVolcanoTileIndexList.add(currentVolcanoTileIndex);
            volcanoTileIndexToSideNumber.put(currentVolcanoTileIndex, 0);

            currentVolcanoTileIndex++;
        }
        topSideVolcanoCardHBox.layout();
        topSideVolcanoCardGroup.getChildren().add(topSideVolcanoCardHBox);

        // Build the Right Side
        VBox rightSideVolcanoCardVBox = new VBox();
        List<Integer> rightSideVolcanoTileIndexList = new ArrayList<>();
        for (int i = 0; i < volcanoTilesForTopBottomAndRightSides; i++) {
            ImageView volcanoTileImageView = new ImageView();
            volcanoTileImageView.setImage(volcanoTileIndexToImage.get(currentVolcanoTileIndex));
            volcanoTileImageView.setFitHeight(70);
            volcanoTileImageView.setFitWidth(70);
            rightSideVolcanoCardVBox.getChildren().add(volcanoTileImageView);

            volcanoTileIndexToVolcanoTileImageView.put(currentVolcanoTileIndex, volcanoTileImageView);
            rightSideVolcanoTileIndexList.add(currentVolcanoTileIndex);
            volcanoTileIndexToSideNumber.put(currentVolcanoTileIndex, 1);

            currentVolcanoTileIndex++;
        }
        rightSideVolcanoCardVBox.layout();
        rightSideVolcanoCardGroup.getChildren().add(rightSideVolcanoCardVBox);
        rightSideVolcanoCardGroup.setLayoutX(topSideVolcanoCardGroup.getLayoutX() + (70 * volcanoTilesForTopBottomAndRightSides));
        rightSideVolcanoCardGroup.setLayoutY(topSideVolcanoCardGroup.getLayoutY());

        // Build the Bottom Side
        currentVolcanoTileIndex--;
        currentVolcanoTileIndex += volcanoTilesForTopBottomAndRightSides;
        HBox bottomSideVolcanoCardHBox = new HBox();
        List<Integer> bottomSideVolcanoTileIndexList = new ArrayList<>();
        for (int i = 0; i < volcanoTilesForTopBottomAndRightSides; i++) {
            ImageView volcanoTileImageView = new ImageView();
            volcanoTileImageView.setImage(volcanoTileIndexToImage.get(currentVolcanoTileIndex));
            volcanoTileImageView.setFitHeight(70);
            volcanoTileImageView.setFitWidth(70);
            bottomSideVolcanoCardHBox.getChildren().add(volcanoTileImageView);

            volcanoTileIndexToVolcanoTileImageView.put(currentVolcanoTileIndex, volcanoTileImageView);
            bottomSideVolcanoTileIndexList.add(currentVolcanoTileIndex);
            volcanoTileIndexToSideNumber.put(currentVolcanoTileIndex, 2);

            currentVolcanoTileIndex--;
        }
        bottomSideVolcanoCardHBox.layout();
        bottomSideVolcanoCardGroup.getChildren().add(bottomSideVolcanoCardHBox);
        bottomSideVolcanoCardGroup.setLayoutX(rightSideVolcanoCardGroup.getLayoutX() - (70 * (volcanoTilesForTopBottomAndRightSides - 1)));
        bottomSideVolcanoCardGroup.setLayoutY(rightSideVolcanoCardGroup.getLayoutY() + (70 * volcanoTilesForTopBottomAndRightSides));

        // Build the Left Side
        List<Integer> leftSideVolcanoTileIndexList = new ArrayList<>();
        currentVolcanoTileIndex += volcanoTilesForTopBottomAndRightSides;
        if (volcanoTilesForLeftSide > 0) {
            currentVolcanoTileIndex += volcanoTilesForLeftSide;
            VBox leftSideVolcanoCardVBox = new VBox();
            for (int i = volcanoTilesForLeftSide; i < volcanoTilesForTopBottomAndRightSides; i++) {
                ImageView volcanoTileImageView = new ImageView();
                volcanoTileImageView.setImage(new Image(getClass().getResourceAsStream("UpArrow.png")));
                volcanoTileImageView.setFitHeight(70);
                volcanoTileImageView.setFitWidth(70);
                leftSideVolcanoCardVBox.getChildren().add(volcanoTileImageView);
            }
            for (int i = 0; i < volcanoTilesForLeftSide; i++) {
                ImageView volcanoTileImageView = new ImageView();
                volcanoTileImageView.setImage(volcanoTileIndexToImage.get(currentVolcanoTileIndex));
                volcanoTileImageView.setFitHeight(70);
                volcanoTileImageView.setFitWidth(70);
                leftSideVolcanoCardVBox.getChildren().add(volcanoTileImageView);

                volcanoTileIndexToVolcanoTileImageView.put(currentVolcanoTileIndex, volcanoTileImageView);
                leftSideVolcanoTileIndexList.add(currentVolcanoTileIndex);
                volcanoTileIndexToSideNumber.put(currentVolcanoTileIndex, 3);

                currentVolcanoTileIndex--;
            }
            leftSideVolcanoCardVBox.layout();
            leftSideVolcanoCardGroup.getChildren().add(leftSideVolcanoCardVBox);
            leftSideVolcanoCardGroup.setLayoutX(bottomSideVolcanoCardGroup.getLayoutX() - 70);
            leftSideVolcanoCardGroup.setLayoutY(bottomSideVolcanoCardGroup.getLayoutY() - (70 * (volcanoTilesForTopBottomAndRightSides - 1)));

        } else {
            // Edge cases for very small volcano sizes
            if (volcanoTilesForLeftSide == 0) {
                VBox leftSideVolcanoCardVBox = new VBox();
                for (int i = 0; i < volcanoTilesForTopBottomAndRightSides; i++) {
                    if (i == volcanoTilesForTopBottomAndRightSides - 1) {
                        ImageView volcanoTileImageView = new ImageView();
                        volcanoTileImageView.setImage(new Image(getClass().getResourceAsStream("BottomLeftArrow.png")));
                        volcanoTileImageView.setFitHeight(70);
                        volcanoTileImageView.setFitWidth(70);
                        leftSideVolcanoCardVBox.getChildren().add(volcanoTileImageView);
                    } else {
                        ImageView volcanoTileImageView = new ImageView();
                        volcanoTileImageView.setImage(new Image(getClass().getResourceAsStream("UpArrow.png")));
                        volcanoTileImageView.setFitHeight(70);
                        volcanoTileImageView.setFitWidth(70);
                        leftSideVolcanoCardVBox.getChildren().add(volcanoTileImageView);
                    }
                }
                leftSideVolcanoCardVBox.layout();
                leftSideVolcanoCardGroup.getChildren().add(leftSideVolcanoCardVBox);
                leftSideVolcanoCardGroup.setLayoutX(bottomSideVolcanoCardGroup.getLayoutX() - 70);
                leftSideVolcanoCardGroup.setLayoutY(bottomSideVolcanoCardGroup.getLayoutY() - (70 * (volcanoTilesForTopBottomAndRightSides - 1)));

            } else if (volcanoTilesForLeftSide == -1) {
                // Edge case when you have just 2 volcano tiles
                if (totalVolcanoTilesCount == 2) {
                    HBox leftSideVolcanoCardHBox = new HBox();
                    ImageView volcanoTileImageView = new ImageView();
                    volcanoTileImageView.setImage(new Image(getClass().getResourceAsStream("BottomLeftArrow.png")));
                    volcanoTileImageView.setFitHeight(70);
                    volcanoTileImageView.setFitWidth(70);
                    leftSideVolcanoCardHBox.getChildren().add(volcanoTileImageView);

                    volcanoTileImageView = new ImageView();
                    volcanoTileImageView.setImage(new Image(getClass().getResourceAsStream("BottomRightArrow.png")));
                    volcanoTileImageView.setFitHeight(70);
                    volcanoTileImageView.setFitWidth(70);
                    leftSideVolcanoCardHBox.getChildren().add(volcanoTileImageView);

                    leftSideVolcanoCardHBox.layout();
                    leftSideVolcanoCardGroup.getChildren().add(leftSideVolcanoCardHBox);
                    leftSideVolcanoCardGroup.setLayoutX(bottomSideVolcanoCardGroup.getLayoutX() - 70);
                    leftSideVolcanoCardGroup.setLayoutY(bottomSideVolcanoCardGroup.getLayoutY() - (70 * (volcanoTilesForTopBottomAndRightSides - 1)));

                } else if (totalVolcanoTilesCount == 5) {
                    // Edge case when you have just 5 volcano tiles
                    VBox leftSideVolcanoCardVBox = new VBox();

                    ImageView volcanoTileImageView = new ImageView();
                    volcanoTileImageView.setImage(new Image(getClass().getResourceAsStream("UpArrow.png")));
                    volcanoTileImageView.setFitHeight(70);
                    volcanoTileImageView.setFitWidth(70);
                    leftSideVolcanoCardVBox.getChildren().add(volcanoTileImageView);

                    volcanoTileImageView = new ImageView();
                    volcanoTileImageView.setImage(new Image(getClass().getResourceAsStream("BottomLeftArrow.png")));
                    volcanoTileImageView.setFitHeight(70);
                    volcanoTileImageView.setFitWidth(70);
                    leftSideVolcanoCardVBox.getChildren().add(volcanoTileImageView);

                    bottomSideVolcanoCardHBox = new HBox();
                    volcanoTileImageView = new ImageView();
                    volcanoTileImageView.setImage(new Image(getClass().getResourceAsStream("LeftArrow.png")));
                    volcanoTileImageView.setFitHeight(70);
                    volcanoTileImageView.setFitWidth(70);
                    bottomSideVolcanoCardHBox.getChildren().add(volcanoTileImageView);

                    leftSideVolcanoCardVBox.layout();
                    leftSideVolcanoCardGroup.getChildren().add(leftSideVolcanoCardVBox);
                    leftSideVolcanoCardGroup.setLayoutX(bottomSideVolcanoCardGroup.getLayoutX() - 70);
                    leftSideVolcanoCardGroup.setLayoutY(bottomSideVolcanoCardGroup.getLayoutY() - (70 * (volcanoTilesForTopBottomAndRightSides - 1)));

                    bottomSideVolcanoCardHBox.layout();
                    bottomSideVolcanoCardGroup.getChildren().add(bottomSideVolcanoCardHBox);

                }

            }
        }

        groupNumberToVolcanoTileIndexList.put(0, topSideVolcanoTileIndexList);
        groupNumberToVolcanoTileIndexList.put(1, rightSideVolcanoTileIndexList);
        groupNumberToVolcanoTileIndexList.put(2, bottomSideVolcanoTileIndexList);
        groupNumberToVolcanoTileIndexList.put(3, leftSideVolcanoTileIndexList);

        // Track Volcano Cards
        // Map volcano tile index positions to the index positions of their associated volcano card
        int volcanoCardIndex = 0;
        int volcanoTileIndex = 0;
        for (int i = 0; i < volcanoTilesPerVolcanoCardList.size(); i++) {
            List<Integer> volcanoTiles = new ArrayList<>();
            for (int j = 0; j < volcanoTilesPerVolcanoCardList.get(i); j++) {
                if (i % 2 == 0) {
                    ImageView volcanoTileForThisCardImageView = volcanoTileIndexToVolcanoTileImageView.get(volcanoTileIndex);
                    ColorAdjust volcanoCardVolcanoTileColorAdjust = new ColorAdjust();
                    volcanoCardVolcanoTileColorAdjust.setSaturation(0.2);
                    volcanoTileForThisCardImageView.setEffect(volcanoCardVolcanoTileColorAdjust);
                }
                volcanoTileIndexToVolcanoCardIndex.put(volcanoTileIndex, volcanoCardIndex);
                volcanoTiles.add(volcanoTileIndex);
                volcanoTileIndex++;
            }
            volcanoCardIndexToVolcanoTilesIndex.put(volcanoCardIndex, volcanoTiles);
            volcanoCardIndex++;
        }

        // Map index positions of caves to the index positions of their associated cave volcano card's starting volcano tile index position
        for (int i = 0; i < caveVolcanoCardIndex.size(); i++) {
            int currentCaveVolcanoCardIndex = caveVolcanoCardIndex.get(i);
            List<Integer> caveVolcanoCardVolcanoTilesIndexList = volcanoCardIndexToVolcanoTilesIndex.get(currentCaveVolcanoCardIndex);
            for (int j = 0; j < caveVolcanoCardVolcanoTilesIndexList.size(); j++) {
                int caveVolcanoTileIndexForThisCard = caveVolcanoCardVolcanoTilesIndexList.get(j);
                if (j == 0) {
                    caveIndexToCaveVolcanoCardStartingVolcanoTileIndex.put(i, caveVolcanoTileIndexForThisCard);
                }


            }
        }






        // Place Caves
        for (int i = 0; i < caveVolcanoTileIndex.size(); i++) {
            ImageView caveImageView = new ImageView();
            switch (i) {
                case 0:
                    caveImageView = cave1ImageView;
                    break;
                case 1:
                    caveImageView = cave2ImageView;
                    break;
                case 2:
                    caveImageView = cave3ImageView;
                    break;
                case 3:
                    caveImageView = cave4ImageView;
                    break;
                default:
                    break;
            }
            switch (caveAnimalNumbers.get(i)) {
                case 0: // Baby Dragon
                    caveImageView.setImage(
                            new Image(getClass().getResourceAsStream("BabyDragonCave.png"))
                    );
                    break;
                case 1: // Salamander
                    caveImageView.setImage(
                            new Image(getClass().getResourceAsStream("SalamanderCave.png"))
                    );
                    break;
                case 2: // Bat
                    caveImageView.setImage(
                            new Image(getClass().getResourceAsStream("BatCave.png"))
                    );
                    break;
                case 3: // Spider
                    caveImageView.setImage(
                            new Image(getClass().getResourceAsStream("SpiderCave.png"))
                    );
                    break;
                default:
                    break;
            }
            caveImageView.setFitHeight(70);
            caveImageView.setFitWidth(70);

            int volcanoTileToInsertCaveNextToIndex = caveVolcanoTileIndex.get(i) +
                    caveIndexToCaveVolcanoCardStartingVolcanoTileIndex.get(i);
            caveIndexToCaveExitVolcanoTileIndex.put(i, volcanoTileToInsertCaveNextToIndex);
            int sideOfVolcanoTileToInsertCaveNextTo = volcanoTileIndexToSideNumber.get(volcanoTileToInsertCaveNextToIndex);
            ImageView volcanoTileToInsertCaveNextToImageView = volcanoTileIndexToVolcanoTileImageView.get(volcanoTileToInsertCaveNextToIndex);







            switch (sideOfVolcanoTileToInsertCaveNextTo) {
                case 0: // Top
                    caveImageView.setLayoutX(
                            volcanoTileToInsertCaveNextToImageView.getParent().getParent().getParent().getLayoutX() +
                                    volcanoTileToInsertCaveNextToImageView.getParent().getParent().getLayoutX() +
                                    volcanoTileToInsertCaveNextToImageView.getParent().getLayoutX() +
                                    volcanoTileToInsertCaveNextToImageView.getLayoutX());

                    caveImageView.setLayoutY(
                            volcanoTileToInsertCaveNextToImageView.getParent().getParent().getParent().getLayoutY() +
                                    volcanoTileToInsertCaveNextToImageView.getParent().getParent().getLayoutY() +
                                    volcanoTileToInsertCaveNextToImageView.getParent().getLayoutY() +
                                    volcanoTileToInsertCaveNextToImageView.getLayoutY() - 70
                    );
                    break;
                case 1: // Right
                    caveImageView.setLayoutX(
                            volcanoTileToInsertCaveNextToImageView.getParent().getParent().getParent().getLayoutX() +
                                    volcanoTileToInsertCaveNextToImageView.getParent().getParent().getLayoutX() +
                                    volcanoTileToInsertCaveNextToImageView.getParent().getLayoutX() +
                                    volcanoTileToInsertCaveNextToImageView.getLayoutX() + 70);

                    caveImageView.setLayoutY(
                            volcanoTileToInsertCaveNextToImageView.getParent().getParent().getParent().getLayoutY() +
                                    volcanoTileToInsertCaveNextToImageView.getParent().getParent().getLayoutY() +
                                    volcanoTileToInsertCaveNextToImageView.getParent().getLayoutY() +
                                    volcanoTileToInsertCaveNextToImageView.getLayoutY()
                    );
                    break;
                case 2: // Bottom
                    caveImageView.setLayoutX(
                            volcanoTileToInsertCaveNextToImageView.getParent().getParent().getParent().getLayoutX() +
                                    volcanoTileToInsertCaveNextToImageView.getParent().getParent().getLayoutX() +
                                    volcanoTileToInsertCaveNextToImageView.getParent().getLayoutX() +
                                    volcanoTileToInsertCaveNextToImageView.getLayoutX());

                    caveImageView.setLayoutY(
                            volcanoTileToInsertCaveNextToImageView.getParent().getParent().getParent().getLayoutY() +
                                    volcanoTileToInsertCaveNextToImageView.getParent().getParent().getLayoutY() +
                                    volcanoTileToInsertCaveNextToImageView.getParent().getLayoutY() +
                                    volcanoTileToInsertCaveNextToImageView.getLayoutY() + 70
                    );
                    break;
                case 3: // Left
                    caveImageView.setLayoutX(
                            volcanoTileToInsertCaveNextToImageView.getParent().getParent().getParent().getLayoutX() +
                                    volcanoTileToInsertCaveNextToImageView.getParent().getParent().getLayoutX() +
                                    volcanoTileToInsertCaveNextToImageView.getParent().getLayoutX() +
                                    volcanoTileToInsertCaveNextToImageView.getLayoutX() - 70);

                    caveImageView.setLayoutY(
                            volcanoTileToInsertCaveNextToImageView.getParent().getParent().getParent().getLayoutY() +
                                    volcanoTileToInsertCaveNextToImageView.getParent().getParent().getLayoutY() +
                                    volcanoTileToInsertCaveNextToImageView.getParent().getLayoutY() +
                                    volcanoTileToInsertCaveNextToImageView.getLayoutY()
                    );
                    break;
            }

            this.caveImageViews.add(caveImageView);

        }

        // Place Players
        // Place Player 1
        player1Circle.setLayoutX(
                caveImageViews.get(0).getLayoutX() + 50
        );
        player1Circle.setLayoutY(
                caveImageViews.get(0).getLayoutY() + 50
        );
        playerCircles.add(player1Circle);
        animalDisplays.add(animal1Display);
        tilesToWinDisplays.add(tilesToWin1Display);
        playerLabels.add(playerLabel1);
        leaderboardCircles.add(circle1);

        // Place Player 2
        player2Circle.setLayoutX(
                caveImageViews.get(1).getLayoutX() + 50
        );
        player2Circle.setLayoutY(
                caveImageViews.get(1).getLayoutY() + 50
        );
        playerCircles.add(player2Circle);
        animalDisplays.add(animal2Display);
        tilesToWinDisplays.add(tilesToWin2Display);
        playerLabels.add(playerLabel2);
        leaderboardCircles.add(circle2);

        // Place Player 3
        player3Circle.setLayoutX(
                caveImageViews.get(2).getLayoutX() + 50
        );
        player3Circle.setLayoutY(
                caveImageViews.get(2).getLayoutY() + 50
        );
        playerCircles.add(player3Circle);
        animalDisplays.add(animal3Display);
        tilesToWinDisplays.add(tilesToWin3Display);
        playerLabels.add(playerLabel3);
        leaderboardCircles.add(circle3);

        // Place Player 4
        player4Circle.setLayoutX(
                caveImageViews.get(3).getLayoutX() + 50
        );
        player4Circle.setLayoutY(
                caveImageViews.get(3).getLayoutY() + 50
        );
        playerCircles.add(player4Circle);
        animalDisplays.add(animal4Display);
        tilesToWinDisplays.add(tilesToWin4Display);
        playerLabels.add(playerLabel4);
        leaderboardCircles.add(circle4);

        // Generate the Player Order
        for (int i = 0; i < playerCount; i++) {
            playerOrder.put(i + 1, i + 1);
        }

        for (int i = 0; i < playerCircles.size(); i++) {
            Circle playerCircle = playerCircles.get(i);
            Circle leaderboardCircle = leaderboardCircles.get(i);
            ImageView animalDisplay = animalDisplays.get(i);

            int caveAnimalNumber = caveAnimalNumbers.get(i);
            switch (caveAnimalNumber) {
                case 0: // Baby Dragon
                    playerCircle.setFill(Paint.valueOf("01FF00"));
                    leaderboardCircle.setFill(Paint.valueOf("01FF00"));
                    animalDisplay.setImage(
                            new Image(getClass().getResourceAsStream("BabyDragonPlayerDisplay.png"))
                    );
                    break;
                case 1: // Salamander
                    playerCircle.setFill(Color.WHITE);
                    leaderboardCircle.setFill(Color.WHITE);
                    animalDisplay.setImage(
                            new Image(getClass().getResourceAsStream("SalamanderPlayerDisplay.png"))
                    );
                    break;
                case 2: // Bat
                    playerCircle.setFill(Paint.valueOf("4A86E8"));
                    leaderboardCircle.setFill(Paint.valueOf("4A86E8"));
                    animalDisplay.setImage(
                            new Image(getClass().getResourceAsStream("BatPlayerDisplay.png"))
                    );
                    break;
                case 3: // Spider
                    playerCircle.setFill(Paint.valueOf("FFD966"));
                    leaderboardCircle.setFill(Paint.valueOf("FFD966"));
                    animalDisplay.setImage(
                            new Image(getClass().getResourceAsStream("SpiderPlayerDisplay.png"))
                    );
                    break;
                default:
                    break;
            }
            playerCircle.setStrokeWidth(1);
            playerCircle.setStrokeType(StrokeType.INSIDE);
            playerCircle.setStrokeLineCap(StrokeLineCap.SQUARE);
            playerCircle.setStrokeLineJoin(StrokeLineJoin.MITER);

            leaderboardCircle.setStrokeWidth(1);
            leaderboardCircle.setStrokeType(StrokeType.INSIDE);
            leaderboardCircle.setStrokeLineCap(StrokeLineCap.SQUARE);
            leaderboardCircle.setStrokeLineJoin(StrokeLineJoin.MITER);
        }



        // Create the Board back-end
        // Create the Volcano Cards, Volcano Tiles, and Caves in the back-end
        // Create the various animal factories
        BabyDragonFactory babyDragonFactory = new BabyDragonFactory();
        SalamanderFactory salamanderFactory = new SalamanderFactory();
        BatFactory batFactory = new BatFactory();
        SpiderFactory spiderFactory = new SpiderFactory();

        HashMap<Integer, VolcanoCard> clockwiseOrderedVolcanoCardBackEnd = new HashMap<>();

        List<VolcanoCard> caveVolcano1VolcanoCardsBackEnd = new ArrayList<>();
        List<VolcanoCard> caveVolcano2VolcanoCardsBackEnd = new ArrayList<>();
        List<VolcanoCard> caveVolcano3VolcanoCardsBackEnd = new ArrayList<>();
        List<VolcanoCard> caveVolcano4VolcanoCardsBackEnd = new ArrayList<>();

        // Create the various Caves
        // caveAnimalNumbers contains the animal number at cave i

        this.caveList = new ArrayList<>();
        for (int i = 0; i < caveAnimalNumbers.size(); i++) {
            switch (caveAnimalNumbers.get(i)) {
                case 0: // Baby Dragon
                    caveList.add(babyDragonFactory.createCave(null,
                            babyDragonFactory.createVolcanoTile()));
                    break;
                case 1: // Salamander
                    caveList.add(salamanderFactory.createCave(null,
                            salamanderFactory.createVolcanoTile()));
                    break;
                case 2: // Bat
                    caveList.add(batFactory.createCave(null,
                            batFactory.createVolcanoTile()));
                    break;
                case 3: // Spider
                    caveList.add(spiderFactory.createCave(null,
                            spiderFactory.createVolcanoTile()));
                    break;
                default:
                    break;
            }
        }

        // Create the Players

        this.playerList = new ArrayList<>();
        for (int i = 0; i < caveAnimalNumbers.size(); i++) {
            playerList.add(new Player(i+1, caveList.get(i), totalVolcanoTilesCount + 2));
            caveList.get(i).setCavePlayer(playerList.get(i));
        }

        // Adjust the Player Volcano Tiles To Win Front-End Leaderboard Statistic
        tilesToWin1Display.setText(String.valueOf(playerList.get(0).getVolcanoTilesToWin()));
        tilesToWin2Display.setText(String.valueOf(playerList.get(1).getVolcanoTilesToWin()));
        tilesToWin3Display.setText(String.valueOf(playerList.get(2).getVolcanoTilesToWin()));
        tilesToWin4Display.setText(String.valueOf(playerList.get(3).getVolcanoTilesToWin()));

        // Set the Cave Ids
        for (int i = 0; i < caveAnimalNumbers.size(); i++) {
            if (caveIndexToCaveExitVolcanoTileIndex.get(i) - 1 < 0) {
                caveList.get(i).getCaveVolcanoTile().setId(-1 * (caveIndexToCaveExitVolcanoTileIndex.get(i) + totalVolcanoTilesCount));
            } else {
                caveList.get(i).getCaveVolcanoTile().setId(-1 * caveIndexToCaveExitVolcanoTileIndex.get(i));
            }
        }



        int currentVolcanoCardIndex = 0;

        // Create Volcano Cards
        // Create Regular Volcano Cards
        for (int i = 0; i < volcanoCardCount; i++) {
            List<VolcanoTile> volcanoTileListForThisVolcanoCard = new ArrayList<>();
            List<Integer> volcanoTileIndexListForThisVolcanoCard = volcanoCardIndexToVolcanoTilesIndex.get(currentVolcanoCardIndex);
            for (int j = 0; j < volcanoTileIndexListForThisVolcanoCard.size(); j++) {
                switch (volcanoTileIndexToAnimalNumber.get(
                        volcanoTileIndexListForThisVolcanoCard.get(j)
                )) {
                    case 0: // Baby Dragon
                        volcanoTileListForThisVolcanoCard.add(babyDragonFactory.createVolcanoTile());
                        break;
                    case 1: // Salamander
                        volcanoTileListForThisVolcanoCard.add(salamanderFactory.createVolcanoTile());
                        break;
                    case 2: // Bat
                        volcanoTileListForThisVolcanoCard.add(batFactory.createVolcanoTile());
                        break;
                    case 3: // Spider
                        volcanoTileListForThisVolcanoCard.add(spiderFactory.createVolcanoTile());
                        break;
                    default:
                        break;
                }
            }
            clockwiseOrderedVolcanoCardBackEnd.put(currentVolcanoCardIndex, new RegularVolcanoCard(volcanoTileListForThisVolcanoCard));
            currentVolcanoCardIndex++;
        }

        // Replace regular volcano cards with cave volcano cards
        for (int i = 0; i < caveVolcanoTileIndex.size(); i++) {
            int caveVolcanoTileIndexForThisCard = caveVolcanoTileIndex.get(i);
            List<VolcanoTile> volcanoTileList = clockwiseOrderedVolcanoCardBackEnd.get(caveVolcanoTileIndexForThisCard).getVolcanoTiles();

            clockwiseOrderedVolcanoCardBackEnd.put(caveVolcanoTileIndexForThisCard,
                    new CaveVolcanoCard(volcanoTileList, caveList.get(i)));
        }









        List<VolcanoCard> volcanoCardListClockwise = new ArrayList<>();

        for (int i = 0; i < clockwiseOrderedVolcanoCardBackEnd.size(); i++) {
            volcanoCardListClockwise.add(clockwiseOrderedVolcanoCardBackEnd.get(i));
        }

        // Map volcano tile index to volcano tile objects
        currentVolcanoTileIndex = 0;
        for (int i = 0; i < volcanoCardListClockwise.size(); i++) {
            VolcanoCard currentVolcanoCard = volcanoCardListClockwise.get(i);
            for (int j = 0; j < currentVolcanoCard.getVolcanoTiles().size(); j++) {
                this.volcanoTileIndexToVolcanoTile.put(currentVolcanoTileIndex, currentVolcanoCard.getVolcanoTiles().get(j));
                currentVolcanoCard.getVolcanoTiles().get(j).setId(
                        currentVolcanoTileIndex + 1
                );
                currentVolcanoTileIndex++;
            }
        }

        // Generate Dragon Cards and randomly distribute their positions
        int dragonCardCount = 18;
        int babyDragonDragonCardCount = 6;
        int salamanderDragonCardCount = 6;
        int batDragonCardCount = 6;
        int spiderDragonCardCount = 6;
        int dragonPirateDragonCardCount = 4;
        int swapDragonCardCount = 2;
        List<DragonCard> dragonCards = new ArrayList<>();

        // Create the Dragon Cards
        // Order goes:
        // 0 -> BabyDragon1, 1 -> Salamander1, 2 -> Bat1, 3 -> Spider1
        // 4 -> BabyDragon2, 5 -> Salamander2, 6 -> Bat1, 7 -> Spider2
        // 8 -> BabyDragon3, 9 -> Salamander3, 10 -> Bat1, 11 -> Spider3
        for (int i = 1; i < 4; i++) {
            DragonCard babyDragonDragonCard = babyDragonFactory.createDragonCard(i,
                    new Image(getClass().getResourceAsStream("BabyDragonDragonCard" + Integer.toString(i) + ".png")));
            dragonCards.add(babyDragonDragonCard);

            DragonCard salamanderDragonCard = salamanderFactory.createDragonCard(i,
                    new Image(getClass().getResourceAsStream("SalamanderDragonCard" + Integer.toString(i) + ".png")));
            dragonCards.add(salamanderDragonCard);

            DragonCard batDragonCard = batFactory.createDragonCard(i,
                    new Image(getClass().getResourceAsStream("BatDragonCard" + Integer.toString(i) + ".png")));
            dragonCards.add(batDragonCard);

            DragonCard spiderDragonCard = spiderFactory.createDragonCard(i,
                    new Image(getClass().getResourceAsStream("SpiderDragonCard" + Integer.toString(i) + ".png")));
            dragonCards.add(spiderDragonCard);

        }

        // 12 -> DragonPirate1, 13 -> DragonPirate1, 14 -> DragonPirate2, 15 -> DragonPirate2
        DragonPirateFactory dragonPirateFactory = new DragonPirateFactory();
        for (int i = 1; i < 3; i++) {
            for (int j = 1; j < 3; j++) {
                DragonCard dragonPiratedDragonCard = dragonPirateFactory.createDragonCard(i,
                        new Image(getClass().getResourceAsStream("DragonPirateDragonCard" + Integer.toString(i) + ".png")));
                dragonCards.add(dragonPiratedDragonCard);
            }
        }

        // 16 -> Swap, 17 -> Swap
        SwapCardFactory swapCardFactory = new SwapCardFactory() {
        };
        for (int i = 1; i < 3; i++) {
            DragonCard swapDragonCard = swapCardFactory.createDragonCard(1,
                    new Image(getClass().getResourceAsStream("SwapDragonCard.png")));
            dragonCards.add(swapDragonCard);
        }

        List<DragonCard> copyOfOriginalDragonCards = new ArrayList<>();
        for (int i = 0; i < dragonCardCount; i++) {
            copyOfOriginalDragonCards.add(dragonCards.get(i));
        }

        // Randomize the Dragon Card positions
        Collections.shuffle(dragonCards, new Random());

        for (int i = 0; i < dragonCardCount; i++) {
            this.dragonCardOrder.put(i, dragonCards.indexOf(
                    copyOfOriginalDragonCards.get(i)
            ));
        }



        // Create the Fiery Dragons Volcano
        Volcano volcano = new Volcano(volcanoCardListClockwise, dragonCards);

        // Create the Fiery Dragons Game
        Game game = new Game(volcano, playerList);

        this.fieryDragonsGame = game;

    }


    /**
     * Method for clicking on a Dragon Card
     *
     * Created by:
     * @author Kerk Han Chin - Kerk Han was responsible for implementing the dragon card flipping functionality
     *
     * @param event the event object from clicking on a dragon card
     */
    public void flipDragonCard(MouseEvent event) {
        // Get the Dragon Card's associated View and Model objects
        ImageView selectedDragonCardImageView = (ImageView) event.getSource();
        int selectedDragonCardNumber = Integer.parseInt(selectedDragonCardImageView.getId().substring("dragonCard".length()));
        List<DragonCard> dragonCards = this.fieryDragonsGame.getVolcano().getDragonCards();
        DragonCard selectedDragonCard = dragonCards.get(selectedDragonCardNumber - 1);

        // Only flip the dragon card if the player can flip a dragon card and said dragon card hasn't already been flipped
        if (canClickOnDragonCard && canFlipDragonCard && !selectedDragonCard.isFlipped()) {
            selectedDragonCardImageView.setImage(selectedDragonCard.getDisplayImage());

            FlipVisitorImpl flipVisitor = new FlipVisitorImpl();
            selectedDragonCard.flipAccept(flipVisitor);
            // Move the player's dragon token based on their current position and the last flipped dragon card
            // The player's dragon token can be moved forwards, backwards, or not at all
            movePlayerDragonToken(fieryDragonsGame.getCurrentPlayer(), selectedDragonCard);
            canClickOnDragonCard = false;
        }
    }

    /**
     * Method for moving a player's dragon token based on their current position and the
     * last flipped dragon card, where the player's dragon token can be moved forwards, backwards, or not at all
     *
     * Created by:
     * @author Kerk Han Chin - Kerk Han was responsible for implementing the movement of player dragon tokens based
     * on their current position as well as the last flipped dragon card functionality, as well as the winning the game functionality
     *
     * Modified by:
     * @author David Lee - David was responsible for implementing the dragon pirate dragon card backwards movement functionality, as well
     * as the functionality where player dragon tokens cannot be moved if their destination tile is currently occupied
     * @author Louis Chow Meng Hoe
     *
     * @param player the player's dragon token which is to be moved
     * @param lastFlippedDragonCard the last flipped dragon card that the player chose to flip
     */
    public void movePlayerDragonToken(Player player, DragonCard lastFlippedDragonCard) {
        // Identify the number of volcano tiles the player's dragon token is to move
        VolcanoTile playerCurrentPosition = player.getCurrentPosition();
        int volcanoTilesToMove = lastFlippedDragonCard.moveDoing(playerCurrentPosition);

        // If the Player flips a non-matching Dragon Card, they aren't allowed to flip anymore dragon cards
        if (volcanoTilesToMove == 0) {
            canFlipDragonCard = false;
        }

        // If a Swap Dragon Card is flipped, perform the swap functionality
        if (lastFlippedDragonCard.isSwap()) {
            swap();
        }

        // If Player moves exactly enough spaces to win, they win!
        // Otherwise, if they overshoot the cave, they don't move and stay where they are
        if (player.getVolcanoTilesToWin() == volcanoTilesToMove && player.getStartCave().getCaveVolcanoTile().getPlayerOnTop() == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Winner");
            alert.setHeaderText("Player " + player.getPlayerId() + " has successfully returned to their cave!");
            alert.setContentText("The winner is Player " + player.getPlayerId());
            alert.getButtonTypes().clear(); // Remove default button

            // Set button to exit to main menu
            ButtonType close = new ButtonType("Exit To Main Menu", ButtonType.CANCEL.getButtonData());
            alert.getButtonTypes().add(close);
            alert.setOnCloseRequest(event -> {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Stage stage = (Stage) gamePane.getScene().getWindow();
                stage.setTitle("Fiery Dragons");
                stage.setScene(scene);
                stage.show();
            });
            alert.show();
            System.out.println("Winner is Player " + player.getPlayerId());
            return;
        } else if (player.getVolcanoTilesToWin() < volcanoTilesToMove) {
            // Player is overshooting the cave and hence cannot flip anymore dragon cards
            System.out.println("Overshooting the cave!");
            canFlipDragonCard = false;
            return;
        } else if (player.getVolcanoTilesToWin() == volcanoTilesToMove && player.getStartCave().getCaveVolcanoTile().getPlayerOnTop() != null) {
            // Player is overshooting the cave and hence cannot flip anymore dragon cards
            System.out.println("There's another token in the cave!");
            canFlipDragonCard = false;
            return;
        }

        // Identify the Destination Volcano Tile
        int currentPositionVolcanoTileId = playerCurrentPosition.getId();



        int destinationVolcanoTileId;
        // Do not move if current position is in Cave and have to move backwards
        // or if no movement at all
        if (currentPositionVolcanoTileId < 0 && volcanoTilesToMove < 0 || volcanoTilesToMove == 0) {
            return;
        } else {
            int totalNumberOfVolcanoTiles = this.totalVolcanoTilesCount;
            // Get the Destination Volcano Tile ID
            if (currentPositionVolcanoTileId < 0) { // Situation when the current position is within a cave
                // Move out of starting Cave volcano tile
                destinationVolcanoTileId = (-1 * currentPositionVolcanoTileId) + volcanoTilesToMove;

            } else {
                // Regular case where the volcano tiles to move is simply added to the current position volcano tile id
                // to identify the destination volcano tile id
                destinationVolcanoTileId = currentPositionVolcanoTileId + volcanoTilesToMove;

            }
            // Edge case where the destination volcano tile id wraps around to the start of the board
            if (destinationVolcanoTileId > totalNumberOfVolcanoTiles) {
                destinationVolcanoTileId = destinationVolcanoTileId - totalNumberOfVolcanoTiles;
                // Very particular edge case where the player wraps around the entire board multiple times
                if (destinationVolcanoTileId > totalNumberOfVolcanoTiles) {
                    destinationVolcanoTileId = destinationVolcanoTileId % totalNumberOfVolcanoTiles;
                }
            } else if (destinationVolcanoTileId < 1) {
                // Edge case where a player has to move back and wrap around to the end of the board
                destinationVolcanoTileId = destinationVolcanoTileId + totalNumberOfVolcanoTiles;
            }
        }

        // Get the Destination Volcano Tile
        VolcanoTile destinationVolcanoTile = this.volcanoTileIndexToVolcanoTile.get(destinationVolcanoTileId - 1);



        // Do not move if destination Volcano Tile already occupied
        // Also, the player can't flip any more dragon cards and must end their turn
        if (destinationVolcanoTile.getPlayerOnTop() != null) {
            // If the destination volcano tile and current volcano tile are the same then don't force the player to end their turn
            if (destinationVolcanoTile.getId() != currentPositionVolcanoTileId) {
                canFlipDragonCard = false;
                return;
            }
        }

        // Move the Player's dragon token internally in the back-end
        playerCurrentPosition.setPlayerOnTop(null);
        player.setCurrentPosition(destinationVolcanoTile);
        destinationVolcanoTile.setPlayerOnTop(player);
        player.setVolcanoTilesToWin(player.getVolcanoTilesToWin() - volcanoTilesToMove);

        // Move the Player's dragon token visually on the front-end
        Circle currentPlayerCircle = playerCircles.get(player.getPlayerId() - 1);
        ImageView destinationVolcanoTileImageView = this.volcanoTileIndexToVolcanoTileImageView.get(destinationVolcanoTileId - 1);

        currentPlayerCircle.setLayoutX(
                destinationVolcanoTileImageView.getLayoutX() +
                        destinationVolcanoTileImageView.getParent().getLayoutX() +
                        destinationVolcanoTileImageView.getParent().getParent().getLayoutX() +
                        destinationVolcanoTileImageView.getParent().getParent().getParent().getLayoutX() +
                        20
        );
        currentPlayerCircle.setLayoutY(
                destinationVolcanoTileImageView.getLayoutY() +
                        destinationVolcanoTileImageView.getParent().getLayoutY() +
                        destinationVolcanoTileImageView.getParent().getParent().getLayoutY() +
                        destinationVolcanoTileImageView.getParent().getParent().getParent().getLayoutY() +
                        50
        );

        // Change player display to show new animal of VolcanoTile that player is on and new number of tiles to win
        String tileName = player.getCurrentPosition().getClass().getSimpleName();
        String imagePath = tileName.substring(0, tileName.length() - 11) + "PlayerDisplay.png";
        animalDisplays.get(player.getPlayerId() - 1).setImage(new Image(getClass().getResourceAsStream(imagePath)));
        tilesToWinDisplays.get(player.getPlayerId() - 1).setText(Integer.toString(player.getVolcanoTilesToWin()));

        // If player lands on an Event Tile, skip next player's turn
        if (destinationVolcanoTile.isEvent()) {
            skip = true;
        }
    }

    /**
     * Method for clicking on the Select Dragon Card button
     *
     * Created by:
     * @author Kerk Han Chin
     *
     */
    public void selectDragonCard() {
        // If currently, the player cannot flip a dragon card, allow them to
        if (!canClickOnDragonCard) {
            canClickOnDragonCard = true;
        }
    }

    /**
     * Method for clicking on the End Turn button
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * Modified by:
     * @author David Lee - David was responsible for implementing the change of turn to the next player functionality
     */
    public void endTurn() {
        // Get the Dragon Cards within the Volcano
        Volcano volcano = this.fieryDragonsGame.getVolcano();
        List<DragonCard> dragonCards = volcano.getDragonCards();

        // Flip all the Dragon Cards face-down
        for (int i = 0; i < dragonCards.size(); i++) {
            DragonCard selectedDragonCard = dragonCards.get(i);
            if (selectedDragonCard.isFlipped()) {
                FlipVisitorImpl flipVisitor = new FlipVisitorImpl();
                selectedDragonCard.flipAccept(flipVisitor);
            }

            ImageView selectedDragonCardImageView = (ImageView) dragonCardGroup.getChildren().get(i);
            selectedDragonCardImageView.setImage(
                    new Image(getClass().getResourceAsStream("FaceDownDragonCard.png")));
        }

        // Change of turn to next player
        canFlipDragonCard = true;
        int turn = fieryDragonsGame.getTurn();
        int nPlayers = fieryDragonsGame.getPlayers().size();
        playerLabels.get((turn) % nPlayers).setTextFill(Color.BLACK);

        turn++;
        // If current player's token landed on an Event Tile before, skip one more turn
        if (skip) {
            turn++;
            skip = false;
        }

        fieryDragonsGame.setTurn(turn);
        fieryDragonsGame.setCurrentPlayer(fieryDragonsGame.getPlayers().get(turn % nPlayers));
        playerLabels.get(turn % nPlayers).setTextFill(Color.RED);
    }

    /**
     * Initialize method that's called when the GameController is initialized
     * to setup the FileChooser for the game
     *
     * Created by:
     * @author Kerk Han Chin
     *
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

    }

    /**
     * Method to adjust the number of players in the game depending
     * on the number of players the player set in the board configuration screen
     *
     * Created by:
     * @author Louis Chow Meng Hoe - Louis' responsibility was modifying the game to allow for the player to configure the number of players
     *
     * Modified by:
     * @author Kerk Han Chin
     *
     * @param numOfPlayers number of players in the game
     */
    public void adjustPlayers(int numOfPlayers) {
        if (numOfPlayers == 2) {
            // Putting Player 2 in Player 3's place in the front-end
            player2Circle.setFill(player3Circle.getFill());
            player2Circle.setLayoutX(player3Circle.getLayoutX());
            player2Circle.setLayoutY(player3Circle.getLayoutY());
            fieryDragonsGame.getPlayers().get(1).setStartCave(
                    fieryDragonsGame.getPlayers().get(2).getStartCave()
            );
            fieryDragonsGame.getPlayers().get(1).setCurrentPosition(
                    fieryDragonsGame.getPlayers().get(2).getCurrentPosition()
            );

            animal2Display.setImage(animal3Display.getImage());
            circle2.setFill(circle3.getFill());

            // Removing objects relevant to Player 3 & 4 internally

            caveList.remove(caveList.get(3));
            caveList.remove(caveList.get(2));
            fieryDragonsGame.getPlayers().remove(3);
            fieryDragonsGame.getPlayers().remove(2);

            // Removing assets relevant to Player 3 in the front-end
            playerCircles.remove(player3Circle);
            animalDisplays.remove(animal3Display);
            tilesToWinDisplays.remove(tilesToWin3Display);
            playerLabels.remove(playerLabel3);


            volcanoAndCavesGroup.getChildren().remove(player3Circle);
            gamePane.getChildren().remove(animal3Display);
            gamePane.getChildren().remove(tilesToWin3Display);
            gamePane.getChildren().remove(playerLabel3);
            gamePane.getChildren().remove(circle3);
            gamePane.getChildren().remove(rectangle3);

            player3Circle = null;
            animal3Display = null;
            tilesToWin3Display = null;
            playerLabel3 = null;
            circle3 = null;
            rectangle3 = null;

            // Removing assets relevant to Player 4 in the front-end
            playerCircles.remove(player4Circle);
            animalDisplays.remove(animal4Display);
            tilesToWinDisplays.remove(tilesToWin4Display);
            playerLabels.remove(playerLabel4);


            volcanoAndCavesGroup.getChildren().remove(player4Circle);
            gamePane.getChildren().remove(animal4Display);
            gamePane.getChildren().remove(tilesToWin4Display);
            gamePane.getChildren().remove(playerLabel4);
            gamePane.getChildren().remove(circle4);
            gamePane.getChildren().remove(rectangle4);

            player4Circle = null;
            animal4Display = null;
            tilesToWin4Display = null;
            playerLabel4 = null;
            circle4 = null;
            rectangle4 = null;

            volcanoAndCavesGroup.getChildren().remove(cave2ImageView);
            volcanoAndCavesGroup.getChildren().remove(cave4ImageView);



        } else if (numOfPlayers == 3) {
            // Removing objects relevant to Player 4 internally
            caveList.remove(caveList.get(3));
            fieryDragonsGame.getPlayers().remove(3);

            // Removing assets relevant to Player 4 in the front-end
            playerCircles.remove(player4Circle);
            animalDisplays.remove(animal4Display);
            tilesToWinDisplays.remove(tilesToWin4Display);
            playerLabels.remove(playerLabel4);


            volcanoAndCavesGroup.getChildren().remove(player4Circle);
            gamePane.getChildren().remove(animal4Display);
            gamePane.getChildren().remove(tilesToWin4Display);
            gamePane.getChildren().remove(playerLabel4);
            gamePane.getChildren().remove(circle4);
            gamePane.getChildren().remove(rectangle4);

            player4Circle = null;
            animal4Display = null;
            tilesToWin4Display = null;
            playerLabel4 = null;
            circle4 = null;
            rectangle4 = null;

            volcanoAndCavesGroup.getChildren().remove(cave4ImageView);


        }

        if (numOfPlayers >= 2) {
            int startingVolcanoTileIndexOfFirstCave = caveIndexToCaveVolcanoCardStartingVolcanoTileIndex.get(0);
            int volcanoCardIndexOfFirstCave = volcanoTileIndexToVolcanoCardIndex.get(startingVolcanoTileIndexOfFirstCave);
            List<Integer> volcanoCardIndexOfFirstCaveVolcanoTileIndexList = volcanoCardIndexToVolcanoTilesIndex.get(volcanoCardIndexOfFirstCave);

            for (int i = 0; i < volcanoCardIndexOfFirstCaveVolcanoTileIndexList.size(); i++) {
                ImageView currentVolcanoTileImageView = volcanoTileIndexToVolcanoTileImageView.get(
                        volcanoCardIndexOfFirstCaveVolcanoTileIndexList.get(i)
                );

                ColorAdjust currentVolcanoTileImageViewColorAdjust = (ColorAdjust) currentVolcanoTileImageView.getEffect();
                if (currentVolcanoTileImageViewColorAdjust == null) {
                    currentVolcanoTileImageViewColorAdjust = new ColorAdjust();
                }
                currentVolcanoTileImageViewColorAdjust.setBrightness(-0.2);
                currentVolcanoTileImageView.setEffect(currentVolcanoTileImageViewColorAdjust);
            }

            int startingVolcanoTileIndexOfThirdCave = caveIndexToCaveVolcanoCardStartingVolcanoTileIndex.get(2);
            int volcanoCardIndexOfThirdCave = volcanoTileIndexToVolcanoCardIndex.get(startingVolcanoTileIndexOfThirdCave);
            List<Integer> volcanoCardIndexOfThirdCaveVolcanoTileIndexList = volcanoCardIndexToVolcanoTilesIndex.get(volcanoCardIndexOfThirdCave);

            for (int i = 0; i < volcanoCardIndexOfThirdCaveVolcanoTileIndexList.size(); i++) {
                ImageView currentVolcanoTileImageView = volcanoTileIndexToVolcanoTileImageView.get(
                        volcanoCardIndexOfThirdCaveVolcanoTileIndexList.get(i)
                );

                ColorAdjust currentVolcanoTileImageViewColorAdjust = (ColorAdjust) currentVolcanoTileImageView.getEffect();
                if (currentVolcanoTileImageViewColorAdjust == null) {
                    currentVolcanoTileImageViewColorAdjust = new ColorAdjust();
                }
                currentVolcanoTileImageViewColorAdjust.setBrightness(-0.2);
                currentVolcanoTileImageView.setEffect(currentVolcanoTileImageViewColorAdjust);
            }

            if (numOfPlayers >= 3) {
                int startingVolcanoTileIndexOfSecondCave = caveIndexToCaveVolcanoCardStartingVolcanoTileIndex.get(1);
                int volcanoCardIndexOfSecondCave = volcanoTileIndexToVolcanoCardIndex.get(startingVolcanoTileIndexOfSecondCave);
                List<Integer> volcanoCardIndexOfSecondCaveVolcanoTileIndexList = volcanoCardIndexToVolcanoTilesIndex.get(volcanoCardIndexOfSecondCave);

                for (int i = 0; i < volcanoCardIndexOfSecondCaveVolcanoTileIndexList.size(); i++) {
                    ImageView currentVolcanoTileImageView = volcanoTileIndexToVolcanoTileImageView.get(
                            volcanoCardIndexOfSecondCaveVolcanoTileIndexList.get(i)
                    );

                    ColorAdjust currentVolcanoTileImageViewColorAdjust = (ColorAdjust) currentVolcanoTileImageView.getEffect();
                    if (currentVolcanoTileImageViewColorAdjust == null) {
                        currentVolcanoTileImageViewColorAdjust = new ColorAdjust();
                    }
                    currentVolcanoTileImageViewColorAdjust.setBrightness(-0.2);
                    currentVolcanoTileImageView.setEffect(currentVolcanoTileImageViewColorAdjust);
                }

                if (numOfPlayers == 4) {
                    int startingVolcanoTileIndexOfFourthCave = caveIndexToCaveVolcanoCardStartingVolcanoTileIndex.get(3);
                    int volcanoCardIndexOfFourthCave = volcanoTileIndexToVolcanoCardIndex.get(startingVolcanoTileIndexOfFourthCave);
                    List<Integer> volcanoCardIndexOfFourthCaveVolcanoTileIndexList = volcanoCardIndexToVolcanoTilesIndex.get(volcanoCardIndexOfFourthCave);

                    for (int i = 0; i < volcanoCardIndexOfFourthCaveVolcanoTileIndexList.size(); i++) {
                        ImageView currentVolcanoTileImageView = volcanoTileIndexToVolcanoTileImageView.get(
                                volcanoCardIndexOfFourthCaveVolcanoTileIndexList.get(i)
                        );

                        ColorAdjust currentVolcanoTileImageViewColorAdjust = (ColorAdjust) currentVolcanoTileImageView.getEffect();
                        if (currentVolcanoTileImageViewColorAdjust == null) {
                            currentVolcanoTileImageViewColorAdjust = new ColorAdjust();
                        }
                        currentVolcanoTileImageViewColorAdjust.setBrightness(-0.2);
                        currentVolcanoTileImageView.setEffect(currentVolcanoTileImageViewColorAdjust);
                    }
                }
            }
        }
    }

    /**
     * Method for saving a game to an external configuration text file
     *
     * Created by:
     * @author Kerk Han Chin
     *
     */
    public void saveGame() {
        fileChooser.setTitle("Save Game");
        fileChooser.setInitialFileName("Fiery Dragons Save Game");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text file", "*.txt"));
        File file = fileChooser.showSaveDialog(new Stage());

        String fieryDragonsSaveData = "";

        // Save the Volcano Card Count
        fieryDragonsSaveData += "volcanoCardCount, NA, ";
        fieryDragonsSaveData += String.valueOf(this.volcanoCardCount);
        fieryDragonsSaveData += "\n";




        // Save the Volcano Cards
        fieryDragonsSaveData += "volcanoCards, ";
        fieryDragonsSaveData += String.valueOf(this.volcanoCardCount);
        fieryDragonsSaveData += ", {";

        for (int i = 0; i < this.volcanoCardCount; i++) {
            fieryDragonsSaveData += "{";
            List<Integer> volcanoCardAnimalNumberOfVolcanoTiles = volcanoCardIndexToVolcanoTilesIndex.get(i);

            for (int j = 0; j < volcanoCardAnimalNumberOfVolcanoTiles.size(); j++) {
                fieryDragonsSaveData += String.valueOf(volcanoTileIndexToAnimalNumber.get(
                                volcanoCardAnimalNumberOfVolcanoTiles.get(j)
                        ));
                fieryDragonsSaveData += ", ";
            }
            fieryDragonsSaveData = fieryDragonsSaveData.substring(0, fieryDragonsSaveData.length() - 2);
            fieryDragonsSaveData += "}-";
        }
        fieryDragonsSaveData = fieryDragonsSaveData.substring(0, fieryDragonsSaveData.length() - 1);
        fieryDragonsSaveData += "}\n";

        // Save the Volcano Card Sequence
        fieryDragonsSaveData += "volcanoCardsSequence, ";
        fieryDragonsSaveData += String.valueOf(this.volcanoCardCount);
        fieryDragonsSaveData += ", {";

        for (int i = 0; i < this.volcanoCardCount; i++) {
            fieryDragonsSaveData += i;
            fieryDragonsSaveData += ", ";
        }
        fieryDragonsSaveData = fieryDragonsSaveData.substring(0, fieryDragonsSaveData.length() - 2);
        fieryDragonsSaveData += "}\n";

        // Save the Cave Volcano Cards
        fieryDragonsSaveData += "caveVolcanoCards, ";
        fieryDragonsSaveData += String.valueOf(this.caveVolcanoCardIndex.size());
        fieryDragonsSaveData += ", {";

        for (int i = 0; i < this.caveVolcanoCardIndex.size(); i++) {
            fieryDragonsSaveData += caveVolcanoCardIndex.get(i);
            fieryDragonsSaveData += ", ";
        }
        fieryDragonsSaveData = fieryDragonsSaveData.substring(0, fieryDragonsSaveData.length() - 2);
        fieryDragonsSaveData += "}\n";

        // Save the Cave Animals
        fieryDragonsSaveData += "caveAnimals, ";
        fieryDragonsSaveData += String.valueOf(this.caveVolcanoCardIndex.size());
        fieryDragonsSaveData += ", {";

        for (int i = 0; i < this.caveAnimalNumbers.size(); i++) {
            fieryDragonsSaveData += String.valueOf(this.caveAnimalNumbers.get(i));
            fieryDragonsSaveData += ", ";
        }
        fieryDragonsSaveData = fieryDragonsSaveData.substring(0, fieryDragonsSaveData.length() - 2);
        fieryDragonsSaveData += "}\n";

        // Save the Cave Locations
        fieryDragonsSaveData += "caveLocations, ";
        fieryDragonsSaveData += String.valueOf(this.caveVolcanoCardIndex.size());
        fieryDragonsSaveData += ", {";

        for (int i = 0; i < this.caveVolcanoTileIndex.size(); i++) {
            fieryDragonsSaveData += String.valueOf(caveVolcanoTileIndex.get(i));
            fieryDragonsSaveData += ", ";
        }
        fieryDragonsSaveData = fieryDragonsSaveData.substring(0, fieryDragonsSaveData.length() - 2);
        fieryDragonsSaveData += "}\n";


        // Save the Player Count
        fieryDragonsSaveData += "playerCount, NA, ";
        fieryDragonsSaveData += String.valueOf(this.fieryDragonsGame.getPlayers().size());
        fieryDragonsSaveData += "\n";

        // Save the Player Locations
        fieryDragonsSaveData += "playerLocations, ";
        fieryDragonsSaveData += String.valueOf(this.fieryDragonsGame.getPlayers().size());
        fieryDragonsSaveData += ", {";

        for (int i = 0; i < this.fieryDragonsGame.getPlayers().size(); i++) {
            fieryDragonsSaveData += String.valueOf(this.fieryDragonsGame.getPlayers().get(i).
                    getCurrentPosition().getId()
            );
            fieryDragonsSaveData += ", ";
        }
        fieryDragonsSaveData = fieryDragonsSaveData.substring(0, fieryDragonsSaveData.length() - 2);
        fieryDragonsSaveData += "}\n";

        // Save the Dragon Cards
        fieryDragonsSaveData += "dragonCards, ";
        fieryDragonsSaveData += String.valueOf(this.fieryDragonsGame.getVolcano().getDragonCards().size());
        fieryDragonsSaveData += ", {";

        for (int i = 0; i < this.fieryDragonsGame.getVolcano().getDragonCards().size(); i++) {
            fieryDragonsSaveData += String.valueOf(this.dragonCardOrder.get(i));
            fieryDragonsSaveData += ", ";
        }
        fieryDragonsSaveData = fieryDragonsSaveData.substring(0, fieryDragonsSaveData.length() - 2);
        fieryDragonsSaveData += "}\n";

        // Save the Player Order
        fieryDragonsSaveData += "playerOrder, ";
        fieryDragonsSaveData += String.valueOf(this.fieryDragonsGame.getPlayers().size());
        fieryDragonsSaveData += ", {";

        for (int i = 1; i <= this.fieryDragonsGame.getPlayers().size(); i++) {
            fieryDragonsSaveData += String.valueOf(this.playerOrder.get(i));
            fieryDragonsSaveData += ", ";
        }
        fieryDragonsSaveData = fieryDragonsSaveData.substring(0, fieryDragonsSaveData.length() - 2);
        fieryDragonsSaveData += "}\n";

        // Save the Player Tiles Away From Winning
        fieryDragonsSaveData += "playerTilesAwayFromWinning, ";
        fieryDragonsSaveData += String.valueOf(this.fieryDragonsGame.getPlayers().size());
        fieryDragonsSaveData += ", {";

        for (int i = 0; i < this.fieryDragonsGame.getPlayers().size(); i++) {
            fieryDragonsSaveData += String.valueOf(this.fieryDragonsGame.getPlayers().get(i).getVolcanoTilesToWin());
            fieryDragonsSaveData += ", ";
        }
        fieryDragonsSaveData = fieryDragonsSaveData.substring(0, fieryDragonsSaveData.length() - 2);
        fieryDragonsSaveData += "}\n";

        // Save the Current Turn
        fieryDragonsSaveData += "turn, NA, ";
        fieryDragonsSaveData += String.valueOf(this.fieryDragonsGame.getTurn());
        fieryDragonsSaveData += "\n";

        // Save the flipped Dragon Cards
        fieryDragonsSaveData += "flippedDragonCards, ";
        fieryDragonsSaveData += String.valueOf(this.fieryDragonsGame.getVolcano().getDragonCards().size());
        fieryDragonsSaveData += ", {";

        for (int i = 0; i < this.fieryDragonsGame.getVolcano().getDragonCards().size(); i++) {
            fieryDragonsSaveData += String.valueOf(this.fieryDragonsGame.getVolcano().getDragonCards()
                    .get(i).isFlipped());
            fieryDragonsSaveData += ", ";
        }

        fieryDragonsSaveData = fieryDragonsSaveData.substring(0, fieryDragonsSaveData.length() - 2);
        fieryDragonsSaveData += "}\n";

        // Save canFlipDragonCards
        fieryDragonsSaveData += "canFlipDragonCards, NA, ";
        fieryDragonsSaveData += String.valueOf(this.canFlipDragonCard);
        fieryDragonsSaveData += "\n";

        // Save canClickOnDragonCards
        fieryDragonsSaveData += "canClickOnDragonCards, NA, ";
        fieryDragonsSaveData += String.valueOf(this.canClickOnDragonCard);
        fieryDragonsSaveData += "\n";

        // Save the player whose turn it currently is
        fieryDragonsSaveData += "currentPlayer, NA, ";
        fieryDragonsSaveData += String.valueOf(this.fieryDragonsGame.getTurn() % this.playerCount + 1);
        fieryDragonsSaveData += "\n";

        // Save the index positions of the event tiles
        fieryDragonsSaveData += "eventTileIndexList, ";
        fieryDragonsSaveData += String.valueOf(this.eventTileIndexes.size());
        fieryDragonsSaveData += ", {";

        for (int i = 0; i < this.eventTileIndexes.size(); i++) {
            fieryDragonsSaveData += String.valueOf(this.eventTileIndexes.get(i));
            fieryDragonsSaveData += ", ";
        }
        fieryDragonsSaveData = fieryDragonsSaveData.substring(0, fieryDragonsSaveData.length() - 2);
        fieryDragonsSaveData += "}\n";

        // Save if we have to skip a turn
        fieryDragonsSaveData += "skip, NA, ";
        fieryDragonsSaveData += String.valueOf(this.skip);


        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write(fieryDragonsSaveData);
            printWriter.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Method to load a saved game from an external configuration text file
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @throws IOException
     */
    public void loadGame() throws IOException {
        File file = fileChooser.showOpenDialog(new Stage());

        List<Integer> savedDataLength = new ArrayList<>();
        List<String> savedDataValues = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String currentLine = scanner.nextLine();
                String currentLineWithoutType = currentLine.substring(
                        currentLine.indexOf(",") + 2);

                String currentLineLength = currentLineWithoutType.substring(0,
                        currentLineWithoutType.indexOf(","));

                String currentLineWithoutTypeAndLength = currentLineWithoutType.substring(
                        currentLineWithoutType.indexOf(",") + 2
                );

                if (currentLineLength.equals("NA")) {
                    savedDataLength.add(-1);
                } else {
                    savedDataLength.add(Integer.parseInt(currentLineLength));
                }
                savedDataValues.add(currentLineWithoutTypeAndLength);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Obtain the loaded game data


        // Load the Volcano Card Count
        int loadedVolcanoCardCount = Integer.parseInt(savedDataValues.get(0));



        // Load the Volcano Cards
        String loadedVolcanoCardsString = savedDataValues.get(1);
        String processedLoadedVolcanoCardsString = loadedVolcanoCardsString.substring(1,
                loadedVolcanoCardsString.length() - 1);
        List<String> loadedVolcanoCardsStringList = Arrays.asList(processedLoadedVolcanoCardsString.split("-"));

        List<List<Integer>> loadedVolcanoCards = new ArrayList<>();
        for (int i = 0; i < loadedVolcanoCardsStringList.size(); i++) {
            String currentLoadedVolcanoCardString = loadedVolcanoCardsStringList.get(i);
            String processedCurrentLoadedVolcanoCardString = currentLoadedVolcanoCardString.substring(1,
                    currentLoadedVolcanoCardString.length() - 1);

            processedCurrentLoadedVolcanoCardString =
                    processedCurrentLoadedVolcanoCardString.replaceAll("\\s", "");

            List<String> processedLoadedVolcanoCardStringList = Arrays.asList(
                    processedCurrentLoadedVolcanoCardString.split(",")
            );

            List<Integer> loadedVolcanoCard = new ArrayList<>();
            for (int j = 0; j < processedLoadedVolcanoCardStringList.size(); j++) {
                loadedVolcanoCard.add(Integer.valueOf(processedLoadedVolcanoCardStringList.get(j)));
            }
            loadedVolcanoCards.add(loadedVolcanoCard);
        }

        // Load the Volcano Cards Sequence
        String loadedVolcanoCardsSequenceString = savedDataValues.get(2);
        String processedLoadedVolcanoCardsSequenceString = loadedVolcanoCardsSequenceString.substring(1,
                loadedVolcanoCardsSequenceString.length() - 1);
        processedLoadedVolcanoCardsSequenceString =
                processedLoadedVolcanoCardsSequenceString.replaceAll("\\s", "");

        List<String> loadedVolcanoCardsSequenceStringList = Arrays.asList(
                processedLoadedVolcanoCardsSequenceString.split(","));

        List<Integer> loadedVolcanoCardsSequence = new ArrayList<>();
        for (int i = 0; i < loadedVolcanoCardsSequenceStringList.size(); i++) {
            loadedVolcanoCardsSequence.add(Integer.parseInt(loadedVolcanoCardsSequenceStringList.get(i)));
        }

        // Load the Cave Volcano Cards
        String loadedCaveVolcanoCardsString = savedDataValues.get(3);
        String processedLoadedCaveVolcanoCardsString = loadedCaveVolcanoCardsString.substring(1,
                loadedCaveVolcanoCardsString.length() - 1);
        processedLoadedCaveVolcanoCardsString =
                processedLoadedCaveVolcanoCardsString.replaceAll("\\s", "");

        List<String> loadedCaveVolcanoCardsStringList = Arrays.asList(
                processedLoadedCaveVolcanoCardsString.split(","));

        List<Integer> loadedCaveVolcanoCards = new ArrayList<>();
        for (int i = 0; i < loadedCaveVolcanoCardsStringList.size(); i++) {
            loadedCaveVolcanoCards.add(Integer.parseInt(loadedCaveVolcanoCardsStringList.get(i)));
        }

        // Load the Cave Animals
        String loadedCaveAnimalsString = savedDataValues.get(4);
        String processedLoadedCaveAnimalsString = loadedCaveAnimalsString.substring(1,
                loadedCaveAnimalsString.length() - 1);
        processedLoadedCaveAnimalsString =
                processedLoadedCaveAnimalsString.replaceAll("\\s", "");

        List<String> loadedCaveAnimalsStringList = Arrays.asList(
                processedLoadedCaveAnimalsString.split(","));

        List<Integer> loadedCaveAnimals = new ArrayList<>();
        for (int i = 0; i < loadedCaveAnimalsStringList.size(); i++) {
            loadedCaveAnimals.add(Integer.parseInt(loadedCaveAnimalsStringList.get(i)));
        }

        // Load the Cave Locations
        String loadedCaveLocationsString = savedDataValues.get(5);
        String processedLoadedCaveLocationsString = loadedCaveLocationsString.substring(1,
                loadedCaveLocationsString.length() - 1);
        processedLoadedCaveLocationsString =
                processedLoadedCaveLocationsString.replaceAll("\\s", "");

        List<String> loadedCaveLocationsStringList = Arrays.asList(
                processedLoadedCaveLocationsString.split(","));

        List<Integer> loadedCaveLocations = new ArrayList<>();
        for (int i = 0; i < loadedCaveLocationsStringList.size(); i++) {
            loadedCaveLocations.add(Integer.parseInt(loadedCaveLocationsStringList.get(i)));
        }

        // Load the Player Count
        int loadedPlayerCount = Integer.parseInt(savedDataValues.get(6));

        // Load the Player Locations
        String loadedPlayerLocationsString = savedDataValues.get(7);
        String processedLoadedPlayerLocationsString = loadedPlayerLocationsString.substring(1,
                loadedPlayerLocationsString.length() - 1);
        processedLoadedPlayerLocationsString =
                processedLoadedPlayerLocationsString.replaceAll("\\s", "");

        List<String> loadedPlayerLocationsStringList = Arrays.asList(
                processedLoadedPlayerLocationsString.split(","));

        List<Integer> loadedPlayerLocations = new ArrayList<>();
        for (int i = 0; i < loadedPlayerLocationsStringList.size(); i++) {
            loadedPlayerLocations.add(Integer.parseInt(loadedPlayerLocationsStringList.get(i)));
        }

        // Load the Dragon Cards
        String loadedDragonCardsString = savedDataValues.get(8);
        String processedLoadedDragonCardsString = loadedDragonCardsString.substring(1,
                loadedDragonCardsString.length() - 1);
        processedLoadedDragonCardsString =
                processedLoadedDragonCardsString.replaceAll("\\s", "");

        List<String> loadedDragonCardsStringList = Arrays.asList(
                processedLoadedDragonCardsString.split(","));

        List<Integer> loadedDragonCards = new ArrayList<>();
        for (int i = 0; i < loadedDragonCardsStringList.size(); i++) {
            loadedDragonCards.add(Integer.parseInt(loadedDragonCardsStringList.get(i)));
        }

        // Load the Player Order
        String loadedPlayerOrderString = savedDataValues.get(9);
        String processedLoadedPlayerOrderString = loadedPlayerOrderString.substring(1,
                loadedPlayerOrderString.length() - 1);
        processedLoadedPlayerOrderString =
                processedLoadedPlayerOrderString.replaceAll("\\s", "");

        List<String> loadedPlayerOrderStringList = Arrays.asList(
                processedLoadedPlayerOrderString.split(","));

        List<Integer> loadedPlayerOrder = new ArrayList<>();
        for (int i = 0; i < loadedPlayerOrderStringList.size(); i++) {
            loadedPlayerOrder.add(Integer.parseInt(loadedPlayerOrderStringList.get(i)));
        }

        // Load the Player Tiles Away From Winning
        String loadedPlayerTilesAwayFromWinningString = savedDataValues.get(10);
        String processedLoadedPlayerTilesAwayFromWinningString = loadedPlayerTilesAwayFromWinningString.substring(1,
                loadedPlayerTilesAwayFromWinningString.length() - 1);
        processedLoadedPlayerTilesAwayFromWinningString =
                processedLoadedPlayerTilesAwayFromWinningString.replaceAll("\\s", "");

        List<String> loadedPlayerTilesAwayFromWinningStringList = Arrays.asList(
                processedLoadedPlayerTilesAwayFromWinningString.split(","));

        List<Integer> loadedPlayerTilesAwayFromWinning = new ArrayList<>();
        for (int i = 0; i < loadedPlayerTilesAwayFromWinningStringList.size(); i++) {
            loadedPlayerTilesAwayFromWinning.add(Integer.parseInt(loadedPlayerTilesAwayFromWinningStringList.get(i)));
        }

        // Load the Current Turn
        int loadedTurn = Integer.parseInt(savedDataValues.get(11));

        // Load the Flipped Dragon Cards
        String loadedFlippedDragonCardsString = savedDataValues.get(12);
        String processedLoadedFlippedDragonCardsString = loadedFlippedDragonCardsString.substring(1,
                loadedFlippedDragonCardsString.length() - 1);
        processedLoadedFlippedDragonCardsString =
                processedLoadedFlippedDragonCardsString.replaceAll("\\s", "");

        List<String> loadedFlippedDragonCardsStringList = Arrays.asList(
                processedLoadedFlippedDragonCardsString.split(","));

        List<Boolean> loadedFlippedDragonCards = new ArrayList<>();
        for (int i = 0; i < loadedFlippedDragonCardsStringList.size(); i++) {
            loadedFlippedDragonCards.add(Boolean.parseBoolean(loadedFlippedDragonCardsStringList.get(i)));
        }

        // Load canFlipDragonCards
        boolean loadedCanFlipDragonCards = Boolean.parseBoolean(savedDataValues.get(13));

        // Load canClickOnDragonCards
        boolean loadedCanClickOnDragonCards = Boolean.parseBoolean(savedDataValues.get(14));

        // Load current player
        int loadedCurrentPlayer = Integer.parseInt(savedDataValues.get(15));

        // Load event tile index list
        String loadedEventTileIndexesString = savedDataValues.get(16);
        String processedLoadedEventTileIndexesString = loadedEventTileIndexesString.substring(1,
                loadedEventTileIndexesString.length() - 1);
        processedLoadedEventTileIndexesString =
                processedLoadedEventTileIndexesString.replaceAll("\\s", "");

        List<String> loadedEventTileIndexesStringList = Arrays.asList(processedLoadedEventTileIndexesString.split(","));

        List<Integer> loadedEventTileIndexes = new ArrayList<>();
        for (int i = 0; i < loadedEventTileIndexesStringList.size(); i++) {
            loadedEventTileIndexes.add(Integer.parseInt(loadedEventTileIndexesStringList.get(i)));
        }

        // Load if we have to skip the next player's turn
        boolean loadedSkip = Boolean.parseBoolean(savedDataValues.get(17));





        HashMap<Integer, List<Integer>> loadedVolcanoTilesPerVolcanoCardHashMap = new HashMap<>();

        for (int i = 0; i < loadedVolcanoCardsSequence.size(); i++) {
            int currentVolcanoCardIndex = loadedVolcanoCardsSequence.get(i);
            loadedVolcanoTilesPerVolcanoCardHashMap.put(i, loadedVolcanoCards.get(currentVolcanoCardIndex));
        }




        List<Integer> loadedVolcanoTilesPerVolcanoCard = new ArrayList<>();
        for (int i = 0; i < loadedVolcanoTilesPerVolcanoCardHashMap.size(); i++) {
            List<Integer> currentVolcanoCardVolcanoTilesAnimalNumbers = loadedVolcanoTilesPerVolcanoCardHashMap.get(i);
            loadedVolcanoTilesPerVolcanoCard.add(currentVolcanoCardVolcanoTilesAnimalNumbers.size());
        }



        // Map the Volcano Tiles to their Animal numbers and their Images
        HashMap<Integer, Integer> loadedVolcanoTileIndexToAnimalNumber = new HashMap<>();
        HashMap<Integer, Image> loadedVolcanoTileIndexToImage = new HashMap<>();

        int currentVolcanoTileIndex = 0;
        for (int i = 0; i < loadedVolcanoTilesPerVolcanoCardHashMap.size(); i++) {
            List<Integer> loadedVolcanoTilesForThisVolcanoCard = loadedVolcanoTilesPerVolcanoCardHashMap.get(i);
            for (int j = 0; j < loadedVolcanoTilesForThisVolcanoCard.size(); j++) {
                loadedVolcanoTileIndexToAnimalNumber.put(currentVolcanoTileIndex, loadedVolcanoTilesForThisVolcanoCard.get(j));

                switch (loadedVolcanoTilesForThisVolcanoCard.get(j)) {
                    case 0: // Baby Dragon
                        loadedVolcanoTileIndexToImage.put(currentVolcanoTileIndex,
                                new Image(getClass().getResourceAsStream("BabyDragonVolcanoTile.png")));
                        break;
                    case 1: // Salamander
                        loadedVolcanoTileIndexToImage.put(currentVolcanoTileIndex,
                                new Image(getClass().getResourceAsStream("SalamanderVolcanoTile.png")));
                        break;
                    case 2: // Bat
                        loadedVolcanoTileIndexToImage.put(currentVolcanoTileIndex,
                                new Image(getClass().getResourceAsStream("BatVolcanoTile.png")));
                        break;
                    case 3: // Spider
                        loadedVolcanoTileIndexToImage.put(currentVolcanoTileIndex,
                                new Image(getClass().getResourceAsStream("SpiderVolcanoTile.png")));
                        break;
                    default:
                        break;
                }

                currentVolcanoTileIndex++;
            }
        }









        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Game.fxml"));
        root = fxmlLoader.load();

        // Recreate the chosen board configuration in the loaded game
        GameController gameController = fxmlLoader.getController();
        gameController.buildGame(
                loadedVolcanoCardCount,
                loadedVolcanoTilesPerVolcanoCard,
                loadedCaveVolcanoCards,
                loadedCaveLocations,
                loadedCaveAnimals,
                loadedPlayerCount,
                loadedVolcanoTileIndexToAnimalNumber,
                loadedVolcanoTileIndexToImage
                );
        gameController.adjustPlayers(loadedPlayerCount);




        gameController.recreateGameConditions(
                loadedDragonCards,
                loadedPlayerTilesAwayFromWinning,
                loadedPlayerLocations,
                loadedTurn,
                loadedFlippedDragonCards,
                loadedCanFlipDragonCards,
                loadedCanClickOnDragonCards,
                loadedEventTileIndexes,
                loadedSkip
        );

        // Swap scene to the Game scene
        stage = (Stage)gamePane.getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();








    }

    /**
     * Method to recreate the game conditions of a saved game
     *
     * Created by:
     * @author Kerk Han Chin
     *
     * @param loadedDragonCards - a list containing the order of the dragon cards within the saved game
     * @param loadedPlayerTilesAwayFromWinning - a list containing the number of tiles away from winning for each player in the saved game
     * @param loadedPlayerLocations - a list containing the current locations of each player's dragon token in the saved game
     * @param loadedTurn - the current turn number in the saved game
     * @param loadedFlippedDragonCards - a list denoting which dragon cards have been flipped within the saved game
     * @param loadedCanFlipDragonCards - a boolean indicating whether or not the current player can still flip additional dragon cards based on the saved game
     * @param loadedCanClickOnDragonCards - a boolean indicating whether or not the current player can click on dragon cards based on the saved game
     * @param loadedEventTileIndexes - a list containing the locations of all the event tiles in the saved game
     * @param loadedSkip - a boolean indicating if the next player's turn is to be skipped based on the saved game
     */
    public void recreateGameConditions(List<Integer> loadedDragonCards,
                                       List<Integer> loadedPlayerTilesAwayFromWinning,
                                       List<Integer> loadedPlayerLocations,
                                       int loadedTurn,
                                       List<Boolean> loadedFlippedDragonCards,
                                       boolean loadedCanFlipDragonCards,
                                       boolean loadedCanClickOnDragonCards,
                                       List<Integer> loadedEventTileIndexes,
                                       boolean loadedSkip) {
        // Recreate loaded Dragon Cards configuration on the back-end
        HashMap<Integer, DragonCard> defaultDragonCardIndexToDragonCardMapping = new HashMap<>();

        BabyDragonFactory babyDragonFactory = new BabyDragonFactory();
        SalamanderFactory salamanderFactory = new SalamanderFactory();
        BatFactory batFactory = new BatFactory();
        SpiderFactory spiderFactory = new SpiderFactory();
        DragonPirateFactory dragonPirateFactory = new DragonPirateFactory();
        SwapCardFactory swapCardFactory = new SwapCardFactory();


        int regularDragonCardCount = 12;
        int dragonPirateDragonCardTypes = 2;
        int dragonPirateDragonCardPerTypeCount = 2;
        int regularAnimalTypes = 4;
        int maximumNumberOfAnimalsOnRegularDragonCardFace = 3;
        int swapCardCount = 2;

        int dragonCardIndex = 0;
        for (int i = 0; i < maximumNumberOfAnimalsOnRegularDragonCardFace; i++) {
            for (int j = 0; j < regularAnimalTypes; j++) {
                switch (j) {
                    case 0: // Baby Dragon
                        defaultDragonCardIndexToDragonCardMapping.put(
                                dragonCardIndex,
                                babyDragonFactory.createDragonCard(
                                        i + 1,
                                        new Image(getClass().getResourceAsStream(
                                                "BabyDragonDragonCard" + Integer.toString(
                                                        (i + 1)) + ".png"
                                                )
                                        ))
                                );
                        break;
                    case 1: // Salamander
                        defaultDragonCardIndexToDragonCardMapping.put(
                                dragonCardIndex,
                                salamanderFactory.createDragonCard(
                                        i + 1,
                                        new Image(getClass().getResourceAsStream(
                                                "SalamanderDragonCard" + Integer.toString(
                                                        (i + 1)) + ".png"
                                        )
                                        ))
                        );
                        break;
                    case 2: // Bat
                        defaultDragonCardIndexToDragonCardMapping.put(
                                dragonCardIndex,
                                batFactory.createDragonCard(
                                        i + 1,
                                        new Image(getClass().getResourceAsStream(
                                                "BatDragonCard" + Integer.toString(
                                                        (i + 1)) + ".png"
                                        )
                                        ))
                        );
                        break;
                    case 3: // Spider
                        defaultDragonCardIndexToDragonCardMapping.put(
                                dragonCardIndex,
                                spiderFactory.createDragonCard(
                                        i + 1,
                                        new Image(getClass().getResourceAsStream(
                                                "SpiderDragonCard" + Integer.toString(
                                                        (i + 1)) + ".png"
                                        )
                                        ))
                        );
                        break;
                    default:
                        break;


                }
                dragonCardIndex++;
            }
        }

        for (int i = 0; i < dragonPirateDragonCardPerTypeCount; i++) {
            for (int j = 0; j < dragonPirateDragonCardTypes; j++) {
                defaultDragonCardIndexToDragonCardMapping.put(
                        dragonCardIndex,
                        dragonPirateFactory.createDragonCard(
                                i + 1,
                                new Image(getClass().getResourceAsStream(
                                        "DragonPirateDragonCard" + Integer.toString(
                                                (i + 1)) + ".png"
                                )
                                ))
                );
                dragonCardIndex++;
            }
        }

        for (int i = 0; i < swapCardCount; i++) {
            defaultDragonCardIndexToDragonCardMapping.put(
                    dragonCardIndex,
                    swapCardFactory.createDragonCard(
                            1,
                            new Image(getClass().getResourceAsStream(
                                    "SwapDragonCard.png"
                            )
                            ))
            );
            dragonCardIndex++;
        }



        HashMap<Integer, DragonCard> loadedDragonCardIndexToDragonCardMapping = new HashMap<>();
        HashMap<Integer, Integer> loadedDragonCardOrder = new HashMap<>();
        List<DragonCard> loadedDragonCardsInOrder = new ArrayList<>();

        for (int i = 0; i < dragonCardIndex; i++) {
            int currentDragonCardPositionIndex = loadedDragonCards.get(i);
            loadedDragonCardIndexToDragonCardMapping.put(
                    currentDragonCardPositionIndex,
                    defaultDragonCardIndexToDragonCardMapping.get(i)
            );

            loadedDragonCardOrder.put(i, currentDragonCardPositionIndex);
        }



        for (int i = 0; i < dragonCardIndex; i++) {
            loadedDragonCardsInOrder.add(loadedDragonCardIndexToDragonCardMapping.get(i));
        }

        this.fieryDragonsGame.getVolcano().setDragonCards(loadedDragonCardsInOrder);



        this.dragonCardOrder = loadedDragonCardOrder;




        // Recreate Flipped Dragon Cards on front-end and back-end
        for (int i = 0; i < loadedFlippedDragonCards.size(); i++) {
            ImageView dragonCardImageView = (ImageView) this.dragonCardGroup.getChildren().get(i);
            DragonCard selectedDragonCard = this.fieryDragonsGame.getVolcano().getDragonCards().get(i);
            boolean loadedDragonCardIsFlipped = loadedFlippedDragonCards.get(i);

            if (loadedDragonCardIsFlipped) {
                dragonCardImageView.setImage(selectedDragonCard.getDisplayImage());
                FlipVisitorImpl flipVisitor = new FlipVisitorImpl();
                selectedDragonCard.flipAccept(flipVisitor);
            }
        }

        // Recreate canFlipDragonCards and canClickOnDragonCards
        this.canFlipDragonCard = loadedCanFlipDragonCards;
        this.canClickOnDragonCard = loadedCanClickOnDragonCards;

        // Recreate Turn
        this.fieryDragonsGame.setTurn(loadedTurn);

        // Recreate Player Data (Location, TilesAwayToWinning)
        // Recreate Player Data on the front-end
        for (int i = 0; i < loadedPlayerLocations.size(); i++) {
            if (loadedPlayerLocations.get(i) <= 0) {
                continue;
            }

            Circle currentPlayerCircle = playerCircles.get(
                    i
            );
            ImageView destinationVolcanoTileImageView = this.volcanoTileIndexToVolcanoTileImageView.get(
                    loadedPlayerLocations.get(i) - 1
            );

            currentPlayerCircle.setLayoutX(
                    destinationVolcanoTileImageView.getLayoutX() +
                            destinationVolcanoTileImageView.getParent().getLayoutX() +
                            destinationVolcanoTileImageView.getParent().getParent().getLayoutX() +
                            destinationVolcanoTileImageView.getParent().getParent().getParent().getLayoutX() +
                            20
            );

            currentPlayerCircle.setLayoutY(
                    destinationVolcanoTileImageView.getLayoutY() +
                            destinationVolcanoTileImageView.getParent().getLayoutY() +
                            destinationVolcanoTileImageView.getParent().getParent().getLayoutY() +
                            destinationVolcanoTileImageView.getParent().getParent().getParent().getLayoutY() +
                            50
            );


        }

        // Recreate Player Data on the back-end
        for (int i = 0; i < loadedPlayerLocations.size(); i++) {
            if (loadedPlayerLocations.get(i) <= 0) {
                continue;
            }

            Player player = this.fieryDragonsGame.getPlayers().get(i);
            VolcanoTile playerCurrentPosition = player.getCurrentPosition();
            VolcanoTile destinationVolcanoTile = this.volcanoTileIndexToVolcanoTile.get(
                    loadedPlayerLocations.get(i) - 1
            );

            playerCurrentPosition.setPlayerOnTop(null);
            player.setCurrentPosition(destinationVolcanoTile);
            destinationVolcanoTile.setPlayerOnTop(player);

            player.setVolcanoTilesToWin(loadedPlayerTilesAwayFromWinning.get(i));

            // Recreate Leaderboard Data on the front-end
            String tileName = player.getCurrentPosition().getClass().getSimpleName();
            String imagePath = tileName.substring(0, tileName.length() - 11) + "PlayerDisplay.png";
            animalDisplays.get(player.getPlayerId() - 1).setImage(new Image(getClass().getResourceAsStream(imagePath)));
            tilesToWinDisplays.get(player.getPlayerId() - 1).setText(Integer.toString(player.getVolcanoTilesToWin()));
        }

        // Set the current player in the back-end
        this.fieryDragonsGame.setCurrentPlayer(
                this.fieryDragonsGame.getPlayers().get(
                        this.fieryDragonsGame.getTurn() % loadedPlayerLocations.size()
                )
        );

        // Indicate the current player in the front-end
        if (this.fieryDragonsGame.getTurn() > 0) {
            for (int i = 0; i < loadedPlayerLocations.size(); i++) {
                playerLabels.get(i).setTextFill(Color.BLACK);
            }
            playerLabels.get(this.fieryDragonsGame.getTurn() % loadedPlayerLocations.size()).setTextFill(Color.RED);
        }

        // Recreate event tile indexes
        this.setEventTile(loadedEventTileIndexes);

        // Recreate if we skip the next player's turn or not
        this.skip = loadedSkip;



    }

    /**
     * Method to set a volcano tiles at given positions to become event tiles
     *
     * Created by:
     * @author David Lee
     */
    public void setEventTile(List<Integer> volcanoTileIndexes) {
        this.eventTileIndexes = volcanoTileIndexes;

        // Set all Volcano Tiles at given indexes to event tiles
        for (int i: volcanoTileIndexes) {
            // Change backend VolcanoTile
            volcanoTileIndexToVolcanoTile.get(i).setEvent();

            // Change frontend image
            ImageView volcanoTileImageView = volcanoTileIndexToVolcanoTileImageView.get(i);
            switch (volcanoTileIndexToAnimalNumber.get(i)) {
                case 0: // Baby Dragon
                    volcanoTileImageView.setImage(
                            new Image(
                                getClass().getResourceAsStream("BabyDragonEventVolcanoTile.png")
                            )
                    );
                    break;
                case 1: // Salamander
                    volcanoTileImageView.setImage(
                            new Image(
                                    getClass().getResourceAsStream("SalamanderEventVolcanoTile.png")
                            )
                    );
                    break;
                case 2: // Bat
                    volcanoTileImageView.setImage(
                            new Image(
                                    getClass().getResourceAsStream("BatEventVolcanoTile.png")
                            )
                    );
                    break;
                case 3: // Spider
                    volcanoTileImageView.setImage(
                            new Image(
                                    getClass().getResourceAsStream("SpiderEventVolcanoTile.png")
                            )
                    );
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Method to swap the current player's position to the closest player (who is eligible for swapping).
     * The swapping is done through these following processes:
     * - Check if there are any players aside from the current player that has left their cave on the board
     * - If there is, calculate their distance to the current player's position (both clockwise and anti-clockwise)
     * - Once all distances are found between all eligible players the lowest distance is selected and the direction
     *      of traversal is determined.
     * - Based on the distance and direction of traversal, both the current player & the closest player's details
     *      adjusted and swapped (position, remaining tiles, current animal, etc.)
     * Created by:
     * @author Louis Meng Hoe Chow
     */
    private void swap() {
        int totalNumberOfVolcanoTiles = this.totalVolcanoTilesCount;
        Player currentPlayer = fieryDragonsGame.getCurrentPlayer();

        Player closestPlayer = null;
        boolean outClock = false;
        int bestDifference = 9999;

        // variables for finding closest distance
        int currentPos = currentPlayer.getCurrentPosition().getId();
        boolean inCave = currentPos < 0 ? true: false;
        currentPos = currentPos < 0 ? currentPos*-1 : currentPos;
        int posDifference;
        int posDifferenceAntiClock;
        int posDifferenceClock;
        boolean isClock;



        // Loop that finds any eligible players and finds the closest one
        for (Player player : fieryDragonsGame.getPlayers()) {
            int playerPos = player.getCurrentPosition().getId();
            if ((player.getPlayerId() != currentPlayer.getPlayerId()) &&
                    (playerPos >= 0)) {

                if (currentPos > playerPos) {
                    posDifferenceAntiClock = inCave ? currentPos - playerPos + 2: currentPos - playerPos;
                    posDifferenceClock = playerPos + totalNumberOfVolcanoTiles - currentPos;
                } else {
                    posDifferenceAntiClock = currentPos + totalNumberOfVolcanoTiles - playerPos;
                    posDifferenceClock = inCave ? playerPos - currentPos + 2 : playerPos - currentPos;
                }



                if (posDifferenceClock <= posDifferenceAntiClock) {
                    isClock = true;
                    posDifference = posDifferenceClock;
                } else {
                    isClock = false;
                    posDifference = posDifferenceAntiClock;
                }

                if (posDifference < bestDifference) {
                    bestDifference = posDifference;
                    closestPlayer = player;
                    outClock = isClock;
                } else if (posDifference == bestDifference && isClock) {
                    closestPlayer = player;
                    outClock = isClock;
                }
            }
        }

        bestDifference = inCave && !outClock ? bestDifference - 2 :  bestDifference;

        // if there isn't any eligible players, don't swap
        if (closestPlayer != null) {
            // Caching current player's position to replace with closest player
            VolcanoTile cacheCurrentPos = currentPlayer.getCurrentPosition();

            // Swapping Current Player's details internally
            currentPlayer.setCurrentPosition(closestPlayer.getCurrentPosition());
            currentPlayer.getCurrentPosition().setPlayerOnTop(currentPlayer);
            if (currentPlayer.getVolcanoTilesToWin() <= bestDifference && outClock) {
                currentPlayer.setVolcanoTilesToWin(currentPlayer.getVolcanoTilesToWin()-bestDifference+totalNumberOfVolcanoTiles);
            } else if (outClock) {
                currentPlayer.setVolcanoTilesToWin(currentPlayer.getVolcanoTilesToWin()-bestDifference);
            } else {
                currentPlayer.setVolcanoTilesToWin(currentPlayer.getVolcanoTilesToWin()+bestDifference);
            }

            // After swapping, if current player's token lands on an event tile, skip next player's turn
            if (currentPlayer.getCurrentPosition().isEvent()) {
                skip = true;
            }

            // Swapping Closest Player's details internally
            closestPlayer.setCurrentPosition(cacheCurrentPos);
            closestPlayer.getCurrentPosition().setPlayerOnTop(closestPlayer);
            if (closestPlayer.getVolcanoTilesToWin() <= bestDifference && !outClock) {
                closestPlayer.setVolcanoTilesToWin(closestPlayer.getVolcanoTilesToWin()-bestDifference+totalNumberOfVolcanoTiles);
            } else if (!outClock) {
                closestPlayer.setVolcanoTilesToWin(closestPlayer.getVolcanoTilesToWin()-bestDifference);
            } else {
                closestPlayer.setVolcanoTilesToWin(closestPlayer.getVolcanoTilesToWin()+bestDifference);
            }

            // Swapping Current Player & Closest Player's details externally
            Circle currentPlayerCircle = playerCircles.get(currentPlayer.getPlayerId() - 1);
            Circle closestPlayerCircle = playerCircles.get(closestPlayer.getPlayerId() - 1);

            // Caching current player's position to replace with closest player
            double cacheCurrentX = currentPlayerCircle.getLayoutX();
            double cacheCurrentY = currentPlayerCircle.getLayoutY();

            // Swapping token position
            currentPlayerCircle.setLayoutX(closestPlayerCircle.getLayoutX());
            currentPlayerCircle.setLayoutY(closestPlayerCircle.getLayoutY());
            closestPlayerCircle.setLayoutX(cacheCurrentX);
            closestPlayerCircle.setLayoutY(cacheCurrentY);

            String tileNameCurrent = currentPlayer.getCurrentPosition().getClass().getSimpleName();
            String imagePathCurrent = tileNameCurrent.substring(0, tileNameCurrent.length() - 11) + "PlayerDisplay.png";
            String tileNameClosest = closestPlayer.getCurrentPosition().getClass().getSimpleName();
            String imagePathClosest = tileNameClosest.substring(0, tileNameClosest.length() - 11) + "PlayerDisplay.png";

            // Swapping animal displays
            animalDisplays.get(currentPlayer.getPlayerId() - 1).setImage(new Image(getClass().getResourceAsStream(imagePathCurrent)));
            animalDisplays.get(closestPlayer.getPlayerId() - 1).setImage(new Image(getClass().getResourceAsStream(imagePathClosest)));

            // Updating tiles to win displays
            tilesToWinDisplays.get(currentPlayer.getPlayerId() - 1).setText(Integer.toString(currentPlayer.getVolcanoTilesToWin()));
            tilesToWinDisplays.get(closestPlayer.getPlayerId() - 1).setText(Integer.toString(closestPlayer.getVolcanoTilesToWin()));
        }

    }
}
