package cards;

import java.io.Serializable;

public abstract class Card implements Serializable {
         String text;

    public Card(String text) {
        this.text = text;
    }

    
    /** 
     * @return String
     */
    public String toString() {
        return text;
    }
}