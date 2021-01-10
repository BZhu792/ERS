import java.util.ArrayList;

/**
 * @author Bo Han Zhu
 * @version 1.0
 */
public class Player {
    private String name;
    private ArrayList<Card> cards;
    private boolean turn;

    /**
     * Only constructor for a Player.
     * @param name A string representing the name of the Player.
     * @param cards An ArrayList of Cards a Player holds.
     * @param turn A boolean representing if it is this Player's turn or not.
     */
    public Player(String name, ArrayList<Card> cards, boolean turn) {
        this.name = name;
        this.cards = cards;
        this.turn = turn;
    }

    /**
     *
     * @return how many cards the Player has.
     */
    public int howManyCards() {
        return this.cards.size();
    }

    /**
     *
     * @return true if it is the Player's turn, false otherwise.
     */
    public boolean isTurn() {
        return this.turn;
    }

    /**
     *
     * @param turn A boolean value determining the Player's turn.
     */
    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    /**
     * Plays a card from the bottom of the Player's deck to the top of the pile.
     * NOTE: Player always plays from the bottom and adds to the top.
     * @param pile an ArrayList of Cards representing the current pile.
     */
    public void playCard(ArrayList<Card> pile) {
        Card temp = this.cards.remove(this.cards.size() - 1);
        pile.add(temp);
    }

    /**
     * Burns a card from the bottom of the Player's deck to the bottom of the pile.
     * NOTE: Player always plays from the bottom and adds to the top.
     * @param burnPile an ArrayList of Cards representing the burn pile.
     */
    public void burnCard(ArrayList<Card> burnPile) {
        Card temp = this.cards.remove(this.cards.size() - 1);
        burnPile.add(0, temp);
    }

    /**
     * Appends both the burn pile and the pile to the top of the Player's deck.
     * NOTE: Player always plays from the bottom and adds to the top.
     * @param pile an ArrayList of Cards representing the current pile.
     * @param burnPile an ArrayList of Cards representing the burn pile.
     */
    public void takePile(ArrayList<Card> pile, ArrayList<Card> burnPile) {
        this.cards.addAll(0, burnPile);
        this.cards.addAll(0, pile);
        burnPile.clear();
        pile.clear();
    }
}
