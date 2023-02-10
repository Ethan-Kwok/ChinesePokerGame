package com.example.chinesepokergame2;

public class Card {
    private int rank;
    private int suit;
    private int id;
    /*
    Rank is an integer from 2-14. 11 is jack, 12 is queen, 13 is king, 14 is ace.
    Suit is an integer from 0-3. 0 is clubs, 1 is diamonds, 2 is hearts, 3 is spades.
    ID is an integer from 0-51. This represents the relative value of the card. 0 is a 2 of clubs, 51 is an ace of spades
     */


    public Card(int newRank, int newSuit) {
        this.rank = newRank;
        this.suit = newSuit;
        this.id = newRank * 4 - 8 + newSuit;
    }

    public Card(int newId) {
        int newRank = (newId / 4) + 2;
        this.rank = newRank;
        this.suit = newId - (4 * (newRank - 2));
        this.id = newId;
    }

    public Card(Card card) {
        this.rank = card.getRank();
        this.suit = card.getSuit();
        this.id = card.getId();
    }

    @Override
    public String toString() {
        String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
        String[] ranks = {null, null, "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        String s = ranks[this.rank] + " of " + suits[this.suit];
        return s;
    }

    public int getRank() {
        return this.rank;
    }

    public int getSuit() {
        return this.suit;
    }

    public int getId() {
        return this.id;
    }

    public String getImagePath() {
        return "/images/card" + this.id + ".png";
    }


}
