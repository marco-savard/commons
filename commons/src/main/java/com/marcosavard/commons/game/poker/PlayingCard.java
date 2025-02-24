package com.marcosavard.commons.game.poker;

import com.marcosavard.commons.lang.StringUtil;

import java.util.*;

public class PlayingCard implements Comparable<PlayingCard> {

    public static int computeScore(List<PokerHand> hands) {
        int total = 0;

        for (PlayingCard.PokerHand pokerHand : hands) {
            total += pokerHand.getHand().getPoints();
        }

        return total;
    }

    public enum Suit {
        SPADE(0x2660),
        HEART(0x2665),
        DIAMOND(0x2666),
        CLUB(0x2663);
        private int codePoint;

        Suit(int codePoint) {
            this.codePoint = codePoint;
        }
    }

    public enum Hand {
        ROYAL_FLUSH(5, 100, "Quinte royale"),
        STRAIGHT_FLUSH(5, 75,"Quinte"),
        FOUR_OF_A_KIND(4, 50,"Carré"),
        FULL_HOUSE(5, 25, "Full"),
        FLUSH(5, 20, "Couleur"),
        STRAIGHT(5, 15,"Suite"),
        THREE_OF_A_KIND(3, 10, "Brelan"),
        TWO_PAIR(4, 5, "Double pair"),
        PAIR(2, 2,"Pair"),
        HIGH_CARD(1, 1, "Carte haute");

        private int cardCount;
        private int points;
        private String frName;

        Hand(int cardCount, int points, String frName) {
            this.cardCount = cardCount;
            this.points = points;
            this.frName = frName;

        }

        public int getCardCount() {
            return cardCount;
        }

        public int getPoints() {
            return points;
        }

        public String getDisplayName(Locale display) {
            String displayName;

            if ("fr".equals(display.getLanguage())) {
                displayName = this.frName;
            } else {
                displayName = StringUtil.capitalize(this.name().replace('_', ' ').toLowerCase(display));
            }

            return displayName;
        }
    }

    private static List<PlayingCard> deck = null;
    private int value;
    private String valueText;
    private Suit suit;

    public static List<PlayingCard.PokerHand> arrangeByPokerHands(List<PlayingCard> cards) {
        List<PlayingCard.PokerHand> arrangedCards = new ArrayList<>();
        List<PlayingCard> remaining = new ArrayList<>(cards);

        for (int i=0; i<5; i++) {
            PlayingCard.PokerHand hand = PlayingCard.findHigherHand(remaining);
            arrangedCards.add(hand);
            remaining.removeAll(hand.getCards());
        }

        for (PlayingCard.PokerHand hand : arrangedCards) {
            int count = 5 - hand.getCards().size();
            for (int i=0; i<count; i++) {
                PlayingCard card = remaining.remove(0);
                hand.getCards().add(card);
            }
        }

        return arrangedCards;
    }

    public static List<PlayingCard> getCardDeck() {
        if (deck == null) {
            List<PlayingCard> cards = new ArrayList<>();

            for (PlayingCard.Suit suit : PlayingCard.Suit.values()) {
                for (int i=1; i<=13; i++) {
                    PlayingCard pc = PlayingCard.of(i, suit);
                    cards.add(pc);
                }
            }

            deck = Collections.unmodifiableList(cards);
        }

        return deck;
    }

    public static List<PlayingCard> pick(int count, Random random) {
        List<PlayingCard> deck = getCardDeck();
        List<PlayingCard> picks = new ArrayList<>();
        count = Math.min(count, deck.size());

        do {
            int idx = random.nextInt(deck.size());
            PlayingCard card = deck.get(idx);

            if (! picks.contains(card)) {
                picks.add(card);
            }
        } while (picks.size() < count);

        return picks;
    }

    @Override
    public int compareTo(PlayingCard other) {
        return this.getRank() - other.getRank();
    }

    public Suit getSuit() {
        return suit;
    }

    private int getRank() {
        return suit.ordinal() * 13 + ((value == 1) ? 14 : value);
    }



    public static PlayingCard of(int value, Suit suit) {
        return new PlayingCard(value, suit);
    }

    private PlayingCard(int value, Suit suit) {
        this.value = (value - 1) % 13 + 1;
        this.suit = suit;

        this.valueText = switch (this.value) {
            case 1 -> "A";
            case 11 -> "J";
            case 12 -> "Q";
            case 13 -> "K";
            default -> Integer.toString(this.value);
        };
    }

