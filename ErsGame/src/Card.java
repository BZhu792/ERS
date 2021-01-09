import javafx.scene.image.Image;

/**
 * @author Bo Han Zhu
 * @version 1.0
 */

public class Card {
    private String suit;
    private boolean special;
    private int value;
    private int cardsAfter;
    private Image image;

    /**
     * Constructor for a card.
     * @param suit A string representing the suit of the card (e.g. "spade").
     * @param special A boolean representing whether the card is special in this context or not.
     * @param value A int representing the number of the card, with A = 1 and K = 13.
     * @param cardsAfter A int representing how many cards need to be played afterwards in the context of ERS.
     * @param image The image of the card.
     */
    public Card(String suit, boolean special, int value, int cardsAfter, Image image) {
        this.suit = suit;
        this.special = special;
        this.value = value;
        this.cardsAfter = cardsAfter;
        this.image = image;
    }

    /**
     *
     * @return true if the card is special, false if it is not.
     */
    public boolean isSpecial() {
        return this.special;
    }

    /**
     *
     * @return number of cards after the card is played in ERS.
     */
    public int getCardsAfter() {
        return this.cardsAfter;
    }

    /**
     *
     * @return
     */
    public int getValue() {
        return this.value;
    }

    public Image getImage() {
        return this.image;
    }

    @Override
    public String toString() {
        return this.suit + " " + this.value;
    }

    @Override
    public boolean equals (Object obj) {
        if (obj == null || !(obj instanceof Card)) {
            return false;
        }
        Card other = (Card) obj;
        return this.value == other.value;
    }

}
