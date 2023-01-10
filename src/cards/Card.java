package cards;

import java.io.Serializable;

public abstract class Card implements Serializable {
    public String text;

    public Card(String text) {
        this.text = text;
    }

    public String toString() {
        return text;
    }
}