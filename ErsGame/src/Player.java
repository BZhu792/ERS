import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> cards;
    private boolean turn;

    public Player(String name, ArrayList<Card> cards, boolean turn) {
        this.name = name;
        this.cards = cards;
        this.turn = turn;
    }

    public int howManyCards() {
        return this.cards.size();
    }

    public boolean isTurn() {
        return this.turn;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public void playCard(ArrayList<Card> pile) {
        Card temp = this.cards.remove(this.cards.size() - 1);
        pile.add(temp);
    }

    public void burnCard(ArrayList<Card> burnPile) {
        Card temp = this.cards.remove(this.cards.size() - 1);
        burnPile.add(0, temp);
    }

    public void takePile(ArrayList<Card> pile, ArrayList<Card> burnPile) {
        this.cards.addAll(0, burnPile);
        this.cards.addAll(0, pile);
        burnPile.clear();
        pile.clear();
    }
}
