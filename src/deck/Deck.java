package deck;

import java.util.*;

import cards.*;
import utils.FileReader;


//Extending ArrayList<Card> to create a deck of cards

public class Deck extends ArrayList<Card>{

    public Deck(String file, String type) {
        createDeck(file, type);
    }

    
    /** 
     * @param file
     * @param type
     */
    public void createDeck(String file, String type) {

        FileReader fr = new FileReader();
        ArrayList<String> cardStrings = fr.scan(file);
        for (String s : cardStrings) {
            switch (type) {
                case "red":
                    this.add(CardFactory.createRedApple(s));
                    break;
                case "green":
                    this.add(CardFactory.createGreenApple(s));
                    break;
                default:
                    System.out.println("Invalid deck type");
                    break;
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(this);
    }

    
    /** 
     * @return Card
     */
    public Card draw() {
        return this.remove(0);
    }

    
    /** 
     * @param nb
     * @return ArrayList<Card>
     */
    public ArrayList<Card> drawMany(int nb) {
        ArrayList<Card> cards = new ArrayList<Card>();
        for (int i = 0; i < nb; i++) {
            cards.add(this.remove(0));
        }
        return cards;
    }
}
