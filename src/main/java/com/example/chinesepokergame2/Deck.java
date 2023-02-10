package com.example.chinesepokergame2;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> deck;

    public Deck() {
        ArrayList<Card> deck = new ArrayList();
        for (int i = 0; i < 52; i++) {
            Card card = new Card(i);
            deck.add(card);
        }
        Collections.shuffle(deck);
        this.deck = deck;
    }

    public ArrayList<Card> getDeck() {
        return this.deck;
    }

    public ArrayList<Card> dealHand() {
        if (this.deck.size() < 13) return null;
        ArrayList<Card> hand = new ArrayList();
        for (int i = 0; i < 13; i++) {
            Card card = this.deck.remove(this.deck.size() - 1);
            hand.add(card);
        }
        return hand;
    }

}
