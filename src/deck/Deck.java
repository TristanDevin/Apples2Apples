package deck;

import java.io.*;
import java.util.*;

import cards.*;
import utils.FileReader;

public class Deck extends ArrayList<Card> {

    public Deck(String file, String type) {
        createDeck(file, type);
    }

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
}
