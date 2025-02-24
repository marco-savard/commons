package com.marcosavard.library.poi.word;

import com.marcosavard.commons.game.poker.PlayingCard;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class PokerSolitaire {
    private static final String BLACK = "000000";
    private static final String RED = "DF0000";
    private static final String GREY = "E0E0E0";
    private static final String PINK = "FFE0E0";

    private List<PlayingCard> pickedCards;
    private List<PlayingCard.PokerHand> solution;
    private int pokerScore;

    public PokerSolitaire(Random random) {
        pickedCards = PlayingCard.pick(25, random);
        solution = PlayingCard.arrangeByPokerHands(pickedCards);
        pokerScore = PlayingCard.computeScore(solution);
    }

    public void printTitle(XWPFDocument document) {
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setFontSize(18);
        run.setText("Carré de poker");
        run.addCarriageReturn();
    }

    public void printInstructions(XWPFDocument document) {
        XWPFParagraph paragraph = document.createParagraph();
        String text = "Identifier les plus hautes mains de poker parmi les cartes suivantes. Placer la meilleure main dans la première rangée. ";
        text = text + "Placer les autres mains dans les rangées inférieures.";
        XWPFRun run = paragraph.createRun();
        run.setFontSize(10);
        run.setText(text);
        run.addCarriageReturn();
        run.addCarriageReturn();

        text = "Les mains possibles sont la pair (deux cartes de même rang), le brelan (trois cartes de même rang), ";
        text = text + "le carré (quatre cartes de même rang), le full (brelan et pair), la couleur (cinq cartes de même couleur), ";
        text = text + "la suite (cinq cartes de rang consécutif, peu importe la couleur), ";
        text = text + "la quinte (cinq cartes de rang consécutif, de même couleur), ";
        text = text + "et la quinte royale (cinq cartes du 10 à l'as, de même couleur). ";
        run = paragraph.createRun();
        run.setFontSize(10);
        run.setText(text);
        run.addCarriageReturn();
        run.addCarriageReturn();

        text = "La pair donne 2 points, deux pairs 5 points, le brelan 10 points, la suite 15 points, la couleur 20 points, ";
        text = text + "le full 25 points, le carré 50 points, la quinte 75 points et la quinte royale 100 points.";
        run = paragraph.createRun();
        run.setFontSize(10);
        run.setText(text);
        run.addCarriageReturn();
        run.addCarriageReturn();

        text = MessageFormat.format("Objectif avec ce jeu : {0} points.", Integer.toString(pokerScore));
        run = paragraph.createRun();
        run.setFontSize(10);
        run.setText(text);
        run.addCarriageReturn();

    }

    public void printCards(XWPFDocument document) {
        XWPFParagraph paragraph = document.createParagraph();

        for (int i=0; i<5; i++) {
            List<PlayingCard> row = pickedCards.subList(i*5, i*5+5);
            printRow(paragraph, row);
        }
    }

    private void printRow(XWPFParagraph paragraph, List<PlayingCard> row) {
        for (int i=0; i<row.size(); i++) {
            PlayingCard card = row.get(i);
            PlayingCard.Suit suit = card.getSuit();
            boolean greyed = false;
            String color = toColor(suit, greyed);
            String text = Character.toString(card.getCodePoint()) + " ";

            XWPFRun run = paragraph.createRun();
            run.setFontSize(64);
            run.setColor(color);
            run.setText(text);
        }

        XWPFRun run = paragraph.createRun();
        run.addCarriageReturn();
    }

    private static String toColor(PlayingCard.Suit suit, boolean greyed) {
        boolean isRed = (suit == PlayingCard.Suit.HEART) ||  (suit == PlayingCard.Suit.DIAMOND);
        return isRed ? (greyed ? PINK : RED) : (greyed ? GREY : BLACK);
    }

    public void printSolution(XWPFParagraph paragraph, Locale display) {
        for (PlayingCard.PokerHand hand : solution) {
            printSolutionRow(paragraph, hand, display);
        }
    }

    private static void printSolutionRow(XWPFParagraph paragraph, PlayingCard.PokerHand pokerHand, Locale display) {
        List<PlayingCard> cards = pokerHand.getCards();
        PlayingCard.Hand hand = pokerHand.getHand();
        int count = hand.getCardCount();

        for (int i=0; i<cards.size(); i++) {
            PlayingCard card = cards.get(i);
            PlayingCard.Suit suit = card.getSuit();
            boolean greyed = i >= count;
            String color = toColor(suit, greyed);
            String text = Character.toString(card.getCodePoint()) + " ";

            XWPFRun run = paragraph.createRun();
            run.setFontSize(48);
            run.setColor(color);
            run.setText(text);
        }

        int points = hand.getPoints();
        String score = (points == 1) ? "1 point" : points + " points";
        String text = MessageFormat.format("{0} ({1})", hand.getDisplayName(display), score);
        XWPFRun run = paragraph.createRun();
        run.setColor(BLACK);
        run.setText(text);
        run.addCarriageReturn();
    }

    public int getScore() {
        return pokerScore;
    }
}
