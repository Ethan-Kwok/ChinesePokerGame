package com.example.chinesepokergame2;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class MainController {

    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private HBox frontHBox, middleHBox, backHBox, buttonsHBox;
    @FXML
    private VBox cheatVBox;
    @FXML
    private Button sortHandButton, shuffleHandButton, resetHandButton, getBestHandButton, showdownButton, startButton,
            restartButton, enableCheatsButton;
    @FXML
    private Label playerScoreLabel, botScoreLabel, accuracyLabel, playerLuckLabel, botLuckLabel;

    private Hand playerHand;
    private Deck deck;
    private boolean gameStart;
    private int playerScore, botScore, numRounds;
    private double cardWidth;
    private double cardHeight;
    private double playerExpVal, bestExpVal, accuracy, botExpVal, playerLuck, botLuck;
    private static final double cardScaleFactor = .25;
    private static final double marginSize = 50, topMarginSize = 40;
    private static final DecimalFormat df = new DecimalFormat("0.00");
    private static final double sceneWidth = 1920, sceneHeight = 1080;

    @FXML
    public void initialize() {
        //get the card width and height
        URL imageURL = getClass().getResource("/images/card1.png");
        Image image = new Image(imageURL.toString());
        cardWidth = image.getWidth() * cardScaleFactor;
        cardHeight = image.getHeight() * cardScaleFactor;

        createHBox(frontHBox, 3);
        createHBox(middleHBox, 5);
        createHBox(backHBox, 5);
        setHBoxLayout();

        buttonsHBox.setLayoutX(marginSize);
        buttonsHBox.setLayoutY(topMarginSize);
        resetCheatLabels();

        enableCheatsButton.setLayoutX(marginSize);
        enableCheatsButton.setLayoutY(sceneHeight - (enableCheatsButton.getPrefHeight() + topMarginSize));
        cheatVBox.setLayoutX(sceneWidth - marginSize - cheatVBox.getPrefWidth());
        cheatVBox.setLayoutY(topMarginSize);
        cheatVBox.setVisible(false);
    }

    public MainController() {
        this.deck = new Deck();
        this.playerHand = new Hand();
        playerExpVal = 0;
        bestExpVal = 0;
        accuracy = 1;
        numRounds = 0;
    }

    public void startGame() {
        if (gameStart == false) {
            gameStart = true;
            startButton.setText("Restart Game");
            dealHand();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Are you sure you want to restart the game?");
            alert.setTitle("Restart Game");
            alert.setHeaderText(null);

            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                playerScore = 0;
                botScore = 0;
                playerExpVal = 0;
                bestExpVal = 0;
                botExpVal = 0;
                accuracy = 0;
                playerLuck = 0;
                botLuck = 0;
                numRounds = 0;
                updateScoreLabels();
                resetCheatLabels();
                enableCheatsButton.setVisible(true);
                cheatVBox.setVisible(false);
                resetDeck();
                dealHand();
            }
        }
    }

    public void createHBox(HBox myHBox, int maxCards) {
        myHBox.setPrefWidth(cardWidth * maxCards + 2);
        myHBox.setPrefHeight(cardHeight + 2);
        myHBox.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Node clickedNode = (Node) event.getTarget();
                if (myHBox.getChildren().contains(clickedNode) && clickedNode instanceof MyImageView) {
                    MyImageView iv = (MyImageView) clickedNode;
                    MyImageView ivCopy = createCardIv(iv.getCard());
                    myHBox.getChildren().remove(iv);
                    playerHand.getFront().remove(iv.getCard());
                    playerHand.getMiddle().remove(iv.getCard());
                    playerHand.getBack().remove(iv.getCard());
                    playerHand.getHand().add(iv.getCard());
                    playerHand.getImageViews().add(ivCopy);
                    ivCopy.setLayoutX(mainAnchorPane.getWidth() / 2 - cardWidth / 2);
                    ivCopy.setLayoutY(mainAnchorPane.getHeight() / 2 - cardHeight / 2);
                    mainAnchorPane.getChildren().add(ivCopy);
                }
            }
        });
    }

    public void setHBoxLayout() {
        frontHBox.setLayoutX(marginSize + cardWidth);
        middleHBox.setLayoutX(marginSize);
        backHBox.setLayoutX(marginSize);

        double top = marginSize;
        double bottom = sceneHeight - marginSize;
        double hBoxHeight = frontHBox.getPrefHeight();
        double firstQuarter = (bottom - top) / 4 + top;
        double secondQuarter = (bottom - top) / 2 + top;
        double thirdQuarter = (3 * (bottom - top)) / 4 + top;
        frontHBox.setLayoutY(firstQuarter - hBoxHeight / 2);
        middleHBox.setLayoutY(secondQuarter - hBoxHeight / 2);
        backHBox.setLayoutY(thirdQuarter - hBoxHeight / 2);
    }

    public void dealHand() {
        if (deck.getDeck().size() != 52) return;
        ArrayList<Card> hand = deck.dealHand();
        if (hand == null) {
            emptyDeckAlert();
            return;
        }
        playerHand.setHand(hand);
        for (int i = 0; i < 13; i++) {
            MyImageView iv = createCardIv(playerHand.getHand().get(i));
            iv.setLayoutX(getXPos(i));
            iv.setLayoutY(getYPos(i));
            playerHand.getImageViews().add(iv);
            mainAnchorPane.getChildren().add(iv);
        }
    }

    private MyImageView createCardIv(Card card) {
        final URL imageURL = getClass().getResource(card.getImagePath());
        Image image = new Image(imageURL.toString());
        final MyImageView iv = new MyImageView(card, image, cardScaleFactor);

        final double[] dragDelta = new double[2];
        iv.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                iv.toFront();
                dragDelta[0] = iv.getTranslateX() - mouseEvent.getSceneX();
                dragDelta[1] = iv.getTranslateY() - mouseEvent.getSceneY();
            }
        });

        iv.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                iv.setTranslateX(event.getSceneX() + dragDelta[0]);
                iv.setTranslateY(event.getSceneY() + dragDelta[1]);
            }
        });

        iv.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (iv.getBoundsInParent().intersects(backHBox.getBoundsInParent()) && playerHand.getBack().size() < 5) {
                    addToBackHBox(iv, card);
                    ((AnchorPane) iv.getParent()).getChildren().remove(iv);
                }
                else if (iv.getBoundsInParent().intersects(middleHBox.getBoundsInParent()) && playerHand.getMiddle().size() < 5) {
                    addToMiddleHBox(iv, card);
                    ((AnchorPane) iv.getParent()).getChildren().remove(iv);
                }
                else if (iv.getBoundsInParent().intersects(frontHBox.getBoundsInParent()) && playerHand.getFront().size() < 3) {
                    addToFrontHBox(iv, card);
                    ((AnchorPane) iv.getParent()).getChildren().remove(iv);
                }
            }
        });

        iv.setOnDragDetected(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                Dragboard db = iv.startDragAndDrop(TransferMode.MOVE);
                ClipboardContent content = new ClipboardContent();
                content.putString(iv.getId());
                db.setContent(content);
                event.consume();
            }
        });

        iv.setOnDragDone(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                if (event.getTransferMode() == TransferMode.MOVE) {
                    iv.setTranslateX(0);
                    iv.setTranslateY(0);
                }
                event.consume();
            }
        });

        return iv;
    }

    private void addToBackHBox(MyImageView iv, Card card) {
        MyImageView ivCopy = iv.clone();
        playerHand.getHand().remove(card);
        playerHand.getImageViews().remove(iv);
        playerHand.getBack().add(card);
        backHBox.getChildren().add(ivCopy);
    }

    private void addToMiddleHBox(MyImageView iv, Card card) {
        MyImageView ivCopy = iv.clone();
        playerHand.getHand().remove(card);
        playerHand.getImageViews().remove(iv);
        playerHand.getMiddle().add(card);
        middleHBox.getChildren().add(ivCopy);
    }

    private void addToFrontHBox(MyImageView iv, Card card) {
        MyImageView ivCopy = iv.clone();
        playerHand.getHand().remove(card);
        playerHand.getImageViews().remove(iv);
        playerHand.getFront().add(card);
        frontHBox.getChildren().add(ivCopy);
    }

    public void onSortHandButtonClick() throws IOException {
        //if (playerHand.getHand().size() != 13) return;
        int handSize = playerHand.getHand().size();
        sort(playerHand.getHand());
        mainAnchorPane.getChildren().removeAll(playerHand.getImageViews());
        playerHand.getImageViews().clear();
        for (int i = 0; i < handSize; i++) {
            MyImageView iv = createCardIv(playerHand.getHand().get(i));

            //iv.setScaleX(cardScaleFactor);
            //iv.setScaleY(cardScaleFactor);
            iv.setLayoutX(getXPos(i));
            iv.setLayoutY(getYPos(i));
            playerHand.getImageViews().add(iv);
            mainAnchorPane.getChildren().add(iv);
        }
    }

    public void onShuffleHandButtonClick() {
        int handSize = playerHand.getHand().size();
        Collections.shuffle(playerHand.getHand());
        mainAnchorPane.getChildren().removeAll(playerHand.getImageViews());
        playerHand.getImageViews().clear();
        for (int i = 0; i < handSize; i++) {
            MyImageView iv = createCardIv(playerHand.getHand().get(i));

            iv.setLayoutX(getXPos(i));
            iv.setLayoutY(getYPos(i));
            playerHand.getImageViews().add(iv);
            mainAnchorPane.getChildren().add(iv);
        }
    }

    //insertion sort for hand of 13 cards
    private void sort(ArrayList<Card> hand) {
        for (int i = 1; i < hand.size(); i++) {
            Card key = hand.get(i);
            int j = i - 1;
            while (j >= 0 && hand.get(j).getId() > key.getId()) {
                hand.set(j + 1, hand.get(j));
                j--;
            }
            hand.set(j + 1, key);
        }
    }

    public void emptyDeckAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("The deck is empty.");
        alert.setTitle("ERROR");
        alert.setHeaderText(null);

        alert.showAndWait();
    }

    private double getXPos(int i) {
        double centerX = getCenterX(mainAnchorPane);
        if (i < 7) {
            double rowWidth = cardWidth * 7;
            double rowCenter = centerX - (rowWidth / 2);
            return rowCenter + i * cardWidth;
        }
        else {
            double rowWidth = cardWidth * 6;
            double rowCenter = centerX - (rowWidth / 2);
            return rowCenter + (i - 7) * cardWidth;
        }
    }

    private double getYPos(int i) {
        double centerY = getCenterY(mainAnchorPane);
        if (i < 7) return centerY - cardHeight;
        else return centerY;
    }

    public void resetDeck() {
        for (MyImageView iv : playerHand.getImageViews()) {
            mainAnchorPane.getChildren().removeAll(iv);
        }
        frontHBox.getChildren().clear();
        middleHBox.getChildren().clear();
        backHBox.getChildren().clear();

        this.deck = new Deck();
        this.playerHand = new Hand();
    }

    public double getCenterX(Pane pane) {
        double center = (pane.getWidth() + marginSize + backHBox.getWidth()) / 2;
        return center;
    }

    public double getCenterY(Pane pane) {
        double center = pane.getHeight() / 2;
        return center;
    }

    public void resetHand() {
        frontHBox.getChildren().clear();
        middleHBox.getChildren().clear();
        backHBox.getChildren().clear();
        resetHandRemoveCards(playerHand.getFront());
        resetHandRemoveCards(playerHand.getMiddle());
        resetHandRemoveCards(playerHand.getBack());

        onShuffleHandButtonClick();
        //cuffedHandTest.runTest();
    }

    //helper method for resetHand
    public void resetHandRemoveCards(ArrayList<Card> subHand) {
        while (subHand.size() != 0) {
            Card card = subHand.get(0);
            MyImageView iv = createCardIv(card);
            playerHand.getFront().remove(iv.getCard());
            playerHand.getMiddle().remove(iv.getCard());
            playerHand.getBack().remove(iv.getCard());
            playerHand.getHand().add(iv.getCard());
            playerHand.getImageViews().add(iv);
            mainAnchorPane.getChildren().add(iv);
        }
    }

    public void getBestHand() {

        frontHBox.getChildren().clear();
        middleHBox.getChildren().clear();
        backHBox.getChildren().clear();
        resetHandRemoveCards(playerHand.getFront());
        resetHandRemoveCards(playerHand.getMiddle());
        resetHandRemoveCards(playerHand.getBack());
        ArrayList<Card> newCards = new ArrayList();
        for (MyImageView iv : playerHand.getImageViews()) {
            newCards.add(iv.getCard());
            mainAnchorPane.getChildren().removeAll(iv);
        }

        Hand newHand = new Hand(newCards);
        if (newHand.getHand().size() != 13) return;
        newHand = newHand.getOptimalHand();

        /*for (int i = 0; i < 13; i++) {
            MyImageView iv = createCardIv(playerHand.getHand().get(i));
            playerHand.getImageViews().add(iv);
        }*/

        for (Card card : newHand.getBack()) {
            MyImageView iv = createCardIv(card);
            addToBackHBox(iv, card);
        }
        for (Card card : newHand.getMiddle()) {
            MyImageView iv = createCardIv(card);
            addToMiddleHBox(iv, card);
        }
        for (Card card : newHand.getFront()) {
            MyImageView iv = createCardIv(card);
            addToFrontHBox(iv, card);
        }

        newHand.getHand().clear();

        this.playerHand = newHand;
    }

    public void startShowdown() throws IOException {
        if (!playerHand.isValidHand()) {
            invalidHandAlert();
            return;
        }
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("showdown-view.fxml"));
        Parent MainPage = fxmlLoader.load();
        ShowdownController showdownController = fxmlLoader.getController();
        showdownController.setPlayerHand(playerHand);
        showdownController.setDeck(deck);
        Stage stage = new Stage();
        stage.setTitle("Showdown");
        stage.setScene(new Scene(MainPage, 1920, 1080));
        showdownController.fillPlayerHand();
        Hand botHand = showdownController.createBotHand();
        showdownController.fillBotHand();

        stage.show();

        playerHand.setHandIds();
        botHand.setHandIds();
        if (playerHand.getHandIds(0) > botHand.getHandIds(0)) {
            playerScore++;
        }
        else if (playerHand.getHandIds(0) < botHand.getHandIds(0)) {
            botScore++;
        }
        if (playerHand.getHandIds(1) > botHand.getHandIds(1)) {
            playerScore++;
        }
        else if (playerHand.getHandIds(1) < botHand.getHandIds(1)) {
            botScore++;
        }
        if (playerHand.getHandIds(2) > botHand.getHandIds(2)) {
            playerScore++;
        }
        else if (playerHand.getHandIds(2) < botHand.getHandIds(2)) {
            botScore++;
        }
        numRounds++;

        playerExpVal += playerHand.calcExpectedVal();
        Hand bestHand = playerHand.resetClone();
        bestExpVal += bestHand.getOptimalHand().calcExpectedVal();
        botExpVal += botHand.calcExpectedVal();
        updateScoreLabels();
        updateCheats();

        this.resetDeck();
        this.dealHand();

    }

    public void enableCheats() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Are you sure you want to enable cheats?");
        alert.setTitle("Enable Cheats");
        alert.setHeaderText(null);

        Optional<ButtonType> option = alert.showAndWait();
        if (option.get() == ButtonType.OK) {
            enableCheatsButton.setVisible(false);
            cheatVBox.setVisible(true);
        }
    }

    public void updateAccuracy() {
        if (bestExpVal == 0) {
            accuracy = 1;
            return;
        }
        accuracy = playerExpVal / bestExpVal;
        //String accuracyString = df.format(accuracy * 100);
        //accuracyLabel.setText(accuracyString + "%");
    }

    public void updateLuck() {
        if (numRounds == 0) {
            playerLuck = 1;
            botLuck = 1;
            return;
        }
        playerLuck = bestExpVal / (3 * numRounds);
        botLuck = botExpVal / (3 * numRounds);
    }

    public void updateScoreLabels() {
        playerScoreLabel.setText("" + playerScore);
        botScoreLabel.setText("" + botScore);
    }

    public void updateCheats() {
        updateAccuracy();
        updateLuck();
        updateCheatLabels();
    }
    public void updateCheatLabels() {
        String accuracyString = df.format(accuracy * 100);
        accuracyLabel.setText(accuracyString + "%");
        String playerLuckString = df.format(playerLuck * 100);
        playerLuckLabel.setText(playerLuckString + "%");
        String botLuckString = df.format(botLuck * 100);
        botLuckLabel.setText(botLuckString + "%");
    }

    public void resetCheatLabels() {
        accuracyLabel.setText("--");
        playerLuckLabel.setText("--");
        botLuckLabel.setText("--");
    }

    public void invalidHandAlert() throws IOException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Invalid hand.");
        alert.setTitle("ERROR");
        alert.setHeaderText(null);

        alert.showAndWait();
    }
}