    @Override
    public boolean equals(Object other) {
        boolean equal = false;

        if (other instanceof PlayingCard otherPlayingCard) {
            equal = otherPlayingCard.getRank() == this.getRank();
        }

        return equal;
    }

    @Override
    public String toString() {
        return valueText + Character.toString(suit.codePoint);
    }

    public String getName() {
        return Character.getName(getCodePoint());
    }

    public int getCodePoint() {
        int idx = (value >= 12) ? value + 1 : value;
        return switch (suit) {
            case SPADE -> 0x1F0A0 + idx;
            case HEART -> 0x1F0B0 + idx;
            case DIAMOND -> 0x1F0C0 + idx;
            case CLUB -> 0x1F0D0 + idx;
        };
    }

    public String getUnicode() {
        return  Character.toString(getCodePoint());
    }

    public static PokerHand findHigherHand(List<PlayingCard> cards) {
        List<PlayingCard> handCards = null;
        Hand foundHand = null;
        
        for (Hand hand : Hand.values()) {
            handCards = findPokerHand(cards, hand); 
            
            if (! handCards.isEmpty()) {
                foundHand = hand;
                break;
            }
        }
        
        return (foundHand == null) ? null : PokerHand.of(handCards, foundHand);
    }

    public static  List<PlayingCard> findPokerHand(List<PlayingCard> cards, Hand hand) {
        List<PlayingCard> pokerHand = new ArrayList<>();

        if (hand == Hand.ROYAL_FLUSH) {
            pokerHand = findRoyalFlush(cards);
        } else if (hand == Hand.STRAIGHT_FLUSH) {
            pokerHand = findStraightFlush(cards);
        } else if (hand == Hand.FOUR_OF_A_KIND) {
            pokerHand = findOfAKind(cards, 4);
        } else if (hand == Hand.FULL_HOUSE) {
            pokerHand = findFullHouse(cards);
        } else if (hand == Hand.FLUSH) {
            pokerHand = findFlush(cards);
        } else if (hand == Hand.STRAIGHT) {
            pokerHand = findStraight(cards);
        } else if (hand == Hand.THREE_OF_A_KIND) {
            pokerHand = findOfAKind(cards, 3);
        } else if (hand == Hand.TWO_PAIR) {
            pokerHand = findTwoPairs(cards);
        } else if (hand == Hand.PAIR) {
            pokerHand = findOfAKind(cards, 2);
        } else if (hand == Hand.HIGH_CARD) {
            pokerHand = List.of(findHighCard(cards));
        }

        return pokerHand;
    }

    private static List<PlayingCard> findFullHouse(List<PlayingCard> cards) {
        List<PlayingCard> trio = findOfAKind(cards, 3);
        List<PlayingCard> remaining = new ArrayList<>(cards);
        remaining.removeAll(trio);
        List<PlayingCard> pair = findOfAKind(remaining, 2);
        List<PlayingCard> pokerHand = new ArrayList<>();

        if (! trio.isEmpty() && ! pair.isEmpty()) {
            pokerHand.addAll(trio);
            pokerHand.addAll(pair);
        }

        return pokerHand;
    }


    private static List<PlayingCard> findTwoPairs(List<PlayingCard> cards) {
        List<PlayingCard> firstPair = findOfAKind(cards, 2);
        List<PlayingCard> remaining = new ArrayList<>(cards);
        remaining.removeAll(firstPair);
        List<PlayingCard> secondPair = findOfAKind(remaining, 2);
        List<PlayingCard> pokerHand = new ArrayList<>();

        if (! firstPair.isEmpty() && ! secondPair.isEmpty()) {
            pokerHand.addAll(firstPair);
            pokerHand.addAll(secondPair);
        }

        return pokerHand;
    }

    public static List<PlayingCard> findRoyalFlush(List<PlayingCard> cards) {
        List<PlayingCard> royalFlush = new ArrayList<>();

        for (PlayingCard.Suit suit : PlayingCard.Suit.values()) {
            royalFlush.addAll(findRoyalFlush(cards, suit));
        }

        Collections.sort(royalFlush);
        return royalFlush;
    }

    public static List<PlayingCard> findRoyalFlush(List<PlayingCard> cards, Suit suit) {
        List<PlayingCard> royalFlush = new ArrayList<>();

        for (int value = 14; value >=10; value--) {
            PlayingCard card = PlayingCard.of(value, suit);
            if (cards.contains(card)) {
                royalFlush.add(card);
            }
         }

        return royalFlush.size() == 5 ? royalFlush : List.of();
    }

