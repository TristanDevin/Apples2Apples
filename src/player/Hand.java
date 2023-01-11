package player;

import java.util.*;

import cards.Card;

public class Hand extends ArrayList<Card> {

    public Hand() {
        super();
    }

    public void addCard(Card card) {
        this.add(card);
    }

    public void removeCard(Card card) {
        this.remove(card);
    }

    

}
