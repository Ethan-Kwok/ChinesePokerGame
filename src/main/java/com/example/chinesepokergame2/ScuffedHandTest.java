package com.example.chinesepokergame2;

import java.util.ArrayList;

public class ScuffedHandTest {
    public static void runTest() {
        test_straight_flush_high();
        test_straight_flush_low();
        //test_straight_flush_mid();
        test_four_kind_high();
        test_four_kind_low();
        test_full_house_high();
        test_full_house_low();
        test_flush_high();
        test_flush_low();
        test_straight_high();
        test_straight_low();
        test_three_kind_high();
        test_three_kind_low();
        test_two_pair_high();
        test_two_pair_low();
        test_pair_high();
        test_pair_low();
        test_high_card_high();
        test_high_card_low();
        test_three_kind_high3();
        test_three_kind_low3();
        test_pair_high3();
        test_pair_low3();
        test_high_card_high3();
        test_high_card_low3();
    }

    public static void test_straight_flush_high() {
        Card card1 = new Card(51);
        Card card2 = new Card(47);
        Card card3 = new Card(43);
        Card card4 = new Card(39);
        Card card5 = new Card(35);
        Hand playerHand = new Hand();
        ArrayList<Card> cardList = new ArrayList();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);
        cardList.add(card4);
        cardList.add(card5);

        int actual = playerHand.findHandId5(cardList);
        int expected = 257;
        if (expected == actual) System.out.println("true");
        else System.out.println(actual);