    public static List<PlayingCard> findStraightFlush(List<PlayingCard> cards) {
        List<PlayingCard> royalFlush = new ArrayList<>();

        for (PlayingCard.Suit suit : PlayingCard.Suit.values()) {
            royalFlush.addAll(findStraightFlush(cards, suit));
        }

        return royalFlush;
    }

    public static List<PlayingCard> findStraightFlush(List<PlayingCard> cards, Suit suit) {
        List<PlayingCard> straightFlush = new ArrayList<>();

        for (int value = 14; value >=2; value--) {
            PlayingCard card = PlayingCard.of(value, suit);
            if (cards.contains(card)) {
                straightFlush.add(card);
            } else {
                straightFlush.clear();
            }

            if (straightFlush.size() >= 5) {
                break;
            }
        }

        Collections.sort(straightFlush);
        return straightFlush.size() == 5 ? straightFlush : new ArrayList<>();
    }

    public static List<PlayingCard> findFlush(List<PlayingCard> cards) {
        List<PlayingCard> flush = new ArrayList<>();

        for (PlayingCard.Suit suit : PlayingCard.Suit.values()) {
            flush.addAll(findFlush(cards, suit));

            if (! flush.isEmpty()) {
                break;
            }
        }

        return flush;
    }

    public static List<PlayingCard> findFlush(List<PlayingCard> cards, Suit suit) {
        List<PlayingCard> flush = new ArrayList<>();

        for (int value = 14; value >=2; value--) {
            PlayingCard card = PlayingCard.of(value, suit);
            if (cards.contains(card)) {
                flush.add(card);
            }

            if (flush.size() >= 5) {
                break;
            }
        }

        Collections.sort(flush);
        return flush.size() == 5 ? flush : List.of();
    }

    public static List<PlayingCard> findOfAKind(List<PlayingCard> cards, int kind) {
        List<PlayingCard> allOfAKind = new ArrayList<>();

        for (int value = 14; value >=2; value--) {
            List<PlayingCard> ofAKind = new ArrayList<>();

            for (PlayingCard.Suit suit : PlayingCard.Suit.values()) {
                PlayingCard card = PlayingCard.of(value, suit);

                if (cards.contains(card)) {
                    ofAKind.add(card);
                }
            }

            if (ofAKind.size() == kind) {
                allOfAKind.addAll(ofAKind);
                break;
            } else {
                allOfAKind.clear();
            }
        }

        return allOfAKind;
    }

    public static List<PlayingCard> findStraight(List<PlayingCard> cards) {
        List<PlayingCard> straight = new ArrayList<>();

        for (int value = 14; value >=2; value--) {
            for (PlayingCard.Suit suit : PlayingCard.Suit.values()) {
                PlayingCard card = PlayingCard.of(value, suit);
                if (cards.contains(card)) {
                    straight.add(card);
                    break;
                }
            }

            if (straight.size() >= 5) {
                break;
            } else {
                straight.clear();
            }
        }

        Collections.reverse(straight);
        return straight.size() == 5 ? straight : new ArrayList<>();
    }

    private static PlayingCard findHighCard(List<PlayingCard> cards) {
        PlayingCard highCard = null;

        for (int value = 14; value >=2; value--) {
            for (PlayingCard.Suit suit : PlayingCard.Suit.values()) {
                PlayingCard card = PlayingCard.of(value, suit);
                if (cards.contains(card)) {
                    highCard = card;
                    break;
                }
            }

            if (highCard != null) {
                break;
            }
        }

        return highCard;
    }


    public static class PokerHand {
        private List<PlayingCard> handCards = new ArrayList<>();
        private Hand foundHand;

        public PokerHand(List<PlayingCard> handCards, Hand foundHand) {
            this.handCards.addAll(handCards);
            this.foundHand = foundHand;
        }

        public static PokerHand of(List<PlayingCard> handCards, Hand foundHand) {
            return new PokerHand(handCards, foundHand);
        }

        public Hand getHand() {
            return foundHand;
        }

        public List<PlayingCard> getCards() {
            return handCards;
        }

        @Override
        public String toString() {
            return String.join(" ", foundHand.toString(), handCards.toString());
        }


    }

}
