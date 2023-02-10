package com.example.chinesepokergame2;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MyImageView extends ImageView {

    private Card card;

    public MyImageView(Card card, Image image, double cardScaleFactor) {
        super(image);
        this.card = card;
        this.setFitWidth(image.getWidth() * cardScaleFactor);
        this.setFitHeight(image.getHeight() * cardScaleFactor);
    }

    public Card getCard() {
        return card;
    }

    public MyImageView clone() {
        double cardScaleFactor = this.getFitWidth() / this.getImage().getWidth();
        MyImageView ivCopy = new MyImageView(this.card, this.getImage(), cardScaleFactor);
        //ivCopy.setFitWidth(this.getFitWidth());
        //ivCopy.setFitHeight(this.getFitHeight());
        // any other properties that need to be copied
        return ivCopy;
    }

}