        double actual2 = playerHand.findBackProb(expected);
        double expected2 = 1;
        if (expected2 == actual2) System.out.println("true");
        else System.out.println(actual2);
    }

    public static void test_straight_flush_low() {
        Card card1 = new Card(0);
        Card card2 = new Card(4);
        Card card3 = new Card(8);
        Card card4 = new Card(12);
        Card card5 = new Card(48);
        Hand playerHand = new Hand();
        ArrayList<Card> cardList = new ArrayList();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);
        cardList.add(card4);
        cardList.add(card5);

        int actual = playerHand.findHandId5(cardList);
        int expected = 218;
        if (expected == actual) System.out.println("true");
        else System.out.println(actual);

        double actual2 = playerHand.findBackProb(expected);
        double expected2 = .9911;
        if (expected2 == actual2) System.out.println("true");
        else System.out.println(actual2);
    }

    /*public static void test_straight_flush_mid() {
        Card card1 = new Card(13);
        Card card2 = new Card(17);
        Card card3 = new Card(21);
        Card card4 = new Card(25);
        Card card5 = new Card(29);
        Hand playerHand = new Hand();
        ArrayList<Card> cardList = new ArrayList();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);
        cardList.add(card4);
        cardList.add(card5);

        int actual = playerHand.findHandId5(cardList);
        int expected = 258;
        if (expected == actual) System.out.println("true");
        else System.out.println(actual);
    }*/

    public static void test_four_kind_high() {
        Card card1 = new Card(13);
        Card card2 = new Card(48);
        Card card3 = new Card(49);
        Card card4 = new Card(50);
        Card card5 = new Card(51);
        Hand playerHand = new Hand();
        ArrayList<Card> cardList = new ArrayList();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);
        cardList.add(card4);
        cardList.add(card5);

        int actual = playerHand.findHandId5(cardList);
        int expected = 217;
        if (expected == actual) System.out.println("true");
        else System.out.println(actual);

        double actual2 = playerHand.findBackProb(expected);
        double expected2 = .9904;
        if (expected2 == actual2) System.out.println("true");
        else System.out.println(actual2);
    }

    public static void test_four_kind_low() {
        Card card1 = new Card(0);
        Card card2 = new Card(1);
        Card card3 = new Card(2);
        Card card4 = new Card(3);
        Card card5 = new Card(13);
        Hand playerHand = new Hand();
        ArrayList<Card> cardList = new ArrayList();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);
        cardList.add(card4);
        cardList.add(card5);

        int actual = playerHand.findHandId5(cardList);
        int expected = 205;
        if (expected == actual) System.out.println("true");
        else System.out.println(actual);

        double actual2 = playerHand.findBackProb(expected);
        double expected2 = .9663;
        if (expected2 == actual2) System.out.println("true");
        else System.out.println(actual2);
    }

    public static void test_full_house_high() {
        Card card1 = new Card(51);
        Card card2 = new Card(50);
        Card card3 = new Card(48);
        Card card4 = new Card(6);
        Card card5 = new Card(7);
        Hand playerHand = new Hand();
        ArrayList<Card> cardList = new ArrayList();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);
        cardList.add(card4);
        cardList.add(card5);

        int actual = playerHand.findHandId5(cardList);
        int expected = 204;
        if (expected == actual) System.out.println("true");
        else System.out.println(actual);

        double actual2 = playerHand.findBackProb(expected);
        double expected2 = .9645;
        if (expected2 == actual2) System.out.println("true");
        else System.out.println(actual2 + "AAA");
    }

    public static void test_full_house_low() {
        Card card1 = new Card(0);
        Card card2 = new Card(1);
        Card card3 = new Card(3);
        Card card4 = new Card(6);
        Card card5 = new Card(7);
        Hand playerHand = new Hand();
        ArrayList<Card> cardList = new ArrayList();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);
        cardList.add(card4);
        cardList.add(card5);

        int actual = playerHand.findHandId5(cardList);
        int expected = 192;
        if (expected == actual) System.out.println("true");
        else System.out.println(actual);

        double actual2 = playerHand.findBackProb(expected);
        double expected2 = .673;
        if (expected2 == actual2) System.out.println("true");
        else System.out.println(actual2);
    }

    public static void test_flush_high() {
        Card card1 = new Card(51);
        Card card2 = new Card(3);
        Card card3 = new Card(7);
        Card card4 = new Card(19);
        Card card5 = new Card(23);
        Hand playerHand = new Hand();
        ArrayList<Card> cardList = new ArrayList();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);
        cardList.add(card4);
        cardList.add(card5);

        int actual = playerHand.findHandId5(cardList);
        int expected = 191;
        if (expected == actual) System.out.println("true");
        else System.out.println(actual);

        double actual2 = playerHand.findBackProb(expected);
        double expected2 = .6537;
        if (expected2 == actual2) System.out.println("true");
        else System.out.println(actual2);
    }

    public static void test_flush_low() {
        Card card1 = new Card(0);
        Card card2 = new Card(4);
        Card card3 = new Card(8);
        Card card4 = new Card(12);
        Card card5 = new Card(20);
        Hand playerHand = new Hand();
        ArrayList<Card> cardList = new ArrayList();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);
        cardList.add(card4);
        cardList.add(card5);

        int actual = playerHand.findHandId5(cardList);
        int expected = 160;
        if (expected == actual) System.out.println("true");
        else System.out.println(actual);

        double actual2 = playerHand.findBackProb(expected);
        double expected2 = .3607;
        if (expected2 == actual2) System.out.println("true");
        else System.out.println(actual2);
    }

    public static void test_straight_high() {
        Card card1 = new Card(51);
        Card card2 = new Card(47);
        Card card3 = new Card(43);
        Card card4 = new Card(39);
        Card card5 = new Card(32);
        Hand playerHand = new Hand();
        ArrayList<Card> cardList = new ArrayList();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);
        cardList.add(card4);
        cardList.add(card5);

        int actual = playerHand.findHandId5(cardList);
        int expected = 159;
        if (expected == actual) System.out.println("true");
        else System.out.println(actual);

        double actual2 = playerHand.findBackProb(expected);
        double expected2 = .3605;
        if (expected2 == actual2) System.out.println("true");
        else System.out.println(actual2);
    }

    public static void test_straight_low() {
        Card card1 = new Card(1);
        Card card2 = new Card(4);
        Card card3 = new Card(8);
        Card card4 = new Card(12);
        Card card5 = new Card(51);
        Hand playerHand = new Hand();
        ArrayList<Card> cardList = new ArrayList();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);
        cardList.add(card4);
        cardList.add(card5);

        int actual = playerHand.findHandId5(cardList);
        int expected = 120;
        if (expected == actual) System.out.println("true");
        else System.out.println(actual);


        double actual2 = playerHand.findBackProb(expected);
        double expected2 = .1695;
        if (expected2 == actual2) System.out.println("true");
        else System.out.println(actual2);
    }

    public static void test_three_kind_high() {
        Card card1 = new Card(51);
        Card card2 = new Card(50);
        Card card3 = new Card(49);
        Card card4 = new Card(5);
        Card card5 = new Card(10);
        Hand playerHand = new Hand();
        ArrayList<Card> cardList = new ArrayList();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);
        cardList.add(card4);
        cardList.add(card5);

        int actual = playerHand.findHandId5(cardList);
        int expected = 119;
        if (expected == actual) System.out.println("true");
        else System.out.println(actual);


        double actual2 = playerHand.findBackProb(expected);
        double expected2 = .1542;
        if (expected2 == actual2) System.out.println("true");
        else System.out.println(actual2);
    }

    public static void test_three_kind_low() {
        Card card1 = new Card(0);
        Card card2 = new Card(1);
        Card card3 = new Card(2);
        Card card4 = new Card(5);
        Card card5 = new Card(10);
        Hand playerHand = new Hand();
        ArrayList<Card> cardList = new ArrayList();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);
        cardList.add(card4);
        cardList.add(card5);

        int actual = playerHand.findHandId5(cardList);
        int expected = 107;
        if (expected == actual) System.out.println("true");
        else System.out.println(actual);


        double actual2 = playerHand.findBackProb(expected);
        double expected2 = .1442;
        if (expected2 == actual2) System.out.println("true");
        else System.out.println(actual2);
    }

    public static void test_two_pair_high() {
        Card card1 = new Card(0);
        Card card2 = new Card(1);
        Card card3 = new Card(51);
        Card card4 = new Card(50);
        Card card5 = new Card(10);
        Hand playerHand = new Hand();
        ArrayList<Card> cardList = new ArrayList();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);
        cardList.add(card4);
        cardList.add(card5);

        int actual = playerHand.findHandId5(cardList);
        int expected = 106;
        if (expected == actual) System.out.println("true");
        else System.out.println(actual);


        double actual2 = playerHand.findBackProb(expected);
        double expected2 = .1436;
        if (expected2 == actual2) System.out.println("true");
        else System.out.println(actual2);
    }

    public static void test_two_pair_low() {
        Card card1 = new Card(0);
        Card card2 = new Card(1);
        Card card3 = new Card(4);
        Card card4 = new Card(5);
        Card card5 = new Card(10);
        Hand playerHand = new Hand();
        ArrayList<Card> cardList = new ArrayList();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);
        cardList.add(card4);
        cardList.add(card5);

        int actual = playerHand.findHandId5(cardList);
        int expected = 71;
        if (expected == actual) System.out.println("true");
        else System.out.println(actual);


        double actual2 = playerHand.findBackProb(expected);
        double expected2 = .0175;
        if (expected2 == actual2) System.out.println("true");
        else System.out.println(actual2);
    }

    public static void test_pair_high() {
        Card card1 = new Card(51);
        Card card2 = new Card(50);
        Card card3 = new Card(0);
        Card card4 = new Card(8);
        Card card5 = new Card(14);
        Hand playerHand = new Hand();
        ArrayList<Card> cardList = new ArrayList();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);
        cardList.add(card4);
        cardList.add(card5);

        int actual = playerHand.findHandId5(cardList);
        int expected = 70;
        if (expected == actual) System.out.println("true");
        else System.out.println(actual);


        double actual2 = playerHand.findBackProb(expected);
        double expected2 = .0171;
        if (expected2 == actual2) System.out.println("true");
        else System.out.println(actual2);
    }

    public static void test_pair_low() {
        Card card1 = new Card(0);
        Card card2 = new Card(1);
        Card card3 = new Card(51);
        Card card4 = new Card(8);
        Card card5 = new Card(14);
        Hand playerHand = new Hand();
        ArrayList<Card> cardList = new ArrayList();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);
        cardList.add(card4);
        cardList.add(card5);

        int actual = playerHand.findHandId5(cardList);
        int expected = 32;
        if (expected == actual) System.out.println("true");
        else System.out.println(actual);


        double actual2 = playerHand.findBackProb(expected);
        double expected2 = 0;
        if (expected2 == actual2) System.out.println("true");
        else System.out.println(actual2);
    }

    public static void test_high_card_high() {
        Card card1 = new Card(0);
        Card card2 = new Card(4);
        Card card3 = new Card(8);
        Card card4 = new Card(16);
        Card card5 = new Card(51);
        Hand playerHand = new Hand();
        ArrayList<Card> cardList = new ArrayList();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);
        cardList.add(card4);
        cardList.add(card5);

        int actual = playerHand.findHandId5(cardList);
        int expected = 31;
        if (expected == actual) System.out.println("true");
        else System.out.println(actual);


        double actual2 = playerHand.findBackProb(expected);
        double expected2 = 0;
        if (expected2 == actual2) System.out.println("true");
        else System.out.println(actual2);
    }

    public static void test_high_card_low() {
        Card card1 = new Card(0);
        Card card2 = new Card(4);
        Card card3 = new Card(8);
        Card card4 = new Card(13);
        Card card5 = new Card(20);
        Hand playerHand = new Hand();
        ArrayList<Card> cardList = new ArrayList();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);
        cardList.add(card4);
        cardList.add(card5);

        int actual = playerHand.findHandId5(cardList);
        int expected = 0;
        if (expected == actual) System.out.println("true");
        else System.out.println(actual);


        double actual2 = playerHand.findBackProb(expected);
        double expected2 = 0;
        if (expected2 == actual2) System.out.println("true");
        else System.out.println(actual2);
    }

    public static void test_three_kind_high3() {
        Card card1 = new Card(50);
        Card card2 = new Card(49);
        Card card3 = new Card(48);
        Hand playerHand = new Hand();
        ArrayList<Card> cardList = new ArrayList();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);

        int actual = playerHand.findHandId3(cardList);
        int expected = 119;
        if (expected == actual) System.out.println("true");
        else System.out.println(actual);


        double actual2 = playerHand.findFrontProb(expected);
        double expected2 = 1;
        if (expected2 == actual2) System.out.println("true");
        else System.out.println(actual2);
    }

    public static void test_three_kind_low3() {
        Card card1 = new Card(0);
        Card card2 = new Card(2);
        Card card3 = new Card(3);
        Hand playerHand = new Hand();
        ArrayList<Card> cardList = new ArrayList();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);

        int actual = playerHand.findHandId3(cardList);
        int expected = 107;
        if (expected == actual) System.out.println("true");
        else System.out.println(actual);


        double actual2 = playerHand.findFrontProb(expected);
        double expected2 = .9943;
        if (expected2 == actual2) System.out.println("true");
        else System.out.println(actual2);
    }

    public static void test_pair_high3() {
        Card card1 = new Card(50);
        Card card2 = new Card(51);
        Card card3 = new Card(5);
        Hand playerHand = new Hand();
        ArrayList<Card> cardList = new ArrayList();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);

        int actual = playerHand.findHandId3(cardList);
        int expected = 70;
        if (expected == actual) System.out.println("true");
        else System.out.println(actual);


        double actual2 = playerHand.findFrontProb(expected);
        double expected2 = .9935;
        if (expected2 == actual2) System.out.println("true");
        else System.out.println(actual2);
    }

    public static void test_pair_low3() {
        Card card1 = new Card(0);
        Card card2 = new Card(1);
        Card card3 = new Card(8);
        Hand playerHand = new Hand();
        ArrayList<Card> cardList = new ArrayList();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);

        int actual = playerHand.findHandId3(cardList);
        int expected = 32;
        if (expected == actual) System.out.println("true");
        else System.out.println(actual);


        double actual2 = playerHand.findFrontProb(expected);
        double expected2 = .4673;
        if (expected2 == actual2) System.out.println("true");
        else System.out.println(actual2);
    }

    public static void test_high_card_high3() {
        Card card1 = new Card(51);
        Card card2 = new Card(47);
        Card card3 = new Card(3);
        Hand playerHand = new Hand();
        ArrayList<Card> cardList = new ArrayList();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);

        int actual = playerHand.findHandId3(cardList);
        int expected = 31;
        if (expected == actual) System.out.println("true");
        else System.out.println(actual);


        double actual2 = playerHand.findFrontProb(expected);
        double expected2 = .4667;
        if (expected2 == actual2) System.out.println("true");
        else System.out.println(actual2);
    }

    public static void test_high_card_low3() {
        Card card1 = new Card(0);
        Card card2 = new Card(4);
        Card card3 = new Card(8);
        Hand playerHand = new Hand();
        ArrayList<Card> cardList = new ArrayList();
        cardList.add(card1);
        cardList.add(card2);
        cardList.add(card3);

        int actual = playerHand.findHandId3(cardList);
        int expected = -12;
        if (expected == actual) System.out.println("true");
        else System.out.println(actual);


        double actual2 = playerHand.findFrontProb(expected);
        double expected2 = 0;
        if (expected2 == actual2) System.out.println("true");
        else System.out.println(actual2);
    }

}
