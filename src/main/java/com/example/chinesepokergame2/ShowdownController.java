package com.example.chinesepokergame2;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;

public class ShowdownController {

    @FXML
    private Label playerHandLabel, playerScoreLabel, botHandLabel, botScoreLabel;
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private HBox frontHBox, middleHBox, backHBox,
            botFrontHBox, botMiddleHBox, botBackHBox;
    @FXML
    private Button revealButton;
    private Hand playerHand, botHand;
    private Deck deck;
    private int revealCounter, playerScore, botScore;
    private double cardWidth;
    private double cardHeight;
    private static final double cardScaleFactor = .25;
    private static final double marginSize = 50;
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
        createHBox(botFrontHBox, 3);
        createHBox(botMiddleHBox, 5);
        createHBox(botBackHBox, 5);
        setHBoxLayout();

        revealButton.setLayoutX(sceneWidth / 2 - revealButton.getPrefWidth() / 2);
        revealButton.setLayoutY(sceneHeight / 2 - revealButton.getPrefHeight() / 2);
        revealButton.setText("Reveal");

        setLabelLayout();
    }

    public ShowdownController() {
        botHand = new Hand();
        revealCounter = 0;
        playerScore = 0;
        botScore = 0;
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

        botFrontHBox.setLayoutX(sceneWidth - (marginSize + cardWidth) - cardWidth * 3);
        botMiddleHBox.setLayoutX(sceneWidth - marginSize - cardWidth * 5);
        botBackHBox.setLayoutX(sceneWidth - marginSize - cardWidth * 5);
        botFrontHBox.setLayoutY(firstQuarter - hBoxHeight / 2);
        botMiddleHBox.setLayoutY(secondQuarter - hBoxHeight / 2);
        botBackHBox.setLayoutY(thirdQuarter - hBoxHeight / 2);
    }

    public void setLabelLayout() {
        playerHandLabel.setLayoutX(marginSize + cardWidth * 2.5 - playerHandLabel.getPrefWidth() / 2);
        botHandLabel.setLayoutX(sceneWidth - (marginSize + cardWidth * 2.5 + botHandLabel.getPrefWidth() / 2));
        playerScoreLabel.setLayoutX(marginSize + cardWidth * 2.5 - playerScoreLabel.getPrefWidth() / 2);
        botScoreLabel.setLayoutX(sceneWidth - (marginSize + cardWidth * 2.5 + botScoreLabel.getPrefWidth() / 2));

        double top = marginSize;
        double bottom = frontHBox.getLayoutY();
        playerScoreLabel.setLayoutY((bottom - top) / 2 - playerScoreLabel.getPrefHeight() / 2 + top);
        botScoreLabel.setLayoutY((bottom - top) / 2 - botScoreLabel.getPrefHeight() / 2 + top);

        top = backHBox.getLayoutY() + backHBox.getPrefHeight();
        bottom = sceneHeight - marginSize;
        playerHandLabel.setLayoutY((bottom - top) / 2 - playerHandLabel.getPrefHeight() / 2 + top);
        botHandLabel.setLayoutY((bottom - top) / 2 - botHandLabel.getPrefHeight() / 2 + top);
    }

    public void fillPlayerHand() {
        URL imageURL = getClass().getResource("/images/back.png");
        for (Card card : playerHand.getFront()) {
            //URL imageURL = getClass().getResource(card.getImagePath());
            Image image = new Image(imageURL.toString());
            MyImageView iv = new MyImageView(card, image, cardScaleFactor);
            frontHBox.getChildren().add(iv);
        }
        for (Card card : playerHand.getMiddle()) {
            //URL imageURL = getClass().getResource(card.getImagePath());
            Image image = new Image(imageURL.toString());
            MyImageView iv = new MyImageView(card, image, cardScaleFactor);
            middleHBox.getChildren().add(iv);
        }
        for (Card card : playerHand.getBack()) {
            //URL imageURL = getClass().getResource(card.getImagePath());
            Image image = new Image(imageURL.toString());
            MyImageView iv = new MyImageView(card, image, cardScaleFactor);
            backHBox.getChildren().add(iv);
        }
    }

    public Hand createBotHand() {
        botHand.setHand(deck.dealHand());
        botHand = botHand.getOptimalHand();
        return botHand;
    }

    public void fillBotHand() {
        URL imageURL = getClass().getResource("/images/back.png");
        for (Card card : botHand.getFront()) {
            Image image = new Image(imageURL.toString());
            MyImageView iv = new MyImageView(card, image, cardScaleFactor);
            botFrontHBox.getChildren().add(iv);
        }
        for (Card card : botHand.getMiddle()) {
            Image image = new Image(imageURL.toString());
            MyImageView iv = new MyImageView(card, image, cardScaleFactor);
            botMiddleHBox.getChildren().add(iv);
        }
        for (Card card : botHand.getBack()) {
            Image image = new Image(imageURL.toString());
            MyImageView iv = new MyImageView(card, image, cardScaleFactor);
            botBackHBox.getChildren().add(iv);
        }
    }

    public void createHBox(HBox myHBox, int maxCards) {
        myHBox.setPrefWidth(cardWidth * maxCards + 2);
        myHBox.setPrefHeight(cardHeight + 2);
    }

    public void setPlayerHand(Hand newHand) {
        this.playerHand = newHand;
    }
    public void setDeck(Deck newDeck) {
        this.deck = newDeck;
    }

    public void onReveal() {
        if (revealCounter == 0) {
            botFrontHBox.getChildren().clear();
            for (Card card : botHand.getFront()) {
                URL imageURL = getClass().getResource(card.getImagePath());
                Image image = new Image(imageURL.toString());
                MyImageView iv = new MyImageView(card, image, cardScaleFactor);
                botFrontHBox.getChildren().add(iv);
            }
            frontHBox.getChildren().clear();
            for (Card card : playerHand.getFront()) {
                URL imageURL = getClass().getResource(card.getImagePath());
                Image image = new Image(imageURL.toString());
                MyImageView iv = new MyImageView(card, image, cardScaleFactor);
                frontHBox.getChildren().add(iv);
            }
            String playerHandString = playerHand.handToString(0);
            playerHandLabel.setText(playerHandString);
            String botHandString = botHand.handToString(0);
            botHandLabel.setText(botHandString);
        }

        else if (revealCounter == 1) {
            botMiddleHBox.getChildren().clear();
            for (Card card : botHand.getMiddle()) {
                URL imageURL = getClass().getResource(card.getImagePath());
                Image image = new Image(imageURL.toString());
                MyImageView iv = new MyImageView(card, image, cardScaleFactor);
                botMiddleHBox.getChildren().add(iv);
            }
            middleHBox.getChildren().clear();
            for (Card card : playerHand.getMiddle()) {
                URL imageURL = getClass().getResource(card.getImagePath());
                Image image = new Image(imageURL.toString());
                MyImageView iv = new MyImageView(card, image, cardScaleFactor);
                middleHBox.getChildren().add(iv);
            }
            String playerHandString = playerHand.handToString(1);
            playerHandLabel.setText(playerHandString);
            String botHandString = botHand.handToString(1);
            botHandLabel.setText(botHandString);
        }

        else if (revealCounter == 2) {
            botBackHBox.getChildren().clear();
            for (Card card : botHand.getBack()) {
                URL imageURL = getClass().getResource(card.getImagePath());
                Image image = new Image(imageURL.toString());
                MyImageView iv = new MyImageView(card, image, cardScaleFactor);
                botBackHBox.getChildren().add(iv);
            }
            backHBox.getChildren().clear();
            for (Card card : playerHand.getBack()) {
                URL imageURL = getClass().getResource(card.getImagePath());
                Image image = new Image(imageURL.toString());
                MyImageView iv = new MyImageView(card, image, cardScaleFactor);
                backHBox.getChildren().add(iv);
            }
            String playerHandString = playerHand.handToString(2);
            playerHandLabel.setText(playerHandString);
            String botHandString = botHand.handToString(2);
            botHandLabel.setText(botHandString);
            revealButton.setPrefWidth(revealButton.getPrefWidth() + 50);
            revealButton.setLayoutX(sceneWidth / 2 - revealButton.getPrefWidth() / 2);
            revealButton.setText("Next Round");
        }

        if (revealCounter < 3) {
            if (playerHand.getHandIds(revealCounter) > botHand.getHandIds(revealCounter)) {
                playerScore++;
            } else if (playerHand.getHandIds(revealCounter) < botHand.getHandIds(revealCounter)) {
                botScore++;
            }
            updateScoreLabels();
        }
        else {
            Stage stage = (Stage) revealButton.getScene().getWindow();
            stage.close();
        }

        revealCounter++;
    }

    public void updateScoreLabels() {
        playerScoreLabel.setText("Player: " + playerScore);
        botScoreLabel.setText("Bot: " + botScore);
    }

}
