package com.marcosavard.commons.game.poker;

import com.marcosavard.commons.debug.Console;
import com.marcosavard.commons.util.PseudoRandom;

import java.util.*;

public class PokerGameDemo {

    public static void main(String[] args) {
        Locale display = Locale.FRENCH;
        Random random = new PseudoRandom(3);
        List<PlayingCard> cards = PlayingCard.pick(25, random);
        Console.println(cards);

        Collections.sort(cards);

        List<PlayingCard.PokerHand> solution = PlayingCard.arrangeByPokerHands(cards);

        for (PlayingCard.PokerHand hand : solution) {
            Console.println(hand);
        }

        Console.println();
    }




}
