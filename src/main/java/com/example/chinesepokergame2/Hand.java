package com.example.chinesepokergame2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hand {
    private ArrayList<Card> hand;
    private ArrayList<Card> front;
    private ArrayList<Card> middle;
    private ArrayList<Card> back;
    private ArrayList<MyImageView> imageViews;
    private int[] handIds; //index 0 is front, index 1 is mid, index 2 is back
    private static final int maxHandDefaultId5 = 257;
    private static final int[] handDefaultId5 = {218, 205, 192, 160, 120, 107, 71, 32, 0};
//    private static final int maxHandDefaultId3 = 95;
//    private static final int[] handDefaultId3 = {83, 44, 0};
    private static final int maxHandDefaultId3 = 119;
    private static final int[] handDefaultId3 = {107, 71, 32, -12};
    private static final double[] backHandLowProb = {.9911, .9663, .6730, .3607, .1695, .1442, .0175, 0, 0};
    private static final double[] backHandHighProb = {1, .9904, .9645, .6537, .3605, .1542, .1436, .0171, 0};
    private static final double[] midHandLowProb = {1, 1, .9938, .9107, .7551, .6442, .3617, .0130, 0};
    private static final double[] midHandHighProb = {1, 1, .9999, .9928, .9107, .7328, .6318, .3602, .0129};
    private static final double[] frontHandLowProb = {.9943, .9935, .4673, 0};
    private static final double[] frontHandHighProb = {1, .9935, .9935, .4667};

    public Hand() {
        this.hand = new ArrayList();
        this.front = new ArrayList();
        this.middle = new ArrayList();
        this.back = new ArrayList();
        this.handIds = new int[3];
        this.imageViews = new ArrayList();
    }
    public Hand(ArrayList<Card> newHand) {
        this.hand = newHand;
        this.front = new ArrayList();
        this.middle = new ArrayList();
        this.back = new ArrayList();
        this.handIds = new int[3];
        this.imageViews = new ArrayList();
    }

    public ArrayList<Card> getHand() {
        return this.hand;
    }

    public void setHand(ArrayList<Card> newHand) {
        this.hand = newHand;
    }

    public ArrayList<Card> getFront() {
        return this.front;
    }

    public ArrayList<Card> getMiddle() {
        return this.middle;
    }

    public ArrayList<Card> getBack() {
        return this.back;
    }

    public ArrayList<MyImageView> getImageViews() {
        return this.imageViews;
    }

    public double calcExpectedVal() {
        //if (this.getHand().size() != 0) return 0;
        double expectedVal = 0;
        setHandIds();
        expectedVal += findBackProb(this.getHandIds(2));
        expectedVal += findMidProb(this.getHandIds(1));
        expectedVal += findFrontProb(this.getHandIds(0));
        return expectedVal;
    }

    public double findBackProb(int handId) {
        double probability = 0;
        for (int i = 0; i < 9; i++) { //9 = number of distinct hands
            if (handId >= handDefaultId5[i]) {
                int maxDiff;
                if (i == 0) maxDiff = maxHandDefaultId5 - handDefaultId5[0];
                else maxDiff = (handDefaultId5[i - 1] - 1) - handDefaultId5[i];
                int currDiff = handId - handDefaultId5[i];
                double rateOfChange = (backHandHighProb[i] - backHandLowProb[i]) / maxDiff;
                probability = backHandLowProb[i] + currDiff * rateOfChange;
                return probability;
            }
        }
        return probability;
    }

    public double findMidProb(int handId) {
        double probability = 0;
        for (int i = 0; i < 9; i++) { //9 = number of distinct hands
            if (handId >= handDefaultId5[i]) {
                int maxDiff;
                if (i == 0) maxDiff = maxHandDefaultId5 - handDefaultId5[0];
                else maxDiff = (handDefaultId5[i - 1] - 1) - handDefaultId5[i];
                int currDiff = handId - handDefaultId5[i];
                double rateOfChange = (midHandHighProb[i] - midHandLowProb[i]) / maxDiff;
                probability = midHandLowProb[i] + currDiff * rateOfChange;
                return probability;
            }
        }
        return probability;
    }

    public double findFrontProb(int handId) {
        double probability = 0;
        for (int i = 0; i < 4; i++) { //4 = number of distinct hands
            if (handId >= handDefaultId3[i]) {
                int maxDiff;
                if (i == 0) maxDiff = maxHandDefaultId3 - handDefaultId3[0];
                else maxDiff = (handDefaultId3[i - 1] - 1) - handDefaultId3[i];
                int currDiff = handId - handDefaultId3[i];
                double rateOfChange = (frontHandHighProb[i] - frontHandLowProb[i]) / maxDiff;
                probability = frontHandLowProb[i] + currDiff * rateOfChange;
                return probability;
            }
        }
        return probability;
    }

    public int getHandIds(int index) {
        return handIds[index];
    }

    public int[] getHandIds() {
        return handIds;
    }

    public void setHandIds(int index, int newVal) {
        handIds[index] = newVal;
    }

    public void setHandIds(int index, ArrayList<Card> hand) {
        this.handIds[index] = findHandId5(hand);
    }

    public void setHandIds() {
        this.setHandIds(2, findHandId5(this.getBack()));
        this.setHandIds(1, findHandId5(this.getMiddle()));
        this.setHandIds(0, findHandId3(this.getFront()));
    }

    public int findHandId5(ArrayList<Card> hand) {
        //if (hand.size() != 5) return -1;
        sort(hand);

        //check fourkind, fullhouse, threekind, twopair, or pair
        Map<Integer, Integer> countByRank = new HashMap();
        for (Card card : hand) {
            countByRank.put(card.getRank(), countByRank.getOrDefault(card.getRank(), 0) + 1);
        }
        boolean hasPair = false;
        boolean hasThreeOfAKind = false;
        boolean hasFourOfAKind = false;
        for (int count : countByRank.values()) {
            if (count == 4) hasFourOfAKind = true;
            else if (count == 3) hasThreeOfAKind = true;
            else if (count == 2) hasPair = true;
        }

        //check straight and flush
        boolean isFlush = true;
        boolean isStraight = true;
        boolean isLowStraight = false;
        int tempHandSize = hand.size();
        for (int i = 0; i < tempHandSize - 1; i++) {
            if (hand.get(i).getSuit() != hand.get(i + 1).getSuit()) {
                isFlush = false;
            }
        }
        for (int i = 0; i < tempHandSize - 1; i++) {
            if (i == 0 && hand.get(hand.size() - 1).getRank() == 14) {
                if (hand.get(0).getRank() == 2) {
                    isLowStraight = true;
                    tempHandSize--;
                }
                else if (hand.get(0).getRank() != 10) {
                    isStraight = false;
                }
            }
            if (hand.get(i).getRank() + 1 != hand.get(i + 1).getRank()) {
                isStraight = false;
                isLowStraight = false;
            }
        }
        if (isFlush && isStraight) {
            int idOfHighestCard = hand.get(hand.size() - 1).getId();
            if (isLowStraight) idOfHighestCard = hand.get(hand.size() - 2).getId();
            //System.out.println("straight flush");
            return handDefaultId5[0] + (idOfHighestCard - 12);
        }
        else if (hasFourOfAKind) {
            int rankOfFourKind = 0;
            for (Card card : hand) {
                if (countByRank.get(card.getRank()) == 4) rankOfFourKind = card.getRank();
            }
            //System.out.println("four of a kind");
            return handDefaultId5[1] + (rankOfFourKind - 2);
        }
        else if (hasThreeOfAKind && hasPair) {
            int rankOfThreeKind = 0;
            for (Card card : hand) {
                if (countByRank.get(card.getRank()) == 3) rankOfThreeKind = card.getRank();
            }
            //System.out.println("full house");
            return handDefaultId5[2] + (rankOfThreeKind - 2);
        }
        else if (isFlush) {
            int idOfHighestCard = hand.get(hand.size() - 1).getId();
            //System.out.println("flush");
            return handDefaultId5[3] + (idOfHighestCard - 20);
        }
        else if (isStraight) {
            int idOfHighestCard = hand.get(hand.size() - 1).getId();
            if (isLowStraight) idOfHighestCard = hand.get(hand.size() - 2).getId();
            //System.out.println("straight");
            return handDefaultId5[4] + (idOfHighestCard - 12);
        }
        else if (hasThreeOfAKind) {
            int rankOfThreeKind = 0;
            for (Card card : hand) {
                if (countByRank.get(card.getRank()) == 3) rankOfThreeKind = card.getRank();
            }
            //System.out.println("three of a kind");
            return handDefaultId5[5] + (rankOfThreeKind - 2);
        }
        else if (hasPair && countByRank.size() == 3) {
            Card highestCard = null;
            for (Card card : hand) {
                if (countByRank.get(card.getRank()) == 2) {
                    if (highestCard == null || card.getId() > highestCard.getId()) highestCard = card;
                }
            }
            //System.out.println("two pair");
            return handDefaultId5[6] + 3 * (highestCard.getRank() - 3) + (highestCard.getSuit() - 1);
        }
        else if (hasPair && countByRank.size() == 4) {
            Card highestCard = null;
            for (Card card : hand) {
                if (countByRank.get(card.getRank()) == 2) {
                    if (highestCard == null || card.getId() > highestCard.getId()) highestCard = card;
                }
            }
            //System.out.println("pair");
            return handDefaultId5[7] + 3 * (highestCard.getRank() - 2) + (highestCard.getSuit() - 1);
        }
        else {
            //System.out.println("nothing");
            return handDefaultId5[8] + hand.get(hand.size() - 1).getId() - 20;
        }

    }

    public int findHandId3(ArrayList<Card> hand) {
        //if (hand.size() != 3) return -1;
        sort(hand);

        //check fourkind, fullhouse, threekind, twopair, or pair
        Map<Integer, Integer> countByRank = new HashMap();
        for (Card card : hand) {
            countByRank.put(card.getRank(), countByRank.getOrDefault(card.getRank(), 0) + 1);
        }
        boolean hasPair = false;
        boolean hasThreeOfAKind = false;
        for (int count : countByRank.values()) {
            if (count == 3) hasThreeOfAKind = true;
            else if (count == 2) hasPair = true;
        }

        if (hasThreeOfAKind) {
            int rankOfThreeKind = hand.get(0).getRank();
            //System.out.println("three of a kind");
            return handDefaultId3[0] + (rankOfThreeKind - 2);
        }
        else if (hasPair) {
            Card highestCard = null;
            for (Card card : hand) {
                if (countByRank.get(card.getRank()) == 2) {
                    if (highestCard == null || card.getId() > highestCard.getId()) highestCard = card;
                }
            }
            //System.out.println("pair");
            return handDefaultId3[2] + 3 * (highestCard.getRank() - 2) + (highestCard.getSuit() - 1);
        }
        else {
            //System.out.println("nothing");
            return handDefaultId3[3] + hand.get(hand.size() - 1).getId() - 8;
        }

    }

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





    public Hand getOptimalHand() {

        ArrayList<Card> cards = this.hand;
        sort(cards);
        Hand bestHand = new Hand(cards);
        double maxExpectedVal = 0;

        int[] indexArray = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        List<int[]> combinations = getCombinations(indexArray, 5);

        //every combination of 13choose5
        for (int[] combination : combinations) {
            Hand currHand = new Hand(cards);

            currHand.getBack().clear();
            for (int i = 0; i < 5; i ++) {
                Card card = cards.get(combination[i]);
                currHand.getBack().add(card);
            }
            currHand.setHandIds(2, findHandId5(currHand.getBack()));

            //every combination of 8choose5 remaining from the original hand
            int[] remaining = getRemaining(indexArray, combination);
            List<int[]> subCombinations = getCombinations(remaining, 5);
            for (int[] subCombination : subCombinations) {
                currHand.getMiddle().clear();
                for (int i = 0; i < 5; i ++) {
                    Card card = cards.get(subCombination[i]);
                    currHand.getMiddle().add(card);
                }
                currHand.setHandIds(1, findHandId5(currHand.getMiddle()));
                double middleExpectedVal = findMidProb(currHand.getHandIds(1));

                //3 cards leftover after the other 10 have been chosen
                List<int[]> leftovers = getCombinations(getRemaining(remaining, subCombination), 3);
                for (int[] leftover : leftovers) {
                    currHand.getFront().clear();
                    for (int i = 0; i < 3; i ++) {
                        Card card = cards.get(leftover[i]);
                        currHand.getFront().add(card);
                    }
                    currHand.setHandIds(0, findHandId3(currHand.getFront()));

                    //check if the hand is optimal
                    double finalExpectedVal = currHand.calcExpectedVal();
                    if (currHand.getHandIds(2) > currHand.getHandIds(1) &&
                            currHand.getHandIds(1) > currHand.getHandIds(0) &&
                            finalExpectedVal > maxExpectedVal) {
                        maxExpectedVal = finalExpectedVal;
                        bestHand = currHand.clone();
                    }
                }
            }
        }

        /*for (Card c : bestHand.getBack()) {
            System.out.print(c.toString() + ", ");
        }
        System.out.print(findBackProb(bestHand.getHandIds(2)) + " " + bestHand.getHandIds(2));
        System.out.println();
        for (Card c : bestHand.getMiddle()) {
            System.out.print(c.toString() + ", ");
        }
        System.out.print(findMidProb(bestHand.getHandIds(1)) + " " + bestHand.getHandIds(1));
        System.out.println();
        for (Card c : bestHand.getFront()) {
            System.out.print(c.toString() + ", ");
        }
        System.out.print(findFrontProb(bestHand.getHandIds(0)) + " " + bestHand.getHandIds(0));
        System.out.println();
        System.out.println("AHHHHH: " + maxExpectedVal + "Poop " + (findFrontProb(bestHand.getHandIds(0)) +
                findMidProb(bestHand.getHandIds(1)) + findBackProb(bestHand.getHandIds(2))));*/
        //bestHand.getHand().clear();

        return bestHand;

    }

    public boolean isValidHand() {
        if (this.getBack().size() != 5) return false;
        if (this.getMiddle().size() != 5) return false;
        if (this.getFront().size() != 3) return false;
        int backId = findHandId5(this.getBack());
        int middleId = findHandId5(this.getMiddle());
        int frontId = findHandId3(this.getFront());
        return (backId > middleId && middleId > frontId);
    }

    private List<int[]> getCombinations(int[] array, int k) {
        List<int[]> combinations = new ArrayList<>();
        int[] combination = new int[k];
        combinationUtil(array, combination, 0, array.length - 1, 0, k, combinations);
        return combinations;
    }

    private void combinationUtil(int[] array, int[] combination, int start, int end, int index, int k, List<int[]> combinations) {
        if (index == k) {
            combinations.add(combination.clone());
            return;
        }

        for (int i = start; i <= end && end - i + 1 >= k - index; i++) {
            combination[index] = array[i];
            combinationUtil(array, combination, i + 1, end, index + 1, k, combinations);
        }
    }

    private int[] getRemaining(int[] array, int[] combination) {
        int[] remaining = new int[array.length - combination.length];
        int index = 0;
        for (int i = 0; i < array.length; i++) {
            boolean found = false;
            for (int j = 0; j < combination.length; j++) {
                if (array[i] == combination[j]) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                remaining[index++] = array[i];
            }
        }
        return remaining;
    }

    public Hand clone() {
        Hand newHand = new Hand();

        for (Card card : this.hand) {
            newHand.getHand().add(new Card(card));
        }

        for (Card card : this.front) {
            newHand.getFront().add(new Card(card));
        }

        for (Card card : this.middle) {
            newHand.getMiddle().add(new Card(card));
        }

        for (Card card : this.back) {
            newHand.getBack().add(new Card(card));
        }

        for (int i = 0; i < 3; i++) {
            int id = this.handIds[i];
            newHand.setHandIds(i, id);
        }

        return newHand;
    }

    public Hand resetClone() {
        Hand newHand = new Hand();

        for (Card card : this.hand) {
            newHand.getHand().add(new Card(card));
        }

        for (Card card : this.front) {
            newHand.getHand().add(new Card(card));
        }

        for (Card card : this.middle) {
            newHand.getHand().add(new Card(card));
        }

        for (Card card : this.back) {
            newHand.getHand().add(new Card(card));
        }

        for (int i = 0; i < 3; i++) {
            int id = this.handIds[i];
            newHand.setHandIds(i, id);
        }

        return newHand;
    }

    public String handToString(int index) {
        if (index == 0) {
            if (handIds[index] >= handDefaultId3[0]) {
                return "Three of a Kind";
            }
            else if (handIds[index] >= handDefaultId3[1]) {
                return "Two Pair";
            }
            else if (handIds[index] >= handDefaultId3[2]) {
                return "One Pair";
            }
            else {
                String[] ranks = {"4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
                int rank = (handIds[index] - handDefaultId3[3]) / 4;
                return ranks[rank] + "-High";
            }
        }
        else {
            if (handIds[index] >= maxHandDefaultId5 - 3) {
                return "Royal Flush";
            }
            else if (handIds[index] >= handDefaultId5[0]) {
                return "Straight Flush";
            }
            else if (handIds[index] >= handDefaultId5[1]) {
                return "Four of a Kind";
            }
            else if (handIds[index] >= handDefaultId5[2]) {
                return "Full House";
            }
            else if (handIds[index] >= handDefaultId5[3]) {
                return "Flush";
            }
            else if (handIds[index] >= handDefaultId5[4]) {
                return "Straight";
            }
            else if (handIds[index] >= handDefaultId5[5]) {
                return "Three of a Kind";
            }
            else if (handIds[index] >= handDefaultId5[6]) {
                return "Two Pair";
            }
            else if (handIds[index] >= handDefaultId5[7]) {
                return "One Pair";
            }
            else {
                String[] ranks = {"7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
                int rank = (handIds[index]) / 4;
                return ranks[rank] + "-High";
            }
        }
    }


    //{218, 205, 192, 160, 120, 107, 71, 32, 0}
}
