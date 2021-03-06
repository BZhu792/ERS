import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.image.Image;

/**
 * @author Bo Han Zhu
 * @version 1.0
 */
public class Deck {
    private Card c1;
    private Card c2;
    private Card c3;
    private Card c4;
    private Card c5;
    private Card c6;
    private Card c7;
    private Card c8;
    private Card c9;
    private Card c10;
    private Card c11;
    private Card c12;
    private Card c13;
    private Card c14;
    private Card c15;
    private Card c16;
    private Card c17;
    private Card c18;
    private Card c19;
    private Card c20;
    private Card c21;
    private Card c22;
    private Card c23;
    private Card c24;
    private Card c25;
    private Card c26;
    private Card c27;
    private Card c28;
    private Card c29;
    private Card c30;
    private Card c31;
    private Card c32;
    private Card c33;
    private Card c34;
    private Card c35;
    private Card c36;
    private Card c37;
    private Card c38;
    private Card c39;
    private Card c40;
    private Card c41;
    private Card c42;
    private Card c43;
    private Card c44;
    private Card c45;
    private Card c46;
    private Card c47;
    private Card c48;
    private Card c49;
    private Card c50;
    private Card c51;
    private Card c52;

    /**
     * Only constructor for a deck.
     * Creates a standard Deck object containing the 52 Cards.
     */
    public Deck() {
        this.c1 = new Card("spade", false, 2, 1, new Image("JPEG/2S.jpg"));
        this.c2 = new Card("spade", false, 3, 1, new Image("JPEG/3S.jpg"));
        this.c3 = new Card("spade", false, 4, 1, new Image("JPEG/4S.jpg"));
        this.c4 = new Card("spade", false, 5, 1, new Image("JPEG/5S.jpg"));
        this.c5 = new Card("spade", false, 6, 1, new Image("JPEG/6S.jpg"));
        this.c6 = new Card("spade", false, 7, 1, new Image("JPEG/7S.jpg"));
        this.c7 = new Card("spade", false, 8, 1, new Image("JPEG/8S.jpg"));
        this.c8 = new Card("spade", false, 9, 1, new Image("JPEG/9S.jpg"));
        this.c9 = new Card("spade", false, 10, 1, new Image("JPEG/10S.jpg"));
        this.c10 = new Card("spade", true, 11, 1, new Image("JPEG/JS.jpg"));
        this.c11 = new Card("spade", true, 12, 2, new Image("JPEG/QS.jpg"));
        this.c12 = new Card("spade", true, 13, 3, new Image("JPEG/KS.jpg"));
        this.c13 = new Card("spade", true, 1, 4, new Image("JPEG/AS.jpg"));
        this.c14 = new Card("heart", false, 2, 1, new Image("JPEG/2H.jpg"));
        this.c15 = new Card("heart", false, 3, 1, new Image("JPEG/3H.jpg"));
        this.c16 = new Card("heart", false, 4, 1, new Image("JPEG/4H.jpg"));
        this.c17 = new Card("heart", false, 5, 1, new Image("JPEG/5H.jpg"));
        this.c18 = new Card("heart", false, 6, 1, new Image("JPEG/6H.jpg"));
        this.c19 = new Card("heart", false, 7, 1, new Image("JPEG/7H.jpg"));
        this.c20 = new Card("heart", false, 8, 1, new Image("JPEG/8H.jpg"));
        this.c21 = new Card("heart", false, 9, 1, new Image("JPEG/9H.jpg"));
        this.c22 = new Card("heart", false, 10, 1, new Image("JPEG/10H.jpg"));
        this.c23 = new Card("heart", true, 11, 1, new Image("JPEG/JH.jpg"));
        this.c24 = new Card("heart", true, 12, 2, new Image("JPEG/QH.jpg"));
        this.c25 = new Card("heart", true, 13, 3, new Image("JPEG/KH.jpg"));
        this.c26 = new Card("heart", true, 1, 4, new Image("JPEG/AH.jpg"));
        this.c27 = new Card("diamond", false, 2, 1, new Image("JPEG/2D.jpg"));
        this.c28 = new Card("diamond", false, 3, 1, new Image("JPEG/3D.jpg"));
        this.c29 = new Card("diamond", false, 4, 1, new Image("JPEG/4D.jpg"));
        this.c30 = new Card("diamond", false, 5, 1, new Image("JPEG/5D.jpg"));
        this.c31 = new Card("diamond", false, 6, 1, new Image("JPEG/6D.jpg"));
        this.c32 = new Card("diamond", false, 7, 1, new Image("JPEG/7D.jpg"));
        this.c33 = new Card("diamond", false, 8, 1, new Image("JPEG/8D.jpg"));
        this.c34 = new Card("diamond", false, 9, 1, new Image("JPEG/9D.jpg"));
        this.c35 = new Card("diamond", false, 10, 1, new Image("JPEG/10D.jpg"));
        this.c36 = new Card("diamond", true, 11, 1, new Image("JPEG/JD.jpg"));
        this.c37 = new Card("diamond", true, 12, 2, new Image("JPEG/QD.jpg"));
        this.c38 = new Card("diamond", true, 13, 3, new Image("JPEG/KD.jpg"));
        this.c39 = new Card("diamond", true, 1, 4, new Image("JPEG/AD.jpg"));
        this.c40 = new Card("club", false, 2, 1, new Image("JPEG/2C.jpg"));
        this.c41 = new Card("club", false, 3, 1, new Image("JPEG/3C.jpg"));
        this.c42 = new Card("club", false, 4, 1, new Image("JPEG/4C.jpg"));
        this.c43 = new Card("club", false, 5, 1, new Image("JPEG/5C.jpg"));
        this.c44 = new Card("club", false, 6, 1, new Image("JPEG/6C.jpg"));
        this.c45 = new Card("club", false, 7, 1, new Image("JPEG/7C.jpg"));
        this.c46 = new Card("club", false, 8, 1, new Image("JPEG/8C.jpg"));
        this.c47 = new Card("club", false, 9, 1, new Image("JPEG/9C.jpg"));
        this.c48 = new Card("club", false, 10, 1, new Image("JPEG/10C.jpg"));
        this.c49 = new Card("club", true, 11, 1, new Image("JPEG/JC.jpg"));
        this.c50 = new Card("club", true, 12, 2, new Image("JPEG/QC.jpg"));
        this.c51 = new Card("club", true, 13, 3, new Image("JPEG/KC.jpg"));
        this.c52 = new Card("club", true, 1, 4, new Image("JPEG/AC.jpg"));

    }

    /**
     * Shuffles a Deck object randomly and transforms it into an ArrayList for easier usage.
     * @return ArrayList of Cards that is shuffled.
     */
    public ArrayList<Card> toShuffledArrayList() {
        ArrayList<Card> out = new ArrayList<Card>();
        out.add(this.c1);
        out.add(this.c2);
        out.add(this.c3);
        out.add(this.c4);
        out.add(this.c5);
        out.add(this.c6);
        out.add(this.c7);
        out.add(this.c8);
        out.add(this.c9);
        out.add(this.c10);
        out.add(this.c11);
        out.add(this.c12);
        out.add(this.c13);
        out.add(this.c14);
        out.add(this.c15);
        out.add(this.c16);
        out.add(this.c17);
        out.add(this.c18);
        out.add(this.c19);
        out.add(this.c20);
        out.add(this.c21);
        out.add(this.c22);
        out.add(this.c23);
        out.add(this.c24);
        out.add(this.c25);
        out.add(this.c26);
        out.add(this.c27);
        out.add(this.c28);
        out.add(this.c29);
        out.add(this.c30);
        out.add(this.c31);
        out.add(this.c32);
        out.add(this.c33);
        out.add(this.c34);
        out.add(this.c35);
        out.add(this.c36);
        out.add(this.c37);
        out.add(this.c38);
        out.add(this.c39);
        out.add(this.c40);
        out.add(this.c41);
        out.add(this.c42);
        out.add(this.c43);
        out.add(this.c44);
        out.add(this.c45);
        out.add(this.c46);
        out.add(this.c47);
        out.add(this.c48);
        out.add(this.c49);
        out.add(this.c50);
        out.add(this.c51);
        out.add(this.c52);
        Collections.shuffle(out);
        return out;
    }
}
